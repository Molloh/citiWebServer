package cn.edu.nju.polaris.controller;

import cn.edu.nju.polaris.service.BalanceSheetService;
import cn.edu.nju.polaris.service.CashFlowService;
import cn.edu.nju.polaris.service.ProfitTableService;
import cn.edu.nju.polaris.sheet.CalculateBalanceSheet;
import cn.edu.nju.polaris.sheet.CashFlowTableSheetCal;
import cn.edu.nju.polaris.sheet.ProfitTableSheetCal;
import cn.edu.nju.polaris.vo.BalanceSheetItemVo;
import cn.edu.nju.polaris.vo.Pro_and_CashVo;
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

    @PutMapping("/balance/update/{companyId}")
    public void updateBalanceSheet(@PathVariable Long companyId,
                                   @RequestParam String phase){
        balanceSheetUpdate.UpdateBalanceSheet(companyId,phase);
    }

    @PutMapping("/cashflow/update/{companyId}")
    public void updateCashFlowSheet(@PathVariable Long companyId,
                                    @RequestParam String phase){
        cashflowSheetUpdate.UpdateCashFlowTable(phase,companyId);
    }

    @PutMapping("/profit/update/{companyId}")
    public void updateProfitSheet(@PathVariable Long companyId,
                                  @RequestParam String phase){
        profitTableSheetUpdate.UpdateProfitTable(phase,companyId);
    }

    @GetMapping("/balance/{companyId}")
    public List<BalanceSheetItemVo> getBalanceSheet(@PathVariable Long companyId,
                                                    @RequestParam String phase){
        return balanceSheetService.getBalanceSheet(companyId,phase);
    }

    @GetMapping("/cashflow/{companyId}")
    public List<Pro_and_CashVo> getCashFLowSheet(@PathVariable Long companyId,
                                                 @RequestParam String phase){
        return cashFlowService.CashFlowTable_Info(phase,companyId);
    }

    @GetMapping("/profit/{companyId}")
    public List<Pro_and_CashVo> getProfitSheet(@PathVariable Long companyId,
                                               @RequestParam String phase){
        return profitTableService.ProfitTable_Info(phase,companyId);
    }
}
