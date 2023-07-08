package site.woulduduo.dto.request.board;

import lombok.*;
import site.woulduduo.entity.Board;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter @Getter
@ToString @EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BoardModifyRequestDTO {
    private Long boardNo;

    // TODO : 카테고리 추가 해야 함.
    @NotBlank
    @Size(min=1,max =50)
    private String boardTitle;
    private String boardContent;

    public BoardModifyRequestDTO(Board modfiedBoard) {
    }
}
