package site.woulduduo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.WebUtils;
import site.woulduduo.dto.request.login.LoginRequestDTO;
import site.woulduduo.dto.request.page.AdminSearchType;
import site.woulduduo.dto.request.page.UserSearchType;
import site.woulduduo.dto.request.user.UserCommentRequestDTO;
import site.woulduduo.dto.request.user.UserModifyRequestDTO;
import site.woulduduo.dto.request.user.UserRegisterRequestDTO;
import site.woulduduo.dto.response.ListResponseDTO;
import site.woulduduo.dto.response.page.PageResponseDTO;
import site.woulduduo.dto.response.user.*;
import site.woulduduo.dto.riot.LeagueV4DTO;
import site.woulduduo.dto.riot.MatchV5DTO;
import site.woulduduo.dto.riot.MostChampInfo;
import site.woulduduo.entity.Accuse;
import site.woulduduo.entity.Board;
import site.woulduduo.entity.User;
import site.woulduduo.entity.UserProfile;
import site.woulduduo.enumeration.Gender;
import site.woulduduo.enumeration.LoginResult;
import site.woulduduo.enumeration.Tier;
import site.woulduduo.exception.NoRankException;
import site.woulduduo.repository.*;
import site.woulduduo.util.LoginUtil;
import site.woulduduo.dto.request.page.PageDTO;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import site.woulduduo.dto.response.login.LoginUserResponseDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static java.util.stream.Collectors.toList;
import static site.woulduduo.enumeration.LoginResult.*;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserQueryDSLRepositoryCustom userQueryDSLRepositoryCustom;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final AccuseRepository accuseRepository;
    private final ReplyRepository replyRepository;
    private final PasswordEncoder passwordEncoder;
    private final RiotApiService riotApiService;
    private final MatchingRepository matchingRepository;
    private final FollowRepository followRepository;
    private final UserProfileRepository userProfileRepository;
    private final MatchingService matchingService;

    final String id = "abc1234";

    public void register(UserRegisterRequestDTO dto) {

        // 이메일 중복 검사
        if (userRepository.countByUserEmail(dto.getUserEmail()) > 0) {
            throw new IllegalArgumentException("이미 등록된 이메일입니다.");
        }

        // 닉네임 중복 검사
        if (userRepository.countByUserNickname(dto.getUserNickname()) > 0) {
            throw new IllegalArgumentException("이미 등록된 닉네임입니다.");
        }

        // 소환사 아이디 중복 검사
        if (userRepository.countByLolNickname(dto.getLolNickname()) > 0) {
            throw new IllegalArgumentException("이미 등록된 롤 닉네임입니다.");
        }

        // 회원 정보 저장

        User user = User.builder()
                .userAccount(dto.getUserEmail())
                .userNickname(dto.getUserNickname())
                .userPassword(passwordEncoder.encode(dto.getUserPassword()))
                .userBirthday(dto.getUserBirthday())
                .userInstagram(dto.getUserInstagram().isEmpty() ? null : dto.getUserInstagram())
                .userTwitter(dto.getUserTwitter().isEmpty() ? null : dto.getUserTwitter())
                .userFacebook(dto.getUserFacebook().isEmpty() ? null : dto.getUserFacebook())
                .lolNickname(dto.getLolNickname())
                .userGender(dto.getUserGender() == Gender.M ? Gender.M : Gender.F)
                .lolTier(riotApiService.getTier(dto.getLolNickname()))
                .userRecentLoginDate(LocalDateTime.now())
                .build();

        userRepository.save(user);

        // 프로필 사진 저장
        String[] profileImagePaths = dto.getProfileImagePaths();
        if (profileImagePaths != null) {
            for (String imagePath : profileImagePaths) {
                if (imagePath != null) {
                    UserProfile userProfile = UserProfile.builder()
                            .user(user)
                            .profileImage(imagePath)
                            .build();
                    userProfileRepository.save(userProfile);
                }
            }
        }



        log.info("회원 가입이 완료되었습니다.");
    }

    // 중복검사 서비스 처리
    public int checkSignUpValue(String type, String keyword) {
        int flagNum;

        switch (type) {
            case "email":
                flagNum = userRepository.countByUserEmail(keyword);
                break;
            case "nickname":
                flagNum = userRepository.countByUserNickname(keyword);
                break;
            case "lolNickname":
                flagNum = userRepository.countByLolNickname(keyword);
                break;
            default:
                throw new IllegalArgumentException("잘못된 검사 타입입니다.");
        }

        return flagNum;
    }

    // 로그인 검증
    public LoginResult authenticate(LoginRequestDTO dto,
                                    HttpSession session,
                                    HttpServletResponse response) {

        User foundUser = userRepository.findByUserAccount(dto.getUserAccount());

        // 회원가입 여부 확인
        if (foundUser == null) {
            log.info("{} - 회원가입 안함", dto.getUserAccount());
            return NO_ACC;
        }
        // 비밀번호 일치 확인
        if (!passwordEncoder.matches(dto.getUserPassword(), foundUser.getUserPassword())) {
            log.info("비밀번호 불일치");
            return NO_PW;
        }

        // 자동로그인 체크 여부 확인
        if (dto.isAutoLogin()) {
            // 1. 쿠키 생성 - 쿠키값에 세션아이디를 저장
            Cookie autoLoginCookie
                    = new Cookie(LoginUtil.AUTO_LOGIN_COOKIE, session.getId());
            // 2. 쿠키 셋팅 - 수명이랑 사용경로
            int limitTime = 60 * 60 * 24 * 90;
            autoLoginCookie.setMaxAge(limitTime);
            autoLoginCookie.setPath("/"); // 전체 경로

            // 3. 쿠키를 클라이언트에 응답전송
            response.addCookie(autoLoginCookie);

            // 4. DB에도 쿠키에 저장된 값과 수명을 저장



        }

        log.info("{}님 로그인 성공!", foundUser.getUserNickname());
        return SUCCESS;

    }

    public void maintainLoginState(HttpSession session, String userAccount) {
        // 현재 로그인한 사람의 모든 정보
        User user = getUser(userAccount);

        // 현재 로그인한 사람의 화면에 보여줄 일부정보(더 추가해야할수도 있음)
        LoginUserResponseDTO dto = LoginUserResponseDTO.builder()
                .userAccount(user.getUserAccount())
                .userNickname(user.getUserNickname())
                .lolNickname(user.getLolNickname())
                .userCurrentPoint(user.getUserCurrentPoint())
                .userProfileImage(user.getLatestProfileImage())
                .build();

        // userProfileImage 값 확인
        String userProfileImage = dto.getUserProfileImage();
        System.out.println("UserProfileImage: " + userProfileImage);

        // 이 정보를 세션에 저장
        session.setAttribute(LoginUtil.LOGIN_KEY, dto);

        // 세션의 수명을 설정
        session.setMaxInactiveInterval(60 * 60); // 1시간
    }

    // 유저 정보를 가져오는 서비스기능
    public User getUser(String userAccount) {
        return userRepository.findByUserAccount(userAccount);
    }

    public void autoLoginClear(HttpServletRequest request, HttpServletResponse response) {

        // 1. 자동로그인 쿠키를 가져온다
        Cookie c = WebUtils.getCookie(request, LoginUtil.AUTO_LOGIN_COOKIE);

        // 2. 쿠키를 삭제 -> 쿠키의 수명을 0초로 만들어서 다시 클라이언트에게 응답
        if (c != null) {
            c.setMaxAge(0);
            c.setPath("/");
            response.addCookie(c);

            // 4. 데이터베이스에도 자동로그인을 해제한다.

        }
    }




    public boolean registerDUO(/*HttpSession session, */UserCommentRequestDTO dto) {

        User exUser = User.builder()
                .userSessionId("abc1234@ddd.com")
                .userAccount("abc1234@ddd.com")
                .userPassword("12345678")
                .lolTier(Tier.CHA)
                .userGender(Gender.M)
                .userBirthday(LocalDate.of(2000, 03, 16))
                .userNickname("HongChaa")
                .lolNickname("HongChaa")
                .build();

        userRepository.save(exUser);

        Optional<User> user = userRepository.findById(exUser.getUserSessionId());

        System.out.println("user = " + user);
        if (user.isEmpty()) {
            return false;
        }
        user.ifPresent(u -> {
            u.setUserPosition(dto.getUserPosition());
            u.setUserComment(dto.getUserComment());
            u.setUserMatchingPoint(dto.getUserMatchingPoint());

            userRepository.save(u);
        });
        return true;
    }

