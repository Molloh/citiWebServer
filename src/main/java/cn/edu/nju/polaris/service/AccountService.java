package cn.edu.nju.polaris.service;

import cn.edu.nju.polaris.entity.Account;
import cn.edu.nju.polaris.vo.AccountInfoVO;
import cn.edu.nju.polaris.vo.AccountVO;
import org.springframework.data.jpa.repository.Query;

public interface AccountService {

    /**
     * 修改账套
     * @param companyId
     * @param vo
     */
    void saveAccount(Long companyId,AccountInfoVO vo);

    /**
     * 注册新账号的同时必须注册新的账套
     * @param vo
     */
    void signUp(AccountVO vo);

    AccountInfoVO findAccountByCompanyName(String companyName);

    Long findCompanyIdByName(String companyName);

    AccountVO findById(Long id);
}
