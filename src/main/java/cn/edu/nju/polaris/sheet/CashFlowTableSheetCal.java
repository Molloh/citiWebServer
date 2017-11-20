package cn.edu.nju.polaris.sheet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.nju.polaris.entity.BalanceSheet;
import cn.edu.nju.polaris.entity.CashflowSheet;
import cn.edu.nju.polaris.entity.VoucherItem;
import cn.edu.nju.polaris.repository.BalanceSheetRepository;
import cn.edu.nju.polaris.repository.CashflowSheetRepository;
import cn.edu.nju.polaris.repository.VoucherItemRepository;
import cn.edu.nju.polaris.service.Impl.TableHelper;
import org.springframework.stereotype.Component;

@Component
public class CashFlowTableSheetCal {
    private VoucherItemRepository vir;
    private TableHelper helper;
    private CashflowSheetRepository cfr;
    private BalanceSheetRepository bsr;

    @Autowired
    public CashFlowTableSheetCal(VoucherItemRepository vir, CashflowSheetRepository cfr, BalanceSheetRepository balanceSheetRepository) {
        this.vir = vir;
        this.cfr = cfr;
        this.helper = new TableHelper();
        this.bsr = balanceSheetRepository;
    }

    public void UpdateCashFlowTable(String time, long company_id) {
        List<VoucherItem> list = vir.getListThroughPeriod(time, company_id);
        double s1 = 0, s2 = 0, temp3 = 0, temp4 = 0, temp5 = 0, temp6 = 0;
        Map<String, double[]> map = new HashMap<String, double[]>();
        map = helper.tempCal(list);


        Map<String, double[]> tool = BalanceGets(time, company_id);

        double[] operating_activities = new double[7];
        s1 = helper.Cal("5001", map);//主营业务收入
        s2 = helper.Cal("5051", map);//其他业务收入
        temp3 = tool.get("应收票据")[0] - tool.get("应收票据")[1];//应收票据期初余额-应收票据期末余额
        temp4 = tool.get("应收账款")[0] - tool.get("应收账款")[1];//应收账款期初余额-应收账款期末余额
        temp5 = tool.get("预收账款")[0] - tool.get("预收账款")[1];//预收账款期初余额-预收账款期末余额
        operating_activities[0] = s1 * (1 + 0.17) + s2 + temp3 + temp4 + temp5;//1.1“销售产成品、商品、提供劳务收到的现金”

        operating_activities[1]=helper.CreditCal("5301",map)+
                helper.CreditCal("5051",map)+helper.CreditCal("2241",map)
                +helper.CreditCal("1221",map);
        //s1 = helper.DebitCal("1403", map) +
                //+helper.DebitCal("1405", map);//原材料、低值易耗品、包装物、库存商品的借方发生额

        s1=tool.get("存货")[1] - tool.get("存货")[0]+helper.DebitCal("222100101",map);
        s2 = tool.get("应付账款")[0] - tool.get("应付账款")[1];//购买原材料、商品、接受劳务的应付账款(期初 一期末)
        temp3 = tool.get("应付票据")[0] - tool.get("应付票据")[1];//购买原材料、商品、接受劳务的应付票据(期初一期末)
        temp4 = tool.get("预付账款")[1] - tool.get("预付账款")[0];//购买原材料、商品、接受劳务的预付账款 (期末一期初)
        operating_activities[2] = s1 + s2 + temp3 + temp4;//1.3“购买原材料、商品、接受劳务支付的现金”的本月金额

        s1 = helper.DebitCal("2211", map);//“应付职工薪酬”科目本期借方发生额累计数
        operating_activities[3] = s1;//“支付的职工薪酬”的本月金额

        s1 = helper.DebitCal(helper.specificSubject("2221", map));//“应交税费”各明细账户本期借方发生额累计数
        s2 = helper.Cal2("222100101", map);//“应交税费-应交增值税-进项税额”
        operating_activities[4] = s1 - s2;//1.5“支付的税费”

        s1 = helper.Cal2(helper.specificSubject("5711", map)) - 2 * helper.Cal2("5711001", map);//营业外支出（-其中的非流动资产处置净损失）
        s2 = helper.Cal2(helper.specificSubject("5602", map)) - 2 * helper.Cal2("5602001", map) -
                2 * helper.Cal2("5602007", map);//管理费用(-其中的应付职工薪酬、折旧费)
        temp3 = helper.Cal2(helper.specificSubject("5601", map)) - 2 * helper.Cal2("5601001", map);//销售费用 (-其中的应付职工薪酬)
        temp4 = helper.DebitCal("1221", map);//其他应收款本期借方发生额
        temp5 = helper.DebitCal("2241", map);//其他应付款本期借方发生额
        temp6 = helper.Cal2("5603002", map);//财务费用——手续费
        operating_activities[5] = s1 + s2 + temp3 + temp4 + temp5 + temp6;//1.6“支付其他与经营活动有关的现金”
        operating_activities[6]=operating_activities[0]+operating_activities[1]-operating_activities[2]-
                operating_activities[3]-operating_activities[4]-operating_activities[5];

        double[] Investment_activities = new double[6];//二、投资活动产生的现金流量
        s1 = tool.get("短期投资")[0] - tool.get("短期投资")[1];//（短期投资期初数－短期投资期末数）（当期初数＞期末数时）
        if (s1 < 0) s1 = 0;
        s2 = tool.get("长期股权投资")[0] - tool.get("长期股权投资")[1];//长期股权投资期初数－长期股权投资期末数）（当期初数＞期末数时）
        if (s2 < 0) s2 = 0;
        temp3 = tool.get("长期债券投资")[0] - tool.get("长期债券投资")[1];//长期债券投资期初数－长期债券投资期末数）（当期初数＞期末数时）
        if (temp3 < 0) temp3 = 0;
        Investment_activities[0] = s1 + s2 + temp3;//2.1“收回短期投资、长期债券投资和长期股权投资收到的现金”

        s1 = helper.Cal("5111", map);//投资收益
        s2 = tool.get("应收利息")[1] - tool.get("应收利息")[0];//（应收利息期末数－应收利息期初数）
        temp3 = tool.get("应收股利")[1] - tool.get("应收股利")[0];//（应收股利期末数－应收股利期初数）
        Investment_activities[1] = s1 - s2 - temp3;//2.2“取得投资收益收到的现金”

        s1 = helper.CreditCal("1606", map);//“固定资产清理”的贷方余额
        s2 = tool.get("无形资产")[1] - tool.get("无形资产")[0];//（无形资产期末数－无形资产期初数）
        Investment_activities[2] = s1 + s2;//2.3“处置固定资产、无形资产和其他非流动资产收回的现金净额”

        s1 = tool.get("短期投资")[1] - tool.get("短期投资")[0];//（短期投资期末数－短期投资期初数）（当期初数＜期末数时）
        if (s1 < 0) s1 = 0;
        s2 = tool.get("长期股权投资")[1] - tool.get("长期股权投资")[0];//（长期股权投资期末数－长期股权投资期初数）（当期初数＜期末数时）
        temp4 = getGivenVourchers(time, "1511", "5111", company_id);//长期股权投资形成的投资收益
        if (s2 < 0) s2 = 0;
        else s2 -= temp4;
        temp3 = tool.get("长期债券投资")[1] - tool.get("长期债券投资")[0];//〔长期债券投资期末数－长期债权投资期初数）（当期初数＜期末数时）
        temp5 = getGivenVourchers(time, "1501", "5111", company_id);//长期债券投资形成的投资收益
        if (temp3 < 0) temp3 = 0;
        else temp3 -= temp5;
        Investment_activities[3] = s1 + s2 + temp3;//2.4“短期投资、长期债券投资和长期股权投资支付的现金”

        s1 = tool.get("在建工程")[1] - tool.get("在建工程")[0];//（在建工程期末数－在建工程期初数）（当期初数＜期末数时）
        if (s1 < 0) s1 = 0;
        s2 = helper.Cal2("1601",map);//（固定资产期末数－固定资产期初数）（当期初数＜期末数时）
        if (s2 < 0) s2 = 0;
        temp3 = tool.get("无形资产")[1] - tool.get("无形资产")[0];//（无形资产期末数－无形资产期初数）（当期初数＜期末数时）
        if (temp3 < 0) temp3 = 0;
        Investment_activities[4] = s1 + s2 + temp3;//“购建固定资产、无形资产和其他非流动资产支付的现金”

        Investment_activities[5] = Investment_activities[0] + Investment_activities[1] + Investment_activities[2] -
                Investment_activities[3] - Investment_activities[4];//“投资活动产生的现金流量净额”

        double[] Financing_activities = new double[6];//三、筹资活动产生的现金流量

        s1 = helper.Cal("2001", map);//短期借款
        s2 = helper.Cal("2501", map);//长期借款
        Financing_activities[0] = s1 + s2;//“取得借款收到的现金”

        Financing_activities[1] = helper.Cal("3001", map);//“吸收投资者投资收到的现金”

        s1 =//“利润分配-应付利润”本期借方发生额中以现金支付的部分
                Financing_activities[3] = getGivenVourchers(time, "2231", "1001", company_id)
                        + getGivenVourchers(time, "2231", "1002", company_id);//3.4偿还借款利息支付的现金

        s1 = tool.get("短期借款")[0] - tool.get("短期借款")[1];//（短期借款期初数－短期借款期末数）
        s2 = tool.get("长期借款")[0] - tool.get("长期借款")[1];//（长期借款期初数－长期借款期末数）
        temp3 = Financing_activities[3];
        Financing_activities[2] = s1 + s2 - temp3;//3.3“偿还借款本金支付的现金”

        Financing_activities[4] = getGivenVourchers(time, "3104005", "1001", company_id)
                + getGivenVourchers(time, "3104005", "1002", company_id);//3.5“分配利润支付的现金”

        Financing_activities[5] = Financing_activities[0] + Financing_activities[1] - Financing_activities[2] -
                Financing_activities[3] - Financing_activities[4];//筹资活动产生的现金流量净额

        double[] Net_cash_increase = new double[2];
        Net_cash_increase[0] = operating_activities[6]+Investment_activities[5]+Financing_activities[5];//“四、现金净增加额”
        CashflowSheet c=cfr.findByPeriodAndCompanyIdAndName(lastTime(time),company_id,"五、期末现金余额");
        Net_cash_increase[1] =c!=null?c.getBalance():0 ;

        double Final_cash_balance = Net_cash_increase[0] + Net_cash_increase[1];//“五、期末现金余额”

        save(company_id, time, "销售产成品、商品、提供劳务收到的现金", operating_activities[0]);
        save(company_id, time, "收到其他与经营活动有关的现金", operating_activities[1]);
        save(company_id, time, "购买原材料、商品、接受劳务支付的现金", operating_activities[2]);
        save(company_id, time, "支付的职工薪酬", operating_activities[3]);
        save(company_id, time, "支付的税费", operating_activities[4]);
        save(company_id, time, "支付其他与经营活动有关的现金", operating_activities[5]);
        save(company_id, time, "经营活动产生的现金流量净额", operating_activities[6]);
        save(company_id, time, "收回短期投资、长期债券投资和长期股权投资收到的现金", Investment_activities[0]);
        save(company_id, time, "取得投资收益收到的现金", Investment_activities[1]);
        save(company_id, time, "处置固定资产、无形资产和其他非流动资产收回的现金净额", Investment_activities[2]);
        save(company_id, time, "短期投资、长期债券投资和长期股权投资支付的现金", Investment_activities[3]);
        save(company_id, time, "购建固定资产、无形资产和其他非流动资产支付的现金", Investment_activities[4]);
        save(company_id, time, "投资活动产生的现金流量净额", Investment_activities[5]);
        save(company_id, time, "取得借款收到的现金", Financing_activities[0]);
        save(company_id, time, "吸收投资者投资收到的现金", Financing_activities[1]);
        save(company_id, time, "偿还借款本金支付的现金", Financing_activities[2]);
        save(company_id, time, "偿还借款利息支付的现金", Financing_activities[3]);
        save(company_id, time, "分配利润支付的现金", Financing_activities[4]);
        save(company_id, time, "筹资活动产生的现金流量净额", Financing_activities[5]);
        save(company_id, time, "四、现金净增加额", Net_cash_increase[0]);
        save(company_id, time, "加：期初现金余额", Net_cash_increase[1]);
        save(company_id, time, "五、期末现金余额", Final_cash_balance);
    }

