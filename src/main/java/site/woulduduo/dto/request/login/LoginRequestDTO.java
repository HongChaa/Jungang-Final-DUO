package site.woulduduo.dto.request.login;

import lombok.*;

@Setter @Getter
@NoArgsConstructor
@ToString @EqualsAndHashCode
public class LoginRequestDTO {

    private String userAccount;

    private String userPassword;

    private String requestURI;

    private boolean autoLogin;
}
