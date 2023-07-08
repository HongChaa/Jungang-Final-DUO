package site.woulduduo.dto.request.user;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserModifyRequestDTO {

    private String userAccount;

    private String userNickname;

    private LocalDate userBirthday;

    private String lolNickname;

    private String userPassword;

    private String userInstagram;

    private String userFacebook;

    private String userTwitter;

    private int userCurrentPoint;

    private int userAddPoint;

    private int userIsBanned;
}
