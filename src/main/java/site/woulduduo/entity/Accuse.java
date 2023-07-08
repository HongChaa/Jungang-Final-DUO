package site.woulduduo.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Table(name = "duo_accuse")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "accuseNo")
@ToString(exclude = {"user"})
public class Accuse {

    @Id
    @Column(name = "accuse_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accuseNo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_account")
    private User user;

    @Column(length = 100, name = "accuse_type")
    private String accuseType;

    @Column(length = 20, name = "accuse_etc")
    private String accuseEtc;

    @CreationTimestamp
    @Column(name = "accuse_written_date", updatable = false)
    private LocalDateTime accuseWrittenDate;

}
