package site.woulduduo.entity;


import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@ToString(exclude = {"user"})
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "profileNo")
@Builder
@Entity
@Table(name = "duo_user_profile")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_no")
    private Long profileNo;

    @Column(length = 1000, nullable = true, name = "profile_image")
    private String profileImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account")
    private User user;

}
