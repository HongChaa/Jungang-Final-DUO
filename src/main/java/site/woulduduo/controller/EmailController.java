package site.woulduduo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import site.woulduduo.dto.request.user.UserRegisterRequestDTO;
import site.woulduduo.service.EmailService;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

import static site.woulduduo.util.EmailUtil.EMAIL_KEY;

@Slf4j
@RestController
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/user/send-email")
    public ResponseEntity<?> sendEmail(
            HttpSession session,
            @RequestBody UserRegisterRequestDTO dto
    ) {
        log.info("/user/send-email POST : email = {}", dto.getUserEmail());
        try {
            String authCode = emailService.sendEmail(dto.getUserEmail());
            session.setAttribute(EMAIL_KEY, authCode);
            return ResponseEntity.ok().build();
        } catch (MessagingException | UnsupportedEncodingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이메일 전송에 실패했습니다.");
        }
    }

    @PostMapping("/user/check-email")
    public ResponseEntity<?> checkEmail(
            HttpSession session,
            @RequestBody String authCode
    ) {
        if (emailService.checkEmail(session, authCode)) {
            return ResponseEntity.ok("인증성공");
        } else {
            // 인증 실패했을떄 위 메서드처럼 에러 보내도
            return ResponseEntity.status(499).build();
        }
    }


}


