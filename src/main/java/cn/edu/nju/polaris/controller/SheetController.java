package cn.edu.nju.polaris.controller;

import cn.edu.nju.polaris.service.BalanceSheetService;
import cn.edu.nju.polaris.service.CashFlowService;
import cn.edu.nju.polaris.service.ProfitTableService;
import cn.edu.nju.polaris.sheet.CalculateBalanceSheet;
import cn.edu.nju.polaris.sheet.CashFlowTableSheetCal;
import cn.edu.nju.polaris.sheet.ProfitTableSheetCal;
import cn.edu.nju.polaris.vo.BalanceSheetItemVo;
import cn.edu.nju.polaris.vo.Pro_and_CashVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sheets")
public class SheetController {

    private final BalanceSheetService balanceSheetService;
    private final CashFlowService cashFlowService;
    private final ProfitTableService profitTableService;
    private final CalculateBalanceSheet balanceSheetUpdate;
    private final CashFlowTableSheetCal cashflowSheetUpdate;
    private final ProfitTableSheetCal profitTableSheetUpdate;

    public SheetController(BalanceSheetService balanceSheetService, CashFlowService cashFlowService, ProfitTableService profitTableService, CalculateBalanceSheet balanceSheetUpdate, CashFlowTableSheetCal cashflowSheetUpdate, ProfitTableSheetCal profitTableSheetUpdate) {
        this.balanceSheetService = balanceSheetService;
        this.cashFlowService = cashFlowService;
        this.profitTableService = profitTableService;
        this.balanceSheetUpdate = balanceSheetUpdate;
        this.cashflowSheetUpdate = cashflowSheetUpdate;
        this.profitTableSheetUpdate = profitTableSheetUpdate;
    }

    @ApiOperation(value = "更新资产负债表",notes = "根据公司id和期数（YYYY-mm）更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyId",value = "公司id",required = true,dataType = "Long"),
            @ApiImplicitParam(name = "phase",value = "期数",required = true,dataType = "String")
    })
    @PutMapping("/balance/update/{companyId}")
    public void updateBalanceSheet(@PathVariable Long companyId,
                                   @RequestParam String phase){
        balanceSheetUpdate.UpdateBalanceSheet(companyId,phase);
    }

    @ApiOperation(value = "更新现金流量表",notes = "根据公司id和期数（YYYY-mm）更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyId",value = "公司id",required = true,dataType = "Long"),
            @ApiImplicitParam(name = "phase",value = "期数",required = true,dataType = "String")
    })
    @PutMapping("/cashflow/update/{companyId}")
    public void updateCashFlowSheet(@PathVariable Long companyId,
                                    @RequestParam String phase){
        cashflowSheetUpdate.UpdateCashFlowTable(phase,companyId);
    }

    @ApiOperation(value = "更新利润表",notes = "根据公司id和期数（YYYY-mm）更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyId",value = "公司id",required = true,dataType = "Long"),
            @ApiImplicitParam(name = "phase",value = "期数",required = true,dataType = "String")
    })
    @PutMapping("/profit/update/{companyId}")
    public void updateProfitSheet(@PathVariable Long companyId,
                                  @RequestParam String phase){
        profitTableSheetUpdate.UpdateProfitTable(phase,companyId);
    }

    @ApiOperation(value = "获得资产负债表",notes = "根据公司id和期数（YYYY-mm）获得")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyId",value = "公司id",required = true,dataType = "Long"),
            @ApiImplicitParam(name = "phase",value = "期数",required = true,dataType = "String")
    })
    @GetMapping("/balance/{companyId}")
    public List<BalanceSheetItemVo> getBalanceSheet(@PathVariable Long companyId,
                                                    @RequestParam String phase){
        return balanceSheetService.getBalanceSheet(companyId,phase);
    }

    @ApiOperation(value = "获得现金流量表",notes = "根据公司id和期数（YYYY-mm）获得")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyId",value = "公司id",required = true,dataType = "Long"),
            @ApiImplicitParam(name = "phase",value = "期数",required = true,dataType = "String")
    })
    @GetMapping("/cashflow/{companyId}")
    public List<Pro_and_CashVo> getCashFLowSheet(@PathVariable Long companyId,
                                                 @RequestParam String phase){
        return cashFlowService.CashFlowTable_Info(phase,companyId);
    }

    @ApiOperation(value = "获得利润表",notes = "根据公司id和期数（YYYY-mm）获得")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyId",value = "公司id",required = true,dataType = "Long"),
            @ApiImplicitParam(name = "phase",value = "期数",required = true,dataType = "String")
    })
    @GetMapping("/profit/{companyId}")
    public List<Pro_and_CashVo> getProfitSheet(@PathVariable Long companyId,
                                               @RequestParam String phase){
        return profitTableService.ProfitTable_Info(phase,companyId);
    }
}
