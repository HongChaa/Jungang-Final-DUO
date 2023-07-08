package site.woulduduo.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@ToString(exclude = {"matchingList", "messageList", "chattingFrom", "chattingTo", "pointList"})
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "chattingNo")
@Builder
@Entity
@Table(name = "duo_chatting")
public class Chatting implements Comparable<Chatting> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatting_no")
    private Long chattingNo;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "chatting_from")
    private User chattingFrom;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "chatting_to")
    private User chattingTo;

    @Builder.Default
    @OneToMany(mappedBy = "chatting", fetch = FetchType.LAZY)
    @OrderBy("matchingNo desc")
    private List<Matching> matchingList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "chatting", fetch = FetchType.LAZY)
    private List<Message> messageList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "chatting", fetch = FetchType.LAZY)
    private List<Point> pointList = new ArrayList<>();

    //양방향 매핑에서 리스트쪽에 데이터를 추가하는 편의메서드 생성
    public void addMatching(Matching matching){
        matchingList.add(matching);
        if(this != matching.getChatting()){
            matching.setChatting(this);
        }
    }

    public void addMessage(Message message){
        messageList.add(message);
        if(this != message.getChatting()){
            message.setChatting(this);
        }
    }

    public void addPoint(Point point) {
        pointList.add(point);
        if (this != point.getChatting()) {
            point.setChatting(this);
        }
    }

    @Override
    public int compareTo(Chatting chatting) {
        if (getMessageListInOrder(this.messageList).isAfter(getMessageListInOrder(chatting.messageList))) return -1;
        else if (getMessageListInOrder(this.messageList).equals(getMessageListInOrder(chatting.messageList))) return 0;
        return 1;
    }

    private LocalDateTime getMessageListInOrder(List<Message> messages) {
        return messages.stream().sorted(Comparator.comparing(Message::getMessageTime).reversed()).collect(Collectors.toList()).get(0).getMessageTime();
    }
}
