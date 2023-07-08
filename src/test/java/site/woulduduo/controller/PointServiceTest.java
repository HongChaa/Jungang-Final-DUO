package site.woulduduo.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import site.woulduduo.entity.Chatting;
import site.woulduduo.entity.User;
import site.woulduduo.repository.ChattingRepository;
import site.woulduduo.service.PointService;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class PointServiceTest {

    @Autowired
    PointService pointService;
    @Autowired
    ChattingRepository chattingRepository;

    @Test
    @DisplayName("포인트를 지급하면 멤버 정보에 포인트가 늘어난다?")
    public void givePointTest(){
        long matching = 4L;
        long chattingNo = 3L;
        Chatting chatting = chattingRepository.findByChattingNo(chattingNo);
        User to = chatting.getChattingTo();
        pointService.giveMatchingPoint(chattingNo, matching);
    }

    @Test
    @DisplayName("포인트를 차감하면 멤버 정보에 포인트가 줄어든다?")
    public void payPointTest(){

    }

    @Test
    @DisplayName("매칭넘버로 포인트 지급이력을 찾을 수 있다")
    public void searchByMatchingTest(){
        long matchingNo = 5L;
        boolean flag = pointService.searchPointHistory(matchingNo);
        assertFalse(flag);
    }
}