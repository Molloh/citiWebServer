package cn.edu.nju.polaris.repository;

import cn.edu.nju.polaris.entity.Industry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IndustryRepository extends JpaRepository<Industry,Long> {


    @Query("select distinct t.firstIndustry from Industry t")
    List<String> findAllFirstIndustry();

    @Query("select t.secondIndustry from Industry t where t.firstIndustry =?1")
    List<String> findAllSecondIndustryByFirstIndustry(String firstIndustry);
}
