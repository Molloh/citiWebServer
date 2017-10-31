package cn.edu.nju.polaris.service;

import java.util.List;

import cn.edu.nju.polaris.vo.Pro_and_CashVo;

/**
 * 
 * @author hyf
 *
 */
public interface CashFlowService {
	
	/**
	 * 
	 * @param time       时间，yyyy-mm
	 * @param company_id 公司id
	 * @return 该期对应公司现金流量表信息
	 */
	public List<Pro_and_CashVo> CashFlowTable_Info(String time,String company_id);
	
	/**
	 * 
	 * @param company_id  公司id
	 * @param time        yyyy-mm
	 * @param path        
	 */
	public void CreateCashFlowTable(String company_id, String time, String path);
}
