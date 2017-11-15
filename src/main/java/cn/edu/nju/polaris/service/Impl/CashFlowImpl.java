package cn.edu.nju.polaris.service.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.nju.polaris.entity.CashflowSheet;
import cn.edu.nju.polaris.repository.CashflowSheetRepository;
import cn.edu.nju.polaris.service.CashFlowService;
import cn.edu.nju.polaris.vo.Pro_and_CashVo;

/**
 * 
 * @author hyf
 *
 */
@Service
public class CashFlowImpl implements CashFlowService{
	private TableHelper helper;
	private CashflowSheetRepository cfsr;
	
	@Autowired
    public CashFlowImpl(CashflowSheetRepository cfsr) {
        this.cfsr = cfsr;
        this.helper=new TableHelper();
    }

	public List<Pro_and_CashVo> CashFlowTable_Info(String time, long company_id) {
		List<CashflowSheet> list=cfsr.findByPeriodAndCompanyId(time, company_id);
		
		List<Pro_and_CashVo> res=new ArrayList<Pro_and_CashVo>();
		if(list.size()==0){
			
		}else{
			List<String> times=helper.getAllPeriodThisYear(time);
			Map<String,Double>map=new HashMap<String,Double>();
			
			List<CashflowSheet> temp=null;
			for(int i=0;i<times.size()-1;i++){
				temp=cfsr.findByPeriodAndCompanyId(times.get(i), company_id);
				if(temp.size()!=0){
					for(CashflowSheet p:temp){
						if(i==0){
							map.put(p.getName(), p.getBalance());
						}else{
							map.put(p.getName(), map.get(p.getName())+p.getBalance());
						}
					}
				}
				
			}
			if(times.size()==1||map.size()==0){
				for(int i=0;i<list.size();i++){
					CashflowSheet p=list.get(i);
					String project=p.getName();	
					double period=p.getBalance();
					double year=period;
					res.add(new Pro_and_CashVo(project,i+1,year,period));
				}
			}else{
				for(int i=0;i<list.size();i++){
					CashflowSheet p=list.get(i);
					String project=p.getName();	
					double period=p.getBalance();
					double year=map.get(project)+period;
					res.add(new Pro_and_CashVo(project,i+1,year,period));
				}
			}
		}
		return res;
	}

	@Override
	public double getNetcashflow(String time, long company_id) {
		CashflowSheet cf=cfsr.findByPeriodAndCompanyIdAndName(time, company_id, "经营活动产生的现金流量净额");
		if(cf==null){
			return 0;
		}else{
			return cf.getBalance();
		}
	}

	@Override
	public double[] getCashFlow(String time, long company_id) {
		double[]res=new double[3];
		double r1=cfsr.findByPeriodAndCompanyIdAndName(time, company_id, "销售产成品、商品、提供劳务收到的现金").getBalance()+
				cfsr.findByPeriodAndCompanyIdAndName(time, company_id, "收到其他与经营活动有关的现金").getBalance()+
				cfsr.findByPeriodAndCompanyIdAndName(time, company_id, "收回短期投资、长期债券投资和长期股权投资收到的现金").getBalance()+
				cfsr.findByPeriodAndCompanyIdAndName(time, company_id, "取得投资收益收到的现金").getBalance()+
				cfsr.findByPeriodAndCompanyIdAndName(time, company_id, "处置固定资产、无形资产和其他非流动资产收回的现金净额").getBalance()+
				cfsr.findByPeriodAndCompanyIdAndName(time, company_id, "取得借款收到的现金").getBalance()+
				cfsr.findByPeriodAndCompanyIdAndName(time, company_id, "吸收投资者投资收到的现金").getBalance();
		double r2=cfsr.findByPeriodAndCompanyIdAndName(time, company_id, "购买原材料、商品、接受劳务支付的现金").getBalance()+
				cfsr.findByPeriodAndCompanyIdAndName(time, company_id, "支付的税费").getBalance()+
				cfsr.findByPeriodAndCompanyIdAndName(time, company_id, "支付的职工薪酬").getBalance()+
				cfsr.findByPeriodAndCompanyIdAndName(time, company_id, "支付其他与经营活动有关的现金").getBalance()+
				cfsr.findByPeriodAndCompanyIdAndName(time, company_id, "短期投资、长期债券投资和长期股权投资支付的现金").getBalance()+
				cfsr.findByPeriodAndCompanyIdAndName(time, company_id, "购建固定资产、无形资产和其他非流动资产支付的现金").getBalance()+
				cfsr.findByPeriodAndCompanyIdAndName(time, company_id, "偿还借款本金支付的现金").getBalance()+
				cfsr.findByPeriodAndCompanyIdAndName(time, company_id, "偿还借款利息支付的现金").getBalance()+
				cfsr.findByPeriodAndCompanyIdAndName(time, company_id, "分配利润支付的现金").getBalance();
		double r3=0;//待补完******************************************************************************************
		
		res[0]=r1;
		res[1]=r2;
		res[2]=r3;
		
		return res;
	}
}
