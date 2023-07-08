package site.woulduduo.interceptor;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.WebUtils;
import site.woulduduo.entity.User;
import site.woulduduo.repository.UserRepository;
import site.woulduduo.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.time.LocalDateTime;

import static site.woulduduo.util.LoginUtil.*;

@Configuration
@RequiredArgsConstructor
public class AutoLoginInterceptor implements HandlerInterceptor {

    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 자동로그인 쿠키를 탐색
        Cookie c = WebUtils.getCookie(request, AUTO_LOGIN_COOKIE);

        if (c != null) {

            // 쿠키에 저장된 세션아이디를 읽는다.
            String sessionId = c.getValue();

            // DB에서 세션아이디로 회원정보를 조회한다.
            User foundUser = userRepository.findUserByCookie(sessionId);

            // 회원이 조회가 되었고 자동로그인 만료일 이전이라면
            if (foundUser != null && LocalDateTime.now().isBefore(foundUser.getUserCookieLimitTime())) {
                // 로그인 처리
                userService.maintainLoginState(request.getSession(), foundUser.getUserAccount());
            }
        }
        return true;
    }

}