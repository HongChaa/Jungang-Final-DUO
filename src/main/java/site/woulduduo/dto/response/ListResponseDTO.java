package site.woulduduo.dto.response;

import lombok.*;
import site.woulduduo.dto.response.page.PageResponseDTO;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class ListResponseDTO<T, U> {

    private int count;

    private PageResponseDTO<U> pageInfo;

    private List<T> list;

}