//    public ListResponseDTO<UsersByAdminResponseDTO> getUserListByAdmin(AdminSearchType type) {
//        userRepository.count();
//        return null;
//    }

    public List<UserByAdminResponseDTO> getUserListByAdmin() {


//        // Pageable객체 생성
//        Pageable pageable = PageRequest.of(
//                type.getPage() - 1,
//                type.getSize(),
//                Sort.by("createDate").descending()
//        );

        //전체불러오기
        List<User> all = userRepository.findAll();
        //user정보
//        List<User> users = all.getContent();

        //dto리스트생성 및 dto 생성
        List<UserByAdminResponseDTO> userListByAdmin = new ArrayList<>();
        UserByAdminResponseDTO dto = new UserByAdminResponseDTO();
        for (User user : all) {
            //bc,rc,rc,fc 카운터 찾는 메서드
            long accuseCount = accuseRepository.countByUser(user);
            long boardCount = boardRepository.countByUser(user);
            long replyCount = replyRepository.countByUser(user);
//            long followToCount = followRepository.findToByAccount(user);


            dto.setUserAccount(user.getUserAccount());
            dto.setGender(user.getUserGender().toString());
            dto.setBoardCount(boardCount);
            dto.setReplyCount(replyCount);
            dto.setReportCount(accuseCount);
            dto.setPoint(user.getUserCurrentPoint());
            dto.setFollowCount(3);

            userListByAdmin.add(dto);
        }
        List<UserByAdminResponseDTO> userListByAdmin1 = userListByAdmin;
        System.out.println("userListByAdmin1 = " + userListByAdmin1);

        return userListByAdmin;
    }

    public AdminPageResponseDTO getAdminPageInfo(){
        int userFindAllCount = userFindAllCount();
        int userFindByToday = userFindByToday();
        int accuseFindAllCount = accuseFindAllCount();
        int accuseFindByToday = accuseFindByToday();
        int boardFindAllCount = boardFindAllCount();
        int boardFindByToday = boardFindByToday();

        AdminPageResponseDTO build = AdminPageResponseDTO.builder()
                .todayBoardCount(boardFindByToday)
                .todayJoinCount(userFindByToday)
                .todayAccuseCount(accuseFindByToday)
                .totalAccuseCount(accuseFindAllCount)
                .totalBoardCount(boardFindAllCount)
                .totalJoinCount(userFindAllCount)
                .build();

        return build;

    }

    //전체 user 조회수(admin)
    public int userFindAllCount() {
        List<User> all = userRepository.findAll();
        int userSize = all.size();
        return userSize;
    }

    //오늘 가입한 회원 수(admin)
    public int userFindByToday() {
        int allWithJoinDate = userRepository.findAllWithJoinDate(LocalDate.now());
        return allWithJoinDate;
    }

    //전체 accuse 조회수(admin)
    public int accuseFindAllCount() {
        List<Accuse> all = accuseRepository.findAll();
        int accuseSize = all.size();
        return accuseSize;
    }

    //오늘 accuse 조회수(admin)
    public int accuseFindByToday() {
        int allWithAccuseWrittenDate = accuseRepository.findAllWithAccuseWrittenDate();
        return allWithAccuseWrittenDate;
    }

    //전체 게시글 조회수(admin)
    public int boardFindAllCount() {
        List<Board> all = boardRepository.findAll();
        int boardsize = all.size();
        return boardsize;
    }

    //오늘 작성된 게시글 조회수(admin)
    public int boardFindByToday() {
        int allWithJoinDate = boardRepository.findAllWithBoardWrittenDate();
        return allWithJoinDate;
    }

    //유저리스트 DTO변환(Admin) + 페이징
    public ListResponseDTO<UserByAdminResponseDTO,User> getUserListByAdmin(PageDTO dto) {
        System.out.println("servicedto="+dto);
        Pageable pageable = PageRequest.of(
                dto.getPage()-1,
                dto.getSize(),
                Sort.by("userJoinDate").descending()
        );
        //전체불러오기
        Page<User> all = userRepository.findAll(pageable);

        for (User user : all) {
            System.out.println("all123"+user);

        }

        List<UserByAdminResponseDTO> collect = all.stream()
                .map(UserByAdminResponseDTO::new)
                .collect(toList());

        for (UserByAdminResponseDTO userByAdminResponseDTO : collect) {
            System.out.println("userByAdminResponseDTO = " + userByAdminResponseDTO);

        }
//
        //1         10
//        page - 1 * size + 1
        // 1 -> 1
        // 2 -> 11
        // 3 -> 21
        // 4 -> 31
        int i= (dto.getPage() - 1) * dto.getSize() + 1 ;
        for (UserByAdminResponseDTO user : collect) {
            user.setRowNum(i);
            i++;
        }


        return ListResponseDTO.builder()
                .count(all.getSize())
                .pageInfo(new PageResponseDTO(all))
                .list(collect)
                .build();

    }

    //금일 가입자(Admin)
    public ListResponseDTO<UserByAdminResponseDTO,User> todayUserByAdMin(PageDTO dto) {
        Pageable pageable = PageRequest.of(
                dto.getPage()-1,
                dto.getSize(),
                Sort.by("userJoinDate").descending()
        );
        //전체불러오기
        Page<User> all = userRepository.findAll(pageable);
        List<User> todayUserList = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();

        for (User user : all) {
            System.out.println("userByAdminResponseDTO = " + user);
            LocalDate joinDate = user.getUserJoinDate();
            if (joinDate!=null&&joinDate.equals(currentDate)) {
                todayUserList.add(user);
            }
        }

        List<UserByAdminResponseDTO> collect = todayUserList.stream()
                .map(UserByAdminResponseDTO::new)
                .collect(toList());
//
        int i=1;
        for (UserByAdminResponseDTO user : collect) {
            user.setRowNum(i);
            i++;
        }

        System.out.println("collect = " + collect);

        return ListResponseDTO.builder()
                .count(collect.size())
                .pageInfo(new PageResponseDTO(all))
                .list(collect)
                .build();
    }



    //userDetailByAdmin
    public UserDetailByAdminResponseDTO getUserDetailByAdmin(String userAccount) {
        User oneUser = userRepository.findByUserAccount("345");
        UserDetailByAdminResponseDTO userDetail =
                new UserDetailByAdminResponseDTO(oneUser);



        return userDetail;
    }


    //포인트 증가
    public boolean increaseUserPoint(UserModifyRequestDTO dto) {
        User byUserNickName = userRepository.findByUserNickName(dto.getUserNickname());

        System.out.println("userByNickName123 = " + byUserNickName);
        //지급포인트
        int userAddPoint = dto.getUserAddPoint();
        System.out.println("userAddPoint = " + userAddPoint);
        String addPoint = String.valueOf(userAddPoint);

        //-99999 ~ +99999 가 맞는지 확인
        boolean matches = addPoint.matches("-?[0-9]{1,5}");
        System.out.println("matches = " + matches);
        //현재포인트
        int userCurrentPoint = byUserNickName.getUserCurrentPoint();
        System.out.println("userCurrentPoint = " + userCurrentPoint);
        //더한값
        int total = userCurrentPoint + userAddPoint;
        System.out.println("total = " + total);



        //현재포인트와 total이 같지 않다면 저장
        if (userCurrentPoint != total) {

            if (matches != false) {
                byUserNickName.setUserCurrentPoint(total);
                User save = userRepository.save(byUserNickName);
                System.out.println("save = " + save);
                return true;
            }
            return false;
        }
        return false;

    }

    //밴 boolean
    public boolean changeBanStatus(UserModifyRequestDTO dto){
        int userIsBanned = dto.getUserIsBanned();
        System.out.println("userIsBanned = " + userIsBanned);
        User byUserNickName = userRepository.findByUserNickName(dto.getUserNickname());
        boolean userIsBanned1 = byUserNickName.isUserIsBanned();
        System.out.println("userIsBanned1 = " + userIsBanned1);

        //클릭이 동작된것 front 에서 1을 보내줄것
        if(userIsBanned==1) {
            //userIsBanned가 1이면 참
            if (userIsBanned1 == true) {
                byUserNickName.setUserIsBanned(false);
                User save = userRepository.save(byUserNickName);
                boolean userIsBanned2 = save.isUserIsBanned();
                System.out.println("userIsBanned2 = " + userIsBanned2);
                return false;
            }
            byUserNickName.setUserIsBanned(true);
            User save = userRepository.save(byUserNickName);
            boolean userIsBanned2 = save.isUserIsBanned();
            System.out.println("userIsBanned2 = " + userIsBanned2);
            return true;
        }
        return false;

    }

    //닉네임으로 user 찾기
    public User findUserByNickName(UserModifyRequestDTO dto){
        User byUserNickName = userRepository.findByUserNickName(dto.getUserNickname());


        return byUserNickName;
    }

