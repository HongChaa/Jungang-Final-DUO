package site.woulduduo.dto.request.chatting;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class GivePointRequestDTO {

    private long chattingNo;
    private long matchingNo;
}
