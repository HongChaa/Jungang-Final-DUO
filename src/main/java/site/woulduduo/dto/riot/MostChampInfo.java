package site.woulduduo.dto.riot;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class MostChampInfo {

    private String champName;
    private int winCount;
    private int loseCount;
    private int winRate;
    private double kda;

}