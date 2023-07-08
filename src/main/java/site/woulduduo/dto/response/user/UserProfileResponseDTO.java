package site.woulduduo.dto.response.user;

import lombok.*;
import site.woulduduo.entity.MostChamp;
import site.woulduduo.enumeration.Gender;
import site.woulduduo.enumeration.Position;
import site.woulduduo.enumeration.Tier;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileResponseDTO {

    private String userAccount;

    @Enumerated(EnumType.STRING)
    private Gender userGender;

    private String userComment;

    private int userMatchingPoint;

//    @Enumerated(EnumType.STRING)
    private String tier;

    private String userInstagram;

    private String userFacebook;

    private String userTwitter;

    private boolean isFollowed;

    @Enumerated(EnumType.STRING)
    private Position userPosition;

    private String userNickname;

    private double avgRate;

    private List<MostChamp> mostChampList;

    private String profileImage;

}
