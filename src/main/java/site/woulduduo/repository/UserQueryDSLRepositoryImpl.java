package site.woulduduo.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.SubQueryExpression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.util.StringUtils;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import site.woulduduo.dto.request.page.UserSearchType;
import site.woulduduo.dto.request.user.UserCommentRequestDTO;
import site.woulduduo.dto.response.login.LoginUserResponseDTO;
import site.woulduduo.dto.response.user.UserProfileResponseDTO;
import site.woulduduo.entity.*;
import site.woulduduo.enumeration.Gender;
import site.woulduduo.enumeration.Position;
import site.woulduduo.enumeration.Tier;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserQueryDSLRepositoryImpl implements UserQueryDSLRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private final QUser user =  QUser.user;
    private final QMostChamp mostChamp = QMostChamp.mostChamp;
    private final QFollow follow = QFollow.follow;

    // 서브쿼리
    QUser subUser = new QUser("subUser");

    @Override
    public List<UserProfileResponseDTO> getUserProfileList(UserSearchType userSearchType, HttpSession session) {
        System.out.println("%%% userSearchType // session = " + userSearchType + session);

        LoginUserResponseDTO login = (LoginUserResponseDTO) session.getAttribute("login");
        System.out.println("hghgfh"+login);

        List<User> followedUsers = (login != null) ? followed(session) : null;

        List<User> userList = queryFactory.selectFrom(user)
                .join(user.mostChampList, mostChamp).fetchJoin()
                .where(keywordContains(userSearchType.getKeyword())
                        , positioneq(userSearchType.getPosition())
                        , gendereq(userSearchType.getGender())
                        , tiereq(userSearchType.getTier())
                        , user.userMatchingPoint.ne(0)
                        , followeq(userSearchType.getFollowers(), session)
                )
                .offset(checkPage(userSearchType.getPage()))
                .limit(userSearchType.getSize())
                .orderBy(user.userAvgRate.desc())
                .fetch();
        log.info("### userList ###: {}", userList);

        // select 로 불러온 user 리스트 UserProfilesResponseDTO로 변환해 리스트에 담아주기
//        List<UserProfileResponseDTO> userProfiles = new ArrayList<>();
//        for (User user : userList) {
//            UserProfileResponseDTO dto = UserProfileResponseDTO.builder()
//                    .userAccount(user.getUserAccount())
//                    .userGender(user.getUserGender())
//                    .userComment(user.getUserComment())
//                    .userMatchingPoint(user.getUserMatchingPoint())
//                    .tier(String.valueOf(user.getLolTier()))
//                    .userInstagram(user.getUserInstagram())
//                    .userFacebook(user.getUserFacebook())
//                    .userTwitter(user.getUserTwitter())
//                    .userPosition(user.getUserPosition())
//                    .userNickname(user.getUserNickname())
//                    .avgRate(user.getUserAvgRate())
//                    .mostChampList(user.getMostChampList())
//                    .profileImage((user.getUserProfileList().size() == 0) ? "basic" : user.getUserProfileList().get(0).getProfileImage())
//                    .build();
//
//            userProfiles.add(dto);
//        }

        // 팔로우 여부 설정
        List<UserProfileResponseDTO> userProfiles = userList.stream()
                .map(u -> {
                    boolean isFollowed = false;

                    if (followedUsers != null) {
                        isFollowed = followedUsers.contains(u);
                    }
                    return UserProfileResponseDTO.builder()
                            .userAccount(u.getUserAccount())
                            .userGender(u.getUserGender())
                            .userComment(u.getUserComment())
                            .userMatchingPoint(u.getUserMatchingPoint())
                            .tier(String.valueOf(u.getLolTier()))
                            .userInstagram(u.getUserInstagram())
                            .userFacebook(u.getUserFacebook())
                            .userTwitter(u.getUserTwitter())
                            .userPosition(u.getUserPosition())
                            .userNickname(u.getUserNickname())
                            .avgRate(u.getUserAvgRate())
                            .mostChampList(u.getMostChampList())
                            .profileImage((u.getUserProfileList().size() == 0) ? "basic" : u.getUserProfileList().get(0).getProfileImage())
                            .isFollowed(isFollowed)
                            .build();
                })
                .collect(Collectors.toList());

        System.out.println("@@@ userProfiles @@@= " + userProfiles);
        return userProfiles;
    }

    private  BooleanExpression followeq(String followers, HttpSession session) {
        LoginUserResponseDTO login = (LoginUserResponseDTO)session.getAttribute("login");

        List<String> collect = null;
        if (login != null) {
            QFollow subFollow = new QFollow("subFollow");
            List<User> followedUserAccountList = queryFactory.select(subFollow.followTo)
                    .from(subFollow)
                    .where(subFollow.followFrom.userAccount.eq(login.getUserAccount()))
                    .fetch();

            collect = followedUserAccountList.stream()
                    .map(u -> u.getUserAccount())
                    .collect(Collectors.toList());
        }
        return (followers.equals("all")) ? null : user.userAccount.in(collect);
    }

    @Override
    public List<User> followed(HttpSession session) {
        LoginUserResponseDTO login = (LoginUserResponseDTO)session.getAttribute("login");

        QFollow subFollow = new QFollow("subFollow");

        JPQLQuery<User> query = queryFactory.select(subFollow.followTo)
                .from(subFollow);

        if (login != null) {
            query.where(subFollow.followFrom.userAccount.eq(login.getUserAccount()));
        }

        return query.fetch();
    }


    // 프로필 카드 수정
    @Override
    public Long modifyProfileCard(UserCommentRequestDTO dto, HttpSession session) {
        LoginUserResponseDTO loginUser = (LoginUserResponseDTO) session.getAttribute("login");

        return queryFactory.update(user)
                .set(user.userPosition, dto.getUserPosition())
                .set(user.userComment, dto.getUserComment())
                .set(user.userMatchingPoint, dto.getUserMatchingPoint())
                .where(user.userAccount.eq(loginUser.getUserAccount()))
                .execute();
    }

    // 프로필 카드 삭제
    @Override
    public Long deleteProfileCard(UserCommentRequestDTO dto, HttpSession session) {
        LoginUserResponseDTO loginUser = (LoginUserResponseDTO) session.getAttribute("login");

        return queryFactory.update(user)
                .set(user.userPosition, Position.NONE)
                .set(user.userComment, "")
                .set(user.userMatchingPoint, 0)
                .where(user.userAccount.eq(loginUser.getUserAccount()))
                .execute();
    }


    // 2페이지부터 offset 조정
    private Long checkPage(int page) {
        return page == 1 ? 0 : ((long) page - 1) * 20;
    }

    // 티어 파라미터가 null인지 체크
    private BooleanExpression tiereq(Tier tier) {
        return (tier != null) ? user.lolTier.eq(tier) : null;
    }


    // 성별 파라미터가 null인지 체크
    private BooleanExpression gendereq(Gender gender) {
        return (gender != null) ? user.userGender.eq(gender) : null;
    }

    // 포지션 파라미터가 null인지 체크
    private BooleanExpression positioneq(Position position) {
        return (position != null) ? user.userPosition.eq(position).or(user.userPosition.eq(Position.ALL)) : null;
    }

    // 검색 키워드가 null인지 체크
    private BooleanExpression keywordContains(String keyword) {
        return StringUtils.isNullOrEmpty(keyword) ? null : user.userNickname.contains(keyword);
    }


}