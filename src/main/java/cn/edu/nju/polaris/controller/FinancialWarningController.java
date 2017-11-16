package cn.edu.nju.polaris.controller;

import cn.edu.nju.polaris.service.FinancialWarningService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/financial")
public class FinancialWarningController {

    private final FinancialWarningService financialWarningService;

    public FinancialWarningController(FinancialWarningService financialWarningService) {
        this.financialWarningService = financialWarningService;
    }

    @ApiOperation(value = "得到财务预警信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyId",value = "公司ID",required = true,dataType = "Long"),
            @ApiImplicitParam(name = "phase",value = "期数",required = true,dataType = "String")
    })
    @GetMapping("")
    double[] getWarningMessage(@RequestParam Long companyId,
                               @RequestParam String phase){
        return financialWarningService.getWarningMessage(companyId,phase);
    }
}
