package cn.edu.nju.polaris.service.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.nju.polaris.entity.ProfitSheet;
import cn.edu.nju.polaris.entity.VoucherItem;
import cn.edu.nju.polaris.repository.ProfitSheetRepository;
import cn.edu.nju.polaris.repository.VoucherItemRepository;
import cn.edu.nju.polaris.service.ProfitTableService;
import cn.edu.nju.polaris.vo.Pro_and_CashVo;

/**
 * 
 * @author hyf
 *
 */
@Service
public class ProfitTableImpl implements ProfitTableService{
	
	private ProfitSheetRepository psr;
	private TableHelper helper;
	private VoucherItemRepository vir;
	
	@Autowired
    public ProfitTableImpl(ProfitSheetRepository psr,VoucherItemRepository vir) {
        this.psr = psr;
        this.vir=vir;
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
				
				if(temp.size()!=0){
					for(ProfitSheet p:temp){
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
					ProfitSheet p=list.get(i);
					String project=p.getName();	
					double period=p.getBalance();
					double year=period;
					res.add(new Pro_and_CashVo(project,i+1,year,period));
				}
			}else{
				for(int i=0;i<list.size();i++){
					ProfitSheet p=list.get(i);
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
		List<VoucherItem> list=vir.getListThroughPeriod(time, company_id);	
		Map<String,double[]> map=new HashMap<String,double[]>();
		map=helper.tempCal(list);
		
		List<VoucherItem> list2=vir.getListThroughPeriod(lastTime(time), company_id);	
		Map<String,double[]> map2=new HashMap<String,double[]>();
		
		double res[]=new double[10];
		
		if(list2.size()==0){
			res[0]=psr.findByCompanyIdAndPeriodAndName(company_id, time, "四、净利润（净亏损以“-”号填列）").getBalance();//净利润
			res[1]=psr.findByCompanyIdAndPeriodAndName(company_id, time, "三、利润总额（亏损总额以“-”号填列）").getBalance();//利润总额
			res[2]=0;//主营业务成本
			res[3]=psr.findByCompanyIdAndPeriodAndName(company_id, time, "销售费用").getBalance();//销售费用
			res[4]=psr.findByCompanyIdAndPeriodAndName(company_id, time, "管理费用").getBalance();//管理费用
			res[5]=psr.findByCompanyIdAndPeriodAndName(company_id, time, "财务费用").getBalance();//财务费用
			res[6]=psr.findByCompanyIdAndPeriodAndName(company_id, time, "减：营业成本").getBalance();//营业成本
			res[7]=0;//其他业务收入
			res[8]=0;//本期主营业务收入
			res[9]=helper.Cal("5001", map2);//上一期主营业务收入
		}
		else{
			map=helper.tempCal(list2);	
			res[0]=psr.findByCompanyIdAndPeriodAndName(company_id, time, "四、净利润（净亏损以“-”号填列）").getBalance();//净利润
			res[1]=psr.findByCompanyIdAndPeriodAndName(company_id, time, "三、利润总额（亏损总额以“-”号填列）").getBalance();//利润总额
			res[2]=helper.Cal("5401", map);//主营业务成本
			res[3]=psr.findByCompanyIdAndPeriodAndName(company_id, time, "销售费用").getBalance();//销售费用
			res[4]=psr.findByCompanyIdAndPeriodAndName(company_id, time, "管理费用").getBalance();//管理费用
			res[5]=psr.findByCompanyIdAndPeriodAndName(company_id, time, "财务费用").getBalance();//财务费用
			res[6]=psr.findByCompanyIdAndPeriodAndName(company_id, time, "减：营业成本").getBalance();//营业成本
			res[7]=helper.Cal("5051", map);//其他业务收入
			res[8]=helper.Cal("5001", map);//本期主营业务收入
			res[9]=helper.Cal("5001", map2);//上一期主营业务收入
		}

		return res;
	}
	
	private String lastTime(String time){
		System.out.print(time);
		String temp[]=time.split("-");
		int year=Integer.parseInt(temp[0]);
		int month=Integer.parseInt(temp[1]);
		if(month!=1)
			month--;
		else{
			year--;
			month=12;
		}
		if(month<10)
			return String.valueOf(year)+"-0"+String.valueOf(month);
		else
			return String.valueOf(year)+"-"+String.valueOf(month);
		
	}
	
}
