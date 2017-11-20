package cn.edu.nju.polaris.service.Impl;

import cn.edu.nju.polaris.entity.Account;
import cn.edu.nju.polaris.entity.IndustryIndex;
import cn.edu.nju.polaris.repository.*;
import cn.edu.nju.polaris.service.BalanceSheetService;
import cn.edu.nju.polaris.service.CashFlowService;
import cn.edu.nju.polaris.service.CashPoolService;
import cn.edu.nju.polaris.service.ProfitTableService;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 费慧通 on 2017/11/13.
 */
@Service
public class CashPoolImpl implements CashPoolService {
    private final BalanceSheetRepository balanceSheetRepository;
    private final ProfitSheetRepository profitSheetRepository;
    private final CashflowSheetRepository cashflowSheetRepository;
    private final VoucherItemRepository voucherItemRepository;
    private final AccountRepository accountRepository;
    private final IndustryIndexRepository industryIndexRepository;

    public CashPoolImpl(BalanceSheetRepository balanceSheetRepository, ProfitSheetRepository profitSheetRepository, CashflowSheetRepository cashflowSheetRepository, VoucherItemRepository voucherItemRepository, AccountRepository accountRepository, IndustryIndexRepository industryIndexRepository) {
        this.balanceSheetRepository = balanceSheetRepository;
        this.profitSheetRepository = profitSheetRepository;
        this.cashflowSheetRepository = cashflowSheetRepository;
        this.voucherItemRepository = voucherItemRepository;
        this.accountRepository = accountRepository;
        this.industryIndexRepository = industryIndexRepository;
    }

    @Override
    public double[] getCashFlow(long company_id, String time) {
        CashFlowService service = new CashFlowImpl(cashflowSheetRepository);
        return service.getCashFlow(time,company_id);
    }

    @Override
    public double[] getFinancialIndex(long company_id, String time) {
        BalanceSheetService service1 = new BalanceSheetImpl(balanceSheetRepository);
        ProfitTableService service2 = new ProfitTableImpl(profitSheetRepository,voucherItemRepository);
        CashFlowService service3 = new CashFlowImpl(cashflowSheetRepository);
        //本期期末总资产、流动资产、流动负债、本期期末应收帐款、本期期末存货
        double[] data1 = service1.getValue2(company_id, time);
        //净利润
        double data2 = service2.getProfit(time, company_id);
        //经营现金净流量
        double data3 = service3.getNetcashflow(time, company_id);

        DecimalFormat df = new DecimalFormat("0.00");

        ArrayList<Double> temp = new ArrayList<>();
        temp.add(Double.valueOf(df.format(data3/data2)));
        temp.add(Double.valueOf(df.format(data3/data1[0]*100)));
        temp.add(Double.valueOf(df.format(data3/data1[2]*100)));
        temp.add(Double.valueOf(df.format((data1[3]+data1[4])/data1[1]*100)));

        Account account = accountRepository.findById(company_id);

        List<IndustryIndex> list = industryIndexRepository.findByCategoryAndFirstIndustryAndSecondIndustryAndScale("现金管理",account.getFirstIndustry(),account.getSecondIndustry(),account.getScale());

        getIndex(list,"盈余现金保障倍数",temp);
        getIndex(list,"资产现金冋收率（％）",temp);
        getIndex(list,"现金流动负债比率（％）",temp);
        getIndex(list,"两金占流动资产比重（％）",temp);
        double[] result = new double[temp.size()];
        for(int i=0;i<temp.size();i++){
            result[i] = temp.get(i);
        }
        return result;
    }

    /**
     * 根据指标名称得到指标值
     * @param list
     * @param index_name
     */
    private void getIndex(List<IndustryIndex> list, String index_name, ArrayList<Double> temp){
        for(int i=0;i<list.size();i++){
            IndustryIndex industryIndex = list.get(i);
            if(industryIndex.getIndexName().equals(index_name)){
                temp.add(industryIndex.getBadIndex());
                temp.add(industryIndex.getLowIndex());
                temp.add(industryIndex.getAverageIndex());
                temp.add(industryIndex.getFineIndex());
                temp.add(industryIndex.getExcellentIndex());
            }
            break;
        }
    }
}
