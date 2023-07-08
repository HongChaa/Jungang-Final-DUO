package site.woulduduo.dto.response.accuse;

import lombok.*;
import site.woulduduo.entity.Accuse;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccuseListResponseDTO {

    private long accuseNo;
    private String userNickname;
    private String accuseType;
    private String accuseETC;
    private LocalDate accuseWrittenDate;


    public AccuseListResponseDTO(Accuse accuse){
        this.accuseNo=accuse.getAccuseNo();
        this.userNickname=accuse.getUser().getUserNickname();
        this.accuseType=accuseType(accuse,20);
        this.accuseETC=accuse.getAccuseEtc();
        this.accuseWrittenDate=accuseDate(accuse.getAccuseWrittenDate());
    }

    public LocalDate accuseDate(LocalDateTime dateTime){

        LocalDate localDate = dateTime.toLocalDate();
        return localDate;
    }
    public String accuseType(Accuse accuse, int maxLength) {
        String string = accuse.getAccuseType().toString();
        int length = string.length();
        if (length <= maxLength) {
            return string;
        }else{
            return string.substring(0,maxLength)+"...";
        }
    }
}
