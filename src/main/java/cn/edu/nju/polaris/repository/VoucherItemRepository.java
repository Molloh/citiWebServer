package cn.edu.nju.polaris.repository;

import cn.edu.nju.polaris.entity.MultiKeysClass.VoucherItemMultiKeysClass;
import cn.edu.nju.polaris.entity.VoucherItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VoucherItemRepository extends JpaRepository<VoucherItem,VoucherItemMultiKeysClass>{

    /**
     * 根据 公司id 和 凭证id 获得 VoucherItem 实体
     * @param companyId
     * @param voucherId
     * @return
     */
    List<VoucherItem> findByCompanyIdAndVoucherId(Long companyId,String voucherId);

    /**
     * 根据 公司id 获得VoucherItem实体
     * @param companyId
     * @return
     */
    List<VoucherItem> findByCompanyId(Long companyId);

    /**
     * 根据凭证日期和公司id获得凭证信息
     * @param date  yyyy
     * @param companyId
     * @return
     */
    @Query("")
    List<VoucherItem> findByYear(String date,String companyId);

    /**
     * 根据凭证日期和公司id获得凭证信息
     * @param date yyyy-mm
     * @param companyId
     * @return
     */
    @Query("")
    List<VoucherItem> findByPeriod(String date,String companyId);

    /**
     *
     * @param time
     * @param id1
     * @param id2
     * @param companyId
     * @return 获取凭证中借方含有科目ID=id1，贷方含有科目ID=id2的项目，取该两个科目对应金额中的较小值
     */
    @Query("")
    List<Double> getGivenVourchers(String time, String id1, String id2,String companyId);

    /**
     *
     * @param time
     * @param id1
     * @param id2
     * @param companyId
     * @return 获取凭证中借方含有科目ID=id1，贷方含有科目ID=id2的项目，取该两个科目对应金额中的较小值
     */
    @Query("")
    List<Double> getGivenVourchersByYear(String time, String id1, String id2,String companyId);
}
