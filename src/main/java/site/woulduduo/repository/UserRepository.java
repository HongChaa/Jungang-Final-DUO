package site.woulduduo.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import site.woulduduo.entity.User;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface
UserRepository extends
        JpaRepository<User,String> {

    //아이디로 검색한 정보보기 + 페이징
    Page<User> findByUserAccountContaining(String userAccount, Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM User u WHERE u.userAccount = :email")
    int countByUserEmail(String email);

    //날짜별 가입 user 보기
    @Query(value = "SELECT COUNT(*) FROM duo_user WHERE user_join_date = :userJoinDate",nativeQuery = true)
    int findAllWithJoinDate(LocalDate userJoinDate);

    //nickname으로 User 객체 찾기

    //가입수
    long countByUserAccount(String userAccount);

    User findByUserAccount(String userAccount);

    // 자동로그인시 사용자의 세션ID와 세션만료시간을 DB에 저장하고 업데이트
    @Modifying
    @Transactional
    @Query(value = "UPDATE User u SET u.userSessionId = :sessionId, u.userCookieLimitTime = :limitTime WHERE u.userAccount = :account")
    void saveAutoLogin(String sessionId, LocalDateTime limitTime, String account);

    // 자동로그인된 사용자의 세션ID를 확인하고, 그 해당 사용자의 정보를 조회(가져옴)
    @Query(value = "SELECT * FROM duo_user WHERE user_session_id = :sessionId", nativeQuery = true)
    User findUserByCookie(@Param("sessionId") String sessionId);

    // 닉네임 중복검사를 위한 쿼리문
    @Query(value = "SELECT COUNT(*) FROM duo_user WHERE user_nickname = :nickname", nativeQuery = true)
    int countByUserNickname(@Param("nickname") String nickname);

    // 롤 닉네임 중복검사를 위한 쿼리문
    @Query(value = "SELECT COUNT(*) FROM duo_user WHERE lol_nickname = :lolNickname", nativeQuery = true)
    int countByLolNickname(@Param("lolNickname") String lolNickname);

    User findByUserNickname(String messageFrom);





}
