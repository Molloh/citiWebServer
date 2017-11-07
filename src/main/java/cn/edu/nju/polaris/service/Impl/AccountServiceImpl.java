package cn.edu.nju.polaris.service.Impl;

import cn.edu.nju.polaris.entity.Account;
import cn.edu.nju.polaris.exception.ResourceConflictException;
import cn.edu.nju.polaris.repository.AccountRepository;
import cn.edu.nju.polaris.service.AccountService;
import cn.edu.nju.polaris.vo.AccountInfoVO;
import cn.edu.nju.polaris.vo.AccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    @Override
    public void saveAccount(Long companyId,AccountInfoVO vo) {
        Account account = accountRepository.findById(companyId);
        if (vo != null && account != null){
            account.setLocation(vo.getLocation());
            account.setEmail(vo.getEmail());
            account.setFirstIndustry(vo.getFirstIndustry());
            account.setSecondIndustry(vo.getSecondIndustry());
            account.setScale(vo.getScale());
            accountRepository.save(account);
        }
    }

    @Override
    public void signUp(AccountVO vo) {
        if (accountRepository.findByEmail(vo.getEmail()) != null){
            throw new ResourceConflictException("该邮箱已被注册!");
        }
        if (accountRepository.findByCompanyName(vo.getCompanyName()) != null){
            throw new ResourceConflictException("该公司已被注册!");
        }
        accountRepository.save(vo2Entity(vo));
    }

    @Override
    public AccountInfoVO findAccountByCompanyName(String companyName) {
        Account account = accountRepository.findByCompanyName(companyName);
        if (account != null){
            AccountInfoVO infoVO = new AccountInfoVO();
            infoVO.setLocation(account.getLocation());
            infoVO.setScale(account.getScale());
            infoVO.setFirstIndustry(account.getFirstIndustry());
            infoVO.setSecondIndustry(account.getSecondIndustry());
            infoVO.setEmail(account.getEmail());
            return infoVO;
        }
        return null;
    }

    @Override
    public Long findCompanyIdByName(String companyName) {
        Account account = accountRepository.findByCompanyName(companyName);
        return account.getId();
    }


    private Account vo2Entity(AccountVO vo){
        Account account = new Account();
        account.setId(vo.getId());
        account.setCompanyName(vo.getCompanyName());
        account.setLocation(vo.getLocation());
        account.setActiveTime(vo.getActiveTime());
        account.setFirstIndustry(vo.getFirstIndustry());
        account.setSecondIndustry(vo.getSecondIndustry());
        account.setEmail(vo.getEmail());
        return account;
    }

}
