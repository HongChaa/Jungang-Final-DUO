package site.woulduduo.dto.request.profile;

import lombok.*;

@Setter @Getter
@ToString @EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileDeleteRequestDTO {

    private String userAccount;
    private long profileNo;
}
