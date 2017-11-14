package cn.edu.nju.polaris.service.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.nju.polaris.entity.VoucherItem;
import cn.edu.nju.polaris.entity.SupplyChainFinancing.ConfirmingStorageFinancing;
import cn.edu.nju.polaris.entity.SupplyChainFinancing.MovablePledgeFinancing;
import cn.edu.nju.polaris.entity.SupplyChainFinancing.ReceivablesFinancing;
import cn.edu.nju.polaris.repository.BalanceSheetRepository;
import cn.edu.nju.polaris.repository.CashflowSheetRepository;
import cn.edu.nju.polaris.repository.IndustryIndexRepository;
import cn.edu.nju.polaris.repository.VoucherItemRepository;
import cn.edu.nju.polaris.repository.SupplyChainFinancing.ConfirmingStorageFinancingRepository;
import cn.edu.nju.polaris.repository.SupplyChainFinancing.MovablePledgeFinancingRepository;
import cn.edu.nju.polaris.repository.SupplyChainFinancing.ReceivablesFinancingRepository;
import cn.edu.nju.polaris.service.SupplyChainService;
import cn.edu.nju.polaris.vo.SupplyModuleOne;
import cn.edu.nju.polaris.entity.Account;
import cn.edu.nju.polaris.entity.IndustryIndex;
import cn.edu.nju.polaris.entity.SupplyChain;
import cn.edu.nju.polaris.entity.SupportItem1;
import cn.edu.nju.polaris.entity.SupportItem2;
import cn.edu.nju.polaris.exception.ResourceConflictException;
import cn.edu.nju.polaris.exception.ResourceNotFoundException;
import cn.edu.nju.polaris.repository.AccountRepository;
import cn.edu.nju.polaris.repository.SupplyChainRepository;
import cn.edu.nju.polaris.repository.SupportItem1Repository;
import cn.edu.nju.polaris.repository.SupportItem2Repository;
import cn.edu.nju.polaris.service.SupplyChainService;
import cn.edu.nju.polaris.vo.RaworProductAndFromVo;
import cn.edu.nju.polaris.vo.SupplyChainVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author hyf
 *
 */
@Service
public class SupplyChainImpl implements SupplyChainService{
	
	private TableHelper helper;
	
	private final BalanceSheetRepository bsr;
	private final VoucherItemRepository vir;
    private final SupplyChainRepository supplyChainRepository;
    private final AccountRepository accountRepository;
    private final SupportItem1Repository sir;
    private final SupportItem2Repository sir2;
    private final CashflowSheetRepository cfr;
    private final AccountRepository ar;
    private final IndustryIndexRepository ir;
    
    private final ReceivablesFinancingRepository rfr;
    private final MovablePledgeFinancingRepository mpfr;
    private final ConfirmingStorageFinancingRepository csfr;

