package site.woulduduo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.woulduduo.dto.response.chatting.ChattingDetailResponseDTO;
import site.woulduduo.dto.response.chatting.ChattingListResponseDTO;
import site.woulduduo.entity.Chatting;
import site.woulduduo.entity.Message;
import site.woulduduo.entity.User;
import site.woulduduo.entity.UserProfile;
import site.woulduduo.repository.ChattingRepository;
import site.woulduduo.repository.MessageRepository;
import site.woulduduo.repository.UserRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ChattingService {

    private final ChattingRepository chattingRepository;
    private final MessageService messageService;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    //채팅 신청하기
    public long makeChatting(
//            HttpSession session,
            String myName,
            String userAccount
    ) {
//
        long chattingNo;

//        추후 세션에서 꺼내온 아이디로 변경해야함
        User me = userRepository.findById(myName).orElseThrow();
        System.out.println("me : " + me);
        User chattingUser = userRepository.findById(userAccount).orElseThrow();

        // 채팅 데이터를 생성하기 전에 이미 존재하는 채팅인지 검사(단방향)
        Chatting chattingFrom = chattingRepository.findByChattingFromAndChattingTo(/*session.getAttribute()*/ me, chattingUser);
        System.out.println("chattingFrom = " + chattingFrom);
//        Chatting chattingTo = chattingRepository.findByChattingFromAndChattingTo(chattingUser, /*session.getAttribute()*/ me);
//        System.out.println("chattingTo = " + chattingTo);
//        if (myName.equals(userAccount)) {
//            System.out.println("나와의 채팅!?");
//            chattingNo = 0;
//        }else
        if (chattingFrom != null) {
            System.out.println("이미 존재하는 채팅내역임");
            chattingNo = chattingFrom.getChattingNo();
        } else {
            // 존재하지 않으면 데이터 생성
            Chatting chatting = Chatting.builder()
                    .chattingFrom(/*session.getAttribute()*/ me)
                    .chattingTo(chattingUser)
                    .build();
            Chatting saved = chattingRepository.save(chatting);
            chattingNo = saved.getChattingNo();

            Message message = Message.builder()
                    .user(saved.getChattingTo())
                    .messageContent("안녕하세요, 대화를 신청해주셔서 감사합니다!")
                    .chatting(saved)
                    .build();
            if (myName.equals(userAccount)){
                message.setMessageContent("나와의 채팅");
            }
            Message firstMessage = messageRepository.save(message);
        }

//      chattingNo 리턴 후 프론트에서 해당 번호로 채팅방 열어주기
        return chattingNo;
    }

    //채팅방 디테일 내역 가져오기
    public ChattingDetailResponseDTO getChattingDetail(/*session.getAttribute()*/ String userAccount, long chattingNo) {

//        채팅 상대방 찾기 - 테스트완
        User user = userRepository.findById(userAccount).orElseThrow();
//        채팅 가져오기
        Chatting chatting = chattingRepository.findByChattingNo(chattingNo);
        log.info("chattingNo = {}", chatting.getChattingNo());

        ChattingDetailResponseDTO chattingResponseDTO = new ChattingDetailResponseDTO();

//      대표 프로필 사진 구하기 - 테스트완
        String fromImage = getRepresentativeProfile(chatting.getChattingFrom());
        String toImage = getRepresentativeProfile(chatting.getChattingTo());

//      내가 채팅을 걸었을 때
        if (chatting.getChattingFrom() == /*session.getAttribute()*/ user) {

//          대화 상대 닉네임
            chattingResponseDTO.setUserNickname(chatting.getChattingTo().getUserNickname());

//          나와 대화 상대의 프로필사진
            chattingResponseDTO.setMyProfileImage(fromImage);
            chattingResponseDTO.setYourProfileImage(toImage);

//      내가 채팅을 받았을 때
        } else {
            chattingResponseDTO.setUserNickname(chatting.getChattingFrom().getUserNickname());
            chattingResponseDTO.setMyProfileImage(toImage);
            chattingResponseDTO.setYourProfileImage(fromImage);
        }

//        메세지 내역 가져오기
        chattingResponseDTO.setMessageList(messageService.getMessages(chatting));

        return chattingResponseDTO;
    }

    //  대표 프로필 사진 가져오기
    public static String getRepresentativeProfile(User user) {
        List<UserProfile> profileList = null;
        try {
            profileList = user.getUserProfileList().stream()
                    .sorted(Comparator.comparing(UserProfile::getProfileNo).reversed())
                    .limit(1)
                    .collect(Collectors.toList());
            return profileList.get(0).getProfileImage();
        } catch (IndexOutOfBoundsException e) {
            return "noProfile"; // default 이미지 경로로 변경예정
        }
    }

    //채팅 목록 가져오기
    public List<ChattingListResponseDTO> getChattingList(
//            HttpSession session
            User user
    ) {
//       내가 보낸 채팅
        List<Chatting> fromList = chattingRepository.findByChattingFrom(user);
        Chatting toBeRemoved = null;
        for (Chatting chatting : fromList) {
            if(chatting.getChattingTo().equals(user)){
                toBeRemoved = chatting;
            }
        }
        fromList.remove(toBeRemoved);
        fromList.forEach(messageService::getMessages);
//       내가 받은 채팅
        List<Chatting> toList = chattingRepository.findByChattingTo(user);
        toList.forEach(messageService::getMessages);
//       보낸 채팅 받은 채팅 합치고 최신 메세지순으로 정렬
        List<Chatting> chattingList = Stream.concat(fromList.stream(), toList.stream())
                .sorted(Chatting::compareTo).collect(Collectors.toList());
        List<ChattingListResponseDTO> dtoList = chattingList.stream()
                .map(ChattingListResponseDTO::new)
                .collect(Collectors.toList());

//      상대방 닉네임, 프로필 사진 세팅
        for (int i = 0; i < dtoList.size(); i++) {
            if(chattingList.get(i).getChattingFrom() == user){
                dtoList.get(i).setUserNickname(chattingList.get(i).getChattingTo().getUserNickname());
                dtoList.get(i).setProfileImage(getRepresentativeProfile(chattingList.get(i).getChattingTo()));
            } else {
                dtoList.get(i).setUserNickname(chattingList.get(i).getChattingFrom().getUserNickname());
                dtoList.get(i).setProfileImage(getRepresentativeProfile(chattingList.get(i).getChattingFrom()));
            }
//           최신 메세지 세팅
            dtoList.get(i).setMessageContent(messageRepository.findByChattingOrderByMessageTimeDesc(chattingList.get(i)).get(0).getMessageContent());
            dtoList.get(i).makeShortenMessage(dtoList.get(i).getMessageContent());
            dtoList.get(i).setMessageUnreadCount(messageRepository.countByChattingAndUserIsNotAndMessageIsRead(chattingList.get(i), user, false));
        }

        return dtoList;
    }

    public int countUnreadMessages(Chatting chatting, User user){
        return messageRepository.countByChattingAndUserIsNotAndMessageIsRead(chatting, user, false);
    }

    public Chatting findByChattingNo(long chattingNo) {
        return chattingRepository.findByChattingNo(chattingNo);
    }

    public int getTotalUnreadMessageCount(User user) {
        List<Chatting> toList = chattingRepository.findByChattingTo(user);
        List<Chatting> fromList = chattingRepository.findByChattingFrom(user);
        List<Chatting> chattingList = Stream.concat(fromList.stream(), toList.stream()).collect(Collectors.toList());
        int totalUnread = 0;
        for (Chatting chat : chattingList) {
            totalUnread += messageRepository.countByChattingAndUserIsNotAndMessageIsRead(chat, user, false);
        }
        return totalUnread;
    }
}