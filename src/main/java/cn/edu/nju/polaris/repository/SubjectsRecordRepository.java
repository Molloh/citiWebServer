package cn.edu.nju.polaris.repository;

import cn.edu.nju.polaris.entity.SubjectsRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectsRecordRepository extends JpaRepository<SubjectsRecord,Long>{

    /**
     * 根据公司编号和凭证编号删除该凭证对应的全部的科目记录
     * @param companyId
     * @param voucherId
     */
    void deleteOneVoucherAllRecord(String companyId,String voucherId);

    /**
     * 获得一个科目的最新一条科目记录
     * @param companyId
     * @param subjectId
     * @return
     */
    SubjectsRecord findNewestSubjectRecord(String companyId,String subjectId);

    /**
     * 查找一个月的全部的科目记录
     * @param factoryId
     * @param month "2010-08"
     * @return
     */
    List<SubjectsRecord> findAllByOneMonth(String factoryId,String month);

    /**
     * 查找一年的全部的科目记录
     * @param factoryId
     * @param year  "2010"
     * @return
     */
    List<SubjectsRecord> findAllByOneYear(String factoryId,String year);

    /**
     * 获得一个公司全部的科目记录信息
     * @param factoryId
     * @return
     */
    List<SubjectsRecord> findAllByFactoryId(String factoryId);

    /**
     * 获得一个科目全部的科目记录信息
     * @param factoryId
     * @param subjectId
     * @return
     */
    List<SubjectsRecord> findAllBySubjectId(String factoryId,String subjectId);

}
