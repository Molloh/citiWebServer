package cn.edu.nju.polaris.service;

import cn.edu.nju.polaris.entity.SubjectInitial;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@CacheConfig(cacheNames = "subjects_initial")
public interface SubjectInitialService {

    @CachePut
    void saveSubjectInitials(List<SubjectInitial> list);

    @Cacheable
    List<SubjectInitial> getAllSubjectInitialByCompanyId(Long id);
}
