package site.woulduduo.dto.response.chatting;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class RecentMessageResponseDTO {

    private String message;
    private int unreadCount;

    public void makeShortenMessage(String messageContent){
        if(messageContent.length() > 20){
            this.message = messageContent.substring(0, 21) + "...";
        }
    }
}
