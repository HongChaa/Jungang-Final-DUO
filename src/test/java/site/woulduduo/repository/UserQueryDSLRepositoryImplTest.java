package site.woulduduo.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import site.woulduduo.entity.Follow;
import site.woulduduo.entity.User;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
@Rollback(value = false)
class UserQueryDSLRepositoryImplTest {

    @Autowired
    private UserQueryDSLRepositoryCustom custom;

    @Test
    @DisplayName("userAccount가 팔로우하고 있는 회원들 리스트가 출력되어야함.")
    void followedListTest() {
        HttpSession session = null;
        List<Follow> followed = custom.followed(session);
    }

}