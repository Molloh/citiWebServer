package cn.edu.nju.polaris.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@CacheConfig(cacheNames = "industries")
public interface IndustryService {

    @Cacheable
    List<String> findAllFirstIndustry();

    @Cacheable
    List<String> findAllSecondIndustryByFirstIndustry(String firstIndustry);
}
