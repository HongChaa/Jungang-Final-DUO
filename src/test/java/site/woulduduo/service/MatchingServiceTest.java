package site.woulduduo.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import site.woulduduo.dto.request.matching.MatchingFixRequestDTO;
import site.woulduduo.dto.request.chatting.ReviewWriteRequestDTO;
import site.woulduduo.dto.response.ListResponseDTO;
import site.woulduduo.dto.response.user.UserReviewResponseDTO;
import site.woulduduo.entity.Matching;
import site.woulduduo.enumeration.MatchingStatus;
import site.woulduduo.repository.ChattingRepository;
import site.woulduduo.repository.MatchingRepository;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MatchingServiceTest {

    @Autowired
    private MatchingService matchingService;
    @Autowired
    MatchingRepository matchingRepository;
    @Autowired
    private ChattingRepository chattingRepository;

    //    매칭 신청하기
//    6/18 테스트 완료
    @Test
    @DisplayName("매칭을 신청하면 매칭 번호를 리턴받는다")
    void makeMatchingTest() {
        long chattingNo = 27L;

        long matchingNo = matchingService.makeMatching(chattingNo);

        System.out.println("matchingNo = " + matchingNo);
    }

    //    매칭 거절하기
//     6/18 테스트 완료
    @Test
    @DisplayName("매칭을 거절하면 매칭상태를 REJECT로 변경한다")
    void rejectMatchingTest() {
        long matchingNo = 2L;
        boolean flag = matchingService.rejectMatching(matchingNo);
        Matching rejected = matchingRepository.findByMatchingNo(matchingNo);
        assertEquals(MatchingStatus.REJECT, rejected.getMatchingStatus());
    }

    //    매칭 확정하기
//  6/18 테스트 완료
    @Test
    @DisplayName("매칭을 확정하면 매칭일자가 입력되고 상태를 CONFIRM으로 변경한다")
    void fixScheduleTest() {
        for (int i = 199; i <240 ; i++) {
        MatchingFixRequestDTO dto = MatchingFixRequestDTO.builder()
                .matchingNo(3L)
                .matchingDate("2023-07-19")
                .build();
        boolean flag = matchingService.fixSchedule(dto);
        Matching rejected = matchingRepository.findByMatchingNo(dto.getMatchingNo());
        assertEquals(MatchingStatus.CONFIRM, rejected.getMatchingStatus());

        }
    }

    //매칭 완료하기
    @Test
    void rejectTest(){
        long matchingNo = 4L;
        boolean flag = matchingService.gameOverMatching(matchingNo);
        System.out.println(flag);
    }

    @Test
    @DisplayName("user3 가 리뷰를 쓰면 잘 쓰여야 한다.")
    void writeReviewTest() {

        ReviewWriteRequestDTO dto = new ReviewWriteRequestDTO();
        dto.setMatchingNo(2L);
        dto.setReviewRate(5);
        dto.setReviewContent("너무 재밌었어요~ 다음에도 같이 할게요");

        matchingService.writeReview(
                "user3",
                dto
        );

    }

    @Test
    @DisplayName("user1 가 리뷰를 쓰면 런타임 오류가 발생해야 한다.")
    @Rollback
    void writeReviewTest2() {

        ReviewWriteRequestDTO dto = new ReviewWriteRequestDTO();
        dto.setMatchingNo(1L);
        dto.setReviewRate(5);
        dto.setReviewContent("너무 재밌었어요~ 다음에도 같이 할게요");

        assertThrows(RuntimeException.class, () -> {
            matchingService.writeReview(
                    "user1",
                    dto
            );
        });

    }

    @Test
    @DisplayName("user1이 받은 모든 리뷰를 리턴하면 길이가 1이어야 한다")
    void getWrittenReviewTest() {
        ListResponseDTO<UserReviewResponseDTO, Matching> gottenReview = matchingService.getGottenReview("user1", 1);

        assertEquals(1, gottenReview.getList().size());
    }

    @Test
    @DisplayName("test1@example.com이 받은 모든 채팅에 대해 매칭을 생성해야 한다.")
    void makeBulkMatchingTest() {
        for (long i = 204; i < 250; i++) {
            matchingService.makeMatching(i);
        }

    }

    @Test
    @DisplayName("test1@example.com이 받은 모든 매칭에 대해 리뷰를 생성한다")
    void writeBulkReviewTest() {
        for (long i = 199; i < 240; i++) {
            ReviewWriteRequestDTO dto = new ReviewWriteRequestDTO();
            dto.setMatchingNo(i);
            dto.setReviewRate((int) (Math.random() * 5 + 1));
            dto.setReviewContent("리뷰내용" + i);
            matchingService.writeReview("ahri@ahri.com", dto);
        }
    }


//    @Test
//    @DisplayName("남자에게 신청온 매칭건수 카운트")
//    void matchingFromMaleTest(){
//        String userID = "test@test.com";
//        int countFromMale = matchingRepository.getMyMatchingInfo(userID);
//        System.out.println("countFromMale = " + countFromMale);
//
//    }

}