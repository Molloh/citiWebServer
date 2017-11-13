package cn.edu.nju.polaris.service;

import cn.edu.nju.polaris.vo.Inventory.*;

import java.util.ArrayList;

/**
 * Created by 费慧通 on 2017/11/13.
 */
public interface InventoryManagementService {
    /**
     * 得到公司截至某一时间原材料库存监控信息
     * @param company_id 公司id
     * @param time 最后时间 YYYY-MM-DD
     * @return
     */
    public ArrayList<InventoryMonitorItemVo> getRawMaterialInventoryMonitorItem(long company_id, String time);

    /**
     * 得到公司截至某一时间产品库存监控信息
     * @param company_id 公司id
     * @param time 最后时间 YYYY-MM-DD
     * @return
     */
    public ArrayList<InventoryMonitorItemVo> getProductInventoryMonitorItem(long company_id, String time);

    /**
     * 原材料/产品库存量与时间的关系
     * @param company_id 公司id
     * @param variety 种类
     * @param time 截止时间 YYYY-MM-DD
     * @return
     */
    public ArrayList<InventoryChangeVo> getInventoryChange(long company_id, String variety, String time);

    /**
     * 原材料/产品准时交货率与时间的关系
     * @param company_id 公司id
     * @param variety 种类
     * @param time 截止时间 YYYY-MM-DD
     * @return
     */
    public ArrayList<PunctualDeliveryRateChangeVo> getPunctualDeliveryRateChange(long company_id, String variety, String time);

    /**
     * 原材料/产品退货率与时间的关系
     * @param company_id 公司id
     * @param variety 种类
     * @param time 截止时间 YYYY-MM-DD
     * @return
     */
    public ArrayList<RefundRateChangeVo> getRefundRateChange(long company_id, String variety, String time);

    /**
     * 原材料库存量与安全库存量的关系
     * @param company_id 公司id
     * @return
     */
    public ArrayList<SafeInventoryRateVo> getRawMaterialSafeInventoryRate(long company_id);

    /**
     * 产品库存量与安全库存量的关系
     * @param company_id 公司id
     * @return
     */
    public ArrayList<SafeInventoryRateVo> getProductSafeInventoryRate(long company_id);

    /**
     * @param company_id1 供应商
     * @param company_id2 生产商
     * @param time yyyy-mm 年月
     * @param variety 原材料种类
     * @return
     */
    public ArrayList<InventoryAppraisalVo> getRawMaterialInventoryAppraisal(long company_id1,long company_id2, String time, ArrayList<String> variety);

    /**
     * @param company_id1 生产商
     * @param company_id2 经销商
     * @param time yyyy-mm 年月
     * @param variety 产品种类
     * @return
     */
    public ArrayList<InventoryAppraisalVo> getProductInventoryAppraisal(long company_id1,long company_id2, String time, ArrayList<String> variety);
}
