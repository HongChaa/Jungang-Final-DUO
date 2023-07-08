package site.woulduduo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import site.woulduduo.entity.Follow;
import site.woulduduo.entity.FollowCompositeKey;
import site.woulduduo.entity.User;

public interface FollowRepository extends JpaRepository<Follow, FollowCompositeKey> {

    @Query(value = "SELECT COUNT(*) FROM duo_user_follow WHERE follow_to = :followTo",nativeQuery = true)
    long findAllWithFollowTo(String followTo);


    @Query("SELECT COUNT(*) FROM Follow f WHERE f.followFrom.userAccount = :followFrom AND f.followTo.userAccount = :followTo")
    long existsByFollowFromAndFollowTo(String followFrom, String followTo);

    int countByFollowTo(User user);
    int countByFollowFrom(User user);

    //팔로워 순위 구하기
    @Query(value = "select ranking from (select follow_to, count (*)\n" +
            ", dense_rank() over (order by count(*)  desc) as ranking\n" +
            "from duo_user_follow\n" +
            "group by follow_to) as rank_table\n" +
            "where follow_to = ?1", nativeQuery = true)
    Integer getFollowerRank(String userAccount);
}
