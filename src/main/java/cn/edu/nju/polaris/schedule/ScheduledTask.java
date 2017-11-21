package cn.edu.nju.polaris.schedule;

import cn.edu.nju.polaris.entity.Account;
import cn.edu.nju.polaris.repository.AccountRepository;
import cn.edu.nju.polaris.sheet.CalculateBalanceSheet;
import cn.edu.nju.polaris.sheet.CashFlowTableSheetCal;
import cn.edu.nju.polaris.sheet.ProfitTableSheetCal;
import cn.edu.nju.polaris.util.DateConvert;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class ScheduledTask {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
    private final AccountRepository accountRepository;
    private final CalculateBalanceSheet balanceSheetUpdate;
    private final CashFlowTableSheetCal cashflowSheetUpdate;
    private final ProfitTableSheetCal profitTableSheetUpdate;

    public ScheduledTask(AccountRepository accountRepository, CalculateBalanceSheet balanceSheetUpdate, CashFlowTableSheetCal cashflowSheetUpdate, ProfitTableSheetCal profitTableSheetUpdate) {
        this.accountRepository = accountRepository;
        this.balanceSheetUpdate = balanceSheetUpdate;
        this.cashflowSheetUpdate = cashflowSheetUpdate;
        this.profitTableSheetUpdate = profitTableSheetUpdate;
    }

//    @Scheduled(cron = "0 0 1 * * ?")
    public void updateSheet() throws ParseException {
        List<Account> accounts = accountRepository.findAll();
        for (Account account : accounts){
            Long companyId = account.getId();
            String beginPeriod = dateFormat.format(account.getActiveTime());
            String endPeriod = dateFormat.format(new Date());
            List<String> section = DateConvert.getPeriodSection(beginPeriod,endPeriod);
            for (String aSection : section) {
                balanceSheetUpdate.UpdateBalanceSheet(companyId, aSection);
            }
            for (String aSection : section){
                cashflowSheetUpdate.UpdateCashFlowTable(aSection,companyId);
            }
            for (String aSection : section){
                profitTableSheetUpdate.UpdateProfitTable(aSection,companyId);
            }
        }
    }

}
