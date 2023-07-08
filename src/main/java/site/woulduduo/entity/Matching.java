package site.woulduduo.entity;

import lombok.*;
import site.woulduduo.enumeration.MatchingStatus;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString(exclude = {"chatting", "pointList"})
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "matchingNo")
@Builder
@Entity
@Table(name = "duo_matching")
public class Matching {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matching_no")
    private Long matchingNo;

    @Column(name = "matching_date")
    private LocalDate matchingDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 7, name = "matching_status")
    private MatchingStatus matchingStatus;

    @Column(nullable = false, name = "matching_point")
    private Integer matchingPoint;

    @Column(length = 100, name = "matching_review_content")
    private String matchingReviewContent;

    @Column(columnDefinition = "INT(1)", name = "matching_review_rate")
    private Integer matchingReviewRate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatting_no")
    private Chatting chatting;

    @Builder.Default
    @OneToMany(mappedBy = "matching", fetch = FetchType.LAZY)
    private List<Point> pointList = new ArrayList<>();

    public void addPoint(Point point) {
        pointList.add(point);
        if (this != point.getMatching()) {
            point.setMatching(this);
        }
    }

}
