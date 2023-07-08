package site.woulduduo.dto.request.chatting;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReviewWriteRequestDTO {

    @NotNull
    private Long matchingNo;

    @Length(max = 100)
    private String reviewContent;

    @NotNull
    private Integer reviewRate;

}
