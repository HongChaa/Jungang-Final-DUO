package site.woulduduo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.woulduduo.entity.MostChamp;

public interface MostChampRepository extends JpaRepository<MostChamp, Long> {

    MostChamp getAllByUser_UserAccountAndMostNo(String userAccount, int mostNo);

}
