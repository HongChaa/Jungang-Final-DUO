package site.woulduduo.dto.response.user;

import lombok.*;

import java.util.List;

@Setter @Getter
@ToString @EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetailResponseDTO {

    private String userNickname;

    private String userBirthday;

    private String lolNickname;

    private String userInstagram;

    private String userFacebook;

    private String userTwitter;

    private int receivedMatchingM;

    private int receivedMatchingF;

    private int successedMatchingM;

    private int successedMatchingF;

    private int follow;

    private int following;

    private int followerRank;

    private int accuseCount;

    private double avgRate;

    private int activityPoint;

    private int boardCount;

    private int replyCount;

    private int dailyPoint;

    private int monthlyPoint;

    private int totalGetPoint;

    private int userCurrentPoint;

    private List<String> userProfiles;


//    public void calculateActivityPoint(int followers, int posts, int comments) {
//        int followerPoints = followers * 3;
//        int postPoints = posts * 2;
//        int commentPoints = comments * 1;
//
//        activityPoint = followerPoints + postPoints + commentPoints;


}
