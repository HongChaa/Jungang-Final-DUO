package site.woulduduo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.woulduduo.entity.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

}
