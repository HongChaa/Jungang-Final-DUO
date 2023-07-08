package site.woulduduo.dto.response.chatting;

import lombok.*;
import site.woulduduo.entity.Chatting;
import site.woulduduo.entity.Matching;
import site.woulduduo.enumeration.MatchingStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class ChattingListResponseDTO {

    private long chattingNo;
    private String chattingFrom;
    private String profileImage;
    private String userNickname;
    private String messageContent;
    private int messageUnreadCount;
    private long matchingNo;
    private MatchingStatus matchingStatus;

    public ChattingListResponseDTO(Chatting chatting){
        this.chattingNo = chatting.getChattingNo();
        this.chattingFrom = chatting.getChattingFrom().getUserNickname();

        try {
            Matching matching = chatting.getMatchingList().get(0);
            this.matchingNo = matching.getMatchingNo();
            this.matchingStatus = matching.getMatchingStatus();
        } catch (IndexOutOfBoundsException e) {
            this.matchingNo = 0;
            this.matchingStatus = null;
        }

    }

    public void makeShortenMessage(String messageContent){
        if(messageContent.length() > 20){
            this.messageContent = messageContent.substring(0, 21) + "...";
        }
    }
}
