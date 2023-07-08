package site.woulduduo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.woulduduo.dto.request.board.BoardModifyRequestDTO;
import site.woulduduo.dto.request.board.BoardWriteRequestDTO;
import site.woulduduo.dto.request.page.PageDTO;
import site.woulduduo.dto.response.ListResponseDTO;
import site.woulduduo.dto.response.board.BoardResponseDTO;
import site.woulduduo.dto.response.board.BoardsByAdminResponseDTO;
import site.woulduduo.dto.response.page.PageResponseDTO;
import site.woulduduo.entity.Board;
import site.woulduduo.enumeration.BoardCategory;
import site.woulduduo.repository.BoardLikeRepository;
import site.woulduduo.repository.BoardRepository;
import site.woulduduo.repository.ReplyRepository;
import site.woulduduo.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;
    private final BoardLikeRepository boardLikeRepository;
    private final UserRepository userRepository;


    //조회
    private Board getBoard(Long boardNo) {
        return boardRepository.findById(boardNo)
                .orElseThrow(
                        () -> new RuntimeException(
                                boardNo + "번 게시물이 존재하지 않습니다."

                        )
                );
    }

    //dto를 받아서 entity 로 저장
    //쓰기
    public boolean writeBoard(/*HttpSession session, */BoardWriteRequestDTO dto) {

        //세션에서정보가져오기


        //게시글 쓰기 board 엔터티 생성
        Board saved = boardRepository.save(dto.toEntity());
        System.out.println("saved = " + saved);
        if (saved != null) {
            return true;
        }
        return false;


    }


    //수정
    public Long modifyBoard(BoardModifyRequestDTO dto) {

        //수정 전 데이터 조회
        final Board boardEntity = getBoard(dto.getBoardNo());
//        if (boardEntity == null) {
//            return false;
//        }

        //수정 시작
        boardEntity.setBoardTitle(dto.getBoardTitle());
        boardEntity.setBoardContent(dto.getBoardContent());
        boardEntity.setBoardCategory(BoardCategory.NOTICE);

        //수정완료
        Board modfiedBoard = boardRepository.save(boardEntity);

        return modfiedBoard.getBoardNo();
        //반환값 설정

    }

    //조회


    //전체 BoardList DTO 변환 (Admin)+ 페이징
    public ListResponseDTO<BoardsByAdminResponseDTO,Board> getBoardListByAdmin(PageDTO dto){


        Pageable pageable = PageRequest.of(
                dto.getPage() - 1,
                dto.getSize(),
                Sort.by("boardNo").descending()
        );

        String keyword = dto.getKeyword();
        if ("".equals(keyword)){
            keyword=null;
        }

        String userAccount = dto.getKeyword();
        Page<Board> boards;

        if (userAccount==null) {
            boards = boardRepository.findAll(pageable);
        }else{
            boards = boardRepository.findByUser_UserAccountContaining(userAccount, pageable);
        }


        System.out.println("all = " + boards);
        List<BoardsByAdminResponseDTO> collect = boards.stream()
                .map(BoardsByAdminResponseDTO::new)
                .collect(toList());



        System.out.println("collect = " + collect);
        return ListResponseDTO.<BoardsByAdminResponseDTO,Board>builder()
                .count(collect.size())
                .pageInfo(new PageResponseDTO<>(boards))
                .list(collect)
                .build();
    }

    //게시글 삭제
    public boolean deleteBoard(long boardNo) {
        System.out.println("boardNo = " + boardNo);
        Board byBoardNo = boardRepository.findByBoardNo(boardNo);
        System.out.println("byBoardNo = " + byBoardNo);

        if (byBoardNo != null) {
            boardRepository.deleteByBoardNo(boardNo);
            return true;
        }

        return false;
    }

    //금일 작성 게시물 (ADMIN)
    public ListResponseDTO<BoardsByAdminResponseDTO, Board> todayBoardByAdmin(PageDTO dto) {

        Pageable pageable = PageRequest.of(
                dto.getPage() - 1,
                dto.getSize(),
                Sort.by("boardNo").descending()
        );

        String keyword = dto.getKeyword();
        if ("".equals(keyword)){
            keyword=null;
        }

        String userAccount = dto.getKeyword();
        Page<Board> boards;

        if (userAccount==null) {
            boards = boardRepository.findAll(pageable);
        }else{
            boards = boardRepository.findByUser_UserAccountContaining(userAccount, pageable);
        }

        List<Board> todayBoardList = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();

        for (Board board : boards) {
            System.out.println("boardsByAdminResponseDTO = " + board);
            LocalDateTime writtenDate = board.getBoardWrittenDate();
            LocalDate localDate = writtenDate.toLocalDate();
            if (localDate != null && localDate.equals(currentDate)) {
                todayBoardList.add(board);
            }
        }

        List<BoardsByAdminResponseDTO> collect = todayBoardList.stream()
                .map(BoardsByAdminResponseDTO::new)
                .collect(toList());


        System.out.println("collect----- = " + collect);


        System.out.println("collect = " + collect);
        return ListResponseDTO.<BoardsByAdminResponseDTO, Board>builder()
                .count(collect.size())
                .pageInfo(new PageResponseDTO<>(boards))
                .list(collect)
                .build();


    }


    public List<BoardResponseDTO> getBoardList(int page, String keyword, BoardCategory boardCategory, String sort) {
        Pageable pageable = PageRequest.of(page, 10);

        List<Board> boardList;

        if (keyword != null) {
            boardList = boardRepository.findByBoardTitleContainingIgnoreCase(keyword);
        } else if (boardCategory != null) {
            boardList = boardRepository.findByBoardCategory(boardCategory);
        } else {
            boardList = boardRepository.findAll();
        }

        List<BoardResponseDTO> boardResponseDTOList = new ArrayList<>();

        for (Board board : boardList) {
            BoardResponseDTO boardResponseDTO = new BoardResponseDTO(board);
            boardResponseDTOList.add(boardResponseDTO);
        }
        return boardResponseDTOList;
    }
}

