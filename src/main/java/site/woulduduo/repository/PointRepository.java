package site.woulduduo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.woulduduo.entity.Matching;
import site.woulduduo.entity.Point;
import site.woulduduo.entity.User;

public interface PointRepository extends JpaRepository<Point, Long> {

    boolean existsByMatchingAndUser(Matching matching, User user);
}
