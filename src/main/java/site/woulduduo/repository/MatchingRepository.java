package site.woulduduo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import site.woulduduo.entity.Chatting;
import site.woulduduo.entity.Matching;
import site.woulduduo.entity.User;
import site.woulduduo.enumeration.Gender;
import site.woulduduo.enumeration.MatchingStatus;

import java.util.Collection;
import java.util.List;

public interface MatchingRepository extends JpaRepository<Matching, Long> {
    Matching findByMatchingNo(long matchingNo);

    @Query("SELECT m FROM Matching m WHERE m.chatting.chattingTo.userAccount = :userAccount AND m.matchingReviewRate IS NOT NULL\n")
    Page<Matching> findOneByChattingTo(String userAccount, Pageable pageable);

    @Query("SELECT m FROM Matching m WHERE m.chatting.chattingTo.userAccount = :userAccount")
    Page<Matching> findOneByChattingToOnMyPage(String userAccount, Pageable pageable);

    @Query("SELECT m FROM Matching m WHERE m.chatting.chattingFrom.userAccount = :userAccount")
    Page<Matching> findOneByChattingFromOnMyPage(String userAccount, Pageable pageable);

    List<Matching> findByChatting(Chatting chatting);

    //성별에 따른 매칭 요청 카운트
    int countByChatting_ChattingFrom_UserGenderAndChatting_ChattingTo(Gender gender, User user);
    //성별에 따른 수락건
    int countByMatchingStatusInAndChatting_ChattingFrom_UserGenderAndChatting_ChattingTo(List<MatchingStatus> matchingStatus, Gender gender, User user);

}
