package site.woulduduo.dto.response.user;

import lombok.*;
import site.woulduduo.dto.riot.MatchV5DTO;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class MatchResponseDTO {

    private int assists;
    private String championName;
    private int deaths;
    private int item0;
    private int item1;
    private int item2;
    private int item3;
    private int item4;
    private int item5;
    private int item6;
    private int kills;
    private MatchV5DTO.MatchInfo.ParticipantDTO.PerksDTO perks;
    private String summonerName;
    private int summoner1Id;
    private int summoner2Id;
    private boolean win;
    private String avgKda;

    // 룬특성 정보
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @ToString
    @Builder
    public static class PerksDTO {
        private MatchV5DTO.MatchInfo.ParticipantDTO.PerksDTO.PerkStyleDTO[] styles;

        // 룬 정보는 runesReforged.json 에 있다.
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        @EqualsAndHashCode
        @ToString
        @Builder
        public static class PerkStyleDTO {

            // 주 또는 보조 룬인지 판단
            private String description;
            // 어떤 룬을 찍었는지
            private MatchV5DTO.MatchInfo.ParticipantDTO.PerksDTO.PerkStyleDTO.PerStyleSelectionDTO[] selections;
            // 룬 카테고리
            private int style;

            @Getter
            @NoArgsConstructor
            @AllArgsConstructor
            @EqualsAndHashCode
            @ToString
            @Builder
            public static class PerStyleSelectionDTO {

                // 어떤 룬인지
                private int perk;

            }
        }
    }

    public MatchResponseDTO(MatchV5DTO.MatchInfo.ParticipantDTO p) {
        this.assists = p.getAssists();
        this.championName = p.getChampionName();
        this.deaths = p.getDeaths();
        this.item0 = p.getItem0();
        this.item1 = p.getItem1();
        this.item2 = p.getItem2();
        this.item3 = p.getItem3();
        this.item4 = p.getItem4();
        this.item5 = p.getItem5();
        this.item6 = p.getItem6();
        this.kills = p.getKills();
        this.perks = p.getPerks();
        this.summonerName = p.getSummonerName();
        this.summoner1Id = p.getSummoner1Id();
        this.summoner2Id = p.getSummoner2Id();
        this.win = p.isWin();
        this.avgKda = String.format("%.2f", ((double) kills + assists) / deaths);
    }
}
