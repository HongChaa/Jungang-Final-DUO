package site.woulduduo.dto.response.chatting;

import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class ChattingDetailResponseDTO {

    private String userNickname;
    private String myProfileImage;
    private String yourProfileImage;
    private List<MessageListResponseDTO> messageList;
}
