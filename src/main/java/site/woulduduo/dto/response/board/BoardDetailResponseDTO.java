package site.woulduduo.dto.response.board;

import lombok.*;
import site.woulduduo.entity.Board;
import site.woulduduo.entity.User;
import site.woulduduo.enumeration.BoardCategory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Setter@Getter
@ToString @EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDetailResponseDTO {

    private Long boardNo;

    private int boardLike;

    private String boardTitle;

    private int replyCount;

    private BoardCategory boardCategory;

    private User userNickname;

    private LocalDateTime writtenDate;

    private int boardViewCount;

    private String boardContent;

    private boolean likeStatus;


    // 엔터티를 DTO로 변환하는 생성자
    //Post post <= 엔터티를 받음

    public BoardDetailResponseDTO(Board board){
        this.boardNo = board.getBoardNo();
        this.boardLike = board.getBoardLike();
        this.boardTitle = board.getBoardTitle();
        //reply 타입을 list 배열의 길이
        //reply 댓글목록에서 댓글 수 를 뽑 는 법
        // list 길이
        this.replyCount = board.getReplyList().size();
//        this.replyCount = board.getReplyList();
        this.boardCategory =board.getBoardCategory();
        this.userNickname = board.getUser();
        this.writtenDate = board.getBoardWrittenDate();
        this.boardContent = board.getBoardContent();
        this.likeStatus = likeStatus;
        //likestatus 맞나?



    }

}
