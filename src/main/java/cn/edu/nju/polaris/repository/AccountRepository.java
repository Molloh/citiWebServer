package cn.edu.nju.polaris.repository;

import cn.edu.nju.polaris.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account,Long>{

    Account findByCompanyName(String companyName);

    Account findByEmail(String email);

    Account findById(Long id);

    List<Account> findAll();
}
