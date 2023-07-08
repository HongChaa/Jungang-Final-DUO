package site.woulduduo.dto.request.profile;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Setter @Getter
@ToString @EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileAddRequestDTO {

    private String userAccount;

    private MultipartFile profileImage;
}
