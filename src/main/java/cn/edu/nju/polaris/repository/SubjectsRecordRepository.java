package cn.edu.nju.polaris.repository;

import cn.edu.nju.polaris.entity.SubjectsRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubjectsRecordRepository extends JpaRepository<SubjectsRecord,Long>{

    /**
     * 根据公司编号和凭证编号删除该凭证对应的全部的科目记录
     * @param companyId
     * @param voucherId
     */
    @Query(value = "delete from subjects_record where company_id=?1 and voucher_id=?2",nativeQuery = true)
    void deleteOneVoucherAllRecord(Long companyId,String voucherId);

    /**
     * 获得一个科目的最新一条科目记录
     * @param companyId
     * @param subjectId
     * @return
     */
    @Query(value = "",nativeQuery = true)
    SubjectsRecord findNewestSubjectRecord(Long companyId,String subjectId);

    /**
     * 查找一个月的全部的科目记录
     * @param companyId
     * @param month "2010-08"
     * @return
     */
    @Query(value = "",nativeQuery = true)
    List<SubjectsRecord> findAllThroughOneMonth(Long companyId,String month);

    /**
     * 查找一年的全部的科目记录
     * @param companyId
     * @param year  "2010"
     * @return
     */
    @Query(value = "",nativeQuery = true)
    List<SubjectsRecord> findAllThroughOneYear(Long companyId,String year);

    /**
     * 获得一个公司全部的科目记录信息
     * @param companyId
     * @return
     */
    List<SubjectsRecord> findAllByCompanyId(Long companyId);

    /**
     * 获得一个科目全部的科目记录信息
     * @param companyId
     * @param subjectId
     * @return
     */
    List<SubjectsRecord> findAllByCompanyIdAndSubjectsId(Long companyId,String subjectId);

}
