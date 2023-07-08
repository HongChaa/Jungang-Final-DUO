package site.woulduduo.dto.response.login;

import lombok.*;
import site.woulduduo.entity.UserProfile;
import site.woulduduo.enumeration.Position;
import site.woulduduo.enumeration.Role;

import java.time.LocalDate;
import java.util.List;

@Setter @Getter
@ToString @EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginUserResponseDTO {

    private String userAccount;

    private String userNickname;

    private String lolNickname;

    private Integer userCurrentPoint;

    private Position userPosition;

    private String userComment;

    private int userMatchingPoint;

    private String userProfileImage;

    private Role role;

    private LocalDate userBirthday;

    private String userInstagram;

    private String userFacebook;

    private String userTwitter;

    // 프로필 사진들중에서 가장최신 1장만 뽑는 메서드
    private String getLatestProfileImage(List<UserProfile> userProfileList) {
        if (userProfileList != null && !userProfileList.isEmpty()) {
            UserProfile latestProfile = userProfileList.get(userProfileList.size() - 1);
            return latestProfile.getProfileImage();
        }
        return null;
    }
}
