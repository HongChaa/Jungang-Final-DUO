package site.woulduduo.dto.request.accuse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import site.woulduduo.entity.Accuse;

import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAccuseRequestDTO {
    private String userNickname;
    @JsonProperty("accuseType")
    private List<String> accuseType;
    @JsonProperty("accuseEtc")
    private String accuseEtc;

    public Accuse toEntity(){
        Accuse accuse = Accuse.builder()
                .accuseType(this.accuseType.toString())
                .accuseEtc(this.accuseEtc)
                .build();

        return accuse;
    }

}