    private void save(Long companyId, String time, String name, Double value) {
        CashflowSheet item = cfr.findByPeriodAndCompanyIdAndName(time, companyId, name);
        if (item != null) {
            item.setBalance(value);
            cfr.save(item);
        } else {
            item = new CashflowSheet(companyId, time, name, value);
            cfr.save(item);
        }
    }

    private double getGivenVourchers(String time, String sub1, String sub2, long company_id) {
        List<VoucherItem> list1 = vir.getDebitVoucherItemByPeriod(time, sub1, company_id);
        List<VoucherItem> list2 = vir.getCreditVoucherItemByPeriod(time, sub2, company_id);

        double res = 0;

        Map<String, Double> map1 = new HashMap<>();
        for (VoucherItem v : list1) {
            String VoucherId = v.getVoucherId();
            if (!map1.containsKey(v.getVoucherId())) {
                map1.put(VoucherId, v.getDebitAmount());
            } else {
                map1.put(VoucherId, map1.get(VoucherId) + v.getDebitAmount());
            }
        }

        Map<String, Double> map2 = new HashMap<>();
        for (VoucherItem v : list2) {
            String VoucherId = v.getVoucherId();
            if (!map2.containsKey(v.getVoucherId())) {
                map2.put(VoucherId, v.getDebitAmount());
            } else {
                map2.put(VoucherId, map2.get(VoucherId) + v.getDebitAmount());
            }
        }

        for (String id : map1.keySet()) {
            if (map2.containsKey(id)) {
                res += Math.min(map1.get(id), map2.get(id));
            }
        }

        return res;
    }

