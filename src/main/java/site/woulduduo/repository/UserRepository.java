package site.woulduduo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.woulduduo.entity.User;

public interface UserRepository extends
        JpaRepository<User,String> {
}
