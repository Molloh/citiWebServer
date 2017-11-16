package cn.edu.nju.polaris.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.nju.polaris.vo.SupplyModuleOne;
import cn.edu.nju.polaris.vo.Inventory.InventoryAppraisalVo;
import cn.edu.nju.polaris.entity.SupplyChain;
import cn.edu.nju.polaris.vo.AccountInfoVO;
import cn.edu.nju.polaris.vo.AccountVO;
import cn.edu.nju.polaris.vo.Financing1;
import cn.edu.nju.polaris.vo.Financing2;
import cn.edu.nju.polaris.vo.Financing3;
import cn.edu.nju.polaris.vo.RaworProductAndFromVo;
import cn.edu.nju.polaris.vo.SupplyChainVO;

import java.util.List;

/**
 * 
 * @author hyf
 *
 */
public interface SupplyChainService {
	
	/**
	 * 
	 * @param Supplier_id     供应商
	 * @param Manufacturer_id 生产商
	 * @param Distributor_id  分销商
	 * @param time yyyy-mm
	 * @return 供应链成员绩效评价(模块一)(0数值代表不存在)
	 */
	public List<SupplyModuleOne> getSupplyChainMember(long Supplier_id,long Manufacturer_id,long Distributor_id,String time);
	
	/**
	 * 
	 * @param Supplier_id
	 * @param Manufacturer_id
	 * @param Distributor_id
	 * @param time
	 * @return 供应链成员绩效评价(模块一)平均值、优秀值、较高值
	 */
	public List<List<SupplyModuleOne>> getSupplyChainMemberReference(long Supplier_id,long Manufacturer_id,long Distributor_id,String time);
	
	/**
	 * 
	 * @param Supplier_id     供应商
	 * @param Manufacturer_id 生产商
	 * @param time yyyy-mm
	 * @return 供应链协作绩效评价(模块二)  1.	准时交货率       3.	退货率
	 */
	public List<InventoryAppraisalVo> getSupplyChainCooperation11(long Supplier_id,long Manufacturer_id,String time);
	
	/**
	 * 
	 * @param Supplier_id     供应商
	 * @param Manufacturer_id 生产商
	 * @param time yyyy-mm
	 * @return 供应链协作绩效评价(模块二)  2.	成本利润率    4.	产需率
	 */
	public double[] getSupplyChainCooperation12(long Supplier_id,long Manufacturer_id,String time);
	
	/**
	 * 
	 * @param Manufacturer_id   生产商
	 * @param Distributor_id    分销商
	 * @param time yyyy-mm
	 * @return 供应链协作绩效评价(模块二)  1.	准时交货率       3.	退货率
	 */
	public List<InventoryAppraisalVo> getSupplyChainCooperation21(long Manufacturer_id,long Distributor_id,String time);
	
	/**
	 * 
	 * @param Manufacturer_id   生产商
	 * @param Distributor_id    分销商
	 * @param time yyyy-mm
	 * @return 供应链协作绩效评价(模块二)  2.	成本利润率    4.	产需率
	 */
	public double[] getSupplyChainCooperation22(long Manufacturer_id,long Distributor_id,String time);
	
	/**
	 * 
	 * @param Supplier_id     供应商
	 * @param Manufacturer_id 生产商
	 * @param Distributor_id  分销商
	 * @param time yyyy-mm
	 * @return 模块三：供应链整体绩效评价(10个数据)
	 */
	public double[] getSupplyChainTotal(String Supplier_name, String Manufacturer_name, String Distributor_name,
			long Supplier_id,long Manufacturer_id,long Distributor_id,String time);

	/**
	 *
	 * @param Supplier_name
	 * @param Manufacturer_name
	 * @param Distributor_name
	 * @return
	 */
	public long[] getIds(String Supplier_name,String Manufacturer_name,String Distributor_name);

    List<SupplyChainVO> findChainByUpstreamCompany(Long companyId);

    List<SupplyChainVO> findChainByMidstreamCompany(Long companyId);

    List<SupplyChainVO> findChainByDownstreamCompany(Long companyId);

    List<SupplyChainVO> findAll();

    void addOneChainForUpstream(SupplyChainVO vo);

    void addOneChainForMidStream(SupplyChainVO vo);

    void addOneChainForDownStream(SupplyChainVO vo);
    
    
    
    /*******************融资******************************/
    
    
    /**
     * 
     * @param company_id
     * @return 应收帐款融资中的【应收帐款对象】
     */
    public List<String> getAccountsreceivableCompanys(long company_id);
    
    /**
     * 
     * @param start yyyy-mm
     * @param end yyyy-mm
     * @param company_id
     * @return 应收帐款净额（明细账的分类汇总结果）
     * @throws ParseException 
     */
    public double getNetreceivables(String start,String end,long company_id) throws ParseException;
    
    
    /**
     * @param id       申请的公司
     * @param company  应收帐款对象（下拉框含应收帐款辅助信息部分的公司名称，让企业选择）
     * @param Netreceivables 	应收帐款净额（明细账的分类汇总结果）
     * @param Mortgageamount 	应收帐款抵押额（企业自己输入）
     * 进行应收帐款融资，保存信息
     */
    public void Applyforfinancing_Accountsreceivable(long id,String company,double Netreceivables,double Mortgageamount);
    
    /**
     * 
     * @param company_id
     * @return 动产质押融资和保兑仓融资中的 【原材料和产品名称,以及与之相关的公司】
     */
    public List<RaworProductAndFromVo> getRawsandProducts(long company_id);
     
    
    /**
     * 
     * @param start yyyy-mm
     * @param end yyyy-mm
     * @param company_id
     * @return 库存净额（明细账的分类汇总导入）
     * @throws ParseException 
     */
    public double getNetinventory(String start,String end,long company_id) throws ParseException;
    
    /**
     * @param id       申请的公司
     * @param type 	库存种类（下拉框含原材料和产品名称，让企业选择）
     * @param Netinventory 	库存净额（明细账的分类汇总导入）
     * @param Inventorypledge 	库存质押额（企业自己输入）
     * 选择动产质押融资，保存信息
     */
    public void Applyforfinancing_Chattelmortgage(long id,String type,double Netinventory,double Inventorypledge);
    
    
    /**
     * @param id       申请的公司
     * @param goods 	计划购买货物（下拉框含原材料和产品名称，让企业选择）
     * @param from      货物来源（根据原材料或产品名称自动匹配来源方,让企业选择）
     * @param money  	拟购货物金额（企业自己输入）
     * @param rate   	保障金比例（企业自己输入）
     * 选择保兑仓融资，保存信息
     */
    public void Applyforfinancing_Confirmingwarehousefinancing(long id, String goods,String from,double money,double rate);
    
    public  long getCompanyId(String name);
    
    /*******金融机构******/
    /**
     * 
     * @return 应收账款融资(金融机构需求)
     */
    public List<Financing1> getfinancings1();
    
    /**
     * 
     * @return 动产质押融资(金融机构需求)
     */
    public List<Financing2> getfinancings2();
    
    /**
     * 
     * @return 保兑仓融资(金融机构需求)
     */
    public List<Financing3> getfinancings3();
    
    /**
     * 
     * @return 企业基本信息
     */
    public AccountVO getCompanyInfo(String name);
    
    /**
     * 
     * @param name
     * @return 供应链构成
     */
    public List<SupplyChainVO> getChains(String name);
    
}
