package site.woulduduo.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import site.woulduduo.entity.User;
import site.woulduduo.entity.UserProfile;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@Rollback(value = false)
class UserProfileRepositoryTest {

    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void addBulkData() {
        userProfileRepository.saveAll(
                List.of(
                        UserProfile.builder()
                                .user(userRepository.findById("test@example.com").orElseThrow())
                                .profileImage("test1")
                                .build(),
                        UserProfile.builder()
                                .user(userRepository.findById("test@example.com").orElseThrow())
                                .profileImage("test2")
                                .build(),
                        UserProfile.builder()
                                .user(userRepository.findById("test@example.com").orElseThrow())
                                .profileImage("test3")
                                .build()
                )
        );
    }
    
    @Test
    @DisplayName("test@example.com 의 프로필 이미지 경로를 조회했을 때 test3 이 나와야 한다.")
    void findProfileImageByUserAccountTest() {

        User foundUser = userRepository.findById("test@example.com").orElseThrow();

        List<UserProfile> collect = foundUser.getUserProfileList().stream()
                .sorted(Comparator.comparing(UserProfile::getProfileImage).reversed())
                .limit(1)
                .collect(Collectors.toList());

        assertEquals(collect.get(0).getProfileImage(), "test3");

    }

}