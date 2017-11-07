package cn.edu.nju.polaris.service;

import java.util.List;

import cn.edu.nju.polaris.vo.Pro_and_CashVo;

/**
 * 
 * @author hyf
 *
 */
public interface ProfitTableService {
	
	/**
	 * 
	 * @param time       时间yyyy-mm
	 * @param company_id 公司id
	 * @return           该期对应利润表信息
	 */
	public List<Pro_and_CashVo> ProfitTable_Info(String time,long company_id);
	
	/**
	 * 
	 * @param time       时间yyyy-mm
	 * @param company_id 公司id
	 * @return           营业收入
	 */
	public double getIncome(String time,long company_id);
	
	
	/**
	 * 
	 * @param time   时间yyyy-mm
	 * @param company_id
	 * @return 净利润
	 */
	public double getProfit(String time,long company_id);
	
	/**
	 * 得到净利润、利润总额、主营业务成本、销售费用、管理费用、财务费用、营业成本、其他业务收入、本期主营业务收入、上一期主营业务收入
	 * @param company_id 公司id
	 * @param time 时间 yyyy-mm
	 * @return
	 */
	public double[] getValues(long company_id, String time);
	
	/**
	 * 
	 * @param company_id
	 * @param time yyyy-mm
	 * @param path
	 *
	public void CreateProfitTable(String company_id,String time,String path);*/
}
