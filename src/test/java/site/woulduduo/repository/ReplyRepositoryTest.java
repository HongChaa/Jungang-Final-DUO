package site.woulduduo.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import site.woulduduo.entity.Board;
import site.woulduduo.entity.Reply;
import site.woulduduo.entity.User;
import site.woulduduo.enumeration.BoardCategory;
import site.woulduduo.enumeration.Gender;
import site.woulduduo.enumeration.Tier;

import java.time.LocalDate;
import java.util.List;
@SpringBootTest
@Transactional
@Rollback(false)
class ReplyRepositoryTest {

    @Autowired
    BoardRepository boardRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ReplyRepository replyRepository;

    @Test
    @DisplayName("댓글 save 및 해당 id가 쓴글 reply")
    void saveTest(){
        List<Reply> all = replyRepository.findAll();
        System.out.println("all = " + all);

        //user객체 생성
        User user4 = User.builder()
                .userAccount("345")
                .userNickname("345")
                .userPassword("345")
                .userCurrentPoint(345)
                .userBirthday(LocalDate.of(1999,11,07))
                .lolNickname("345")
                .userGender(Gender.F)
                .lolTier(Tier.DIA)
                .replyList(all)
                .build();
        User save2 = userRepository.save(user4);

        User user1 = User.builder()
                .userAccount("123")
                .userNickname("123")
                .userPassword("123")
                .userCurrentPoint(345)
                .userBirthday(LocalDate.of(1999,11,07))
                .lolNickname("123")
                .userGender(Gender.F)
                .lolTier(Tier.DIA)
                .replyList(all)
                .build();
        User save1 = userRepository.save(user1);

        //board객체 생성
        Board board2 = Board.builder()
                .user(save2)
                .boardTitle("게시글제목1")
                .boardContent("게시글내용1")
                .boardCategory(BoardCategory.NOTICE)
                .build();

        Board save = boardRepository.save(board2);



        //댓글 객체 생성
        Reply reply = Reply.builder()
                .user(save2)
                .replyContent("댓글1")
                .board(save)
                .build();

        Reply reply1 = Reply.builder()
                .user(save2)
                .replyContent("댓글2")
                .board(save)
                .build();

        Reply reply2 = Reply.builder()
                .user(save2)
                .replyContent("댓글3")
                .board(save)
                .build();

        //댓글저장
        Reply asave = replyRepository.save(reply);
        Reply asave1 = replyRepository.save(reply1);
        Reply asave2 = replyRepository.save(reply2);



        long count = replyRepository.countByUser((save2));
        System.out.println("count = " + count);
    }
}