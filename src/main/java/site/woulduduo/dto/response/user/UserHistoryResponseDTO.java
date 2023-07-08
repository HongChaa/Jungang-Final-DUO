package site.woulduduo.dto.response.user;

import lombok.*;
import site.woulduduo.dto.riot.MostChampInfo;
import site.woulduduo.enumeration.Position;
import site.woulduduo.enumeration.Tier;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class UserHistoryResponseDTO {

    // 데이터베이스에서 가져오는 유저 정보
    private String userAccount;
    // 가장 나중에 추가된 프로필사진
    private String profileImage;
    private String userNickname;
    private Position userPosition;
    // session 에 로그인된 사용자가 이 사용자를 팔로우했는지 여부
    private boolean isFollowed;
    private String userAvgRate;
    private Integer userMatchingPoint;
    private String userInstagram;
    private String userFacebook;
    private String userTwitter;
    private String lolNickname;
    private String userComment;
    private String tier;
    private String rank;

    // riot api 를 통해 얻어오는 데이터
    private int leaguePoints;
    private int totalWinCount;
    private int totalLoseCount;
    private double winRate;

    private int last20WinCount;
    private int last20LoseCount;
    private double last20WinRate;

    private String last20KDA;
    private int last20Kill;
    private int last20Death;
    private int last20Assist;

    private List<MostChampInfo> mostChampInfos;

    private List<MatchResponseDTO> last20Matches;

    // 빌더 커스텀
    public static class UserHistoryResponseDTOBuilder {

        public UserHistoryResponseDTOBuilder tier(Tier tier) {
            switch (tier) {
                case CHA:
                    this.tier = "Challenger";
                    break;
                case IRO:
                    this.tier = "Iron";
                    break;
                case BRO:
                    this.tier = "Bronze";
                    break;
                case SIL:
                    this.tier = "Silver";
                    break;
                case GOL:
                    this.tier = "Gold";
                    break;
                case PLA:
                    this.tier = "Platinum";
                    break;
                case DIA:
                    this.tier = "Diamond";
                    break;
                case MAS:
                    this.tier = "Master";
                    break;
                case GRA:
                    this.tier = "GrandMaster";
                    break;
                default:
                    this.tier = null;
            }
            return this;
        }

        public UserHistoryResponseDTOBuilder last20Matches(List<MatchResponseDTO> last20Matches) {
            this.last20Matches = last20Matches;
            this.last20WinCount = (int) last20Matches.stream()
                    .filter(MatchResponseDTO::isWin).count();
            this.last20LoseCount = (int) last20Matches.stream()
                    .filter(m -> !m.isWin()).count();
            this.last20WinRate = Math.round((double) this.last20WinCount / (this.last20WinCount + this.last20LoseCount) * 100);
            this.last20Kill = last20Matches.stream()
                    .mapToInt(MatchResponseDTO::getKills)
                    .sum();
            this.last20Death = last20Matches.stream()
                    .mapToInt(MatchResponseDTO::getDeaths)
                    .sum();
            this.last20Assist = last20Matches.stream()
                    .mapToInt(MatchResponseDTO::getAssists)
                    .sum();
            this.last20KDA = String.format("%.2f", ((double) last20Kill + last20Assist) / last20Death);

            return this;
        }
    }
}
