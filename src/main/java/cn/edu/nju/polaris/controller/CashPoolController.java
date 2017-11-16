package cn.edu.nju.polaris.controller;

import cn.edu.nju.polaris.service.CashPoolService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cashpool")
public class CashPoolController {

    private final CashPoolService cashPoolService;

    public CashPoolController(CashPoolService cashPoolService) {
        this.cashPoolService = cashPoolService;
    }

    @ApiOperation(value = "得到现金流量表里的 现金流入、现金流出、现金池内留存的现金")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyId",value = "公司ID",required = true,dataType = "Long"),
            @ApiImplicitParam(name = "time",value = "时间(yyyy-mm)",required = true,dataType = "String")
    })
    @GetMapping("/flow")
    double[] getCashFlow(@RequestParam Long companyId,
                         @RequestParam String time){
        return cashPoolService.getCashFlow(companyId,time);
    }

    @ApiOperation(value = "得到与现金有关的财务指标")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyId",value = "公司ID",required = true,dataType = "Long"),
            @ApiImplicitParam(name = "time",value = "时间(yyyy-mm)",required = true,dataType = "String")
    })
    @GetMapping("/index")
    double[] getFinancialIndex(@RequestParam Long companyId,
                               @RequestParam String time){
        return cashPoolService.getFinancialIndex(companyId,time);
    }
}
