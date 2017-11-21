package cn.edu.nju.polaris.controller;

import cn.edu.nju.polaris.service.AccountService;
import cn.edu.nju.polaris.vo.AccountInfoVO;
import cn.edu.nju.polaris.vo.AccountVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @ApiOperation(value = "添加新账套",notes = "一个公司仅有一个账套，公司初次使用时必须添加账套和ADMIN用户")
    @ApiImplicitParam(name = "vo",value = "账套VO",required = true,dataType = "AccountVO")
    @PostMapping
    public void signUp(@RequestBody AccountVO vo){
        accountService.signUp(vo);
    }

    @ApiOperation(value = "获得账套信息",notes = "根据公司名称获得账套信息")
    @ApiImplicitParam(name = "companyName",value = "公司名称",required = true,dataType = "String")
    @GetMapping("/{companyName}")
    public AccountInfoVO findAccountByCompanyName(@PathVariable String companyName){
        return accountService.findAccountByCompanyName(companyName);
    }

    @ApiOperation(value = "获得公司的id",notes = "根据公司名称获得公司id")
    @ApiImplicitParam(name = "companyName",value = "公司名称",required = true,dataType = "String")
    @GetMapping("/id/{companyName}")
    public Long findCompanyIdByName(@PathVariable String companyName){
        return accountService.findCompanyIdByName(companyName);
    }

    @ApiOperation(value = "修改账套信息",notes = "根据公司id修改公司账套信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyId",value = "公司id",required = true,dataType = "Long"),
            @ApiImplicitParam(name = "vo",value = "公司账套信息VO",required = true,dataType = "AccountInfoVO")
    })
    @PutMapping("{companyId}")
    public void modifyAccount(@PathVariable Long companyId,
                              @RequestBody AccountInfoVO vo){
        accountService.saveAccount(companyId,vo);
    }

    @ApiOperation(value = "获得账套信息",notes = "根据公司id获得账套信息")
    @ApiImplicitParam(name = "id",value = "公司ID",required = true,dataType = "Long")
    @GetMapping("/{id}")
    public AccountVO findById(@PathVariable Long id){
        return accountService.findById(id);
    }
}
