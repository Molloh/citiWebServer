package cn.edu.nju.polaris.repository;

import cn.edu.nju.polaris.entity.MultiKeysClass.SupportItemMultiKeysClass;
import cn.edu.nju.polaris.entity.SupportItem1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
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


    /**
     * 根据公司id和时间之后 获得原材料辅助信息
     * @param companyId
     * @param date  yyyy-mm-dd
     * @return
     */
    @Query(value = "select * from support_item1 where company_id=?1 and date<?2 and variety in (select name from inventory_item where variety='原材料')",nativeQuery = true)
    List<SupportItem1> findAllRawMaterial(Long companyId, String date);


    /**
     * 根据公司id和时间之后 获得产品辅助信息
     * @param companyId
     * @param date
     * @return
     */
    @Query(value = "select * from support_item1 where company_id=?1 and date<?2 and variety in (select name from inventory_item where variety='产品')",nativeQuery = true)
    List<SupportItem1> findAllProduct(Long companyId, String date);


    /**
     * 获得某一个特定原材料的辅助信息
     * @param companyId
     * @param date
     * @param materialName
     * @return
     */
    @Query(value = "select * from support_item1 where company_id=?1 and date<?2 and variety=?3",nativeQuery = true)
    List<SupportItem1> findOneMaterial(Long companyId, Timestamp date, String materialName);

    /**
     * 获得某一个特定产品的辅助信息
     * @param companyId
     * @param date
     * @param productName
     * @return
     */
    @Query(value = "select * from support_item1 where company_id=?1 and date<?2 and variety=?3",nativeQuery = true)
    List<SupportItem1> findOneProduct(Long companyId, Timestamp date, String productName);
}
