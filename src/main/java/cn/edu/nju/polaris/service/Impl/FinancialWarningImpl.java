package cn.edu.nju.polaris.service.Impl;

import cn.edu.nju.polaris.entity.Account;
import cn.edu.nju.polaris.entity.IndustryIndex;
import cn.edu.nju.polaris.repository.*;
import cn.edu.nju.polaris.service.BalanceSheetService;
import cn.edu.nju.polaris.service.CashFlowService;
import cn.edu.nju.polaris.service.FinancialWarningService;
import cn.edu.nju.polaris.service.ProfitTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by 费慧通 on 2017/11/3.
 * <p>
 * 实现财务预警功能
 */
@Service
public class FinancialWarningImpl implements FinancialWarningService {
    private final BalanceSheetRepository balanceSheetRepository;
    private final IndustryIndexRepository industryIndexRepository;
    private final AccountRepository accountRepository;
    private final CashflowSheetRepository cashflowSheetRepository;
    private final ProfitSheetRepository profitSheetRepository;
    private final VoucherItemRepository voucherItemRepository;

    private List<IndustryIndex> list;

    @Autowired
    public FinancialWarningImpl(BalanceSheetRepository balanceSheetRepository, IndustryIndexRepository industryIndexRepository, AccountRepository accountRepository, CashflowSheetRepository cashflowSheetRepository, ProfitSheetRepository profitSheetRepository, VoucherItemRepository voucherItemRepository) {
        this.balanceSheetRepository = balanceSheetRepository;
        this.industryIndexRepository = industryIndexRepository;
        this.accountRepository = accountRepository;
        this.cashflowSheetRepository = cashflowSheetRepository;
        this.profitSheetRepository = profitSheetRepository;
        this.voucherItemRepository = voucherItemRepository;
    }

