package site.woulduduo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import site.woulduduo.entity.Board;
import site.woulduduo.entity.User;
import site.woulduduo.enumeration.BoardCategory;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {


    //게시글 삭제
    Board findByBoardNo(long boardNo);

    void deleteByBoardNo(long boardNo);

//    List<Board> findByBoardTitleContainingIgnoreCase(String keyword, Pageable pageable);
//    List<Board> findByBoardCategory(BoardCategory boardCategory, Pageable pageable);

    //해당 id 게시글 작성
    long countByUser(User user);

    @Query(value = "SELECT COUNT(*) FROM duo_board WHERE board_written_date >= DATE(NOW()) AND board_written_date < DATE_ADD(DATE(NOW()), INTERVAL 1 DAY)", nativeQuery = true)
    int findAllWithBoardWrittenDate();

    Page<Board> findByUser_UserAccountContaining(String userAccount, Pageable pageable);

    List<Board> findByBoardCategory(BoardCategory boardCategory);

    List<Board> findByBoardTitleContainingIgnoreCase(String keyword);
}
