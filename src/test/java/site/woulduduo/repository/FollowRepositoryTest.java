package site.woulduduo.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import site.woulduduo.entity.Follow;
import site.woulduduo.entity.User;

@SpringBootTest
@Transactional
@Rollback(false)
class FollowRepositoryTest {
    @Autowired
    FollowRepository followRepository;

    @Autowired
    UserRepository userRepository;



    @Test
    @DisplayName("사용자 저장 및 팔로우설정")
    void saveTest() {
        User acvd1 = userRepository.findByUserAccount("acvd1");
        User acvd2 = userRepository.findByUserAccount("acvd2");
        User acvd3 = userRepository.findByUserAccount("acvd3");


        System.out.println("acvd1 = " + acvd1);
        System.out.println("acvd2 = " + acvd2);
        System.out.println("acvd3 = " + acvd3);
        Follow follow1 = Follow.builder()
                .followTo(acvd1)
                .followFrom(acvd2)
                .build();

        Follow follow2 = Follow.builder()
                .followTo(acvd2)
                .followFrom(acvd1)
                .build();


        Follow save4 = followRepository.save(follow2);


    }
}