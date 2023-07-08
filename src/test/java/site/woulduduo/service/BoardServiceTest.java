package site.woulduduo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import site.woulduduo.dto.request.board.BoardWriteRequestDTO;
import site.woulduduo.dto.request.page.PageDTO;
import site.woulduduo.dto.response.ListResponseDTO;
import site.woulduduo.dto.response.board.BoardsByAdminResponseDTO;
import site.woulduduo.entity.Board;
import site.woulduduo.entity.User;
import site.woulduduo.enumeration.BoardCategory;
import site.woulduduo.enumeration.Gender;
import site.woulduduo.enumeration.Tier;
import site.woulduduo.repository.BoardRepository;
import site.woulduduo.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@Rollback(value = false)
class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;


    @BeforeEach
    void userInsert() {
        User User1 = User.builder()
                .userAccount("acvd")
                .userNickname("asd")
                .userPassword("12345")
                .userCurrentPoint(123)
                .userBirthday(LocalDate.of(1990, 11, 07))
                .lolNickname("asd")
                .userGender(Gender.F)
                .lolTier(Tier.DIA)
                .build();
        User save = userRepository.save(User1);

        for (int i = 1; i <= 30; i++) {
            Board board = Board.builder()
                    .user(save)
                    .boardTitle("게시글제목" + i)
                    .boardContent("게시글내용" + i)
                    .boardWrittenDate(LocalDateTime.of(2023, 06, 11, 11, 00, 00))
                    .boardCategory(BoardCategory.ACCUSE)
                    .build();

            Board asave = boardRepository.save(board);
        }
        for (int i = 1; i <= 30; i++) {

            Board board1 = Board.builder()
                    .user(save)
                    .boardTitle("게시글제목" + i)
                    .boardContent("게시글내용" + i)
                    .boardCategory(BoardCategory.ACCUSE)
                    .build();

            Board asave1 = boardRepository.save(board1);

        }

    }

    @Test
    @DisplayName("bulk insert")
    void bulkinser() {
        for (int i = 1; i <= 10; i++) {
            BoardWriteRequestDTO build = BoardWriteRequestDTO.builder()
                    .boardTitle("아라랑")
                    .boardContent("jpa넘어렵 ㅜ")
                    .boardCategory(BoardCategory.NOTICE)
                    .build();

            System.out.println("build = " + build);

            boolean b = boardService.writeBoard(build);
            assertTrue(b);

        }


    }

//    @Test
//    @DisplayName("update")
//    void update(){
//        for(int 1 =1;)
//    }

    //
//}
//
    @Test
    @DisplayName("관리자용 전체 boardList dto변환")
    void getBoardListByAdmin(){

        PageDTO dto = new PageDTO();

        ListResponseDTO<BoardsByAdminResponseDTO, Board> boardListByAdmin = boardService.getBoardListByAdmin(dto);
        System.out.println("boardListByAdmin = " + boardListByAdmin);
    }

    @Test
    @DisplayName("관리자용 금일 boardList 확인")
    void todayBoardByAdmin(){
        PageDTO dto = new PageDTO();
        dto.setPage(3);


        ListResponseDTO<BoardsByAdminResponseDTO, Board> boardsByAdminResponseDTOUserListResponseDTO = boardService.todayBoardByAdmin(dto);

        System.out.println("userByAdminResponseDTOBoardListResponseDTO = " + boardsByAdminResponseDTOUserListResponseDTO);

    }


}

