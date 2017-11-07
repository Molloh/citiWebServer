package cn.edu.nju.polaris.repository;

import cn.edu.nju.polaris.entity.SubjectsBalance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectsBalanceRepository extends JpaRepository<SubjectsBalance,Long>{

    List<SubjectsBalance> findByCompanyId(Long companyId);
}