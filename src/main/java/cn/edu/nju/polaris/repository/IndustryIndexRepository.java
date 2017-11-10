package cn.edu.nju.polaris.repository;

import cn.edu.nju.polaris.entity.IndustryIndex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IndustryIndexRepository extends JpaRepository<IndustryIndex,Long>{

    /**
     *
     * @param category 财务预警/现金管理/供应链绩效评价
     * @param firstIndustry 一级行业
     * @param secondIndustry 二级行业
     * @param scale 企业规模
     * @param indexName 指标
     * @return
     */
    IndustryIndex findByCategoryAndFirstIndustryAndSecondIndustryAndScaleAndIndexNameContaining(String category,String firstIndustry,String secondIndustry,String scale,String indexName);

    List<IndustryIndex> findByCategoryAndFirstIndustryAndSecondIndustryAndScale(String category,String firstIndustry, String secondIndustry, String scale);
}
