package cn.edu.nju.polaris.service.Impl;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author hyf
 *
 */
public class TableHelper {
	
	static final String Profit[]={"一、营业收入","减：营业成本","营业税金及附加","其中：消费税",
			"营业税","城市维护建设税","资源税","土地增值税","城镇土地使用税、房产税、车船税、印花税",
			"教育费附加、矿产资源补偿费、排污费","销售费用","其中：商品维修费","广告费和业务宣传费",
			"管理费用","其中：开办费","业务招待费","研究费用","财务费用","其中：利息费用（收入以“-”号填列）",
			"加：投资收益（亏损以“-”号填列）","二、营业利润（亏损以“-”号填列）","加：营业外收入",
			"其中：政府补助","减：营业外支出","其中：坏账损失","无法收回的长期债券投资损失",
			"无法收回的长期股权投资损失","自然灾害等不可抗力因素造成的损失","税收滞纳金",
			"三、利润总额（亏损总额以“-”号填列）","减：所得税费用","四、净利润（净亏损以“-”号填列）"};

	static final String CashFlow[]={"销售产成品、商品、提供劳务收到的现金","收到其他与经营活动有关的现金",
			"购买原材料、商品、接受劳务支付的现金","支付的职工薪酬","支付的税费","支付其他与经营活动有关的现金",
			"经营活动产生的现金流量净额","收回短期投资、长期债券投资和长期股权投资收到的现金","取得投资收益收到的现金",
			"处置固定资产、无形资产和其他非流动资产收回的现金净额","短期投资、长期债券投资和长期股权投资支付的现金",
			"购建固定资产、无形资产和其他非流动资产支付的现金","投资活动产生的现金流量净额","取得借款收到的现金",
			"吸收投资者投资收到的现金","偿还借款本金支付的现金","偿还借款利息支付的现金","分配利润支付的现金",
			"筹资活动产生的现金流量净额","四、现金净增加额","加：期初现金余额","五、期末现金余额"};

	/**
	 * 
	 * @param time
	 *            时间yyyy-mm
	 * @return 返回包括该月的，之前的当年的所有的期
	 */
	public List<String> getAllPeriodThisYear(String time) {
		List<String> res = new ArrayList<String>();

		String[] temp = time.split("-");
		String year = temp[0];
		String month = temp[1];

		int mon = Integer.parseInt(month);
		for (int i = 1; i <= mon; i++) {
			if (i < 10) {
				res.add(year + "-0" + String.valueOf(i));
			} else {
				res.add(year + "-" + String.valueOf(i));
			}
		}
		return res;
	}
}
