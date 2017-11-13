package cn.edu.nju.polaris.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.nju.polaris.entity.VoucherItem;
import cn.edu.nju.polaris.repository.BalanceSheetRepository;
import cn.edu.nju.polaris.repository.VoucherItemRepository;
import cn.edu.nju.polaris.service.SupplyChainService;
import cn.edu.nju.polaris.vo.SupplyModuleOne;

/**
 * 
 * @author hyf
 *
 */
@Service
public class SupplyChainImpl implements SupplyChainService{
	
	private BalanceSheetRepository bsr;
	private VoucherItemRepository vir;
	private TableHelper helper;
	
	@Autowired
	public SupplyChainImpl(VoucherItemRepository vir,BalanceSheetRepository bsr){
		this.bsr=bsr;
		this.vir=vir;
		this.helper=new TableHelper();
	}

	@Override
	public List<SupplyModuleOne> getSupplyChainMember(long Supplier_id, long Manufacturer_id, long Distributor_id,
			String time) {
		String last=helper.lastTime(time);
		
		List<VoucherItem> list11=vir.getListThroughPeriod(last, Supplier_id);
		Map<String,double[]> map11=helper.tempCal(list11);
		List<VoucherItem> list12=vir.getListThroughPeriod(time, Manufacturer_id);
		Map<String,double[]> map12=helper.tempCal(list12);
		List<VoucherItem> list21=vir.getListThroughPeriod(last, Distributor_id);
		Map<String,double[]> map21=helper.tempCal(list21);
		List<VoucherItem> list22=vir.getListThroughPeriod(time, Supplier_id);
		Map<String,double[]> map22=helper.tempCal(list22);
		List<VoucherItem> list31=vir.getListThroughPeriod(last, Manufacturer_id);
		Map<String,double[]> map31=helper.tempCal(list31);
		List<VoucherItem> list32=vir.getListThroughPeriod(time, Distributor_id);
		Map<String,double[]> map32=helper.tempCal(list32);
		
		List<SupplyModuleOne> res=new ArrayList<SupplyModuleOne>();
		res.add(MemberTempCal(map11,map12,Supplier_id,time,last));
		res.add(MemberTempCal(map21,map22,Manufacturer_id,time,last));
		res.add(MemberTempCal(map31,map32,Distributor_id,time,last));
		
		return res;
	}
	
	private SupplyModuleOne MemberTempCal(Map<String,double[]> map1,Map<String,double[]> map2,long company_id,String time,String last){
		double this_zyshouru1=helper.Cal("5001",map2);//当期主营业务收入
		double last_zyshouru1=helper.Cal("5001", map1);//上期主营业务收入
		double this_zychenben1=helper.Cal2("5401", map2);//当期主营业务成本
		double last_zychenben1=helper.Cal2("5401", map1);//上期主营业务成本
		double last_zong1=bsr.findByCompanyIdAndPeriodAndName(company_id, last, "资产合计").getBalance();//上期期末总资产
		double this_zong1=bsr.findByCompanyIdAndPeriodAndName(company_id, time, "资产合计").getBalance();//本期期末总资产
		double this_qtshouru1=helper.Cal("5051", map2);//当期其他业务收入
		double last_cunhuo1=bsr.findByCompanyIdAndPeriodAndName(company_id, last, "存货").getBalance();//上期期末存货
		double this_cunhuo1=bsr.findByCompanyIdAndPeriodAndName(company_id, time, "存货").getBalance();//当期期末存货
		double zongfu1=bsr.findByCompanyIdAndPeriodAndName(company_id, time, "负债合计").getBalance();//总负债
		double this_liudongzichan=bsr.findByCompanyIdAndPeriodAndName(company_id, time, "流动资产合计").getBalance();//当期期末流动资产
		double this_liudongfuzhai=bsr.findByCompanyIdAndPeriodAndName(company_id, time, "流动负债合计").getBalance();//当期期末流动负债
		
	    double[] Profit=new double[2];
		double[] Operate=new double[2];
		double[] Develop=new double[2];
		double[] Sinking=new double[2];
		
		Profit[0]=(last_zong1+this_zong1)!=0?2*(this_zyshouru1-this_zychenben1)/(last_zong1+this_zong1):0;//盈利能力-总资产报酬率
		Profit[1]=this_zyshouru1!=0?(this_zyshouru1-this_zychenben1)/this_zyshouru1:0;//盈利能力-销售净利率
		
		Operate[0]=(last_zong1+this_zong1)!=0?2*(this_zyshouru1+this_qtshouru1)/(last_zong1+this_zong1):0;//运营能力-总资产周转率
		Operate[1]=(last_cunhuo1+this_cunhuo1)!=0?2*this_zychenben1/(last_cunhuo1+this_cunhuo1):0;//运营能力-存货周转率
		
		Develop[0]=last_zyshouru1!=0?(this_zyshouru1-last_zyshouru1)/last_zyshouru1:0;//发展能力-销售增长率
		Develop[1]=(last_zyshouru1-last_zychenben1)!=0?((this_zyshouru1-this_zychenben1)-(last_zyshouru1-last_zychenben1))/
				(last_zyshouru1-last_zychenben1):0;//发展能力-利润增长率
		
		Sinking[0]=zongfu1!=0?this_zong1/zongfu1:0;//偿债能力-资产负债率
		Sinking[1]=this_liudongfuzhai!=0?(this_liudongzichan-last_cunhuo1)/this_liudongfuzhai:0;//偿债能力-速动比率
		
		return new SupplyModuleOne(Profit,Operate,Develop,Sinking);
		
	}

	@Override
	public List<SupplyModuleOne> getSupplyChainMemberAverage(long Supplier_id, long Manufacturer_id,
			long Distributor_id, String time) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[] getSupplyChainCooperation1(long Supplier_id, long Manufacturer_id, String time) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[] getSupplyChainCooperation2(long Manufacturer_id, long Distributor_id, String time) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[] getSupplyChainTotal(long Supplier_id, long Manufacturer_id, long Distributor_id, String time) {
		// TODO Auto-generated method stub
		return null;
	}

}
