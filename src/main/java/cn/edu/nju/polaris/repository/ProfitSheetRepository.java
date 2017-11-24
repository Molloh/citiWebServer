package cn.edu.nju.polaris.repository;

import cn.edu.nju.polaris.entity.ProfitSheet;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@CacheConfig(cacheNames = "profit_sheet")
public interface ProfitSheetRepository extends JpaRepository<ProfitSheet,Long>{

    /**
     * 根据 公司id、期数获得 利润表实体
     * @param companyId
     * @param period
     * @param name
     * @return
     */
    @Cacheable
    ProfitSheet findByCompanyIdAndPeriodAndName(Long companyId,String period,String name);

    /**
     * 根据 公司id、期数 获得利润表实体
     * @param companyId
     * @param period
     * @return
     */
    List<ProfitSheet> findByCompanyIdAndPeriod(Long companyId,String period);
}
