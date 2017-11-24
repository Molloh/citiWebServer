package cn.edu.nju.polaris.repository;

import cn.edu.nju.polaris.entity.Account;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@CacheConfig(cacheNames = "accounts")
public interface AccountRepository extends JpaRepository<Account,Long>{

    @Cacheable
    Account findByCompanyName(String companyName);

    Account findByEmail(String email);

    @Cacheable
    Account findById(Long id);

    List<Account> findAll();
}
