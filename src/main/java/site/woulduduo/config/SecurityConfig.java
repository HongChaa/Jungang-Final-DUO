package site.woulduduo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    // 비밀번호 암호화 객체 빈 등록
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    // 시큐리티 설정을 처리하는 빈 등록
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 초기에 뜨는 강제 인증 해제
        http.csrf().disable() // csrf토큰 방어 해제
                .authorizeRequests()
                .antMatchers("/**")
                .permitAll();

        return http.build();
    }
}
