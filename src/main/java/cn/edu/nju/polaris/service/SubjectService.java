package cn.edu.nju.polaris.service;

import cn.edu.nju.polaris.vo.SubjectVO;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * Created by zhangzy on 2017/11/7 下午9:09
 */
@CacheConfig(cacheNames = "subjects")
public interface SubjectService {

    @Cacheable
    List<SubjectVO> findAllSubject();
}
