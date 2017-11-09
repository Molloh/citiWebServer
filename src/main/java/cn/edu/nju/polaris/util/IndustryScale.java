package cn.edu.nju.polaris.util;

import cn.edu.nju.polaris.entity.Account;
import cn.edu.nju.polaris.repository.AccountRepository;
import cn.edu.nju.polaris.repository.BalanceSheetRepository;
import cn.edu.nju.polaris.repository.ProfitSheetRepository;
import cn.edu.nju.polaris.repository.VoucherItemRepository;
import cn.edu.nju.polaris.service.BalanceSheetService;
import cn.edu.nju.polaris.service.Impl.BalanceSheetImpl;
import cn.edu.nju.polaris.service.Impl.ProfitTableImpl;
import cn.edu.nju.polaris.service.ProfitTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Created by 费慧通 on 2017/11/3.
 */
@Component
public class IndustryScale {
    private final BalanceSheetRepository balanceSheetRepository;
    private final ProfitSheetRepository profitSheetRepository;
    private final AccountRepository accountRepository;
    private final VoucherItemRepository voucherItemRepository;

    @Autowired
    public IndustryScale(BalanceSheetRepository balanceSheetRepository, ProfitSheetRepository profitSheetRepository, AccountRepository accountRepository, VoucherItemRepository voucherItemRepository) {
        this.balanceSheetRepository = balanceSheetRepository;
        this.profitSheetRepository = profitSheetRepository;
        this.accountRepository = accountRepository;
        this.voucherItemRepository = voucherItemRepository;
    }

    public void setIndustrySize(long company_id){
        String phase = LocalDate.now().toString().substring(0,7);
        ProfitTableService service1 = new ProfitTableImpl(profitSheetRepository,voucherItemRepository);
        double income = service1.getIncome(phase,company_id);
        BalanceSheetService service2 = new BalanceSheetImpl(balanceSheetRepository);
        double asset = service2.getTotalAsset(company_id,phase);

        Account account = accountRepository.findById(company_id);
        String industry = account.getFirstIndustry();
        String size = "";
        switch (industry){
            case "农林牧渔业":
                //后期检查需要考虑单位
                if(income<5000000){
                    size = "小型";
                }else{
                    size = "中型";
                }
                break;
            case "工业":
                if(income<20000000){
                    size = "小型";
                }else{
                    size = "中型";
                }
                break;
            case "建筑业":
                if(income<60000000&&asset<50000000){
                    size = "小型";
                }else{
                    size = "中型";
                }
                break;
            case "批发业":
                if(income<50000000){
                    size = "小型";
                }else{
                    size = "中型";
                }
                break;
            case "零售业":
                if(income<5000000){
                    size = "小型";
                }else{
                    size = "中型";
                }
                break;
            case "交通运输业":
                if(income<30000000){
                    size = "小型";
                }else{
                    size = "中型";
                }
                break;
            case "仓储业":
                if(income<10000000){
                    size = "小型";
                }else{
                    size = "中型";
                }
                break;
            case "邮政业":
                if(income<20000000){
                    size = "小型";
                }else{
                    size = "中型";
                }
                break;
            case "住宿业":
                if(income<20000000){
                    size = "小型";
                }else{
                    size = "中型";
                }
                break;
            case "餐饮业":
                if(income<20000000){
                    size = "小型";
                }else{
                    size = "中型";
                }
                break;
            case "信息传输业":
                if(income<10000000){
                    size = "小型";
                }else{
                    size = "中型";
                }
                break;
            case "软件和信息服务业":
                if(income<10000000){
                    size = "小型";
                }else{
                    size = "中型";
                }
                break;
            case "房地产开发经营":
                if(income<10000000&&asset<50000000){
                    size = "小型";
                }else{
                    size = "中型";
                }
                break;
            case "物业管理":
                if(income<10000000){
                    size = "小型";
                }else{
                    size = "中型";
                }
                break;
            case "租赁和商务服务业":
                if(asset<80000000){
                    size = "小型";
                }else{
                    size = "中型";
                }
                break;
            default:
                size = "小型";
                break;
        }

        //需要将行业类型划分保存进数据库
        account.setScale(size);
        accountRepository.save(account);
    }
}
