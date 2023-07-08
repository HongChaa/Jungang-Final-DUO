package site.woulduduo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.woulduduo.repository.PointRepository;
import site.woulduduo.entity.Chatting;
import site.woulduduo.entity.Matching;
import site.woulduduo.entity.Point;
import site.woulduduo.entity.User;
import site.woulduduo.repository.ChattingRepository;
import site.woulduduo.repository.MatchingRepository;
import site.woulduduo.repository.UserRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class PointService {

    private final PointRepository pointRepository;
    private final MatchingRepository matchingRepository;
    private final ChattingRepository chattingRepository;
    private final UserRepository userRepository;

    //잔여 포인트 확인하여 매칭신청 가능 여부 확인
    public int checkCurrentPoint(long chattingNo) {
        Chatting chatting = chattingRepository.findByChattingNo(chattingNo);

        User from = userRepository.findById(chatting.getChattingFrom().getUserAccount()).orElseThrow();
        User to = userRepository.findById(chatting.getChattingTo().getUserAccount()).orElseThrow();

        int currentPoint = from.getUserCurrentPoint();
        int needed = to.getUserMatchingPoint();

        return (currentPoint - needed);
    }

    //매칭 신청시 포인트 차감
    public int payMatchingPoint(long chattingNo, long matchingNo) {
        System.out.println("payMatchingPoint 서비스 " +chattingNo);
        System.out.println("payMatchingPoint 서비스 확인");
        Matching matching = matchingRepository.findByMatchingNo(matchingNo);
        Chatting chatting = chattingRepository.findByChattingNo(chattingNo);

        Point givingPoint = Point.builder()
                .user(chatting.getChattingFrom())
                .pointAmount(-matching.getMatchingPoint())
                .chatting(chatting)
                .matching(matching)
                .build();
        Point pointHistory = pointRepository.save(givingPoint);
        System.out.println("payMatchingPoint 서비스 givingPoint : " + givingPoint.getPointAmount());

        User user = userRepository.findById(chatting.getChattingFrom().getUserAccount()).orElseThrow();
        user.setUserCurrentPoint(user.getUserCurrentPoint() + givingPoint.getPointAmount());
        user.addPoint(pointHistory);
        userRepository.save(user);

        return Math.abs(pointHistory.getPointAmount());
    }


    //게임완료 후 포인트 지급
    public int giveMatchingPoint(long chattingNo, long matchingNo) {
        System.out.println("giveMatchingPoint 서비스 " +chattingNo);
        System.out.println("giveMatchingPoint 서비스 확인");
        Matching matching = matchingRepository.findByMatchingNo(matchingNo);
        Chatting chatting = chattingRepository.findByChattingNo(chattingNo);

        Point givingPoint = Point.builder()
                .user(chatting.getChattingTo())
                .pointAmount(matching.getMatchingPoint())
                .chatting(chatting)
                .matching(matching)
                .build();
        Point pointHistory = pointRepository.save(givingPoint);

        User user = userRepository.findById(chatting.getChattingTo().getUserAccount()).orElseThrow();
        user.setUserCurrentPoint(user.getUserCurrentPoint() + givingPoint.getPointAmount());
        user.addPoint(pointHistory);
        userRepository.save(user);

        return pointHistory.getPointAmount();
    }

    public boolean searchPointHistory(long matchingNo) {
        Matching matching = matchingRepository.findByMatchingNo(matchingNo);
        User user = matching.getChatting().getChattingTo();
        return pointRepository.existsByMatchingAndUser(matching, user);
    }
}
