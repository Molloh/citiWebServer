package cn.edu.nju.polaris.repository;

import cn.edu.nju.polaris.entity.MultiKeysClass.VoucherItemMultiKeysClass;
import cn.edu.nju.polaris.entity.VoucherItem;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface VoucherItemRepository extends JpaRepository<VoucherItem,VoucherItemMultiKeysClass>{

    /**
     * 根据 公司id 和 凭证id 获得 VoucherItem 实体
     * @param companyId
     * @param voucherId
     * @return
     */
    @Cacheable
    List<VoucherItem> findByCompanyIdAndVoucherId(Long companyId,String voucherId);

    /**
     * 根据 公司id 获得VoucherItem实体
     * @param companyId
     * @return
     */
    @Cacheable
    List<VoucherItem> findByCompanyId(Long companyId);

    /**
     *
     * @param companyId
     * @param voucherId
     */
    @Transactional
    @CachePut
    void deleteAllByCompanyIdAndVoucherId(Long companyId, String voucherId);

    /**
     * 根据凭证日期和公司id获得凭证信息
     * @param date  yyyy
     * @param companyId
     * @return
     */
    @Query(value = "select * from voucher_item t1 where t1.voucher_id in (select voucher_id from voucher t2 where year(t2.date)=?1 and t2.company_id=?2) and t1.company_id=?2",nativeQuery = true)
    List<VoucherItem> getListThroughYear(String date, Long companyId);

    /**
     * 根据凭证日期和公司id获得凭证信息
     * @param date yyyy-mm
     * @param companyId
     * @return
     */
    @Query(value = "select * from voucher_item t1 where t1.voucher_id in (select voucher_id from voucher t2 where date_format(date,'%Y-%m')=?1 and t2.company_id=?2) and t1.company_id=?2",nativeQuery = true)
    List<VoucherItem> getListThroughPeriod(String date,Long companyId);

    /**
     *
     * @param time  yyyy
     * @param subjectId
     * @param companyId
     * @return 获取凭证中时间为time、科目id为subjectId，公司id为companyId的贷方金额不为0的条目
     */
    @Query(value = "select * from voucher_item t1 where t1.voucher_id in (select voucher_id from voucher t2 where year(t2.date)=?1 and t2.company_id=?3) and t1.company_id=?3 and t1.subjects=?2 and t1.credit_amount<>0",nativeQuery = true)
    List<VoucherItem> getCreditVoucherItemByYear(String time, String subjectId,Long companyId);

    /**
     *
     * @param time yyyy
     * @param subjectId
     * @param companyId
     * @return 获取凭证中时间为time、科目id为subjectId，公司id为companyId的借方金额不为0的条目
     */
    @Query(value = "select * from voucher_item t1 where t1.voucher_id in (select voucher_id from voucher t2 where year(t2.date)=?1 and t2.company_id=?3) and t1.company_id=?3 and t1.subjects=?2 and t1.debit_amount<>0",nativeQuery = true)
    List<VoucherItem> getDebitVoucherItemByYear(String time, String subjectId,Long companyId);

    /**
     *
     * @param time yyyy-mm
     * @param subjectId
     * @param companyId
     * @return 获取凭证中时间为time、科目id为subjectId，公司id为companyId的贷方金额不为0的条目
     */
    @Query(value = "select * from voucher_item t1 where t1.voucher_id in (select voucher_id from voucher t2 where date_format(t2.date,'%Y-%m')=?1 and t2.company_id=?3) and t1.company_id=?3 and t1.subjects=?2 and t1.credit_amount<>0",nativeQuery = true)
    List<VoucherItem> getCreditVoucherItemByPeriod(String time,String subjectId,Long companyId);

    /**
     *
     * @param time yyyy-mm
     * @param subjectId
     * @param companyId
     * @return 获取凭证中时间为time、科目id为subjectId，公司id为companyId的借方金额不为0的条目
     */
    @Query(value = "select * from voucher_item t1 where t1.voucher_id in (select voucher_id from voucher t2 where date_format(t2.date,'%Y-%m')=?1 and t2.company_id=?3) and t1.company_id=?3 and t1.subjects=?2 and t1.debit_amount<>0",nativeQuery = true)
    List<VoucherItem> getDebitVoucherItemByPeriod(String time,String subjectId,Long companyId);
}
