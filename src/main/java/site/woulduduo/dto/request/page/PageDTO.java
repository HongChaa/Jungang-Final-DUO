package site.woulduduo.dto.request.page;

import lombok.*;

@Getter @Setter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class PageDTO{
    private int page;
    private String keyword;
    private int size;

    public PageDTO(){

        this.page=1;
        this.keyword="";
        this.size=10;
    }
}