    @Override
    public double[] getWarningMessage(long company_id, String phase) {
        BalanceSheetService service = new BalanceSheetImpl(balanceSheetRepository);
        CashFlowService service1 = new CashFlowImpl(cashflowSheetRepository);
        ProfitTableService service2 = new ProfitTableImpl(profitSheetRepository, voucherItemRepository);
        //上一期期末的总资产、本期期末总资产、总负债、流动资产、流动负债、上一期期末应收帐款、本期期末应收帐款、上期期末存货、本期期末存货、本期所有者权益、上一期所有者权益
        double[] data1 = service.getValue(company_id, phase);
        //净利润、利润总额、主营业务成本、销售费用、管理费用、财务费用、营业成本、其他业务收入、本期主营业务收入、上一期主营业务收入
        double[] data2 = service2.getValues(company_id, phase);
        //经营现金净流量
        double data3 = service1.getNetcashflow(phase, company_id);

        Account account = accountRepository.findById(company_id);

        list = industryIndexRepository.findByCategoryAndFirstIndustryAndSecondIndustryAndScale("财务预警", account.getFirstIndustry(), account.getSecondIndustry(), account.getScale());
        //实际值
        double[] actual_value = new double[12];

        //净资产收益率  （净利润／所有者权益）
        actual_value[0] = (data2[0] / data1[10]) * 100;
        //总资产报酬率  利润总额*12／［（上一期期末的总资产＋本期期末总资产）／2］
        actual_value[1] = data2[1] / ((data1[0] + data1[1]) / 2) * 100;
        //盈余现金保障倍数  经营现金净流量／净利润
        if(data2[0]==0){
            actual_value[2] = 1;
        }else{
            actual_value[2] = data3 / data2[0];
        }
        //成本费用利润率  利润总额／（主营业务成本＋销售费用＋管理费用＋财务费用）
        if((data2[2] + data2[3] + data2[4] + data2[5])==0){
            actual_value[3] = 1;
        }else{
            actual_value[3] = data2[1] / (data2[2] + data2[3] + data2[4] + data2[5]) * 100;
        }
        //资产负债率  总负债／总资产
        actual_value[4] = data1[2] / data1[1] * 100;
        //速动比率  （流动资产－存货）／流动负债
        if(data1[4]==0){
            actual_value[5] = 1;
        }else{
            actual_value[5] = (data1[3] - data1[8]) / data1[4] * 100;
        }
        //现金流动负债比率  经营现金净流量*12／流动负债
        if(data1[4]==0){
            actual_value[6] = 1;
        }else{
            actual_value[6] = data3 / data1[4] * 100;
        }
        //总资产周转率  （主营业务收入＋其他业务收入）*12／［（上一期期末的总资产＋本期期末总资产）／2］
        actual_value[7] = (data2[8] + data2[7]) / ((data1[0] + data1[1]) / 2);
        //应收账款周转率  （主营业务收入＋其他业务收入）*12／［（上一期期末应收帐款＋本期期末应收帐款）／2］
        if(((data1[5] + data1[6]) / 2)==0){
            actual_value[8] = 1;
        }else{
            actual_value[8] = (data2[8] + data2[7])/ ((data1[5] + data1[6]) / 2);
        }
        //存货周转率  营业成本*12／［（上一期期末存货＋本期期末存货）／2］
        if(((data1[7] + data1[8]) / 2)==0){
            actual_value[9] = 1;
        }else{
            actual_value[9] = data2[6] / ((data1[7] + data1[8]) / 2);
        }
        //销售增长率  （本期主营业务收入－上一期主营业务收入）／上一期主营业务收入
        if(data2[9]==0){
            actual_value[10] = 1;
        }else{
            actual_value[10] = (data2[8] - data2[9]) / data2[9] * 100;
        }
        //资本积累率  （本期所有者权益－上一期所有者权益）／上一期所有者权益
        actual_value[11] = (data1[9] - data1[10]) / data1[10] * 100;

        for(int i=0;i<12;i++){
            actual_value[i] = getNumber(actual_value[i]);
        }

        double[] rate = new double[12];
        double[] average_value = new double[12];
        double[] unallowed_value = new double[12];

        //净资产收益率
        average_value[0] = getAverageIndex("净资产收益率（％）");
        if (actual_value[0] < average_value[0]) {
            unallowed_value[0] = getUnallowedIndex("净资产收益率（％）");
            rate[0] = 40 * (actual_value[0] - unallowed_value[0]) / (average_value[0] - unallowed_value[0]) + 60;
        } else {
            rate[0] = 100;
        }

        //总资产报酬率
        average_value[1] = getAverageIndex("总资产报酬率（％）");
        if (actual_value[1] < average_value[1]) {
            unallowed_value[1] = getUnallowedIndex("总资产报酬率（％）");
            rate[1] = 40 * (actual_value[1] - unallowed_value[1]) / (average_value[1] - unallowed_value[1]) + 60;
        } else {
            rate[1] = 100;
        }

        //盈余现金保障倍数
        average_value[2] = getAverageIndex("盈余现金保障倍数");
        if (actual_value[2] < average_value[2]) {
            unallowed_value[2] = getUnallowedIndex("盈余现金保障倍数");
            rate[2] = 40 * (actual_value[2] - unallowed_value[2]) / (average_value[2] - unallowed_value[2]) + 60;
        } else {
            rate[2] = 100;
        }

        //成本费用利润率
        average_value[3] = getAverageIndex("成本费用利润率（％）");
        if (actual_value[3] < average_value[3]) {
            unallowed_value[3] = getUnallowedIndex("成本费用利润率（％）");
            rate[3] = 40 * (actual_value[3] - unallowed_value[3]) / (average_value[3] - unallowed_value[3]) + 60;
        } else {
            rate[3] = 100;
        }

        //资产负债率
        average_value[4] = getAverageIndex("资产负债率（％）");
        double fine_value1 = getFineIndex("资产负债率（％）");
        double bad_value1 = getBadIndex("资产负债率（％）");
        if (actual_value[4] < average_value[4]) {
            rate[4] = 40 * (actual_value[4] - bad_value1) / (average_value[4] - bad_value1) + 60;
        } else if (actual_value[4] >= bad_value1 && actual_value[4] <= fine_value1) {
            rate[4] = 100;
        } else if (actual_value[4] > average_value[4]) {
            rate[4] = 40 * (actual_value[4] - fine_value1) / (average_value[4] - fine_value1) + 60;
        }

        //速动比率
        average_value[5] = getAverageIndex("速动比率（％）");
        double fine_value2 = getFineIndex("速动比率（％）");
        double bad_value2 = getBadIndex("速动比率（％）");
        if (actual_value[5] < average_value[5]) {
            rate[5] = 40 * (actual_value[5] - bad_value2) / (average_value[5] - bad_value2) + 60;
        } else {
            rate[5] = 40 * (actual_value[5] - fine_value2) / (average_value[5] - fine_value2) + 60;
        }

        //现金流动负债比率
        average_value[6] = getAverageIndex("现金流动负债比率（％）");
        double fine_value3 = getFineIndex("现金流动负债比率（％）");
        double bad_value3 = getBadIndex("现金流动负债比率（％）");
        if (actual_value[6] < average_value[6]) {
            rate[6] = 40 * (actual_value[6] - bad_value3) / (average_value[6] - bad_value3) + 60;
        } else if (actual_value[6] >= bad_value3 && actual_value[6] <= fine_value3) {
            rate[6] = 100;
        } else if (actual_value[4] > average_value[4]) {
            rate[6] = 40 * (actual_value[6] - fine_value3) / (average_value[6] - fine_value3) + 60;
        }

        //总资产周转率
        average_value[7] = getAverageIndex("总资产周转率（次）");
        if (actual_value[7] < average_value[7]) {
            unallowed_value[7] = getUnallowedIndex("总资产周转率（次）");
            rate[7] = 40 * (actual_value[7] - unallowed_value[7]) / (average_value[7] - unallowed_value[7]) + 60;
        } else {
            rate[7] = 100;
        }

        //应收帐款周转率
        average_value[8] = getAverageIndex("应收帐款周转率（次）");
        if (actual_value[8] < average_value[8]) {
            unallowed_value[8] = getUnallowedIndex("应收帐款周转率（次）");
            rate[8] = 40 * (actual_value[8] - unallowed_value[8]) / (average_value[8] - unallowed_value[8]) + 60;
        } else {
            rate[8] = 100;
        }

        //存货周转率
        average_value[9] = getAverageIndex("存货周转率（次）");
        if (actual_value[9] < average_value[9]) {
            unallowed_value[9] = getUnallowedIndex("存货周转率（次）");
            rate[9] = 40 * (actual_value[9] - unallowed_value[9]) / (average_value[9] - unallowed_value[9]) + 60;
        } else {
            rate[9] = 100;
        }

        //销售增长率
        average_value[10] = getAverageIndex("销售（营业）增长率（％）");
        if (actual_value[10] < average_value[10]) {
            unallowed_value[10] = getUnallowedIndex("销售（营业）增长率（％）");
            rate[10] = 40 * (actual_value[10] - unallowed_value[10]) / (average_value[10] - unallowed_value[10]) + 60;
        } else {
            rate[10] = 100;
        }

        //资本积累率
        average_value[11] = getAverageIndex("资本积累率（％）");
        if (actual_value[11] < average_value[11]) {
            unallowed_value[11] = getUnallowedIndex("资本积累率（％）");
            rate[11] = 40 * (actual_value[11] - unallowed_value[11]) / (average_value[11] - unallowed_value[11]) + 60;
        } else {
            rate[11] = 100;
        }

        double[] result = new double[5];

        //盈利能力
        result[0] = (rate[1] * 0.09 + rate[0] * 0.16 + rate[2] * 0.07 + rate[3] * 0.06) / 0.38;
        //偿债能力
        result[1] = (rate[4] * 0.12 + rate[6] * 0.12 + rate[5] * 0.1) / 0.34;
        //营运能力
        result[2] = (rate[7] * 0.05 + rate[9] * 0.05 + rate[8] * 0.08) / 0.18;
        //成长能力
        result[3] = (rate[10] * 0.05 + rate[11] * 0.05) / 0.1;
        //总评分
        result[4] = rate[0] * 0.16 + rate[1] * 0.09 + rate[2] * 0.07 + rate[3] * 0.06 + rate[4] * 0.12 + rate[5] * 0.1 + rate[6] * 0.12 + rate[7] * 0.05 + rate[8] * 0.08 + rate[9] * 0.05 + rate[10] * 0.05 + rate[11] * 0.05;

        return result;
    }

