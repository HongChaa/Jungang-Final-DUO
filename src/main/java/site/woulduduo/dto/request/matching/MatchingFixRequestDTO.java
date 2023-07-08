package site.woulduduo.dto.request.matching;

import lombok.*;

import java.time.LocalDate;
import java.util.StringTokenizer;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class MatchingFixRequestDTO {

    private long matchingNo;
    private String matchingDate;

    public LocalDate getLocalDateType(String matchingDate){
        StringTokenizer st = new StringTokenizer(matchingDate, "-");
        return LocalDate.of(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
    }

}
