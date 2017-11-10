package cn.edu.nju.polaris.repository;

import cn.edu.nju.polaris.entity.SubjectsBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubjectsBalanceRepository extends JpaRepository<SubjectsBalance,Long>{

    /**
     * @param companyId
     * @param date  yyyy-mm
     * @return
     */
    List<SubjectsBalance> findByCompanyIdAndDate(Long companyId, String date);


    /**
     * 获取一个会计科目最新的余额信息
     * @param companyId
     * @param subjectId
     * @return
     */
    SubjectsBalance findByCompanyIdAndSubjectsIdAndDate(Long companyId,String subjectId,String period);
}