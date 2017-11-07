package cn.edu.nju.polaris.repository;

import cn.edu.nju.polaris.entity.MultiKeysClass.VoucherItemMultiKeysClass;
import cn.edu.nju.polaris.entity.VoucherItem;
import org.springframework.data.jpa.repository.JpaRepository;

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

    //TODO
//    List<VoucherItem>
}
