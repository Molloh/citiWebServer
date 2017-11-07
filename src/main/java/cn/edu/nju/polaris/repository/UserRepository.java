package cn.edu.nju.polaris.repository;

import cn.edu.nju.polaris.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long>{

    User findByCompanyIdAndUserName(String companyId,String userName);

    User findByUserName(String userName);
}
