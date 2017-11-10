package cn.edu.nju.polaris.repository.SupplyChainFinancing;

import cn.edu.nju.polaris.entity.SupplyChainFinancing.MovablePledgeFinancing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovablePledgeFinancingRepository extends JpaRepository<MovablePledgeFinancing,Long>{

    /**
     * 获得所有申请'动产质押融资'的请求
     * @return
     */
    List<MovablePledgeFinancing> findAll();
}