	@Autowired
	public SupplyChainImpl(VoucherItemRepository vir,BalanceSheetRepository bsr,SupplyChainRepository supplyChainRepository, AccountRepository accountRepository,
			SupportItem1Repository sir,CashflowSheetRepository cfr,AccountRepository ar,IndustryIndexRepository ir,SupportItem2Repository sir2,
			ReceivablesFinancingRepository rfr,MovablePledgeFinancingRepository mpfr,ConfirmingStorageFinancingRepository csfr){
		this.bsr=bsr;
		this.vir=vir;
		this.helper=new TableHelper();
        this.supplyChainRepository = supplyChainRepository;
        this.accountRepository = accountRepository;
        this.sir=sir;
        this.cfr=cfr;
        this.ar=ar;
        this.ir=ir;
        this.sir2=sir2;
        this.rfr=rfr;
        this.mpfr=mpfr;
        this.csfr=csfr;
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
	public List<List<SupplyModuleOne>> getSupplyChainMemberReference(long Supplier_id, long Manufacturer_id,
			long Distributor_id, String time) {
		Account account1=ar.findById(Supplier_id);
		Account account2=ar.findById(Manufacturer_id);
		Account account3=ar.findById(Distributor_id);
		
		List<List<SupplyModuleOne>>res=new ArrayList<>();
		
		List<IndustryIndex> l1=ir.findByCategoryAndFirstIndustryAndSecondIndustryAndScale("供应链绩效评价",
				account1.getFirstIndustry(),account1.getSecondIndustry(),account1.getScale());
		List<IndustryIndex> l2=ir.findByCategoryAndFirstIndustryAndSecondIndustryAndScale("供应链绩效评价",
				account2.getFirstIndustry(),account2.getSecondIndustry(),account2.getScale());
		List<IndustryIndex> l3=ir.findByCategoryAndFirstIndustryAndSecondIndustryAndScale("供应链绩效评价",
				account3.getFirstIndustry(),account3.getSecondIndustry(),account3.getScale());
		
		List<SupplyModuleOne> temp1=ReferenceDeal(l1);
		List<SupplyModuleOne> temp2=ReferenceDeal(l2);
		List<SupplyModuleOne> temp3=ReferenceDeal(l3);
		
		List<SupplyModuleOne> res1=new ArrayList<>();
		List<SupplyModuleOne> res2=new ArrayList<>();
		List<SupplyModuleOne> res3=new ArrayList<>();
		
		res1.add(temp1.get(0));
		res1.add(temp2.get(0));
		res1.add(temp3.get(0));
		res2.add(temp1.get(1));
		res2.add(temp2.get(1));
		res2.add(temp3.get(1));
		res3.add(temp1.get(2));
		res3.add(temp2.get(2));
		res3.add(temp3.get(2));
		
		res.add(res1);
		res.add(res2);
		res.add(res3);
		
		return res;
	}
	
	private List<SupplyModuleOne> ReferenceDeal(List<IndustryIndex> list){
		List<SupplyModuleOne> res=new ArrayList<>();
		
		double[] Profit1=new double[2];
		double[] Operate1=new double[2];
		double[] Develop1=new double[2];
		double[] Sinking1=new double[2];
		
		double[] Profit2=new double[2];
		double[] Operate2=new double[2];
		double[] Develop2=new double[2];
		double[] Sinking2=new double[2];
		
		double[] Profit3=new double[2];
		double[] Operate3=new double[2];
		double[] Develop3=new double[2];
		double[] Sinking3=new double[2];
		
		Map<String,IndustryIndex> map=new HashMap<>();
		for(IndustryIndex v:list)
			map.put(v.getIndexName(), v);
		
		Profit1[0]=map.get("总资产报酬率").getAverageIndex();
		Profit2[0]=map.get("总资产报酬率").getExcellentIndex();
		Profit3[0]=map.get("总资产报酬率").getFineIndex();
		Profit1[1]=map.get("销售（营业）利润率").getAverageIndex();
		Profit2[1]=map.get("销售（营业）利润率").getExcellentIndex();
		Profit3[1]=map.get("销售（营业）利润率").getFineIndex();
		
		Operate1[0]=map.get("总资产周转率").getAverageIndex();
		Operate2[0]=map.get("总资产周转率").getExcellentIndex();
		Operate3[0]=map.get("总资产周转率").getFineIndex();
		Operate1[1]=map.get("存货周转率").getAverageIndex();
		Operate2[1]=map.get("存货周转率").getExcellentIndex();
		Operate3[1]=map.get("存货周转率").getFineIndex();
		
		Develop1[0]=map.get("销售（营业）增长率").getAverageIndex();
		Develop2[0]=map.get("销售（营业）增长率").getExcellentIndex();
		Develop3[0]=map.get("销售（营业）增长率").getFineIndex();
		Develop1[1]=map.get("销售（营业）利润增长率").getAverageIndex();
		Develop2[1]=map.get("销售（营业）利润增长率").getExcellentIndex();
		Develop3[1]=map.get("销售（营业）利润增长率").getFineIndex();
		
		Sinking1[0]=map.get("资产负债率").getAverageIndex();
		Sinking2[0]=map.get("资产负债率").getExcellentIndex();
		Sinking3[0]=map.get("资产负债率").getFineIndex();
		Sinking1[1]=map.get("速动比率").getAverageIndex();
		Sinking2[1]=map.get("速动比率").getExcellentIndex();
		Sinking3[1]=map.get("速动比率").getFineIndex();
		
		res.add(new SupplyModuleOne(Profit1,Operate1,Develop1,Sinking1));
		res.add(new SupplyModuleOne(Profit2,Operate2,Develop2,Sinking2));
		res.add(new SupplyModuleOne(Profit3,Operate3,Develop3,Sinking3));
		
		return res;
	}

	@Override
	public double[] getSupplyChainCooperation1(long Supplier_id, long Manufacturer_id, String time) {
		
		return null;
	}

	@Override
	public double[] getSupplyChainCooperation2(long Manufacturer_id, long Distributor_id, String time) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[] getSupplyChainTotal(String Supplier_name, String Manufacturer_name, String Distributor_name,
			long Supplier_id, long Manufacturer_id, long Distributor_id, String time) {
		double []res=new double[10];
		
		String last=helper.lastTime(time);
		
		List<SupportItem1> l1=sir.findAllByCompanyIdAndEndSideAndDate(Manufacturer_id,Supplier_name,time);
		List<SupportItem1> l2=sir.findAllByCompanyIdAndEndSideAndDate(Distributor_id,Manufacturer_name,time);
		List<SupportItem1> l3=sir.findAllByCompanyIdAndEndSideAndDate(Manufacturer_id,Supplier_name,time);
		
		//1.财务方面
		//res[0]=;//供应链资产收益率
		//res[1]=;//现金周转率
		//res[2]=;///资产负债率
		
		//2.客户方面
		List<Double> list=CalLv(sir.findAllItemByDate(Supplier_id, time),sir.findAllItemByDate(Manufacturer_id, time),sir.findAllItemByDate(Distributor_id, time));
		//res[3]=list.get(0);//退货率
		//res[4]=list.get(1);//准时交货率
		//res[5]=list.get(2);//产品柔性
		
		//3.业务流程
		//res[6]=;//存货周转率
		res[7]=list.get(3);//完美交货完成水平
		
		//4.未来发展
		//新产品销售比率
		//res[9]=;//利润增长率
		return res;
	}
	
	private List<Double> CalLv(List<SupportItem1> list1,List<SupportItem1> list2,List<SupportItem1> list3){
		List<Double> res=new ArrayList<>();
		if((list1.size()+list2.size()+list3.size())==0){
			res.add((double) 0);res.add((double) 0);res.add((double) 0);res.add((double) 0);
			return res;
		}
		int count1=0;
		int count2=0;
		int count3=0;

		for(SupportItem1 s:list1){
			if(s.getReturnedPurchase())
				count1++;
			if(s.getDispatchOntime())
				count2++;
			if(s.getNew())
				count3++;
		}
		
		for(SupportItem1 s:list2){
			if(s.getReturnedPurchase())
				count1++;
			if(s.getDispatchOntime())
				count2++;
			if(s.getNew())
				count3++;
		}
		
		for(SupportItem1 s:list3){
			if(s.getReturnedPurchase())
				count1++;
			if(s.getDispatchOntime())
				count2++;
			if(s.getNew())
				count3++;
		}
		
		
		int size=list1.size()+list2.size()+list3.size();
		res.add((double)count1/(double)size);
		res.add((double)count2/(double)size);
		res.add((double)count3/(double)size);
		res.add((double)(count2-count1)/(double)size);
		
		return res;
	}


    @Override
    public List<SupplyChainVO> findChainByUpstreamCompany(Long companyId) {
        Account account = accountRepository.findById(companyId);
        if (account == null){
            throw new ResourceNotFoundException("该公司不存在");
        }
        String name = account.getCompanyName();
        List<SupplyChain> entityList = supplyChainRepository.findAllByUpstreamCompany(name);
        List<SupplyChainVO> voList = new ArrayList<>();
        for (SupplyChain entity : entityList){
            voList.add(entity2VO(entity));
        }
        return voList;
    }

    @Override
    public List<SupplyChainVO> findChainByMidstreamCompany(Long companyId) {
        Account account = accountRepository.findById(companyId);
        if (account == null){
            throw new ResourceNotFoundException("该公司不存在");
        }
        String name = account.getCompanyName();
        List<SupplyChain> entityList = supplyChainRepository.findAllByMidstreamCompany(name);
        List<SupplyChainVO> voList = new ArrayList<>();
        for (SupplyChain entity : entityList){
            voList.add(entity2VO(entity));
        }
        return voList;
    }

    @Override
    public List<SupplyChainVO> findChainByDownstreamCompany(Long companyId) {
        Account account = accountRepository.findById(companyId);
        if (account == null){
            throw new ResourceNotFoundException("该公司不存在");
        }
        String name = account.getCompanyName();
        List<SupplyChain> entityList = supplyChainRepository.findAllByDownstreamCompany(name);
        List<SupplyChainVO> voList = new ArrayList<>();
        for (SupplyChain entity : entityList){
            voList.add(entity2VO(entity));
        }
        return voList;
    }

    @Override
    public List<SupplyChainVO> findAll() {
        List<SupplyChainVO> voList = new ArrayList<>();
        List<SupplyChain> list = supplyChainRepository.findAll();
        for (SupplyChain chain : list){
            voList.add(entity2VO(chain));
        }
        return voList;
    }

    @Override
    public void addOneChainForUpstream(SupplyChainVO vo) {
        supplyChainRepository.insertUp2Mid(vo.upstreamCompany,vo.midstreamCompany);
    }

    @Override
    public void addOneChainForMidStream(SupplyChainVO vo) {
        SupplyChain temp = supplyChainRepository.findByUpstreamCompanyAndMidstreamCompanyAndDownstreamCompany(vo.upstreamCompany,vo.midstreamCompany,vo.downstreamCompany);
        if (temp != null){
            throw new ResourceConflictException("该条供应链已存在");
        }
        List<String> upstreamList = supplyChainRepository.findAllUpByMid(vo.midstreamCompany);
        List<String> downstreamList = supplyChainRepository.findAllDownByMid(vo.midstreamCompany);
        if (upstreamList.contains(vo.upstreamCompany) && downstreamList.contains(vo.downstreamCompany)){
            SupplyChain chain = new SupplyChain();
            chain.setUpstreamCompany(vo.upstreamCompany);
            chain.setMidstreamCompany(vo.midstreamCompany);
            chain.setDownstreamCompany(vo.downstreamCompany);
            supplyChainRepository.save(chain);
        }else if (!upstreamList.contains(vo.upstreamCompany)){
            throw new ResourceNotFoundException("该企业不是您的上游企业");
        }else if (!downstreamList.contains(vo.downstreamCompany)){
            throw new ResourceNotFoundException("该企业不是您的下游企业");
        }
    }

    @Override
    public void addOneChainForDownStream(SupplyChainVO vo) {
        supplyChainRepository.insertMid2Down(vo.midstreamCompany,vo.downstreamCompany);
    }


    private SupplyChainVO entity2VO(SupplyChain chain){
        SupplyChainVO vo = new SupplyChainVO();
        vo.upstreamCompany = chain.getUpstreamCompany();
        vo.midstreamCompany = chain.getMidstreamCompany();
        vo.downstreamCompany = chain.getDownstreamCompany();
        return vo;
    }

	@Override
	public long[] getIds(String Supplier_name, String Manufacturer_name, String Distributor_name) {
		long []res=new long[3];
		
		res[0]=ar.findByCompanyName(Supplier_name).getId();
		res[1]=ar.findByCompanyName(Manufacturer_name).getId();
		res[2]=ar.findByCompanyName(Distributor_name).getId();
		
		return res;
	}

	
	
	
	/*************************************融资***********************************************************/

	@Override
	public List<String> getAccountsreceivableCompanys(long company_id) {
		List<SupportItem2> list=sir2.findAllByCompanyId(company_id);
		
		List<String> res=new ArrayList<String>();
		
		for(SupportItem2 s:list){
			String name=s.getCompanyName();
			if(!res.contains(name))
				res.add(name);
		}
		
		return res;
	}

	@Override
	public double getNetreceivables(String start, String end, long company_id) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void Applyforfinancing_Accountsreceivable(long id,String company, double Netreceivables, double Mortgageamount) {
		ReceivablesFinancing r=new ReceivablesFinancing(id,company,Netreceivables,Mortgageamount);
		rfr.save(r);
	}

	@Override
	public List<RaworProductAndFromVo> getRawsandProducts(long company_id) {
		List<SupportItem2> list=sir2.findAllByCompanyId(company_id);
		
		List<RaworProductAndFromVo> res=new ArrayList<>();
		Map<Long,List<String>> map=new HashMap<>();
		
		for(SupportItem2 s:list){
			long id=s.getCompanyId();
			String name=s.getCompanyName();
			if(map.containsKey(id)){
				List<String> t=map.get(id);
				if(!t.contains(name)){
					t.add(name);
					map.put(id, t);
				}
			}else{
				List<String> t=new ArrayList<>();
				t.add(name);
				map.put(id, t);
			}
		}
		
		for(Map.Entry<Long, List<String>> entry:map.entrySet()){
			res.add(new RaworProductAndFromVo(getCompanyName(entry.getKey()),entry.getValue()));
		}
		
		return res;
	}

	@Override
	public double getNetinventory(String start, String end, long company_id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void Applyforfinancing_Chattelmortgage(long id,String type, double Netinventory, double Inventorypledge) {
		MovablePledgeFinancing m=new MovablePledgeFinancing(id,type,Netinventory,Inventorypledge);
		mpfr.save(m);
	}

	@Override
	public void Applyforfinancing_Confirmingwarehousefinancing(long id ,String goods, String from, double money, double rate) {
		ConfirmingStorageFinancing c=new ConfirmingStorageFinancing(id,goods,from,money,rate);
		csfr.save(c);
	}
	
	@Override
	public long getCompanyId(String name){
		return ar.findByCompanyName(name).getId();
	}
	
	private String getCompanyName(long id){
		return ar.findById(id).getCompanyName();
	}
}
