package site.woulduduo.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import site.woulduduo.enumeration.BoardCategory;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString(exclude = {"user", "replyList"})
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "boardNo")
@Builder
@Entity
@Table(name = "duo_board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_no")
    private Long boardNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account")
    private User user;

    @Column(name = "board_title", nullable = false, length = 50)
    private String boardTitle;

    @Column(name = "board_content", nullable = false, length = 1000)
    private String boardContent;

    @Builder.Default
    @Column(name = "board_like")
    private Integer boardLike = 0;

    @Builder.Default
    @Column(name = "board_view_count")
    private Integer boardViewCount = 0;

    @Column(name = "board_category", nullable = false)
    @Enumerated(EnumType.STRING)
    private BoardCategory boardCategory;

    @CreationTimestamp
    @Column(name = "board_written_date", updatable = false)
    private LocalDateTime boardWrittenDate;

    // 댓글 목록들
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @Builder.Default
    private List<Reply> replyList = new ArrayList<>();

    public void addReply(Reply reply) {
        replyList.add(reply);
        if (this != reply.getBoard()) {
            reply.setBoard(this);
        }
    }



}
