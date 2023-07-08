package site.woulduduo.dto.request.page;

import lombok.*;
import site.woulduduo.enumeration.Gender;
import site.woulduduo.enumeration.Position;
import site.woulduduo.enumeration.Tier;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@ToString
public class UserSearchType extends PageDTO{

    @Enumerated(EnumType.STRING)
    private Position position;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Tier tier;

    private String followers;

    private String sort;

    public UserSearchType() {
        this.setSize(40);
//        this.sort = "userAvgRate";
    }
}
