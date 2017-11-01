package cn.edu.nju.polaris.repository;

import cn.edu.nju.polaris.entity.MultiKeysClass.VoucherItemMultiKeysClass;
import cn.edu.nju.polaris.entity.VoucherItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoucherItemRepository extends JpaRepository<VoucherItem,VoucherItemMultiKeysClass>{


}
