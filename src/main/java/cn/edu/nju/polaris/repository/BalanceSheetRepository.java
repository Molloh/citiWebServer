package cn.edu.nju.polaris.repository;

import cn.edu.nju.polaris.entity.BalanceSheet;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Id;
import java.util.List;

public interface BalanceSheetRepository extends JpaRepository<BalanceSheet,Long>{

    /**
     * 根据 公司id、期数、名称获得 资产负债表实体
     * @param companyId
     * @param period
     * @param name
     * @return
     */
    BalanceSheet findByCompanyIdAndPeriodAndName(Long companyId,String period,String name);

    /**
     * 根据 公司id、期数 获得资产负债表实体
     * @param companyId
     * @param period
     * @return
     */
    List<BalanceSheet> findByCompanyIdAndPeriod(Long companyId,String period);


}
