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
     * 根据公司id获得所有记录
     * @param companyId
     * @return
     */
    List<SupportItem1> findAllByCompanyId(Long companyId);

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

    void deleteByCompanyIdAndVoucherId(Long companyId,String voucherId);


    /**
     * 得到一个公司所有原材料的最新一条记录
     * @param companyId
     * @return
     */
    @Query(value = "SELECT * FROM support_item1 WHERE company_id =1 AND (date,variety) IN \n" +
            "(SELECT max(date),variety\n" +
            "FROM support_item1\n" +
            "WHERE company_id = 1 AND variety IN (SELECT name FROM inventory_item WHERE category='原材料')\n" +
            "GROUP BY variety)",nativeQuery = true)
    List<SupportItem1> findNewestRawMaterial(Long companyId);

    /**
     * 得到一个公司所有产品的最新一条记录
     * @param companyId
     * @return
     */
    @Query(value = "SELECT * FROM support_item1 WHERE company_id =1 AND (date,variety) IN \n" +
            "(SELECT max(date),variety\n" +
            "FROM support_item1\n" +
            "WHERE company_id = 1 AND variety IN (SELECT name FROM inventory_item WHERE category='产品')\n" +
            "GROUP BY variety)",nativeQuery = true)
    List<SupportItem1>findNewestProduct(Long companyId);

    /**
     * 根据公司id，收入/发出方名称，日期为期数
     * @param compangId
     * @param companyName 当companyId所代表公司为发出方时，companyName为收入方名称   当companyId所代表公司为收入方时，companyName为发出方名称
     * @param date yyyy-mm
     * @return
     */
    @Query(value = "select * from support_item1 where company_id=?1 and date_format(date,'%Y-%m')=?3 and end_side=?2",nativeQuery = true)
    List<SupportItem1> findAllByCompanyIdAndEndSideAndDate(Long compangId,String companyName,String date);

    /**
     * 根据公司id和时间 获得当期辅助信息
     * @param companyId
     * @param date 期数
     * @return
     */
    @Query(value = "select * from support_item1 where company_id=?1 and date_format(date,'%Y-%m')=?2",nativeQuery = true)
    List<SupportItem1> findAllItemByDate(Long companyId,String date);


    /**
     * 根据公司id和时间之后 获得原材料辅助信息
     * @param companyId
     * @param date  yyyy-mm-dd
     * @return
     */
    @Query(value = "select * from support_item1 where company_id=?1 and date>?2 and variety in (select name from inventory_item where category='原材料')",nativeQuery = true)
    List<SupportItem1> findAllRawMaterial(Long companyId, String date);


    /**
     * 根据公司id和时间之后 获得产品辅助信息
     * @param companyId
     * @param date
     * @return
     */
    @Query(value = "select * from support_item1 where company_id=?1 and date>?2 and variety in (select name from inventory_item where category='产品')",nativeQuery = true)
    List<SupportItem1> findAllProduct(Long companyId, String date);


    /**
     * 获得某一个特定原材料的辅助信息
     * @param companyId
     * @param date
     * @param materialName
     * @return
     */
    @Query(value = "select * from support_item1 where company_id=?1 and date>?2 and variety=?3",nativeQuery = true)
    List<SupportItem1> findOneMaterial(Long companyId, Timestamp date, String materialName);

    /**
     * 获得某一个特定产品的辅助信息
     * @param companyId
     * @param date
     * @param productName
     * @return
     */
    @Query(value = "select * from support_item1 where company_id=?1 and date>?2 and variety=?3",nativeQuery = true)
    List<SupportItem1> findOneProduct(Long companyId, Timestamp date, String productName);
}
