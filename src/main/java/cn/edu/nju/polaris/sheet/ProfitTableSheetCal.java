package cn.edu.nju.polaris.sheet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import cn.edu.nju.polaris.entity.ProfitSheet;
import cn.edu.nju.polaris.entity.VoucherItem;
import cn.edu.nju.polaris.repository.ProfitSheetRepository;
import cn.edu.nju.polaris.repository.VoucherItemRepository;
import cn.edu.nju.polaris.service.Impl.TableHelper;

/**
 * 
 * @author hyf
 *
 */
@Component
public class ProfitTableSheetCal {
	
	private VoucherItemRepository vir;
	private TableHelper th;
	private ProfitSheetRepository psr;
	
	@Autowired
	public ProfitTableSheetCal(VoucherItemRepository vir,ProfitSheetRepository psr){
		this.vir=vir;
		this.psr=psr;
		th=new TableHelper();
	}
	
	
	public void UpdateProfitTable(String time,long company_id){
		List<VoucherItem> list=vir.getListThroughPeriod(time, company_id);
		
		Map<String,double[]> map=new HashMap<String,double[]>();
		map=th.tempCal(list);
		
		double business_income=0;//营业收入
		double temp1=th.Cal("5001", map);
		double temp2=th.Cal("5051", map);
		business_income=temp1+temp2;//主营业务收入+其他业务收入
		psr.save(new ProfitSheet(company_id,time,"一、营业收入",business_income));
		
		double business_costs=0;//营业成本
		temp1=th.Cal2("5401", map);
		temp2=th.Cal2("5451", map);
		business_costs=temp1+temp2;//主营业务成本+其他业务成本
		psr.save(new ProfitSheet(company_id,time,"减：营业成本",business_costs));
		
		double[] Business_Taxes_and_Surcharges=new double[8];
		Business_Taxes_and_Surcharges[0]=th.Cal2("5403", map);//税金及附加
		Business_Taxes_and_Surcharges[1]=th.CreditCal("2221003", map);//应交消费税
		Business_Taxes_and_Surcharges[2]=th.CreditCal("2221004", map);//应交营业税
		Business_Taxes_and_Surcharges[3]=th.CreditCal("2221008", map);//应交城市维护建设税
		Business_Taxes_and_Surcharges[4]=th.CreditCal("2221005", map);//应交资源税
		Business_Taxes_and_Surcharges[5]=th.CreditCal("2221007", map);//应交土地增值税
		Business_Taxes_and_Surcharges[6]=th.CreditCal("2221010", map)+//应交城镇土地使用税
				th.CreditCal("2221011", map)+//应交车船使用税
				th.CreditCal("2221009", map)+//应交房产税
				th.CreditCal("2221017", map);//印花税
		Business_Taxes_and_Surcharges[7]=th.CreditCal("2221013", map)+//教育费附加
				th.CreditCal("2221014", map)+//地方教育费附加
				th.CreditCal("2221016", map)+//排污费
				th.CreditCal("2221015", map);//矿产资源补偿费
		psr.save(new ProfitSheet(company_id,time,"营业税金及附加",Business_Taxes_and_Surcharges[0]));
		psr.save(new ProfitSheet(company_id,time,"其中：消费税",Business_Taxes_and_Surcharges[1]));
		psr.save(new ProfitSheet(company_id,time,"营业税",Business_Taxes_and_Surcharges[2]));
		psr.save(new ProfitSheet(company_id,time,"城市维护建设税",Business_Taxes_and_Surcharges[3]));
		psr.save(new ProfitSheet(company_id,time,"资源税",Business_Taxes_and_Surcharges[4]));
		psr.save(new ProfitSheet(company_id,time,"土地增值税",Business_Taxes_and_Surcharges[5]));
		psr.save(new ProfitSheet(company_id,time,"城镇土地使用税、房产税、车船税、印花税",Business_Taxes_and_Surcharges[6]));
		psr.save(new ProfitSheet(company_id,time,"教育费附加、矿产资源补偿费、排污费",Business_Taxes_and_Surcharges[7]));
		
		
				
		double[] Selling_expenses=new double[3];
		Selling_expenses[0]=th.Cal2(th.specificSubject("5601", map));//销售费用
		Selling_expenses[1]=th.Cal2("5601010", map);//商品维修费
		Selling_expenses[2]=th.Cal2("5601015", map)+
				th.Cal2("5601016", map);//广告费+业务宣传费
		psr.save(new ProfitSheet(company_id,time,"销售费用",Selling_expenses[0]));
		psr.save(new ProfitSheet(company_id,time,"其中：商品维修费",Selling_expenses[1]));
		psr.save(new ProfitSheet(company_id,time,"广告费和业务宣传费",Selling_expenses[2]));
		
		double[] Management_expenses=new double[4];
		Management_expenses[0]=th.Cal2(th.specificSubject("5602", map));//管理费用
		Management_expenses[1]=th.Cal2("5602009", map);//开办费
		Management_expenses[2]=th.Cal2("5602002", map);//业务招待费
		Management_expenses[3]=th.Cal2("5602010", map);//研究费用
		psr.save(new ProfitSheet(company_id,time,"管理费用",Management_expenses[0]));
		psr.save(new ProfitSheet(company_id,time,"其中：开办费",Management_expenses[1]));
		psr.save(new ProfitSheet(company_id,time,"业务招待费",Management_expenses[2]));
		psr.save(new ProfitSheet(company_id,time,"研究费用",Management_expenses[3]));
		
		
		double[] Financial_expenses=new double[2];//财务费用
		Financial_expenses[0]=th.Cal2(th.specificSubject("5603", map));//财务费用
		Financial_expenses[1]=th.Cal2("5603001", map);//利息费用	
		psr.save(new ProfitSheet(company_id,time,"财务费用",Financial_expenses[0]));
		psr.save(new ProfitSheet(company_id,time,"其中：利息费用（收入以“-”号填列）",Financial_expenses[1]));
		
		
		double investment_proceeds=th.Cal("5111", map);//投资收益
		psr.save(new ProfitSheet(company_id,time,"加：投资收益（亏损以“-”号填列）",investment_proceeds));
		
		double operating_profit=business_income+investment_proceeds-business_costs-
				Business_Taxes_and_Surcharges[0]-Selling_expenses[0]-Management_expenses[0]-Financial_expenses[0];
		psr.save(new ProfitSheet(company_id,time,"二、营业利润（亏损以“-”号填列）",operating_profit));
		
		double[] Non_operating_income=new double[2];
		Non_operating_income[0]=th.Cal(th.specificSubject("5301", map));
		Non_operating_income[1]=th.Cal("5301002", map);//营业外收入-政府补助
		psr.save(new ProfitSheet(company_id,time,"加：营业外收入",Non_operating_income[0]));
		psr.save(new ProfitSheet(company_id,time,"其中：政府补助",Non_operating_income[1]));
		
		double[] Non_operating_expenses=new double[6];
		Non_operating_expenses[0]=th.Cal2("5711", map);
		Non_operating_expenses[1]=th.Cal2("5711005", map);//坏账损失
		Non_operating_expenses[2]=th.Cal2("5711007", map);//无法收回的长期债券投资损失
		Non_operating_expenses[3]=th.Cal2("5711008", map);//无法收回的长期股权投资
		Non_operating_expenses[4]=th.Cal2("5711009", map);//自然灾害等不可抗因素造成的损失
		Non_operating_expenses[5]=th.Cal2("5711010", map);//税收滞纳金
		psr.save(new ProfitSheet(company_id,time,"减：营业外支出",Non_operating_expenses[0]));
		psr.save(new ProfitSheet(company_id,time,"其中：坏账损失",Non_operating_expenses[1]));
		psr.save(new ProfitSheet(company_id,time,"无法收回的长期债券投资损失",Non_operating_expenses[2]));
		psr.save(new ProfitSheet(company_id,time,"无法收回的长期股权投资损失",Non_operating_expenses[3]));
		psr.save(new ProfitSheet(company_id,time,"自然灾害等不可抗力因素造成的损失",Non_operating_expenses[4]));
		psr.save(new ProfitSheet(company_id,time,"税收滞纳金",Non_operating_expenses[5]));
		
		double Total_profit=operating_profit+Non_operating_income[0]-Non_operating_expenses[0];
		psr.save(new ProfitSheet(company_id,time,"三、利润总额（亏损总额以“-”号填列）",Total_profit));
		
		double Income_tax_expense=th.Cal2("5801", map);
		psr.save(new ProfitSheet(company_id,time,"减：所得税费用",Income_tax_expense));
		
		double Net_profit=Total_profit-Income_tax_expense;
		psr.save(new ProfitSheet(company_id,time,"四、净利润（净亏损以“-”号填列）",Net_profit));
		
	}
}
