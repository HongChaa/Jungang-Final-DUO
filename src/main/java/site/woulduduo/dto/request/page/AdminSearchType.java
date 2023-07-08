package site.woulduduo.dto.request.page;

import lombok.*;
import site.woulduduo.enumeration.AdminViewType;

@Getter @Setter
@ToString
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
public class AdminSearchType extends PageDTO{
    AdminViewType adminViewType;

    //today 로 기본값 설정
    public AdminSearchType(){

        this.adminViewType= AdminViewType.TODAY;

    }

//    public AdminSearchType(int page, String keyword, int size) {
//        super(page, keyword, size);
//    }
}
