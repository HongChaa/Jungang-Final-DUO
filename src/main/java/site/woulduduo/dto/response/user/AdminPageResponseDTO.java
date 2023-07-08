package site.woulduduo.dto.response.user;

import lombok.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminPageResponseDTO {

    private int todayJoinCount;
    private int totalJoinCount;
    private int todayAccuseCount;
    private int totalAccuseCount;
    private int todayBoardCount;
    private int totalBoardCount;

}
