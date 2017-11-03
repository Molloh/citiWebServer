package cn.edu.nju.polaris.repository;

import cn.edu.nju.polaris.entity.MultiKeysClass.VoucherItemMultiKeysClass;
import cn.edu.nju.polaris.entity.MultiKeysClass.VoucherMultiKeysClass;
import cn.edu.nju.polaris.entity.Voucher;
import cn.edu.nju.polaris.entity.VoucherItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoucherRepository extends JpaRepository<Voucher,VoucherMultiKeysClass>{

    /**
     * 根据凭证号和公司id 获得一个凭证
     * @param voucherId
     * @param companyId
     * @return
     */
    Voucher findByVoucherIdAndCompanyId(String voucherId,String companyId);

    /**
     * 获得一个公司全部的凭证
     * @param compamyId
     * @return
     */
    List<Voucher> findByCompanyId(String compamyId);


}
