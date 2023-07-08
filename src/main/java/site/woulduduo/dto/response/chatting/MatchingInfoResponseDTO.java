package site.woulduduo.dto.response.chatting;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class MatchingInfoResponseDTO {

    //남자에게 받은 매칭 요청
    private int receivedMatchingFromMale;
    //여자에게 받은 매칭 요청
    private int receivedMatchingFromFemale;
    //남자에게 받은 매칭 수락건
    private int confirmedMatchingWithMale;
    //여자에게 받은 매칭 수락건
    private int confirmedMatchingWithFemale;
    //남자와 매칭확정률
    private double confirmRateWithMale;
    //여자와 매칭확정률
    private double confirmRateWithFemale;
    //팔로워
    private int followers;
    //팔로잉
    private int followings;
    //호감도
    private double userAvgRate;
    //팔로워 순위
    private Integer rank;
    //경고횟수
    private long accuseCount;
    //총활동점수
    private long totalScore;
}
