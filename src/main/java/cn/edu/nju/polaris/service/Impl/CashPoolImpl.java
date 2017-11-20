package cn.edu.nju.polaris.service.Impl;

import cn.edu.nju.polaris.repository.*;
import cn.edu.nju.polaris.service.BalanceSheetService;
import cn.edu.nju.polaris.service.CashFlowService;
import cn.edu.nju.polaris.service.CashPoolService;
import cn.edu.nju.polaris.service.ProfitTableService;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

/**
 * Created by 费慧通 on 2017/11/13.
 */
@Service
public class CashPoolImpl implements CashPoolService {
    private final BalanceSheetRepository balanceSheetRepository;
    private final ProfitSheetRepository profitSheetRepository;
    private final CashflowSheetRepository cashflowSheetRepository;
    private final VoucherItemRepository voucherItemRepository;

    public CashPoolImpl(BalanceSheetRepository balanceSheetRepository, ProfitSheetRepository profitSheetRepository, CashflowSheetRepository cashflowSheetRepository, VoucherItemRepository voucherItemRepository) {
        this.balanceSheetRepository = balanceSheetRepository;
        this.profitSheetRepository = profitSheetRepository;
        this.cashflowSheetRepository = cashflowSheetRepository;
        this.voucherItemRepository = voucherItemRepository;
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

        double[] result = new double[4];
        result[0] = Double.valueOf(df.format(data3/data2));
        result[1] = Double.valueOf(df.format(data3/data1[0]));
        result[2] = Double.valueOf(df.format(data3/data1[2]));
        result[3] = Double.valueOf(df.format((data1[3]+data1[4])/data1[1]));
        return result;
    }
}
