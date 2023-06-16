package site.woulduduo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import site.woulduduo.dto.riot.LeagueV4DTO;
import site.woulduduo.dto.riot.MatchV5DTO;
import site.woulduduo.dto.riot.SummonerV4DTO;
import site.woulduduo.enumeration.Tier;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RiotApiService {

    // 라이엇 요청 uri 공통
    @Value("${riot.api.request.uri.common}")
    private String RIOT_URI;

    // 라이엇 api key
    @Value("${riot.api.key}")
    private String RIOT_API_KEY;

    // 매치 데이터 요청 uri
    @Value("${riot.api.request.uri.matches}")
    private String RIOT_MATCH_URI;

    /**
     * 티어 정보를 얻는 메서드
     *
     * @param lolNickname - 롤 닉네임
     * @return - 티어 열거형
     */
    public Tier getTier(String lolNickname) {

        LeagueV4DTO[] responseDTO = getLeagueV4DTO(lolNickname);

        LeagueV4DTO soloRankInfo = null;
        try {
            for (LeagueV4DTO leagueV4DTO : responseDTO) {
                if (leagueV4DTO.getQueueType().equals("RANKED_SOLO_5x5")) {
                    soloRankInfo = leagueV4DTO;
                    break;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            // 솔로랭크 정보가 없을 때
            return Tier.UNR;
        }

        return Objects.requireNonNull(soloRankInfo).getTierEnum();
    }

    /**
     * 랭크 정보 데이터를 담은 DTO 를 반환
     *
     * @param lolNickname - 소환사명
     * @return - 랭크 정보 데이터 배열 (0번은 솔로랭크, 1번은 자유랭크)
     */
    private LeagueV4DTO[] getLeagueV4DTO(String lolNickname) {
        String encryptedSummonerId = getSummonerV4DTO(lolNickname).getId();

        String leagueV4RequestUri = RIOT_URI + "/league/v4/entries/by-summoner/"
                + encryptedSummonerId + "?api_key=" + RIOT_API_KEY;

        HttpHeaders httpHeaders = getHttpHeaders();

        ResponseEntity<LeagueV4DTO[]> responseEntity = new RestTemplate().exchange(
                leagueV4RequestUri,
                HttpMethod.GET,
                new HttpEntity<>(httpHeaders),
                LeagueV4DTO[].class
        );

        return responseEntity.getBody();
    }

    /**
     * 소환사 명을 입력받아 정보 DTO 를 얻어내는 메서드
     *
     * @param lolNickname - 소환사명
     * @return - 소환사 정보 DTO
     */
    public SummonerV4DTO getSummonerV4DTO(String lolNickname) {

        // 요청 URI
        String summonerV4RequestUri = RIOT_URI + "/summoner/v4/summoners/by-name/"
                + lolNickname + "?api_key=" + RIOT_API_KEY;

        // 요청 헤더 설정
        HttpHeaders httpHeaders = getHttpHeaders();

        // 요청 보내기
        // 소환사 정보가 없을 시 HttpClientErrorException.NotFound 가 발생
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<SummonerV4DTO> responseEntity = restTemplate.exchange(
                summonerV4RequestUri,
                HttpMethod.GET,
                new HttpEntity<>(httpHeaders),
                SummonerV4DTO.class
        );

        return responseEntity.getBody();
    }

    /**
     * 요청 헤더를 설정
     *
     * @return - 요청 헤더
     */
    private static HttpHeaders getHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36");
        httpHeaders.add("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7");
        httpHeaders.add("Accept-Charset", "application/x-www-form-urlencoded; charset=UTF-8");
        httpHeaders.add("Origin", "https://developer.riotgames.com");
        return httpHeaders;
    }

    /**
     * 최근 n 개의 게임정보를 받아오는 메서드
     *
     * @param puuid - 소환사의 puuid
     * @return - 20 개의 matchId
     */
    public List<String> getLast20Games(String puuid) {

        String requestUri = RIOT_MATCH_URI + "/by-puuid/" + puuid + "/ids?start=0&count=" + 20 +
                "&api_key=" + RIOT_API_KEY;

        HttpHeaders httpHeaders = getHttpHeaders();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String[]> responseEntity = restTemplate.exchange(
                requestUri,
                HttpMethod.GET,
                new HttpEntity<>(httpHeaders),
                String[].class
        );

        return List.of(Objects.requireNonNull(responseEntity.getBody()));

    }

    /**
     * 매치 정보를 얻어오는 메서드
     *
     * @param matchId - 매치 아이디
     * @return - 매치 정보
     */
    public MatchV5DTO getMatchInfo(String matchId) {

        String requestUri = RIOT_MATCH_URI + "/" + matchId + "?api_key=" + RIOT_API_KEY;

        HttpHeaders httpHeaders = getHttpHeaders();

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<MatchV5DTO> responseEntity = restTemplate.exchange(
                requestUri,
                HttpMethod.GET,
                new HttpEntity<>(httpHeaders),
                MatchV5DTO.class
        );

        return responseEntity.getBody();
    }

    /**
     * 모스트 챔피언 세 개를 구하는 메서드
     * 모스트 1 이 0 번 인덱스이고 3이 2번 인덱스이다.
     *
     * @param lolNickname - 소환사명
     * @return - 모스트 챔피언 세 개의 이름이 담긴 리스트
     */
    public List<String> getMost3Champions(String lolNickname) {

        List<MatchV5DTO.MatchInfo.ParticipantDTO> last20ParticipantDTOList = getLast20ParticipantDTOList(lolNickname);

        return getTop3PopularityItems(last20ParticipantDTOList.stream()
                .map(MatchV5DTO.MatchInfo.ParticipantDTO::getChampionName)
                .collect(Collectors.toList()), 3);
    }

    /**
     * 최근 해당 소환사의 20게임의 정보를 구하는 메서드
     *
     * @param lolNickname - 소환사명
     * @return - 해당 소환사의 20게임 내 정보
     */
    private List<MatchV5DTO.MatchInfo.ParticipantDTO> getLast20ParticipantDTOList(String lolNickname) {
        // puuid를 얻어온다.
        String puuid = getSummonerV4DTO(lolNickname).getPuuid();

        List<MatchV5DTO> last20MatchInfo = getLast20MatchInfo(lolNickname);

        // 자기의 정보만 추출한다.
        return last20MatchInfo.stream()
                .map(m -> {

                    // 플레이어 중에서 해당 소환사의 인덱스
                    String[] participants = m.getMetadata().getParticipants();

                    int index = 0;
                    for (int i = 0; i < participants.length; i++) {
                        if (participants[i].equals(puuid))
                            index = i;
                    }

                    // 해당 소환사의 매치 정보
                    return m.getInfo().getParticipants()[index];

                }).collect(Collectors.toList());
    }

    /**
     * 최근 20 게임의 매치 정보를 구하는 메서드
     *
     * @param lolNickname - 소환사명
     * @return -
     */
    private List<MatchV5DTO> getLast20MatchInfo(String lolNickname) {
        SummonerV4DTO v4DTO = getSummonerV4DTO(lolNickname);

        List<String> last20Games = getLast20Games(v4DTO.getPuuid());

        return last20Games.stream()
                .map(this::getMatchInfo).collect(Collectors.toList());
    }

    /**
     * 리스트에서 가장 많이 들어있는 n 개를 구하는 메서드
     * @param items - 리스트
     * @param n - 갯수
     * @return - n 개의 리스트
     */
    public static <T> List<T> getTop3PopularityItems(List<T> items, int n) {
        // Step 1: Create a HashMap to store the frequency of each item
        Map<T, Integer> frequencyMap = new HashMap<>();

        // Step 2: Iterate over the list and update the frequency in the HashMap
        for (T item : items) {
            frequencyMap.put(item, frequencyMap.getOrDefault(item, 0) + 1);
        }

        // Step 3: Sort the entries of the HashMap based on their values in descending order
        List<Map.Entry<T, Integer>> sortedEntries = new ArrayList<>(frequencyMap.entrySet());
        sortedEntries.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        // Step 4: Extract the top 3 entries from the sorted HashMap
        List<Map.Entry<T, Integer>> top3Entries = sortedEntries.subList(0, Math.min(n, sortedEntries.size()));

        // Step 5: Store the keys (popularity items) of the top 3 entries in a separate list
        List<T> top3PopularityItems = new ArrayList<>();
        for (Map.Entry<T, Integer> entry : top3Entries) {
            top3PopularityItems.add(entry.getKey());
        }

        return top3PopularityItems;
    }

}
