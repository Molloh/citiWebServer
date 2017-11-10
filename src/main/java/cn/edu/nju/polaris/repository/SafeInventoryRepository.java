package cn.edu.nju.polaris.repository;

import cn.edu.nju.polaris.entity.SafeInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SafeInventoryRepository extends JpaRepository<SafeInventory,Long>{

    /**
     * 获得公司的所有 原材料安全库存量
     * @param companyId
     * @return
     */
    @Query(value = "select * from inventory_safe where company_id=?1 and name in (select name from inventory_item where category = '原材料')",nativeQuery = true)
    List<SafeInventory> findAllMaterialSafeInventory(Long companyId);


    /**
     * 获得公司的所有 产品安全库存量
     * @param companyId
     * @return
     */
    @Query(value = "select * from inventory_safe where company_id=?1 and name in (select name from inventory_item where category = '产品')",nativeQuery = true)
    List<SafeInventory> findAllProductSafeInventory(Long companyId);

    /**
     * 获得公司的指定的 原材料安全库存量
     * @param companyId
     * @param materialName
     * @return
     */
    @Query(value = "select * from inventory_safe where company_id=?1 and name=?2",nativeQuery = true)
    List<SafeInventory> findOneMaterialSafeInventory(Long companyId,String materialName);

    /**
     * 获得公司的指定的 产品安全库存量
     * @param companyId
     * @param productName
     * @return
     */
    @Query(value = "select * from inventory_safe where company_id=?1 and name=?2",nativeQuery = true)
    List<SafeInventory> findOneProductSafeInventory(Long companyId,String productName);
}
