package site.woulduduo.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.woulduduo.dto.request.chatting.ReviewWriteRequestDTO;
import site.woulduduo.dto.request.matching.MatchingFixRequestDTO;
import site.woulduduo.dto.response.ListResponseDTO;
import site.woulduduo.dto.response.login.LoginUserResponseDTO;
import site.woulduduo.dto.response.user.MyPageReviewResponseDTO;
import site.woulduduo.dto.response.user.UserReviewResponseDTO;
import site.woulduduo.entity.Chatting;
import site.woulduduo.entity.Matching;
import site.woulduduo.service.ChattingService;
import site.woulduduo.service.MatchingService;

import javax.servlet.http.HttpSession;

import static site.woulduduo.util.LoginUtil.LOGIN_KEY;
import static site.woulduduo.util.LoginUtil.isMyChatting;

@RestController
@RequestMapping("/api/v1/matchings")
@RequiredArgsConstructor
@Slf4j
public class MatchingController {

    private final MatchingService matchingService;
    private final ChattingService chattingService;

    //채팅의 가장 최신 매칭번호 구해오기
    @GetMapping("/{chattingNo}")
    public ResponseEntity<?> getMatchingNo(
            HttpSession session,
            @PathVariable long chattingNo){

        LoginUserResponseDTO loginUserInfo = (LoginUserResponseDTO) session.getAttribute(LOGIN_KEY);
        Chatting chatting = chattingService.findByChattingNo(chattingNo);
        boolean accessFlag = isMyChatting(loginUserInfo, chatting);
        if(!accessFlag){
            return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).build();
        }

