package cn.edu.nju.polaris.repository;

import cn.edu.nju.polaris.entity.IndustryIndex;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IndustryIndexRepository extends JpaRepository<IndustryIndex,Long>{

    IndustryIndex findByCategoryAndFirstIndustryAndSecondIndustryAndScaleAndIndexNameLike(String category,String firstIndustry,String secondIndustry,String scale,String indexName);

    List<IndustryIndex> findByCategoryAndFirstIndustryAndSecondIndustryAndScale(String category,String firstIndustry, String secondIndustry, String scale);
}
