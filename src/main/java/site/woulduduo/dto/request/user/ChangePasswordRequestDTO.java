package site.woulduduo.dto.request.user;

import lombok.*;

@Setter @Getter
@ToString @EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangePasswordRequestDTO {

    private String userPassword;

    private String newPassword;
}
