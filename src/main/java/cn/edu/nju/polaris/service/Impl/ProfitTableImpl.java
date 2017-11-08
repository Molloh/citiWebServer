package cn.edu.nju.polaris.service.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import cn.edu.nju.polaris.entity.ProfitSheet;
import cn.edu.nju.polaris.repository.ProfitSheetRepository;
import cn.edu.nju.polaris.service.ProfitTableService;
import cn.edu.nju.polaris.vo.Pro_and_CashVo;

/**
 * 
 * @author hyf
 *
 */
public class ProfitTableImpl implements ProfitTableService{
	
	private ProfitSheetRepository psr;
	private TableHelper helper;
	
	@Autowired
    public ProfitTableImpl(ProfitSheetRepository psr) {
        this.psr = psr;
        this.helper=new TableHelper();
    }
	
	
	public List<Pro_and_CashVo> ProfitTable_Info(String time, long company_id) {
		List<ProfitSheet> list=psr.findByCompanyIdAndPeriod(company_id, time);
		
		List<Pro_and_CashVo> res=new ArrayList<Pro_and_CashVo>();
		if(list.size()==0){
			
		}else{
			List<String> times=helper.getAllPeriodThisYear(time);
			Map<String,Double>map=new HashMap<String,Double>();
			
			List<ProfitSheet> temp=null;
			for(int i=0;i<times.size()-1;i++){
				temp=psr.findByCompanyIdAndPeriod(company_id, times.get(i));
				for(ProfitSheet p:temp){
					if(i==0){
						map.put(p.getName(), p.getBalance());
					}else{
						map.put(p.getName(), map.get(p.getName())+p.getBalance());
					}
				}
			}
			
			for(int i=0;i<list.size();i++){
				ProfitSheet p=list.get(i);
				String project=p.getName();	
				double period=p.getBalance();
				double year=map.get(project)+period;
				res.add(new Pro_and_CashVo(project,i+1,year,period));
			}
			
		}
		return res;
	}
	
	

	@Override
	public double getIncome(String time, long company_id) {
		ProfitSheet p=psr.findByCompanyIdAndPeriodAndName(company_id, time, "一、营业收入");
		if(p==null){
			return 0;
		}
		else{
			return p.getBalance();
		}
	}


	@Override
	public double getProfit(String time, long company_id) {
		ProfitSheet ps=psr.findByCompanyIdAndPeriodAndName(company_id, time, "四、净利润（净亏损以“-”号填列）");
		if(ps==null){
			
			return 0;
		}else{
			return ps.getBalance();
		}
	}


	@Override
	public double[] getValues(long company_id, String time) {
		// TODO Auto-generated method stub
		return null;
	}
}
