package cn.edu.nju.polaris.service;

import java.util.List;

import cn.edu.nju.polaris.vo.SupplyModuleOne;
import cn.edu.nju.polaris.entity.SupplyChain;
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
	 * @return 供应链成员绩效评价(模块一)平均值
	 */
	public List<SupplyModuleOne> getSupplyChainMemberAverage(long Supplier_id,long Manufacturer_id,long Distributor_id,String time);
	
	/**
	 * 
	 * @param Supplier_id     供应商
	 * @param Manufacturer_id 生产商
	 * @param time yyyy-mm
	 * @return 供应链协作绩效评价(模块二)
	 */
	public double[] getSupplyChainCooperation1(long Supplier_id,long Manufacturer_id,String time);
	
	/**
	 * 
	 * @param Manufacturer_id   生产商
	 * @param Distributor_id    分销商
	 * @param time yyyy-mm
	 * @return 供应链协作绩效评价(模块二)
	 */
	public double[] getSupplyChainCooperation2(long Manufacturer_id,long Distributor_id,String time);
	
	/**
	 * 
	 * @param Supplier_id     供应商
	 * @param Manufacturer_id 生产商
	 * @param Distributor_id  分销商
	 * @param time yyyy-mm
	 * @return 模块三：供应链整体绩效评价
	 */
	public double[] getSupplyChainTotal(long Supplier_id,long Manufacturer_id,long Distributor_id,String time);

    List<SupplyChainVO> findChainByUpstreamCompany(Long companyId);

    List<SupplyChainVO> findChainByMidstreamCompany(Long companyId);

    List<SupplyChainVO> findChainByDownstreamCompany(Long companyId);

    List<SupplyChainVO> findAll();

    void addOneChainForUpstream(SupplyChainVO vo);

    void addOneChainForMidStream(SupplyChainVO vo);

    void addOneChainForDownStream(SupplyChainVO vo);
}
