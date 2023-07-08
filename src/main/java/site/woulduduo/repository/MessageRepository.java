package site.woulduduo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.woulduduo.entity.Chatting;
import site.woulduduo.entity.Message;
import site.woulduduo.entity.User;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    //메세지 발송시간 역순 검색
    List<Message> findByChattingOrderByMessageTimeDesc(Chatting chatting);

    //채팅 메세지 내역 검색
    List<Message> findByChatting(Chatting chatting);

    int countByChattingAndUserIsNotAndMessageIsRead(Chatting chatting, User user, boolean flag);

    List<Message> findByChattingAndUserIsNot(Chatting chatting, User user);
}
