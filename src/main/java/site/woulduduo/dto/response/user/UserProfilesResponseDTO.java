package site.woulduduo.dto.response.user;

import lombok.*;
import site.woulduduo.enumeration.Gender;
import site.woulduduo.enumeration.Position;
import site.woulduduo.enumeration.Tier;

import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfilesResponseDTO {

    private String userAccount;

    private Gender gender;

    private String comment;

    private int matchingPoint;

    private Tier tier;

    private String instagram;

    private String facebook;

    private String twitter;

    private boolean isFollowed;

    private Position position;  // 디비에서 꺼낸거 넣는건데 String 안되나

    private String nickname;

    private double avgRate;

    private List<String> mostChampList;

    private String profileImage;
}
