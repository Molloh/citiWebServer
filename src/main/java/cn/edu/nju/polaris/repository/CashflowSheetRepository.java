package cn.edu.nju.polaris.repository;

import cn.edu.nju.polaris.entity.CashflowSheet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface CashflowSheetRepository extends JpaRepository<CashflowSheet,Long>{

    /**
     * 根据 期数、公司id、项目 获得CashflowSheet实体
     * @param peroid
     * @param companyId
     * @param name
     * @return
     */
    CashflowSheet findByPeriodAndCompanyIdAndName(String peroid,String companyId,String name);

    /**
     * 根据 期数、公司id 获得所有的CashflowSheet实体
     * @param peroid
     * @param companyId
     * @return
     */
    List<CashflowSheet> findByPeriodAndCompanyId(String peroid, String companyId);



}
