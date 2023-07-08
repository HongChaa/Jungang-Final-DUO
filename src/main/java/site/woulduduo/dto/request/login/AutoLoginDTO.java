package site.woulduduo.dto.request.login;

import lombok.*;

import java.time.LocalDateTime;

@Setter @Getter
@ToString @EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AutoLoginDTO {

    private String userSessionId;

    private LocalDateTime userCookieLimitTime;

    private String userAccount;
}
