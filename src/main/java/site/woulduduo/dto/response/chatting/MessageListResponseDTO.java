package site.woulduduo.dto.response.chatting;

import lombok.*;
import site.woulduduo.entity.Message;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class MessageListResponseDTO {

    private String messageFrom;
    private String messageContent;
    private String messageTime;

    public MessageListResponseDTO(Message m) {
        this.messageFrom = m.getUser().getUserNickname();
        this.messageContent = m.getMessageContent();
        this.messageTime = getStringMessageTime(m.getMessageTime());
    }

    private String getStringMessageTime(LocalDateTime time){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM.dd HH:mm");
        return dtf.format(time);
    }
}
