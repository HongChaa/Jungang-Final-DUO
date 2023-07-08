package site.woulduduo.entity;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class FollowCompositeKey implements Serializable {

    private String followFrom;

    private String followTo;

}
