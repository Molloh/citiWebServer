package cn.edu.nju.polaris.repository;

import cn.edu.nju.polaris.entity.AccountSet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountSetRepository extends JpaRepository<AccountSet, Long> {

    AccountSet findByAccountId(String id);

}
