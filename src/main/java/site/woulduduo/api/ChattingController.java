package site.woulduduo.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.woulduduo.dto.request.chatting.MessageRequestDTO;
import site.woulduduo.dto.response.chatting.ChattingDetailResponseDTO;
import site.woulduduo.dto.response.chatting.ChattingListResponseDTO;
import site.woulduduo.dto.response.chatting.RecentMessageResponseDTO;
import site.woulduduo.dto.response.login.LoginUserResponseDTO;
import site.woulduduo.entity.Chatting;
import site.woulduduo.entity.Message;
import site.woulduduo.entity.User;
import site.woulduduo.repository.UserRepository;
import site.woulduduo.service.ChattingService;
import site.woulduduo.service.MessageService;

import javax.servlet.http.HttpSession;
import java.util.List;

import static site.woulduduo.util.LoginUtil.LOGIN_KEY;
import static site.woulduduo.util.LoginUtil.isMyChatting;

@RestController
@RequestMapping("/api/v1/chat")
@Slf4j
@RequiredArgsConstructor
public class ChattingController {

    private final ChattingService chattingService;
    private final MessageService messageService;
    private final UserRepository userRepository;

    //채팅방목록 불러오기
    // 추후 session에 회원정보 담기면 경로 수정
    @GetMapping("/chattings")
    public ResponseEntity<?> getChattingList(
            HttpSession session
//            @PathVariable String userId
    ) {
        LoginUserResponseDTO loginUserInfo = (LoginUserResponseDTO) session.getAttribute(LOGIN_KEY);
        User user = null;
        try {
            user = userRepository.findById(loginUserInfo.getUserAccount()).orElseThrow(
                    () -> new RuntimeException("로그인 하고 이용해주세요")
            );
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        List<ChattingListResponseDTO> chattingList = chattingService.getChattingList(user);

        return ResponseEntity.ok().body(chattingList);
    }

    //채팅방 대화내역 가져오기
    // 추후 session에 회원정보 담기면 경로 수정
    @GetMapping("/messages/{chattingNo}")
    public ResponseEntity<?> getChattingDetail(
            HttpSession session
//            @PathVariable String userId
            , @PathVariable long chattingNo
    ) {
        LoginUserResponseDTO loginUserInfo = (LoginUserResponseDTO) session.getAttribute(LOGIN_KEY);
        Chatting chatting = chattingService.findByChattingNo(chattingNo);
        boolean accessFlag = false;

        accessFlag = isMyChatting(loginUserInfo, chatting);

        if (!accessFlag) {
            return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).build();
        }

//      대화 읽음 처리 해야함**
        messageService.readMessage(loginUserInfo.getUserAccount(), chattingNo);

        ChattingDetailResponseDTO messages = chattingService.getChattingDetail(loginUserInfo.getUserAccount(), chattingNo);
        return ResponseEntity.ok().body(messages);
    }

    //메세지 저장하기
    @PostMapping("/messages")
    public ResponseEntity<?> sendMessage(
            HttpSession session,
            @RequestBody MessageRequestDTO dto
    ) {
        LoginUserResponseDTO loginUserInfo = (LoginUserResponseDTO) session.getAttribute(LOGIN_KEY);
        Chatting chatting = chattingService.findByChattingNo(dto.getChattingNo());
        boolean accessFlag = false;

        accessFlag = isMyChatting(loginUserInfo, chatting);

        if (!accessFlag) {
            return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).build();
        }

        boolean flag = messageService.saveMessage(dto);
        return ResponseEntity.ok().body(flag);
    }

    //채팅별 안읽은 메세지 개수 읽어오기
    // 추후 session에 회원정보 담기면 경로 수정
    @GetMapping("/messages/unread/{chattingNo}")
    public ResponseEntity<?> getUnreadMessageCount(
            HttpSession session
//            @PathVariable String userId
            , @PathVariable long chattingNo
    ) {
        LoginUserResponseDTO loginUserInfo = (LoginUserResponseDTO) session.getAttribute(LOGIN_KEY);
        Chatting chatting = chattingService.findByChattingNo(chattingNo);
        boolean accessFlag = false;

        accessFlag = isMyChatting(loginUserInfo, chatting);

        if (!accessFlag) {
            return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).build();
        }

        User user = userRepository.findById(loginUserInfo.getUserAccount()).orElseThrow();
        int unreadMessages = chattingService.countUnreadMessages(chatting, user);
        Message recentMessage = getRecentMessage(chatting);

        RecentMessageResponseDTO recentMessageResponseDTO = RecentMessageResponseDTO.builder()
                .unreadCount(unreadMessages)
                .message(recentMessage.getMessageContent())
                .build();
        recentMessageResponseDTO.makeShortenMessage(recentMessageResponseDTO.getMessage());
        return ResponseEntity.ok().body(recentMessageResponseDTO);
    }

    // 전체 안읽은 메세지 개수 읽어오기
    @GetMapping("/messages/unread")
    public ResponseEntity<?> getTotalUnreadMessageCount(
//            @PathVariable String userId
            HttpSession session
    ) {
        LoginUserResponseDTO loginUserInfo = (LoginUserResponseDTO) session.getAttribute(LOGIN_KEY);
        User user = null;
        try {
            user = userRepository.findById(loginUserInfo.getUserAccount()).orElseThrow();
        } catch (NullPointerException e) {
            return ResponseEntity.badRequest().body("로그인하고 이용해주세요");
        }
        int totalUnreadMessages = chattingService.getTotalUnreadMessageCount(user);

        return ResponseEntity.ok().body(totalUnreadMessages);
    }

    // 최신 메세지 읽어오기
    public Message getRecentMessage(Chatting chatting){
       return messageService.findByChattingOrderByMessageTimeDesc(chatting);
    }

    //    채팅 신청하기
    @PostMapping("/chattings/new")
    public ResponseEntity<?> makeChatting(
            HttpSession session,
            @RequestBody String userAccount
//            ,@PathVariable String userAccount
    ) {
        LoginUserResponseDTO loginUserInfo = (LoginUserResponseDTO) session.getAttribute(LOGIN_KEY);
//        System.out.println("userId = " + loginUserInfo.getUserAccount());
//        System.out.println("userAccount" + userAccount);
        long chattingNo = 0;
        try {
            chattingNo = chattingService.makeChatting(loginUserInfo.getUserAccount(), userAccount);
        } catch (NullPointerException e) {
            return ResponseEntity.badRequest().body("로그인하고 이용해주세요");
        }
        return ResponseEntity.ok().body(chattingNo);
    }

    //메세지 읽기
    @RequestMapping(value = "/messages/read", method = {RequestMethod.PATCH, RequestMethod.PUT})
    public ResponseEntity<?> readMessages(@RequestBody long chattingNo, HttpSession session) {

        LoginUserResponseDTO loginUserInfo = (LoginUserResponseDTO) session.getAttribute(LOGIN_KEY);
        Chatting chatting = chattingService.findByChattingNo(chattingNo);
        boolean accessFlag = isMyChatting(loginUserInfo, chatting);
        if (!accessFlag) {
            return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).build();
        }
        String userAccount = loginUserInfo.getUserAccount();
        messageService.readMessage(userAccount, chattingNo);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
