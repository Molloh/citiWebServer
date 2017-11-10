package cn.edu.nju.polaris.repository.SupplyChainFinancing;

import cn.edu.nju.polaris.entity.SupplyChainFinancing.ReceivablesFinancing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReceivablesFinancingRepository extends JpaRepository<ReceivablesFinancing,Long>{

    /**
     * 获得所有申请'应收账款融资'的请求
     * @return
     */
    List<ReceivablesFinancing> findAll();
}
