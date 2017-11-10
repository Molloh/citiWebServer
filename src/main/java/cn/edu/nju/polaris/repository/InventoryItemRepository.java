package cn.edu.nju.polaris.repository;

import cn.edu.nju.polaris.entity.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryItemRepository extends JpaRepository<InventoryItem,Long>{

    /**
     * 获得所有的原材料或产品列表
     * @param category  填'原材料'或'产品'
     * @return
     */
    List<InventoryItem> findAllByCategory(String category);

}
