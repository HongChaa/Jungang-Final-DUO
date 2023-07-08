package site.woulduduo.dto.response.board;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import site.woulduduo.entity.Board;
import site.woulduduo.entity.Reply;
import site.woulduduo.enumeration.BoardCategory;

import java.time.LocalDateTime;
import java.util.List;

@Setter@Getter
@ToString
public class BoardResponseDTO{
    private Long boardNo;
    private int boardLike;

    private String boardTitle;

    private List<Reply> replyCount;

    private BoardCategory boardCategory;

    private String userNickname;

    @JsonFormat(pattern = "yyyyy/MM/dd")
    private LocalDateTime writtenDate;

    private int boardViewCount;



    public BoardResponseDTO (Board board){

        this.boardNo = board.getBoardNo();
        this.boardLike = board.getBoardLike();
        this.boardTitle = board.getBoardTitle();
        this.replyCount = board.getReplyList();
        this.boardCategory = board.getBoardCategory();
        this.userNickname = board.getUser().getUserNickname();
        this.writtenDate = board.getBoardWrittenDate();
        this.boardViewCount = board.getBoardViewCount();


    }


    public BoardResponseDTO(Long boardNo, String boardTitle, String boardContent) {

    }
}
