package site.woulduduo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.woulduduo.dto.request.chatting.MessageRequestDTO;
import site.woulduduo.dto.response.chatting.MessageListResponseDTO;
import site.woulduduo.entity.Chatting;
import site.woulduduo.entity.Message;
import site.woulduduo.entity.User;
import site.woulduduo.repository.ChattingRepository;
import site.woulduduo.repository.MessageRepository;
import site.woulduduo.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChattingRepository chattingRepository;
    private final UserRepository userRepository;

    //    메세지 저장하기
    public boolean saveMessage(MessageRequestDTO dto){
        User user = userRepository.findByUserNickname(dto.getMessageFrom());
        Message message = Message.builder()
                .chatting(chattingRepository.findByChattingNo(dto.getChattingNo()))
                .user(user)
                .messageContent(dto.getMessageContent())
                .build();
        try {
            Message saved = messageRepository.save(message);
            return true;
        } catch (Exception e) {
            log.info("service - 메세지 저장에 실패함");
            return false;
        }

    }

    //    메세지 내역 가져오기
    public List<MessageListResponseDTO> getMessages (Chatting chatting){

        List<Message> messages = messageRepository.findByChatting(chatting);
        if(messages.size() == 0){
            Message message = Message.builder()
                    .user(chatting.getChattingTo())
                    .messageContent("안녕하세요, 대화를 신청해주셔서 감사합니다!")
                    .chatting(chatting)
                    .build();
            Message saved = messageRepository.save(message);
            messages.add(saved);
        }

        return messages.stream()
                .map(MessageListResponseDTO::new)
                .collect(Collectors.toList());
    }

    public void readMessage(String userId, long chattingNo) {
        User user = userRepository.findById(userId).orElseThrow();
        Chatting chatting = chattingRepository.findByChattingNo(chattingNo);
        List<Message> messagesFromOther = messageRepository.findByChattingAndUserIsNot(chatting, user);
        //      상대방이 보낸 메세지 읽음 처리
        messagesFromOther.forEach(m -> {
            m.setMessageIsRead(true);
            messageRepository.save(m);
        });
    }

    public Message findByChattingOrderByMessageTimeDesc(Chatting chatting) {
        return messageRepository.findByChattingOrderByMessageTimeDesc(chatting).get(0);
    }
}