    /**
     * 得到指标的满意值（平均值）
     *
     * @param index_name
     * @return
     */
    private double getAverageIndex(String index_name) {
        for (int i = 0; i < list.size(); i++) {
            IndustryIndex po = list.get(i);
            if (po.getIndexName().equals(index_name)) {
                return po.getAverageIndex();
            }
        }
        return 0;
    }

    /**
     * 得到行业的不允许值(极低值)
     *
     * @param index_name
     * @return
     */
    private double getUnallowedIndex(String index_name) {
        for (int i = 0; i < list.size(); i++) {
            IndustryIndex po = list.get(i);
            if (po.getIndexName().equals(index_name)) {
                return po.getLowIndex();
            }
        }
        return 0;
    }

    /**
     * 得到行业的上限不允许值(优秀值)
     *
     * @param index_name
     * @return
     */
    private double getFineIndex(String index_name) {
        for (int i = 0; i < list.size(); i++) {
            IndustryIndex po = list.get(i);
            if (po.getIndexName().equals(index_name)) {
                return po.getFineIndex();
            }
        }
        return 0;
    }

    /**
     * 得到行业的下限不允许值(极差值)
     *
     * @param index_name
     * @return
     */
    private double getBadIndex(String index_name) {
        for (int i = 0; i < list.size(); i++) {
            IndustryIndex po = list.get(i);
            if (po.getIndexName().equals(index_name)) {
                return po.getBadIndex();
            }
        }
        return 0;
    }

    /**
     * 取两位小数
     * @param num
     * @return
     */
    private double getNumber(double num){
        if(Double.isNaN(num)||Double.isInfinite(num)){
            return num;
        }else{
            DecimalFormat df = new DecimalFormat("0.00");
            return Double.valueOf(df.format(num));
        }
    }
}
