package site.woulduduo.dto.request.chatting;

import lombok.*;
import site.woulduduo.enumeration.MatchingStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class MessageRequestDTO {

    private long chattingNo;
    private String messageContent;
    private String messageFrom;
}
