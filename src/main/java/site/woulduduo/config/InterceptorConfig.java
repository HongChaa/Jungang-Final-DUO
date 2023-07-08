package site.woulduduo.config;

// 인터셉터 관련 설정 등록

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import site.woulduduo.interceptor.AfterLoginInterceptor;
import site.woulduduo.interceptor.AutoLoginInterceptor;

@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {

    private final AfterLoginInterceptor afterLoginInterceptor;
    private final AutoLoginInterceptor autoLoginInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //로그인 후처리 인터셉터 설정
        registry.addInterceptor(afterLoginInterceptor)
                .addPathPatterns("/user/sign-in", "/user/sign-up");

        // 자동 로그인 인터셉터 설정
        registry.addInterceptor(autoLoginInterceptor)
                .addPathPatterns("/**");

    }
}
