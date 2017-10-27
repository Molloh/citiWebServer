package cn.edu.nju.polaris.controller;

import cn.edu.nju.polaris.entity.AccountSet;
import cn.edu.nju.polaris.service.AccountSetService;
import cn.edu.nju.polaris.vo.AccountSetVO;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountSetController {

    private final AccountSetService accountSetService;

    public AccountSetController(AccountSetService accountSetService) {
        this.accountSetService = accountSetService;
    }

    @GetMapping(value = "/{id}")
    public AccountSetVO getAccountSet(@PathVariable String id){
        return accountSetService.getAccountSet(id);
    }
}
