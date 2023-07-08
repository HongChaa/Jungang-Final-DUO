package site.woulduduo.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import site.woulduduo.dto.response.chatting.ChattingDetailResponseDTO;
import site.woulduduo.dto.response.chatting.ChattingListResponseDTO;
import site.woulduduo.entity.User;
import site.woulduduo.entity.UserProfile;
import site.woulduduo.repository.ChattingRepository;
import site.woulduduo.repository.UserRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@Rollback(value = false)
class ChattingServiceTest {

    @Autowired
    private ChattingService chattingService;
    @Autowired
    private ChattingRepository chattingRepository;
    @Autowired
    private UserRepository userRepository;


    // 채팅 신청하기
    // 6/17 테스트 완료
    @Test
    @DisplayName("채팅 데이터 1개 생성")
    void makeChattingTest(){

        String userAccount = "test1";
        String me = "test3";

        long chattingNo = chattingService.makeChatting(me, userAccount);

//        assertEquals(3, chattingRepository.count());
    }

    @Test
    @DisplayName("채팅 데이터 많이 생성")
    void makeBulkChattingTest() {
        String chattingTo = "ahri@ahri.com";

        for (int i = 1; i < 100; i++) {
            String chattingFrom = "acvd" + i;

            chattingService.makeChatting(chattingFrom, chattingTo);

        }
    }

    //  대표 프로필 사진 가져오기
    //6/17 테스트 완료
    @Test
    @DisplayName("프로필 사진중 가장 첫 사진을 가져온다")
    void getRepresentativeProfileTest() {
        User user = userRepository.findByUserAccount("test123");

        List<UserProfile> profileList = null;
        try {
            profileList = user.getUserProfileList().stream()
                    .sorted(Comparator.comparing(UserProfile::getProfileNo).reversed())
                    .limit(1)
                    .collect(Collectors.toList());
            System.out.println(profileList.get(0).getProfileImage());
        } catch (IndexOutOfBoundsException e) {
            System.out.println("프로필 사진이 존재하지 않습니다."); // default 이미지 경로로 변경예정
        }
    }

    // 채팅방 디테일 내역 가져오기
    // 6/17 테스트 완료
    @Test
    @DisplayName("채팅방 디테일 가져오기")
    void getChattingDetailTest(){
        String me = "test2";
        long chattingNo = 3L;

        ChattingDetailResponseDTO detail = chattingService.getChattingDetail(me, chattingNo);
        assertEquals("test456", detail.getUserNickname());
        System.out.println(detail.getMessageList());
    }

//    채팅 목록 가져오기
//    6/18 테스트 완료
    @Test
    @DisplayName("유저가 가진 채팅목록을 가져온다")
    void getChattingListTest(){
        User user = userRepository.findByUserAccount("test1");
        List<ChattingListResponseDTO> chattingList = chattingService.getChattingList(user);

        System.out.println(chattingList);
    }

}