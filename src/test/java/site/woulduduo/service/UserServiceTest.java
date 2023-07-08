package site.woulduduo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import site.woulduduo.dto.request.page.PageDTO;
import site.woulduduo.dto.request.page.UserSearchType;
import site.woulduduo.dto.request.user.UserCommentRequestDTO;
import site.woulduduo.dto.request.user.UserModifyRequestDTO;
import site.woulduduo.dto.request.user.UserRegisterRequestDTO;
import site.woulduduo.dto.response.ListResponseDTO;
import site.woulduduo.dto.response.user.*;
import site.woulduduo.dto.response.user.UserProfileResponseDTO;
import site.woulduduo.entity.User;
import site.woulduduo.dto.response.user.UserByAdminResponseDTO;
import site.woulduduo.dto.response.user.UserHistoryResponseDTO;
import site.woulduduo.enumeration.Gender;
import site.woulduduo.enumeration.Position;
import site.woulduduo.enumeration.Role;
import site.woulduduo.enumeration.Tier;
import site.woulduduo.repository.MostChampRepository;
import site.woulduduo.repository.UserRepository;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MostChampRepository mostChampRepository;


//    @BeforeEach
//    void userInsert() {
//        for (int i = 1; i < 80; i++) {
//            if (i < 36) {
//                User user = User.builder()
//                        .userAccount("user" + i)
//                        .userNickname("nickname" + i)
//                        .userPassword("pwd" + i)
//                        .userBirthday(LocalDate.of(2000, 1, 1))
//                        .lolNickname("lolNickname" + i)
//                        .userGender(Gender.M)
//                        .lolTier(Tier.CHA)
//                        .userPosition(Position.MID)
//                        .userComment("안녕하세요 트롤아닙니다." + i)
//                        .userMatchingPoint(500)
//                        .build();
//                userRepository.save(user);
//
//            } else if (i >= 36 && i < 52) {
//            User user = User.builder()
//                    .userAccount("user" + i)
//                    .userNickname("nickname" + i)
//                    .userPassword("pwd" + i)
//                    .userBirthday(LocalDate.of(2000, 1, 1))
//                    .lolNickname("lolNickname" + i)
//                    .userGender(Gender.F)
//                    .lolTier(Tier.DIA)
//                    .userPosition(Position.MID)
//                    .userComment("안녕하세요 트롤아닙니다." + i)
//                    .userMatchingPoint(500)
//                    .build();
//                userRepository.save(user);
//
//            } else {
//                User user = User.builder()
//                        .userAccount("user" + i)
//                        .userNickname("nickname" + i)
//                        .userPassword("pwd" + i)
//                        .userBirthday(LocalDate.of(2000, 1, 1))
//                        .lolNickname("lolNickname" + i)
//                        .userGender(Gender.M)
//                        .lolTier(Tier.DIA)
//                        .userPosition(Position.MID)
//                        .userComment("안녕하세요 트롤아닙니다." + i)
//                        .userMatchingPoint(500)
//                        .build();
//                    userRepository.save(user);
//            }
//        }
//        List<String> mostChampList = List.of("Sett", "Vex", "Vi");
//
//        for (int i = 1; i < 80; i++) {
//            for (int j = 0; j < 3; j++) {
//                User user = userRepository.findById("user" + i).orElseThrow();
//                MostChamp mostChamp = MostChamp.builder()
//                        .champName(mostChampList.get(j))
//                        .mostNo(j + 1)
//                        .build();
//                user.addMostChampList(mostChamp);
//
//                userRepository.save(user);
//                mostChampRepository.save(mostChamp);
//            }
//
//        }
//
//    }

    @Test
    @DisplayName("회원 하나에 ADMIN 부여하기")
    void addAdmin() {
        User user = userService.getUser("jun761_1@naver.com");
        user.setRole(Role.ADMIN);

        userRepository.save(user);
    }

    @Test
    @DisplayName("QueryDSL을 이용해 필터와 정렬 조건에 맞춰 userList가 출력되어야한다.")
    void testGetUserProfileList() {
        HttpSession session = null;
        UserSearchType userSearchType = new UserSearchType();
//        userSearchType.setKeyword("40");
//        userSearchType.setPage(3);
        userSearchType.setPosition(Position.JUG);
        userSearchType.setSize(20);
//        userSearchType.setGender(Gender.M);
//        userSearchType.setTier(Tier.DIA);
//        userSearchType.setSort("avgRate");

        List<UserProfileResponseDTO> userProfileList = userService.getUserProfileList(session, userSearchType);
        System.out.println("userProfileList POIPOPO= " + userProfileList);

//        userProfileList.stream().forEach(n -> {
//            System.out.println("n = " + n);
//        });
            assertEquals(1, userProfileList.size());
//        assertEquals(16, userProfileList.size());
    }

    @Test
    @DisplayName("유요한 사용자 정보로 회원 가입 테스트")
    void testRegisterWithValidUserInfo() {
        // Given
        UserRegisterRequestDTO dto = UserRegisterRequestDTO.builder()
                .userEmail("test1@example.com")
                .userPassword("abC123@")
                .userNickname("송유근")
                .userBirthday(LocalDate.of(2000, 1, 1))
                .lolNickname("결속의반지")
                .userGender(Gender.M)
                .build();

        // When
        userService.register(dto);

        // Then
//        assertEquals(1, userRepository.count());
    }

    @Test
    @DisplayName("이미 등록된 이메일로 회원 가입 시도")
    void testRegisterWithDuplicateEmail() {
        // Given
        UserRegisterRequestDTO dto1 = UserRegisterRequestDTO.builder()
                .userEmail("test@example.com")
                .userPassword("password123")
                .userNickname("nickname1")
                .userBirthday(LocalDate.of(2000, 1, 1))
                .lolNickname("lolnickname1")
                .userGender(Gender.M)
                .build();

        UserRegisterRequestDTO dto2 = UserRegisterRequestDTO.builder()
                .userEmail("test@example.com")
                .userPassword("password456")
                .userNickname("nickname2")
                .userBirthday(LocalDate.of(2000, 2, 2))
                .lolNickname("lolnickname2")
                .userGender(Gender.F)
                .build();

        // When
        userService.register(dto1);

        // Then
        assertThrows(IllegalArgumentException.class, () -> userService.register(dto2));
    }

    @Test
    @DisplayName("이미 등록된 닉네임으로 회원 가입 시도")
    void testRegisterWithDuplicateNickname() {
        // Given
        UserRegisterRequestDTO dto1 = UserRegisterRequestDTO.builder()
                .userEmail("test1@example.com")
                .userPassword("password123")
                .userNickname("nickname")
                .userBirthday(LocalDate.of(2000, 1, 1))
                .lolNickname("lolnickname1")
                .userGender(Gender.M)
                .build();

        UserRegisterRequestDTO dto2 = UserRegisterRequestDTO.builder()
                .userEmail("test2@example.com")
                .userPassword("password456")
                .userNickname("nickname")
                .userBirthday(LocalDate.of(2000, 2, 2))
                .lolNickname("lolnickname2")
                .userGender(Gender.F)
                .build();

        // When
        userService.register(dto1);

        // Then
        assertThrows(IllegalArgumentException.class, () -> userService.register(dto2));
    }







    @Test
    @DisplayName("관리자페이지 정보 count 확인")
    void getCountByAdmin() {
        AdminPageResponseDTO adminPageInfo = userService.getAdminPageInfo();
        System.out.println("stringIntegerMap = " + adminPageInfo);
    }


    @Test
    @DisplayName("관리자 전체유저 리스트 dto 변환+페이징")
    void userExchangeDTO(){


        PageDTO dto = new PageDTO();

        System.out.println("dto = " + dto);
        ListResponseDTO<UserByAdminResponseDTO, User> userListByAdmin = userService.getUserListByAdmin(dto);

        System.out.println("userListByAdmin = " + userListByAdmin);


    }


    @Test
    @DisplayName("관리자 금일가입유저 리스트 dto 변환")
    void todayUserExchangeDTO(){


        PageDTO dto = new PageDTO();
        String userAccount = "123";

        ListResponseDTO<UserByAdminResponseDTO, User>
                userByAdminResponseDTOUserListResponseDTO
                = userService.todayUserByAdMin(dto);

        System.out.println("userByAdminResponseDTOUserListResponseDTO = " + userByAdminResponseDTOUserListResponseDTO);


    }

    @Test
    @DisplayName("유저 디테일 dto 변환")
    void getUserDetailByAdmin(){


        UserDetailByAdminResponseDTO UserByAdmin =
                userService.getUserDetailByAdmin("345");

        System.out.println("todayUserListByAdmin = " + UserByAdmin);

    }

    @Test
    @DisplayName("포인트 증가")
    void increaseUserPoint() {
        UserModifyRequestDTO modify = UserModifyRequestDTO.builder()
                .userNickname("123")
                .userBirthday(LocalDate.of(2012, 01, 01))
                .lolNickname("아무나")
                .userPassword("123")
                .userInstagram("123")
                .userFacebook("123")
                .userTwitter("123")
                .userCurrentPoint(123123)
                .userAddPoint(33333)
                .userIsBanned(0)
                .build();



        System.out.println("modify1 = " + modify);
        boolean b123 = userService.increaseUserPoint(modify);
        System.out.println("b123 = " + b123);
        System.out.println("modify2 = " + modify);

    }

    @Test
    @DisplayName("밴 boolean")
    void changeBanStatus() {
        UserModifyRequestDTO modify = UserModifyRequestDTO.builder()
                .userNickname("123")
                .userBirthday(LocalDate.of(2012, 01, 01))
                .lolNickname("아무나")
                .userPassword("123")
                .userInstagram("123")
                .userFacebook("123")
                .userTwitter("123")
                .userCurrentPoint(123123)
                .userAddPoint(33333)
                .userIsBanned(0)
                .build();

        System.out.println("modify1 = " + modify);
        boolean b123 = userService.changeBanStatus(modify);
        System.out.println("b123 = " + b123);
        System.out.println("modify2 = " + modify);

    }

    @Test
    @DisplayName("회원의 프로필 카드 등록에 성공해야 한다.")
    void registerDUO() {
        UserCommentRequestDTO userCommentRequestDTO = UserCommentRequestDTO.builder()
                .userPosition(Position.MID)
                .userComment("안녕하세요 트롤아닙니다.")
                .userMatchingPoint(500)
                .build();
//        boolean b = userService.registerDUO(userCommentRequestDTO);
//
//        assertTrue(b);
    }

    @Test
    @DisplayName("유저 전적페이지에 쓰이는 정보들을 보여줄 수 있어야 한다")
    void getUserDUOInfoTest() {
        UserHistoryResponseDTO userDUOInfo = userService.getUserHistoryInfo(null, "test@example.com");
        System.out.println("userDUOInfo = " + userDUOInfo);
    }



}