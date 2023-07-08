package site.woulduduo.dto.request.profile;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Setter @Getter
@ToString @EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileRegisterRequestDTO {

    private String userAccount;

    private List<MultipartFile> profileImages;
}
