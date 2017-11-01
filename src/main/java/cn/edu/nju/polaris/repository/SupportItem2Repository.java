package cn.edu.nju.polaris.repository;

import cn.edu.nju.polaris.entity.MultiKeysClass.SupportItemMultiKeysClass;
import cn.edu.nju.polaris.entity.SupportItem2;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupportItem2Repository extends JpaRepository<SupportItem2,SupportItemMultiKeysClass>{
}
