package site.woulduduo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import site.woulduduo.dto.request.chatting.GivePointRequestDTO;
import site.woulduduo.dto.response.login.LoginUserResponseDTO;
import site.woulduduo.entity.Chatting;
import site.woulduduo.entity.Matching;
import site.woulduduo.service.ChattingService;
import site.woulduduo.service.MatchingService;
import site.woulduduo.service.PointService;

import javax.servlet.http.HttpSession;

import static site.woulduduo.util.LoginUtil.LOGIN_KEY;
import static site.woulduduo.util.LoginUtil.isMyChatting;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;
    private final ChattingService chattingService;
    private final MatchingService matchingService;

    //매칭 신청하려는 사람 포인트가 충분한지 확인
    @ResponseBody
    @GetMapping("/api/v1/points/check/{chattingNo}")
    public ResponseEntity<?> checkCurrentPoint(
            HttpSession session,
            @PathVariable long chattingNo){
        LoginUserResponseDTO loginUserInfo = (LoginUserResponseDTO) session.getAttribute(LOGIN_KEY);
        Chatting chatting = chattingService.findByChattingNo(chattingNo);
        boolean accessFlag = isMyChatting(loginUserInfo, chatting);
        if(!accessFlag){
            return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).build();
        }

        int result = pointService.checkCurrentPoint(chattingNo);
        return ResponseEntity.ok().body(result);
    }

    //매칭 요청한 회원 포인트 차감하기
    @ResponseBody
    @PostMapping("/api/v1/points/pay")
    public ResponseEntity<?> payMatchingPoint(
            HttpSession session,
            @RequestBody GivePointRequestDTO dto){
        LoginUserResponseDTO loginUserInfo = (LoginUserResponseDTO) session.getAttribute(LOGIN_KEY);
        Chatting chatting = chattingService.findByChattingNo(dto.getChattingNo());
        boolean accessFlag = isMyChatting(loginUserInfo, chatting);
        if(!accessFlag){
            return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).build();
        }

        int paidPoints = pointService.payMatchingPoint(dto.getChattingNo(), dto.getMatchingNo());
        return ResponseEntity.ok().body(paidPoints);
    }


    //매칭 요청받은 듀오에게 포인트 지급하기
    @ResponseBody
    @PostMapping("/api/v1/points/give")
    public ResponseEntity<?> giveMatchingPoint(
            HttpSession session,
            @RequestBody GivePointRequestDTO dto){

        LoginUserResponseDTO loginUserInfo = (LoginUserResponseDTO) session.getAttribute(LOGIN_KEY);
        Chatting chatting = chattingService.findByChattingNo(dto.getChattingNo());
        boolean accessFlag = isMyChatting(loginUserInfo, chatting);
        if(!accessFlag){
            return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).build();
        }

        int givenPoints = pointService.giveMatchingPoint(dto.getChattingNo(), dto.getMatchingNo());
        return ResponseEntity.ok().body(givenPoints);
    }

    //포인트 지급여부 확인
    @ResponseBody
    @GetMapping("/api/v1/points/matching/{matchingNo}")
    public ResponseEntity<?> searchPointHistory(
            HttpSession session,
            @PathVariable long matchingNo){

        LoginUserResponseDTO loginUserInfo = (LoginUserResponseDTO) session.getAttribute(LOGIN_KEY);
        Matching matching = matchingService.findByMatchingNo(matchingNo);
        Chatting chatting = chattingService.findByChattingNo(matching.getChatting().getChattingNo());
        boolean accessFlag = isMyChatting(loginUserInfo, chatting);
        if(!accessFlag){
            return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).build();
        }

        boolean flag = pointService.searchPointHistory(matchingNo);
        return ResponseEntity.ok().body(flag);
    }
}
