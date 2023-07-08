package site.woulduduo.dto.response.user;

import lombok.*;
import site.woulduduo.entity.Matching;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class UserReviewResponseDTO {

    private String userNickname;
    private String profileImage;
    private Integer matchingReviewRate;
    private String matchingReviewContent;

    public UserReviewResponseDTO(Matching matching) {
        this.userNickname = matching.getChatting().getChattingFrom().getUserNickname();
        this.profileImage = matching.getChatting().getChattingFrom().getLatestProfileImage();
        this.matchingReviewRate = matching.getMatchingReviewRate();
        this.matchingReviewContent = matching.getMatchingReviewContent();
    }

}