//    public boolean changeUserPoint(UserModifyRequestDTO dto){
//
//        return false;
//    }

    /**
     * 사용자의 듀오 정보를 구하는 메서드
     *
     * @param session     - 접속한 사용자
     * @param userAccount - 대상 사용자
     * @return - 응답 DTO
     */
    public UserHistoryResponseDTO getUserHistoryInfo(HttpSession session, String userAccount) {

        User foundUser = userRepository.findById(userAccount).orElseThrow(
                () -> new RuntimeException("해당하는 유저가 없습니다.")
        );
        String lolNickname = foundUser.getLolNickname();

        List<MatchV5DTO.MatchInfo.ParticipantDTO> last20ParticipantDTOList = riotApiService.getLast20ParticipantDTOList(lolNickname);

        LeagueV4DTO rankInfo = null;
        try {
            rankInfo = riotApiService.getRankInfo(lolNickname, "RANKED_SOLO_5x5");
        } catch (NoRankException e) {
            try {
                rankInfo = riotApiService.getRankInfo(lolNickname, "RANKED_FLEX_SR");
            } catch (NoRankException ex) {
                rankInfo = LeagueV4DTO.builder().build();
            }
        }

        // 사용자가 받은 모든 리뷰
        List<UserReviewResponseDTO> reviews = foundUser.getChattingFromList().stream()
                .map(c -> c.getMatchingList().stream()
                        .map(UserReviewResponseDTO::new)
                        .collect(toList())
                ).collect(toList())
                .stream()
                .flatMap(List::stream)
                .collect(toList());

        boolean isFollowed = false;
        try {
            isFollowed = followRepository.existsByFollowFromAndFollowTo(session.getAttribute("로그인키").toString(), userAccount);
        } catch (NullPointerException ignored) {
        }

        List<MostChampInfo> mostChampInfoList = riotApiService.getMost3Champions(lolNickname).stream()
                .map(m -> {
                    List<MatchV5DTO.MatchInfo.ParticipantDTO> championMatchInfoList = last20ParticipantDTOList.stream()
                            .filter(p -> p.getChampionName().equals(m))
                            .collect(toList());

                    int winCount = (int) championMatchInfoList.stream()
                            .filter(MatchV5DTO.MatchInfo.ParticipantDTO::isWin).count();

                    int loseCount = (int) championMatchInfoList.stream()
                            .filter(c -> !c.isWin()).count();

                    int winRate = winCount * 100 / (winCount + loseCount);

                    int kills = championMatchInfoList.stream()
                            .mapToInt(MatchV5DTO.MatchInfo.ParticipantDTO::getKills).sum();

                    int deaths = championMatchInfoList.stream()
                            .mapToInt(MatchV5DTO.MatchInfo.ParticipantDTO::getDeaths).sum();

                    int assists = championMatchInfoList.stream()
                            .mapToInt(MatchV5DTO.MatchInfo.ParticipantDTO::getAssists).sum();

                    double kda = (double) (kills + assists) / deaths;

                    return MostChampInfo.builder()
                            .champName(m)
                            .winCount(winCount)
                            .loseCount(loseCount)
                            .winRate(winRate)
                            .kda(kda)
                            .build();
                })
                .collect(toList());

        return UserHistoryResponseDTO.builder()
                .userAccount(userAccount)
                .profileImage(foundUser.getLatestProfileImage())
                .userNickname(foundUser.getUserNickname())
                .userPosition(foundUser.getUserPosition())
                .isFollowed(isFollowed)
                .userAvgRate(String.format("%.2f", foundUser.getUserAvgRate()))
                .userMatchingPoint(foundUser.getUserMatchingPoint())
                .userInstagram(foundUser.getUserInstagram())
                .userFacebook(foundUser.getUserFacebook())
                .userTwitter(foundUser.getUserTwitter())
                .lolNickname(lolNickname)
                .userComment(foundUser.getUserComment())
                .tier(foundUser.getLolTier())
                .userReviews(reviews)
                // 모스트 3 챔피언 정보
                .mostChampInfos(mostChampInfoList)
                // riot api 를 통해 얻어오는 솔로랭크 혹은 자유랭크 데이터
                .leaguePoints(rankInfo.getLeaguePoints())
                .totalWinCount(rankInfo.getWins())
                .totalLoseCount(rankInfo.getLosses())
                .winRate(Math.round(rankInfo.getWinRate() * 100))
                // 최근 20 매치의 정보 데이터
                .last20Matches(last20ParticipantDTOList.stream()
                        .map(MatchResponseDTO::new)
                        .collect(toList()))
                .userReviews(matchingService.getGottenReview(userAccount, 1).getList())
                .build();

    }
    public List<UserProfilesResponseDTO> getUserProfileList(/*HttpSession session, */UserSearchType userSearchType) {
        List<UserProfilesResponseDTO> userProfileList = userQueryDSLRepositoryCustom.getUserProfileList(userSearchType);

        for (UserProfilesResponseDTO userProfile : userProfileList) {
            log.info("@@@ userProfile @@@@@ : {}", userProfile.toString());
        }
        return userProfileList;
    }





}
