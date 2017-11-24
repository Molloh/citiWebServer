package cn.edu.nju.polaris.repository;

import cn.edu.nju.polaris.entity.MultiKeysClass.VoucherItemMultiKeysClass;
import cn.edu.nju.polaris.entity.MultiKeysClass.VoucherMultiKeysClass;
import cn.edu.nju.polaris.entity.Voucher;
import cn.edu.nju.polaris.entity.VoucherItem;
import org.hibernate.annotations.Cache;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface VoucherRepository extends JpaRepository<Voucher,VoucherMultiKeysClass>{

    /**
     * 根据凭证号和公司id 获得一个凭证
     * @param voucherId
     * @param companyId
     * @return
     */
    Voucher findByVoucherIdAndCompanyId(String voucherId,Long companyId);

    /**
     * 获得一个公司全部的凭证
     * @param companyId
     * @return
     */
    List<Voucher> findByCompanyId(Long companyId);


    /**
     * 删除一个公司的全部凭证
     * @param companyId
     */
    @Transactional
    void deleteAllByCompanyId(Long companyId);


}