    private String lastTime(String time) {
        System.out.print(time);
        String temp[] = time.split("-");
        int year = Integer.parseInt(temp[0]);
        int month = Integer.parseInt(temp[1]);
        if (month != 1)
            month--;
        else {
            year--;
            month = 12;
        }
        if (month < 10)
            return String.valueOf(year) + "-0" + String.valueOf(month);
        else
            return String.valueOf(year) + "-" + String.valueOf(month);

    }

    /**
     * @param time
     * @return 期末，期初
     */
    private Map<String, double[]> BalanceGets(String time, long company_id) {
        Map<String, double[]> res = new HashMap<String, double[]>();

        List<BalanceSheet> l1 = bsr.findByCompanyIdAndPeriod(company_id, time);
        List<BalanceSheet> l2 = bsr.findByCompanyIdAndPeriod(company_id, lastTime(time));

        Map<String, Double> map1 = new HashMap<>();
        Map<String, Double> map2 = new HashMap<>();
        for (BalanceSheet b : l1)
            map1.put(b.getName(), b.getBalance());
        if (l2 == null || l2.size() == 0){
            for (BalanceSheet b : l1)
                map2.put(b.getName(), 0.0);
        }else {
            for (BalanceSheet b : l2)
                map2.put(b.getName(), b.getBalance());
        }


        res.put("货币资金", BalanceDeal("货币资金", map1, map2));
        res.put("短期投资", BalanceDeal("短期投资", map1, map2));
        res.put("应收票据", BalanceDeal("应收票据", map1, map2));
        res.put("应收账款", BalanceDeal("应收账款", map1, map2));
        res.put("预付账款", BalanceDeal("预付账款", map1, map2));
        res.put("应收股利", BalanceDeal("应收股利", map1, map2));
        res.put("应收利息", BalanceDeal("应收利息", map1, map2));
        res.put("短期借款", BalanceDeal("短期借款", map1, map2));
        res.put("应付票据", BalanceDeal("应付票据", map1, map2));
        res.put("应付账款", BalanceDeal("应付账款", map1, map2));
        res.put("预收账款", BalanceDeal("预收账款", map1, map2));
        res.put("长期债券投资", BalanceDeal("长期债券投资", map1, map2));
        res.put("长期股权投资", BalanceDeal("长期股权投资", map1, map2));
        res.put("无形资产", BalanceDeal("无形资产", map1, map2));
        res.put("在建工程", BalanceDeal("在建工程", map1, map2));
        res.put("长期借款", BalanceDeal("长期借款", map1, map2));
        res.put("实收资本", BalanceDeal("实收资本", map1, map2));
        res.put("存货",BalanceDeal("存货",map1,map2));
        return res;
    }

    private double[] BalanceDeal(String subjectname, Map<String, Double> map1, Map<String, Double> map2) {
        double res[] = new double[2];
        if (map1.containsKey(subjectname))
            res[1] = map1.get(subjectname);
        if (map2.containsKey(subjectname))
            res[0] = map2.get(subjectname);
        return res;
    }
}
