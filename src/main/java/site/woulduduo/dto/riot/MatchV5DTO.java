package site.woulduduo.dto.riot;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class MatchV5DTO {

    private MatchMetaData metadata;

    private MatchInfo info;

    // 매치 메타데이터
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @ToString
    @Builder
    public static class MatchMetaData {

//        private String dataVersion;
//        private String matchId;
        private String[] participants;

    }

    // 게임 정보
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @ToString
    @Builder
    public static class MatchInfo {

        // 플레이어 정보들
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        @EqualsAndHashCode
        @ToString
        @Builder
        public static class ParticipantDTO {

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
            private PerksDTO statPerks;
            private String summonerName;
            // 소환사 주문 정보 summoner.json에 있다.
            private int summoner1Id;
            private int summoner2Id;
            private boolean win;

            // 룬특성 정보
            @Getter
            @NoArgsConstructor
            @AllArgsConstructor
            @EqualsAndHashCode
            @ToString
            @Builder
            public static class PerksDTO {
                private PerkStyleDTO[] styles;

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
                    private PerStyleSelectionDTO[] selections;
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
        }

        private ParticipantDTO[] participants;
    }
}
