package cn.edu.nju.polaris.repository;

import cn.edu.nju.polaris.entity.BalanceSheet;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Id;

public interface BalanceSheetRepository extends JpaRepository<BalanceSheet,Long>{


}
