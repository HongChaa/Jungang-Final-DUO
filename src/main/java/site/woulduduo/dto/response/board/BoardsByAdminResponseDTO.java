package site.woulduduo.dto.response.board;

import lombok.*;
import site.woulduduo.entity.Board;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class BoardsByAdminResponseDTO {

    private long boardNo;
    private String userAccount;
    private String boardTitle;
    private String boardWrittenDate;
    private int boardViewCount;


    public BoardsByAdminResponseDTO(Board board) {
        this.boardNo = board.getBoardNo();
        this.userAccount = board.getUser().getUserAccount();
        this.boardTitle = boardTitle(board,20);
        this.boardWrittenDate = localDateTime(board);
        this.boardViewCount = board.getBoardViewCount();


    }

    public String localDateTime(Board board) {
        LocalDateTime date = board.getBoardWrittenDate();
        LocalDateTime localDateTime = date.withNano(0);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String datetime = localDateTime.format(dateTimeFormatter).replace("T", "");

        return datetime;

    }

    public String boardTitle(Board board, int maxLength) {
        String boardTitle1 = board.getBoardTitle();
        int length = boardTitle1.length();
        if (length <= maxLength) {
            return boardTitle1;
        }else{
            return boardTitle1.substring(0,maxLength)+"...";
        }
    }

}


