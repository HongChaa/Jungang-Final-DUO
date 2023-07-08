package site.woulduduo.entity;

import lombok.*;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.CreationTimestamp;
import site.woulduduo.enumeration.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static site.woulduduo.enumeration.LoginType.NORMAL;

@Setter
@Getter
@ToString(exclude = {"replyList", "userProfileList", "attendanceList", "accuseList", "followToList", "followFromList",
        "pointList", "boardList", "chattingToList", "chattingFromList", "attendanceStampList"})
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "userAccount")
@Builder
@Entity
@Table(name = "duo_user")
public class User {

    @Id
    @Column(name = "user_account", length = 50)
    private String userAccount;

    @Column(name = "user_nickname", length = 15, nullable = false, unique = true)
    private String userNickname;

    @Column(name = "user_password", nullable = false)
    private String userPassword;

    @Column(name = "user_birthday", nullable = false)
    @Check(constraints = "TIMESTAMPDIFF(YEAR, user_birthday, CURDATE()) > 18")
    private LocalDate userBirthday;

    @Column(name = "lol_nickname", length = 20, nullable = false, unique = true)
    private String lolNickname;

    @Column(name = "user_gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender userGender;

    @CreationTimestamp
    @Column(name = "user_join_date", updatable = false)
    private LocalDate userJoinDate;

    private LocalDateTime userRecentLoginDate;

    @Builder.Default
    @Column(columnDefinition = "TINYINT(1)")
    private boolean userIsBanned = false;

    @Enumerated(EnumType.STRING)
    private Position userPosition;

    @Column(length = 100)
    private String userComment;

    @Column(length = 3)
    private Integer userMatchingPoint;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Tier lolTier;

    @Builder.Default
    private Integer userCurrentPoint = 1000;

    @Column(length = 30, unique = true)
    private String userTwitter;

    @Column(length = 30, unique = true)
    private String userFacebook;

    @Column(length = 30, unique = true)
    private String userInstagram;

    @Builder.Default
    @Column(columnDefinition = "DOUBLE(2, 1)")
    private Double userAvgRate = 0.0;

    @Column(name = "user_cookie_limit_time")
    private LocalDateTime userCookieLimitTime;

    @Column(length = 200)
    @Builder.Default
    private String userSessionId = "none";

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private LoginType userLoginType = NORMAL;

    // 쓴 댓글들
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Reply> replyList = new ArrayList<>();

    // 프로필 사진들
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @Builder.Default
    private List<UserProfile> userProfileList = new ArrayList<>();

    // 포인트 증감 내역
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @Builder.Default
    private List<Point> pointList = new ArrayList<>();

    // 작성한 게시글들
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Board> boardList = new ArrayList<>();

    // 내가 팔로우한 사람들
    @OneToMany(mappedBy = "followFrom", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @Builder.Default
    private List<Follow> followToList = new ArrayList<>();

    // 나를 팔로우하는 사람들
    @OneToMany(mappedBy = "followTo", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @Builder.Default
    private List<Follow> followFromList = new ArrayList<>();

    // 신고 내역
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @Builder.Default
    private List<Accuse> accuseList = new ArrayList<>();

    // 출석 내역
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @Builder.Default
    private List<Attendance> attendanceList = new ArrayList<>();

    // 출석도장 내역
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @Builder.Default
    private List<AttendanceStamp> attendanceStampList = new ArrayList<>();

    // 내가 건 채팅 내역
    @OneToMany(mappedBy = "chattingFrom", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Chatting> chattingToList = new ArrayList<>();

    // 내가 받은 채팅 내역
    @OneToMany(mappedBy = "chattingTo", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Chatting> chattingFromList = new ArrayList<>();

    // 내 모스트 챔피언들
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @Builder.Default
    private List<MostChamp> mostChampList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.COMMON;

    // 양방향 매핑에서 리스트쪽에 데이터를 추가하는 편의메서드 생성
    public void addReply(Reply reply) {
        replyList.add(reply);
        if (this != reply.getUser()) {
            reply.setUser(this);
        }
    }

    public void addUserProfile(UserProfile userProfile) {
        userProfileList.add(userProfile);
        if (this != userProfile.getUser()) {
            userProfile.setUser(this);
        }
    }

    public void addPoint(Point point) {
        pointList.add(point);
        if (this != point.getUser()) {
            point.setUser(this);
        }
    }

    public void addAccuse(Accuse accuse) {
        accuseList.add(accuse);
        if (this != accuse.getUser()) {
            accuse.setUser(this);
        }
    }

    public void addAttendance(Attendance attendance) {
        attendanceList.add(attendance);
        if (this != attendance.getUser()) {
            attendance.setUser(this);
        }
    }

    public void addBoard(Board board) {
        boardList.add(board);
        if (this != board.getUser()) {
            board.setUser(this);
        }
    }

    public void addFollowTo(Follow follow) {
        followToList.add(follow);
        if (this != follow.getFollowFrom()) {
            follow.setFollowFrom(this);
        }
    }

    public void addFollowFrom(Follow follow) {
        followFromList.add(follow);
        if (this != follow.getFollowTo()) {
            follow.setFollowTo(this);
        }
    }

    public void addAttendanceStamp(AttendanceStamp attendanceStamp) {
        attendanceStampList.add(attendanceStamp);
        if (this != attendanceStamp.getUser()) {
            attendanceStamp.setUser(this);
        }
    }

    public void addChattingToList(Chatting chatting) {
        chattingToList.add(chatting);
        if (this != chatting.getChattingFrom()) {
            chatting.setChattingFrom(this);
        }
    }

    public void addChattingFromList(Chatting chatting) {
        chattingFromList.add(chatting);
        if (this != chatting.getChattingTo()) {
            chatting.setChattingTo(this);
        }
    }

    public String getLatestProfileImage() {
        return this.userProfileList.stream()
                .sorted(Comparator.comparing(UserProfile::getProfileImage).reversed())
                .map(UserProfile::getProfileImage)
                .findFirst()
                .orElse(null);
    }

    public void addMostChampList(MostChamp mostChamp) {
        mostChampList.add(mostChamp);
        if (this != mostChamp.getUser()) {
            mostChamp.setUser(this);
        }
    }
}
