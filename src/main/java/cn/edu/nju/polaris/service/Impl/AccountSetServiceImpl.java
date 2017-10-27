package cn.edu.nju.polaris.service.Impl;

import cn.edu.nju.polaris.entity.AccountSet;
import cn.edu.nju.polaris.repository.AccountSetRepository;
import cn.edu.nju.polaris.service.AccountSetService;
import cn.edu.nju.polaris.vo.AccountSetVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountSetServiceImpl implements AccountSetService{

    private final AccountSetRepository accountSetRepository;

    @Autowired
    public AccountSetServiceImpl(AccountSetRepository accountSetRepository){
        this.accountSetRepository = accountSetRepository;
    }


    @Override
    public AccountSetVO getAccountSet(String id) {
        AccountSet set = accountSetRepository.findByAccountId(id);
        AccountSetVO vo = new AccountSetVO();
        vo.accountId = set.getAccountId();
        vo.chainPlace = set.getChainPlace();
        vo.companyId = set.getCompanyId();
        vo.companyName = set.getCompanyName();
        vo.contact = set.getContact();
        vo.date = set.getDate();
        vo.creditCode = set.getCreditCode();
        vo.location = set.getLocation();
        vo.industry = set.getIndustry();
        return vo;
    }
}
