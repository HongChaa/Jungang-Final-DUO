package site.woulduduo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import site.woulduduo.dto.request.board.BoardModifyRequestDTO;
import site.woulduduo.dto.request.board.BoardWriteRequestDTO;
import site.woulduduo.dto.request.page.PageDTO;
import site.woulduduo.dto.response.ListResponseDTO;
import site.woulduduo.dto.response.board.BoardResponseDTO;
import site.woulduduo.dto.response.board.BoardsByAdminResponseDTO;
import site.woulduduo.entity.Board;
import site.woulduduo.dto.response.login.LoginUserResponseDTO;
import site.woulduduo.entity.User;
import site.woulduduo.enumeration.BoardCategory;
import site.woulduduo.service.BoardService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor

//경로
public class BoardController {

    private final BoardService boardService;

    //게시물 등록

    // 컨트롤러에서 서비스 값 로그 찍어줌
    @PostMapping("/board/write")
    public String writeBoard(HttpSession session, BoardWriteRequestDTO dto) {
        log.info("/board/write: {} DTO", dto);

        LoginUserResponseDTO loginUserInfo
                = (LoginUserResponseDTO) session.getAttribute("login");
        String userAccount = loginUserInfo.getUserAccount();

        log.info("{}---------- ", userAccount);

        dto.setUserAccount(userAccount);

//        String userAccount = (String) session.getAttribute("user_account");
//        log.info("/board/write {} Dto",dto);
//
//        // 게시글 작성자 정보 설정
//        dto.setUserAccount(userAccount);

        // 유효성 검사 및 에러 처리
        if (dto.getBoardCategory() == null) {
            BoardCategory boardCategory = dto.getBoardCategory();
            if (boardCategory != null) {
                if (boardCategory.equals("FREE")) {
                    dto.setBoardCategory(BoardCategory.FREE);
                } else if (boardCategory.equals("ACCUSE")) {
                    dto.setBoardCategory(BoardCategory.ACCUSE);
                } else {
                    // 선택한 값에 따라 추가적인 처리가 필요한 경우
                    // 예: 기본값 설정, 에러 처리 등
                }
            } else {
                // 선택한 값이 없는 경우의 처리
            }
        }

        // 서비스 호출
        boolean isBoardWritten = boardService.writeBoard(dto);
        if (isBoardWritten) {
            return "redirect:/board/list";
        } else {
            // 게시글 쓰기 실패 시, 적절한 에러..?
            return "board/board-write";
        }
    }


    @GetMapping("/board/")
    public String showBoardPage() {
        return "board/board-write"; //  JSP경로  yml,build 확인후  패키지경로까지확인하기
    }


    // 게시물 수정

    //gt 보여주는거
    @GetMapping("/api/v1/boards/{boardNo}")
    public ResponseEntity<?> modifyBoard(HttpSession session, Long boardNo) {

        log.info("/api/v1/boards/{boardNo}");
        return ResponseEntity
                .ok()
                .body(boardNo);


    }


    //해당하는 boardNo로 리다이렉트
    //    동기로 서비스로 보내주기 위한
    @PostMapping("/board/modify")
    public String modifyBoard(HttpSession session, BoardModifyRequestDTO dto) {

        System.out.println("/board/modify : POST");
        Long boardNo = boardService.modifyBoard(dto);
//string 뒤에 우리가 뽑은거 활용
        return "redirect/board/boardNo/" + boardNo;

    }



    //불러오기
//+ getBoardList(page : int, keyword : String, boardCategory : BoardCategory, sort : String) : ResponseEntity<?> @GetMapping("/api/v1/boards/{page}/{keyword}/{boardCategory}/{sort}") 검색은 제목으로만

    @GetMapping("/api/v1/boards/{page}/{keyword}/{boardCategory}/{sort}")
    public ResponseEntity<List<BoardResponseDTO>> getBoardList(
            @PathVariable int page,
            @PathVariable String keyword,
            @PathVariable BoardCategory boardCategory,
            @PathVariable String sort
    ) {
        List<BoardResponseDTO> boardList = boardService.getBoardList(page, keyword, boardCategory, sort);
        log.info("boardList",boardList);

        return ResponseEntity.ok(boardList);
    }


    //삭제
//deleteBoard(session HttpSession, boadNo : long) : ResponseEntity<?> @DeleteMapping("/api/v1/boards/{boardNo}")


    //조회
//    + findOne(boadNo : long, model : Model, session HttpSession) : String @GetMapping("/board/detail")



    //창여는거=> 비동기
    //경로문제
    @GetMapping("/board/list")
    public String showBoardList() {
        log.info("asdsad");
        return "board/community";
    }












    //관리자페이지 boardlist 가져오기
    @GetMapping("/api/v1/boards/admin")
    public ResponseEntity<?> getBoardListByAdmin(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "1") int pageNum) {

        PageDTO dto = PageDTO.builder()
                .page(pageNum)
                .keyword(keyword)
                .size(10)
                .build();

        log.info("{}ddttoo==",dto);
        ListResponseDTO<BoardsByAdminResponseDTO, Board> boardListByAdmin = boardService.getBoardListByAdmin(dto);
        log.info("userbyadmin11111 : {}",boardListByAdmin);

        log.info("/api/v1/users/admin/");


        return ResponseEntity
                .ok()
                .body(boardListByAdmin);
    }

    //보드 삭제
    @DeleteMapping("/api/v1/boards/admin/delete")
    public ResponseEntity<?>  deleteBoardByAdmin(long boardNo ){
        log.info("{}boardNo1212==",boardNo);

        boolean b1 = boardService.deleteBoard(boardNo);
        System.out.println("b1 = " + b1);

        return ResponseEntity
                .ok()
                .body(b1);

    }


    //금일 등록된 boardlist
    @GetMapping("/api/v1/boards/admin1")
    public ResponseEntity<?> getTodayBoardListByAdmin(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "1") int pageNum) {

        PageDTO dto = PageDTO.builder()
                .page(pageNum)
                .keyword(keyword)
                .size(10)
                .build();
        log.info("{}ddttoo==",dto);

        ListResponseDTO<BoardsByAdminResponseDTO, Board> boardsByAdminResponseDTOUserListResponseDTO = boardService.todayBoardByAdmin(dto);
        log.info("boardListByAdmin : {}",boardsByAdminResponseDTOUserListResponseDTO);


        return ResponseEntity
                .ok()
                .body(boardsByAdminResponseDTOUserListResponseDTO);
    }

}

