package cn.edu.nju.polaris.service.Impl;

import cn.edu.nju.polaris.service.InventoryManagementService;
import cn.edu.nju.polaris.vo.Inventory.*;

import java.util.ArrayList;

/**
 * Created by 费慧通 on 2017/11/13.
 */
public class InventoryManagementImpl implements InventoryManagementService {
    @Override
    public ArrayList<InventoryMonitorItemVo> getRawMaterialInventoryMonitorItem(long company_id, String time) {
        return null;
    }

    @Override
    public ArrayList<InventoryMonitorItemVo> getProductInventoryMonitorItem(long company_id, String time) {
        return null;
    }

    @Override
    public ArrayList<InventoryChangeVo> getInventoryChange(long company_id, String variety, String time) {
        return null;
    }

    @Override
    public ArrayList<PunctualDeliveryRateChangeVo> getPunctualDeliveryRateChange(long company_id, String variety, String time) {
        return null;
    }

    @Override
    public ArrayList<RefundRateChangeVo> getRefundRateChange(long company_id, String variety, String time) {
        return null;
    }

    @Override
    public ArrayList<SafeInventoryRateVo> getSafeInventoryRate(long company_id) {
        return null;
    }

    @Override
    public ArrayList<InventoryAppraisalVo> getRawMaterialInventoryAppraisal(long company_id1, long company_id2, String time, ArrayList<String> variety) {
        return null;
    }

    @Override
    public ArrayList<InventoryAppraisalVo> getProductInventoryAppraisal(long company_id1, long company_id2, String time, ArrayList<String> variety) {
        return null;
    }
}
