package cn.edu.nju.polaris.sheet;

import cn.edu.nju.polaris.entity.BalanceSheet;
import cn.edu.nju.polaris.entity.SubjectsBalance;
import cn.edu.nju.polaris.repository.BalanceSheetRepository;
import cn.edu.nju.polaris.repository.SubjectsBalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 费慧通 on 2017/11/7.
 */
@Service
public class CalculateBalanceSheet {
    private final BalanceSheetRepository balanceSheetRepository;
    private final SubjectsBalanceRepository subjectsBalanceRepository;

    private List<SubjectsBalance> list;

    @Autowired
    public CalculateBalanceSheet(BalanceSheetRepository balanceSheetRepository, SubjectsBalanceRepository subjectsBalanceRepository) {
        this.balanceSheetRepository = balanceSheetRepository;
        this.subjectsBalanceRepository = subjectsBalanceRepository;
    }

    /**
     * 向数据库保存/更新资产负债表的数据
     * @param company_id
     * @param phase
     */
    public void UpdateBalanceSheet(long company_id,String phase){
        list = subjectsBalanceRepository.findByCompanyId(company_id);

        //1.1货币资金=其他货币资金+库存现金+银行存款
        double balance1_1 = getMoneyByCourseId("1012", true) + getMoneyByCourseId("1001", true) + getMoneyByCourseId("1002", true);
        BalanceSheet item1_1 = new BalanceSheet(company_id,phase,"货币资金",balance1_1);
        balanceSheetRepository.save(item1_1);
        //1.2短期投资
        double balance1_2 = getMoneyByCourseId("1101", true);
        BalanceSheet item1_2 = new BalanceSheet(company_id,phase,"短期投资",balance1_2);
        balanceSheetRepository.save(item1_2);
        //1.3应收票据
        double balance1_3 = getMoneyByCourseId("1121", true);
        BalanceSheet item1_3 = new BalanceSheet(company_id,phase,"应收票据",balance1_3);
        balanceSheetRepository.save(item1_3);
        //1.4应收账款=应收账款+预收账款（*余额在借方时）
        double balance1_4 = getMoneyByCourseId( "1122", true) + getMoneyByCourseId( "2203", true);
        BalanceSheet item1_4 = new BalanceSheet(company_id,phase,"应收账款",balance1_4);
        balanceSheetRepository.save(item1_4);
        //1.5预付账款=预付账款+应付账款（*余额在借方时）
        double balance1_5 = getMoneyByCourseId( "1123", true) + getMoneyByCourseId( "2202", true);
        BalanceSheet item1_5 = new BalanceSheet(company_id,phase,"预付账款",balance1_5);
        balanceSheetRepository.save(item1_5);
        //1.6应收股利
        double balance1_6 = getMoneyByCourseId( "1131", true);
        BalanceSheet item1_6 = new BalanceSheet(company_id,phase,"应收股利",balance1_6);
        balanceSheetRepository.save(item1_6);
        //1.7应收利息
        double balance1_7 = getMoneyByCourseId( "1132", true);
        BalanceSheet item1_7 = new BalanceSheet(company_id,phase,"应收利息",balance1_7);
        balanceSheetRepository.save(item1_7);
        //1.8其他应收款=其他应收款+其他应付款（*余额在借方时）
        double balance1_8 = getMoneyByCourseId( "1121", true) + getMoneyByCourseId( "2241", true);
        BalanceSheet item1_8 = new BalanceSheet(company_id,phase,"其他应收款",balance1_8);
        balanceSheetRepository.save(item1_8);
        //1.9存货=在途物资+材料采购+原材料+材料成本差异+库存商品-商品进销差价+委托加工物资+周转材料+消耗性生物资产+生产成本+制造费用+工程施工+机械作业
        double balance1_9 = getMoneyByCourseId( "1402", true) + getMoneyByCourseId( "1401", true) + getMoneyByCourseId( "1403", true)
                + getMoneyByCourseId( "1404", true) + getMoneyByCourseId( "1405", true) - getMoneyByCourseId( "1407", true)
                + getMoneyByCourseId( "1408", true) + getMoneyByCourseId( "1411", true) + getMoneyByCourseId( "1421", true)
                + getMoneyByCourseId( "4001", true) + getMoneyByCourseId( "4101", true) + getMoneyByCourseId( "4401", true)
                + getMoneyByCourseId( "4403", true);
        BalanceSheet item1_9 = new BalanceSheet(company_id,phase,"存货",balance1_9);
        balanceSheetRepository.save(item1_9);
        //1.9.1.1原材料
        double balance1_9_1 = getMoneyByCourseId( "1403", true);
        BalanceSheet item1_9_1 = new BalanceSheet(company_id,phase,"原材料",balance1_9_1);
        balanceSheetRepository.save(item1_9_1);
        //1.9.1.2在产品=生产成本+制造费用+工程施工+机械作业
        double balance1_9_2 = getMoneyByCourseId( "4001", true)+getMoneyByCourseId( "4101", true)
                +getMoneyByCourseId( "4401", true)+getMoneyByCourseId( "4403", true);
        BalanceSheet item1_9_2 = new BalanceSheet(company_id,phase,"在产品",balance1_9_2);
        balanceSheetRepository.save(item1_9_2);
        //1.9.1.3库存商品
        double balance1_9_3 = getMoneyByCourseId( "1405", true);
        BalanceSheet item1_9_3 = new BalanceSheet(company_id,phase,"库存产品",balance1_9_3);
        balanceSheetRepository.save(item1_9_3);
        //1.9.1.4周转材料
        double balance1_9_4 = getMoneyByCourseId( "1411", true);
        BalanceSheet item1_9_4 = new BalanceSheet(company_id,phase,"周转材料",balance1_9_4);
        balanceSheetRepository.save(item1_9_4);
        //1.10其他流动资产
        double balance1_10 = getMoneyByCourseId( "6000", true);
        BalanceSheet item1_10 = new BalanceSheet(company_id,phase,"其他流动资产",balance1_10);
        balanceSheetRepository.save(item1_10);
        //1.11流动资产合计=货币资金+短期投资+应收票据+应收账款+预付账款+应收股利+应收利息+其他应收款+存货+其他流动资产
        double balance1_11 = balance1_1 + balance1_2 + balance1_3 + balance1_4 + balance1_5 + balance1_6 + balance1_7 + balance1_8 + balance1_9 + balance1_10;
        BalanceSheet item1_11 = new BalanceSheet(company_id,phase,"流动资产合计",balance1_11);
        balanceSheetRepository.save(item1_11);
        //2.1长期债券投资
        double balance2_1 = getMoneyByCourseId( "1501", true);
        BalanceSheet item2_1 = new BalanceSheet(company_id,phase,"长期债券投资",balance2_1);
        balanceSheetRepository.save(item2_1);
        //2.2长期股权投资
        double balance2_2 = getMoneyByCourseId( "1511", true);
        BalanceSheet item2_2 = new BalanceSheet(company_id,phase,"长期股权投资",balance2_2);
        balanceSheetRepository.save(item2_2);
        //2.3固定资产原价=固定资产
        double balance2_3 = getMoneyByCourseId( "1601", true);
        BalanceSheet item2_3 = new BalanceSheet(company_id,phase,"固定资产原价",balance2_3);
        balanceSheetRepository.save(item2_3);
        //2.4减：累计折旧（数值=累计折旧）
        double balance2_4 = getMoneyByCourseId( "1602", true);
        BalanceSheet item2_4 = new BalanceSheet(company_id,phase,"减：累计折旧",balance2_4);
        balanceSheetRepository.save(item2_4);
        //2.5固定资产账面价值=固定资产原价-累计折旧
        double balance2_5 = balance2_3 - balance2_4;
        BalanceSheet item2_5 = new BalanceSheet(company_id,phase,"固定资产账面价值",balance2_5);
        balanceSheetRepository.save(item2_5);
        //2.6在建工程
        double balance2_6 = getMoneyByCourseId( "1604", true);
        BalanceSheet item2_6 = new BalanceSheet(company_id,phase,"在建工程",balance2_6);
        balanceSheetRepository.save(item2_6);
        //2.7工程物资
        double balance2_7 = getMoneyByCourseId( "1605", true);
        BalanceSheet item2_7 = new BalanceSheet(company_id,phase,"工程物资",balance2_7);
        balanceSheetRepository.save(item2_7);
        //2.8固定资产清理
        double balance2_8 = getMoneyByCourseId( "1606", true);
        BalanceSheet item2_8 = new BalanceSheet(company_id,phase,"固定资产清理",balance2_8);
        balanceSheetRepository.save(item2_8);
        //2.9生产性生物资产=生产性生物资产累计折旧+生产性生物资产
        double balance2_9 = getMoneyByCourseId( "1622", true) + getMoneyByCourseId( "1621", true);
        BalanceSheet item2_9 = new BalanceSheet(company_id,phase,"生产性生物资产",balance2_9);
        balanceSheetRepository.save(item2_9);
        //2.10无形资产=无形资产-累计摊销
        double balance2_10 = getMoneyByCourseId( "1701", true) - getMoneyByCourseId( "1702", true);
        BalanceSheet item2_10 = new BalanceSheet(company_id,phase,"无形资产",balance2_10);
        balanceSheetRepository.save(item2_10);
        //2.11开发支出=研发支出
        double balance2_11 = getMoneyByCourseId( "4301", true);
        BalanceSheet item2_11 = new BalanceSheet(company_id,phase,"开发支出",balance2_11);
        balanceSheetRepository.save(item2_11);
        //2.12长期待摊费用
        double balance2_12 = getMoneyByCourseId( "1801", true);
        BalanceSheet item2_12 = new BalanceSheet(company_id,phase,"长期待摊费用",balance2_12);
        balanceSheetRepository.save(item2_12);
        //2.13其他非流动资产
        double balance2_13 = getMoneyByCourseId("6001", true);
        BalanceSheet item2_13 = new BalanceSheet(company_id,phase,"其他非流动资产",balance2_13);
        balanceSheetRepository.save(item2_13);
        //2.14非流动资产合计=长期股权投资+长期债券投资+固定资产账面价值+工程物资+在建工程+固定资产清理+生产性生物资产+开发支出+无形资产+长期待摊费用+其他非流动资产
        double balance2_14 = balance2_1 + balance2_2 + balance2_3 + balance2_4 + balance2_5 + balance2_6 + balance2_7 + balance2_8 + balance2_9 + balance2_10 + balance2_11 + balance2_12 + balance2_13;
        BalanceSheet item2_14 = new BalanceSheet(company_id,phase,"非流动资产合计",balance2_14);
        balanceSheetRepository.save(item2_14);
        //3资产合计= 非流动资产合计+流动资产合计
        double balance3 = balance1_11 + balance2_14;
        BalanceSheet item3 = new BalanceSheet(company_id,phase,"资产合计",balance3);
        balanceSheetRepository.save(item3);
        //4.1短期借款
        double balance4_1 = getMoneyByCourseId( "2001", false);
        BalanceSheet item4_1 = new BalanceSheet(company_id,phase,"短期借款",balance4_1);
        balanceSheetRepository.save(item4_1);
        //4.2应付票据
        double balance4_2 = getMoneyByCourseId( "2201", false);
        BalanceSheet item4_2 = new BalanceSheet(company_id,phase,"应付票据",balance4_2);
        balanceSheetRepository.save(item4_2);
        //4.3应付账款=应付账款+预付账款（*余额在贷方时）
        double balance4_3 = getMoneyByCourseId( "2202", false) + getMoneyByCourseId( "1123", false);
        BalanceSheet item4_3 = new BalanceSheet(company_id,phase,"应付账款",balance4_3);
        balanceSheetRepository.save(item4_3);
        //4.4预收账款=预收账款+应收账款（*余额在贷方时）
        double balance4_4 = getMoneyByCourseId( "2203", false) + getMoneyByCourseId( "1122", false);
        BalanceSheet item4_4 = new BalanceSheet(company_id,phase,"预收账款",balance4_4);
        balanceSheetRepository.save(item4_4);
        //4.5应付职工薪酬
        double balance4_5 = getMoneyByCourseId( "2211", false);
        BalanceSheet item4_5 = new BalanceSheet(company_id,phase,"应付职工薪酬",balance4_5);
        balanceSheetRepository.save(item4_5);
        //4.6应交税费
        double balance4_6 = getMoneyLikeCourseId("2221",false);
        BalanceSheet item4_6 = new BalanceSheet(company_id,phase,"应付税费",balance4_6);
        balanceSheetRepository.save(item4_6);
        //4.7应付利息
        double balance4_7 = getMoneyByCourseId( "2231", false);
        BalanceSheet item4_7 = new BalanceSheet(company_id,phase,"应付利息",balance4_7);
        balanceSheetRepository.save(item4_7);
        //4.8应付利润
        double balance4_8 = getMoneyByCourseId( "2232", false);
        BalanceSheet item4_8 = new BalanceSheet(company_id,phase,"应付利润",balance4_8);
        balanceSheetRepository.save(item4_8);
        //4.9其他应付款=其他应付款+其他应收款（*余额在贷方时）
        double balance4_9 = getMoneyByCourseId( "2241", false) + getMoneyByCourseId( "1221", false);
        BalanceSheet item4_9 = new BalanceSheet(company_id,phase,"其他应付款",balance4_9);
        balanceSheetRepository.save(item4_9);
        //4.10其他流动负债
        double balance4_10 = getMoneyByCourseId("8000", false);
        BalanceSheet item4_10 = new BalanceSheet(company_id,phase,"其他流动负债",balance4_10);
        balanceSheetRepository.save(item4_10);
        //4.11流动负债合计=短期借款+应付票据+应付账款+预收账款+应付职工薪酬+应交税费+应付利息+其他应付款+应付利润+其他流动负债
        double balance4_11 = balance4_1+balance4_2+balance4_3+balance4_4+balance4_5+balance4_6+balance4_7+balance4_8+balance4_9+balance4_10;
        BalanceSheet item4_11 = new BalanceSheet(company_id,phase,"流动负债合计",balance4_11);
        balanceSheetRepository.save(item4_11);
        //5.1长期借款
        double balance5_1 = getMoneyByCourseId( "2501", false);
        BalanceSheet item5_1 = new BalanceSheet(company_id,phase,"长期借款",balance5_1);
        balanceSheetRepository.save(item5_1);
        //5.2长期应付款
        double balance5_2 = getMoneyByCourseId( "2701", false);
        BalanceSheet item5_2 = new BalanceSheet(company_id,phase,"长期应付款",balance5_2);
        balanceSheetRepository.save(item5_2);
        //5.3递延收益
        double balance5_3 = getMoneyByCourseId( "2401", false);
        BalanceSheet item5_3 = new BalanceSheet(company_id,phase,"递延收益",balance5_3);
        balanceSheetRepository.save(item5_3);
        //5.4其他非流动负债
        double balance5_4 = getMoneyByCourseId( "8001", false);
        BalanceSheet item5_4 = new BalanceSheet(company_id,phase,"其他非流动负债",balance5_4);
        balanceSheetRepository.save(item5_4);
        //5.5非流动负债合计
        double balance5_5 = balance5_1+balance5_2+balance5_3+balance5_4;
        BalanceSheet item5_5 = new BalanceSheet(company_id,phase,"非流动负债合计",balance5_5);
        balanceSheetRepository.save(item5_5);
        //6负债合计=流动负债合计+非流动负债合计
        double balance6 = balance4_11+balance5_5;
        BalanceSheet item6 = new BalanceSheet(company_id,phase,"负债合计",balance6);
        balanceSheetRepository.save(item6);
        //7.1实收资本（或股本）
        double balance7_1 = getMoneyByCourseId( "3001", false);
        BalanceSheet item7_1 = new BalanceSheet(company_id,phase,"实收资本",balance7_1);
        balanceSheetRepository.save(item7_1);
        //7.2资本公积
        double balance7_2 = getMoneyByCourseId( "3002", false);
        BalanceSheet item7_2 = new BalanceSheet(company_id,phase,"资本公积",balance7_2);
        balanceSheetRepository.save(item7_2);
        //7.3盈余公积
        double balance7_3 = getMoneyLikeCourseId( "3101", false);
        BalanceSheet item7_3 = new BalanceSheet(company_id,phase,"盈余公积",balance7_3);
        balanceSheetRepository.save(item7_3);
        //7.4未分配利润=利润分配+本年利润
        double balance7_4 = getMoneyByCourseId( "3103", false)+getMoneyLikeCourseId("3104",false);
        BalanceSheet item7_4 = new BalanceSheet(company_id,phase,"未分配利润",balance7_4);
        balanceSheetRepository.save(item7_4);
        //7.5所有者权益（或股东权益）合计=实收资本+资本公积+盈余公积+未分配利润
        double balance7_5 = balance7_1+balance7_2+balance7_3+balance7_4;
        BalanceSheet item7_5 = new BalanceSheet(company_id,phase,"所有者权益",balance7_5);
        balanceSheetRepository.save(item7_5);
        //8负债和所有者权益（或股东权益）合计=负债合计+所有者权益合计
        double balance8 = balance6+balance7_5;
        BalanceSheet item8 = new BalanceSheet(company_id,phase,"负债和所有者权益",balance8);
        balanceSheetRepository.save(item8);
    }

    /**
     * 根据科目编号已经是否借方得到科目金额
     * @param course_id 科目编号
     * @param IsDebit   是否借方
     * @return
     */
    public double getMoneyByCourseId(String course_id, boolean IsDebit){
        for(int i=0;i<list.size();i++){
            SubjectsBalance subjectsBalance = list.get(i);
            if(subjectsBalance.getSubjectsId().equals(course_id)){
                if(IsDebit){
                    return subjectsBalance.getDebitAmount();
                }else {
                    return subjectsBalance.getCreditAmount();
                }
            }
        }
        return 0.0;
    }

    /**
     * 根据科目编号前四位已经是否借方得到科目金额
     * @param course_id 科目编号
     * @param IsDebit   是否借方
     * @return
     */
    public double getMoneyLikeCourseId(String course_id, boolean IsDebit){
        double result = 0.0;
        for(int i=0;i<list.size();i++){
            SubjectsBalance subjectsBalance = list.get(i);
            if(subjectsBalance.getSubjectsId().substring(0,4).equals(course_id)){
                if(IsDebit){
                    result = result+subjectsBalance.getDebitAmount();
                }else {
                    result = result+subjectsBalance.getCreditAmount();
                }
            }
        }
        return result;
    }
}
