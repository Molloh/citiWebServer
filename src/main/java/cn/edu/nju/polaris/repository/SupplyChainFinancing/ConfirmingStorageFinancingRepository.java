package cn.edu.nju.polaris.repository.SupplyChainFinancing;

import cn.edu.nju.polaris.entity.SupplyChainFinancing.ConfirmingStorageFinancing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConfirmingStorageFinancingRepository extends JpaRepository<ConfirmingStorageFinancing,Long> {

    /**
     * 获得所有申请'保兑仓融资'的请求
     * @return
     */
    List<ConfirmingStorageFinancing> findAll();
}
