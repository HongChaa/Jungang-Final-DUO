package site.woulduduo.dto.riot;

import lombok.*;
import site.woulduduo.enumeration.Tier;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class LeagueV4DTO {

    // 필요한 데이터가 있다면 주석 해제하여 사용
    private String leagueId;
    private String queueType;
    private String tier;
    private String rank;
//    private String summonerId;
//    private String summonerName;
    private int leaguePoints;
    private int wins;
    private int losses;
//    private boolean veteran;
//    private boolean inactive;
//    private boolean freshBlood;
//    private boolean hotStreak;

    public Tier getTierEnum() {
        switch (this.tier) {
            case "IRON":
                return Tier.IRO;
            case "BRONZE":
                return Tier.BRO;
            case "SILVER":
                return Tier.SIL;
            case "GOLD":
                return Tier.GOL;
            case "PLATINUM":
                return Tier.PLA;
            case "DIAMOND":
                return Tier.DIA;
            case "MASTER":
                return Tier.MAS;
            case "GRANDMASTER":
                return Tier.GRA;
            case "CHALLENGER":
                return Tier.CHA;
            default:
                return Tier.UNR;
        }
    }

    public double getWinRate() {
        return (double) wins / (wins + losses);
    }
}
