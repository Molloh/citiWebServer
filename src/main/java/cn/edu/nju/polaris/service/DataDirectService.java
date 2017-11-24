package cn.edu.nju.polaris.service;

import cn.edu.nju.polaris.vo.Inventory.*;
import cn.edu.nju.polaris.vo.SupplyModuleOne;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public interface DataDirectService {

    double[] getFinancialWarningData(String phase);

    double[] getCashPoolData(String phase);

    ArrayList<InventoryMonitorItemVo> getRawMaterialInventoryMonitorItem(String phase) throws ParseException;

    ArrayList<InventoryMonitorItemVo> getProductInventoryMonitorItem(String phase) throws ParseException;

    ArrayList<SafeInventoryRateVo> getRawMaterialSafeInventoryRate(String phase) throws ParseException;

    ArrayList<SafeInventoryRateVo> getProductSafeInventoryRate(String phase) throws ParseException;

    ArrayList<InventoryChangeVo> getInventoryChange(String variety);

    ArrayList<PunctualDeliveryRateChangeVo> getPunctualDeliveryRateChange(String variety);

    ArrayList<RefundRateChangeVo> getRefundRateChange(String variety);

    List<SupplyModuleOne> getSupplyChain1Data();

    List<double[]> getSupplyChain3Date();

    List<double[]> getSupplyChain2Data1();

    List<double[]> getSupplyChain2Data2();

    List<double[]> getSupplyChain2Data3();
}
