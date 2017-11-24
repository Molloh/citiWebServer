package cn.edu.nju.polaris.service;

import cn.edu.nju.polaris.vo.SubjectBalanceVO;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

@CacheConfig(cacheNames = "subjects_balance")
public interface SubjectBalanceService {

    @Cacheable
    void initialSubjectBalance(Long companyId,String phase);

    @CachePut
    void saveOneSubjectBalance(SubjectBalanceVO vo);
}
