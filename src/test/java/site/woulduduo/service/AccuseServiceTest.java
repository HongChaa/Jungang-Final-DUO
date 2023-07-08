package site.woulduduo.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import site.woulduduo.dto.request.accuse.UserAccuseRequestDTO;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
@Rollback(false)
class AccuseServiceTest {

    @Autowired
    AccuseService accuseService;

    @Test
    @DisplayName("Accuse list dto 변환")
    void saveTest() {
//        List<AccuseListResponseDTO> accuseListByAdmin =
//                accuseService.getAccuseListByAdmin();
//
//        System.out.println("accuseListByAdmin = " + accuseListByAdmin);
    }

    @Test
    @DisplayName("관리자용 금일 accuseList 확인")
    void todayBoardByAdmin(){
//        List<AccuseListResponseDTO> accuseByAdminResponseDTOS =
//                accuseService.todayAccuseByAdmin();
//
//        System.out.println("accuseByAdminResponseDTOS = " + accuseByAdminResponseDTOS);
//        int size = accuseByAdminResponseDTOS.size();
//        System.out.println("size = " + size);
    }

    @Test
    @DisplayName("관리자용 금일 accuseList 확인")
    void accuseUser(){

        List<String>accuseType=new ArrayList<>();
        accuseType.add("1번경고");
        accuseType.add("2번경고");
        accuseType.add("3번경고");


        UserAccuseRequestDTO accuse = UserAccuseRequestDTO.builder()
                .userNickname( "123")
                .accuseType(accuseType)
                .accuseEtc("기타")
                .build();

        boolean booleanResult = accuseService.accuseUser(accuse);

    }

}