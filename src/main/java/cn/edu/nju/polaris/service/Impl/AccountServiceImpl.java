package cn.edu.nju.polaris.service.Impl;

import cn.edu.nju.polaris.entity.Account;
import cn.edu.nju.polaris.entity.SubjectInitial;
import cn.edu.nju.polaris.entity.Subjects;
import cn.edu.nju.polaris.exception.ResourceConflictException;
import cn.edu.nju.polaris.exception.ResourceNotFoundException;
import cn.edu.nju.polaris.repository.AccountRepository;
import cn.edu.nju.polaris.repository.SubjectInitialRepository;
import cn.edu.nju.polaris.repository.SubjectsRepository;
import cn.edu.nju.polaris.service.AccountService;
import cn.edu.nju.polaris.service.SubjectBalanceService;
import cn.edu.nju.polaris.util.DateHelper;
import cn.edu.nju.polaris.vo.AccountInfoVO;
import cn.edu.nju.polaris.vo.AccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final SubjectBalanceService subjectBalanceService;
    private final SubjectInitialRepository subjectInitialRepository;
    private final SubjectsRepository subjectsRepository;

    public AccountServiceImpl(AccountRepository accountRepository, SubjectBalanceService subjectBalanceService, SubjectInitialRepository subjectInitialRepository, SubjectsRepository subjectsRepository) {
        this.accountRepository = accountRepository;
        this.subjectBalanceService = subjectBalanceService;
        this.subjectInitialRepository = subjectInitialRepository;
        this.subjectsRepository = subjectsRepository;
    }

    @Override
    public void saveAccount(Long companyId,AccountInfoVO vo) {
        Account account = accountRepository.findById(companyId);
        if (vo != null && account != null){
            account.setLocation(vo.getLocation());
            account.setEmail(vo.getEmail());
            account.setActiveTime(vo.getActiveTime());
            account.setFirstIndustry(vo.getFirstIndustry());
            account.setSecondIndustry(vo.getSecondIndustry());
            account.setSupplyChainIndex(vo.getSupplyChainIndex());
            account.setScale(vo.getScale());
            accountRepository.save(account);
        }
    }

    @Override
    public void signUp(AccountVO vo) {
        if (accountRepository.findByEmail(vo.getEmail()) != null){
            throw new ResourceConflictException("该邮箱已被注册");
        }
        if (accountRepository.findByCompanyName(vo.getCompanyName()) != null){
            throw new ResourceConflictException("该公司已被注册");
        }
        accountRepository.save(vo2Entity(vo));

        Account account = accountRepository.findByCompanyName(vo.getCompanyName());
        subjectBalanceService.initialSubjectBalance(account.getId(), vo.getActiveTime().toString().substring(0,7));

        List<Subjects> subjects = subjectsRepository.findAll();
        for (Subjects subject : subjects){
            SubjectInitial initial = new SubjectInitial();
            initial.setSubjectsId(subject.getSubjectsId());
            initial.setCompanyId(account.getId());
            initial.setBalance(0.0);
            initial.setDebitAmount(0.0);
            initial.setCreditAmount(0.0);
            initial.setYearBalance(0.0);
            subjectInitialRepository.save(initial);
        }
    }

    @Override
    public AccountInfoVO findAccountByCompanyName(String companyName) {
        Account account = accountRepository.findByCompanyName(companyName);
        if (account != null){
            AccountInfoVO infoVO = new AccountInfoVO();
            infoVO.setLocation(account.getLocation());
            infoVO.setScale(account.getScale());
            infoVO.setSupplyChainIndex(account.getSupplyChainIndex());
            infoVO.setFirstIndustry(account.getFirstIndustry());
            infoVO.setSecondIndustry(account.getSecondIndustry());
            infoVO.setEmail(account.getEmail());
            infoVO.setActiveTime(account.getActiveTime());
            return infoVO;
        }
        throw new ResourceNotFoundException("不存在此公司的账套");
    }

    @Override
    public Long findCompanyIdByName(String companyName) {
        Account account = accountRepository.findByCompanyName(companyName);
        return account.getId();
    }

    @Override
    public AccountVO findById(Long id) {
        Account account = accountRepository.findById(id);
        AccountVO vo = new AccountVO();
        if (account != null){
            vo.setId(account.getId());
            vo.setScale(account.getScale());
            vo.setEmail(account.getEmail());
            vo.setFirstIndustry(account.getFirstIndustry());
            vo.setSecondIndustry(account.getSecondIndustry());
            vo.setLocation(account.getLocation());
            vo.setCompanyName(account.getCompanyName());
            vo.setActiveTime(account.getActiveTime());
            vo.setSupplyChainIndex(account.getSupplyChainIndex());
        }
        return vo;
    }


    private Account vo2Entity(AccountVO vo){
        Account account = new Account();
        account.setId(vo.getId());
        account.setCompanyName(vo.getCompanyName());
        account.setSupplyChainIndex(vo.getSupplyChainIndex());
        account.setScale(vo.getScale());
        account.setLocation(vo.getLocation());
        account.setActiveTime(vo.getActiveTime());
        account.setFirstIndustry(vo.getFirstIndustry());
        account.setSecondIndustry(vo.getSecondIndustry());
        account.setEmail(vo.getEmail());
        account.setActiveTime(vo.getActiveTime());
        return account;
    }

}
