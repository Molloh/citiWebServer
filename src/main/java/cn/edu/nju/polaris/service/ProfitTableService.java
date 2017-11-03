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
	 * @param time      时间yyyy-mm
	 * @param company_id公司id
	 * @return          该期对应利润表信息
	 */
	public List<Pro_and_CashVo> ProfitTable_Info(String time,String company_id);
	
	/**
	 * 
	 * @param time       时间yyyy-mm
	 * @param company_id 公司id
	 * @return           营业收入
	 */
	public double getIncome(String time,String company_id);
	/**
	 * 
	 * @param company_id
	 * @param time yyyy-mm
	 * @param path
	 *
	public void CreateProfitTable(String company_id,String time,String path);*/
}
