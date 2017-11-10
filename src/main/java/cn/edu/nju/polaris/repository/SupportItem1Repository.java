package cn.edu.nju.polaris.repository;

import cn.edu.nju.polaris.entity.MultiKeysClass.SupportItemMultiKeysClass;
import cn.edu.nju.polaris.entity.SupportItem1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SupportItem1Repository extends JpaRepository<SupportItem1,SupportItemMultiKeysClass>{

    /**
     * 获得一个凭证行数对应的全部辅助信息一 如果没有就返回null
     * @param companyId
     * @param voucherId
     * @param voucherLines
     * @return
     */
    List<SupportItem1> findAllByCompanyIdAndVoucherIdAndVoucherLines(Long companyId, String voucherId, int voucherLines);

    /**
     * 删除一个凭证行数对应的全部辅助信息一
     * @param companyId
     * @param voucherId
     * @param voucherLines
     */
    void deleteAllByCompanyIdAndVoucherIdAndVoucherLines(Long companyId,String voucherId,int voucherLines);

    /**
     * 删除辅助信息一种的一行
     * @param companyId
     * @param voucherId
     * @param voucherLines
     * @param supportLines
     */
    void deleteByCompanyIdAndVoucherIdAndVoucherLinesAndSupportLines(Long companyId,String voucherId,int voucherLines,int supportLines);


}
