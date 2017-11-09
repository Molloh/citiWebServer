package cn.edu.nju.polaris.repository;

import cn.edu.nju.polaris.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long>{

//    User findByCompanyIdAndUserName(Long companyId,String userName);

    /**
     * 因为userName不允许重复，所以不需要通过公司id
     * @param userName
     * @return
     */
    User findByUserName(String userName);
}
