package cn.edu.nju.polaris.repository;

import cn.edu.nju.polaris.entity.Industry;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@CacheConfig(cacheNames = "industry")
public interface IndustryRepository extends JpaRepository<Industry,Long> {


    @Cacheable
    @Query("select distinct t.firstIndustry from Industry t")
    List<String> findAllFirstIndustry();

    @Cacheable
    @Query("select t.secondIndustry from Industry t where t.firstIndustry =?1")
    List<String> findAllSecondIndustryByFirstIndustry(String firstIndustry);
}