        Long matchingNo = null;
        try {
            matchingNo = matchingService.findMatchingByChatting(chattingNo).get(0).getMatchingNo();
            return ResponseEntity.ok().body(matchingNo);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * 리뷰 작성
     * @param session - 본인인지 확인하기용 세션
     * @param dto - 작성 요청 dto
     * @return - 작성 결과를 담은 응답객체
     */
    @RequestMapping(value = "/reviews", method = {RequestMethod.PATCH, RequestMethod.PUT})
    public ResponseEntity<?> writeReview(HttpSession session, @RequestBody ReviewWriteRequestDTO dto) {

//       세션에 로그인처리가 완료된 이후에 사용
        String userAccount = null;
        try {
            userAccount = ((LoginUserResponseDTO) session.getAttribute(LOGIN_KEY)).getUserAccount();
        } catch (NullPointerException e) {
            return ResponseEntity.badRequest().body("로그인하고 이용해주세요");
        }

//        String userAccount = "user1";

        try {
            matchingService.writeReview(userAccount, dto);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }

    }

    /**
     * 유저가 받은 리뷰를 전적 페이지에 가져오는 메서드
     *
     * @param userAccount - 해당 유저
     * @return - 유저가 받은 리뷰 목록을 담은 응답
     */
    @GetMapping("/reviews/{userAccount}/{pageNo}")
    public ResponseEntity<?> getGottenReview(
            @PathVariable String userAccount,
            @PathVariable int pageNo
    ) {

        ListResponseDTO<UserReviewResponseDTO, Matching> responseDTO = null;
        try {
            responseDTO = matchingService.getGottenReview(userAccount, pageNo);
            return ResponseEntity.ok(responseDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body("알 수 없는 에러가 발생했습니다");
        }

    }

    /**
     * 유저가 받은 리뷰를 전적 페이지에 가져오는 메서드
     *
     * @param userAccount - 해당 유저
     * @return - 유저가 받은 리뷰 목록을 담은 응답
     */
    @GetMapping("/reviews/gotten/{userAccount}/{pageNo}")
    public ResponseEntity<?> getGottenReviewOnMyPage(
            @PathVariable String userAccount,
            @PathVariable int pageNo
    ) {

        log.info("/api/v1/matchings/reviews/gotten/{} {} ", userAccount, pageNo);

        ListResponseDTO<MyPageReviewResponseDTO, Matching> responseDTO = null;
        try {
            responseDTO = matchingService.getGottenReviewOnMyPage(userAccount, pageNo);
            log.info("gotten responseDTO : {}", responseDTO);
            return ResponseEntity.ok(responseDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body("알 수 없는 에러가 발생했습니다");
        }

    }

    /**
     * 유저가 쓴 리뷰를 마이 페이지에 가져오는 메서드
     *
     * @param userAccount - 해당 유저
     * @param pageNo      - 페이지 번호
     * @return - 유저가 쓴 리뷰를 담은 응답
     */
    @GetMapping("/reviews/written/{userAccount}/{pageNo}")
    public ResponseEntity<?> getWrittenReviewOnMyPage(
            @PathVariable String userAccount,
            @PathVariable int pageNo
    ) {

        log.info("/api/v1/matchings/reviews/written/{} {} ", userAccount, pageNo);

        ListResponseDTO<MyPageReviewResponseDTO, Matching> responseDTO = null;
        try {
            responseDTO = matchingService.getWrittenReviewOnMyPage(userAccount, pageNo);
            log.info("written responseDTO : {}", responseDTO);
            return ResponseEntity.ok(responseDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body("알 수 없는 에러가 발생했습니다");
        }

    }

    // 매칭 신청
    @PostMapping
    public ResponseEntity<?> makeMatching(
            HttpSession session,
            @RequestBody long chattingNo) {

        LoginUserResponseDTO loginUserInfo = (LoginUserResponseDTO) session.getAttribute(LOGIN_KEY);
        Chatting chatting = chattingService.findByChattingNo(chattingNo);
        boolean accessFlag = isMyChatting(loginUserInfo, chatting);
        if(!accessFlag){
            return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).build();
        }

        long matchingNo = matchingService.makeMatching(chattingNo);
        return ResponseEntity.ok().body(matchingNo);
    }

    //매칭 확정
    @RequestMapping(value = "/fix", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity<?> fixSchedule(
            HttpSession session,
            @RequestBody MatchingFixRequestDTO dto){

        LoginUserResponseDTO loginUserInfo = (LoginUserResponseDTO) session.getAttribute(LOGIN_KEY);
        Matching matching = matchingService.findByMatchingNo(dto.getMatchingNo());
        Chatting chatting = chattingService.findByChattingNo(matching.getChatting().getChattingNo());
        boolean accessFlag = isMyChatting(loginUserInfo, chatting);
        if(!accessFlag){
            return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).build();
        }

        boolean flag = matchingService.fixSchedule(dto);

        log.info("확정 직전!");
        return ResponseEntity.ok().body(flag);
    }

    //매칭 거절
    @RequestMapping(value = "/reject", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity<?> rejectMatching(
            HttpSession session,
            @RequestBody long matchingNo){

        LoginUserResponseDTO loginUserInfo = (LoginUserResponseDTO) session.getAttribute(LOGIN_KEY);
        Matching matching = matchingService.findByMatchingNo(matchingNo);
        Chatting chatting = chattingService.findByChattingNo(matching.getChatting().getChattingNo());
        boolean accessFlag = isMyChatting(loginUserInfo, chatting);
        if(!accessFlag){
            return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).build();
        }

        boolean flag = matchingService.rejectMatching(matchingNo);
        return ResponseEntity.ok().body(flag);
    }

    //게임 완료
    @RequestMapping(value = "/done", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity<?> gameOverMatching(
            HttpSession session,
            @RequestBody long matchingNo){

        LoginUserResponseDTO loginUserInfo = (LoginUserResponseDTO) session.getAttribute(LOGIN_KEY);
        Matching matching = matchingService.findByMatchingNo(matchingNo);
        Chatting chatting = chattingService.findByChattingNo(matching.getChatting().getChattingNo());
        boolean accessFlag = isMyChatting(loginUserInfo, chatting);
        if(!accessFlag){
            return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).build();
        }

//        log.info("done 컨트롤러 진입");
        boolean flag = matchingService.gameOverMatching(matchingNo);
//        log.info("DONE으로 변경!");
        return ResponseEntity.ok().body(flag);
    }
}
