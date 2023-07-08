package site.woulduduo.dto.response.user;

import lombok.*;
import site.woulduduo.entity.Matching;

import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class MyPageReviewResponseDTO {

    private Long matchingNo;
    private String opponentNickname;
    private String matchingDate;
    private String matchingReviewContent;
    private String profileImage;

    public MyPageReviewResponseDTO(Matching matching, boolean isGotten) {

        this.matchingNo = matching.getMatchingNo();

        if (isGotten) {
            this.opponentNickname = matching.getChatting().getChattingFrom().getUserNickname();
        } else {
            this.opponentNickname = matching.getChatting().getChattingTo().getUserNickname();
            this.profileImage = matching.getChatting().getChattingTo().getLatestProfileImage();
        }

        if (matchingDate == null) {
            this.matchingDate = null;
        } else
            this.matchingDate = matching.getMatchingDate().format(DateTimeFormatter.ofPattern("yy-MM-dd"));

        if (matching.getMatchingReviewContent() != null && matching.getMatchingReviewContent().length() > 20) {
            this.matchingReviewContent = matching.getMatchingReviewContent().substring(0, 17) + "...";
        } else {
            this.matchingReviewContent = matching.getMatchingReviewContent();
        }
    }
}
