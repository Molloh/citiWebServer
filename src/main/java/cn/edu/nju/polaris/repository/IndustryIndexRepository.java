package cn.edu.nju.polaris.repository;

import cn.edu.nju.polaris.entity.IndustryIndex;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IndustryIndexRepository extends JpaRepository<IndustryIndex,Long>{

    IndustryIndex findByCategoryAndFirstIndustryAndSecondIndustryAndScaleAndIndexName(String category,String firstIndustry,String secondIndustry,String scale,String indexName);


}
