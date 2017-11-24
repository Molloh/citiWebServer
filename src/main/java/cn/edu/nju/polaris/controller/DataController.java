package cn.edu.nju.polaris.controller;

import cn.edu.nju.polaris.service.DataDirectService;
import cn.edu.nju.polaris.vo.Inventory.*;
import cn.edu.nju.polaris.vo.SupplyModuleOne;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/data")
public class DataController {

    private final DataDirectService dataDirectService;

    public DataController(DataDirectService dataDirectService) {
        this.dataDirectService = dataDirectService;
    }

    /**
     * 财务预警数据
     * @param phase yyyy-mm
     * @return
     */
    @GetMapping("/warning")
    public double[] getFinancialWarningData(@RequestParam String phase){
        return dataDirectService.getFinancialWarningData(phase);
    }

    /**
     * 现金池
     * @param phase
     * @return
     */
    @GetMapping("cashpool")
    public double[] getCashPoolData(@RequestParam String phase){
        return dataDirectService.getCashPoolData(phase);
    }


    /**
     * 得到公司截至某一时间原材料库存监控信息
     * @return
     */
    @GetMapping("/stock/material")
    public ArrayList<InventoryMonitorItemVo> getRawMaterialInventoryMonitorItem(@RequestParam String phase) throws ParseException {
        return dataDirectService.getRawMaterialInventoryMonitorItem(phase);
    }

    /**
     * 得到公司截至某一时间产品库存监控信息
     * @return
     */
    @GetMapping("/stock/product")
    public ArrayList<InventoryMonitorItemVo> getProductInventoryMonitorItem(@RequestParam String phase) throws ParseException {
        return dataDirectService.getProductInventoryMonitorItem(phase);
    }

    /**
     * 原材料库存量与安全库存量的关系
     * @return
     */
    @GetMapping("/relation/safe/material")
    ArrayList<SafeInventoryRateVo> getRawMaterialSafeInventoryRate(@RequestParam String phase) throws ParseException {
        return dataDirectService.getRawMaterialSafeInventoryRate(phase);
    }

    /**
     * 产品库存量与安全库存量的关系
     * @return
     */
    @GetMapping("/relation/safe/product")
    ArrayList<SafeInventoryRateVo> getProductSafeInventoryRate(@RequestParam String phase) throws ParseException {
        return dataDirectService.getProductSafeInventoryRate(phase);
    }

    @GetMapping("/relation/stock")
    /**
     * 原材料/产品库存量与时间的关系
     */
    ArrayList<InventoryChangeVo> getInventoryChange(@RequestParam String variety){
        return dataDirectService.getInventoryChange(variety);
    }

    @GetMapping("/relation/delivery")
    /**
     * 原材料/产品准时交货率与时间的关系
     */
    ArrayList<PunctualDeliveryRateChangeVo> getPunctualDeliveryRateChange(@RequestParam String variety){
        return dataDirectService.getPunctualDeliveryRateChange(variety);
    }


    @GetMapping("/relation/refund")
    /**
     * 原材料/产品退货率与时间的关系
     */
    ArrayList<RefundRateChangeVo> getRefundRateChange(@RequestParam String variety){
        return dataDirectService.getRefundRateChange(variety);
    }


    @GetMapping("/evaluation/module1")
    /**
     * 供应链绩评价模块一
     */
    List<SupplyModuleOne> getSupplyChain1Data(){
        return dataDirectService.getSupplyChain1Data();
    }

    /**
     * 准时交货率
     * 供应链绩评价模块二
     * @return
     */
    @GetMapping("/evaluation/module2/data1")
    List<double[]> getSupplyChain2Data1(){
        return dataDirectService.getSupplyChain2Data1();
    }

    /**
     * 成本利润率
     * 供应链绩评价模块二
     * @return
     */
    @GetMapping("/evaluation/module2/data2")
    List<double[]> getSupplyChain2Data2(){
        return dataDirectService.getSupplyChain2Data2();
    }

    /**
     * 产需率
     * 供应链绩评价模块二
     * @return
     */
    @GetMapping("/evaluation/module2/data3")
    List<double[]> getSupplyChainData3(){
        return dataDirectService.getSupplyChain2Data3();
    }


    @GetMapping("/evaluation/module3")
    List<double[]> getSupplyChain3Date(){
        return dataDirectService.getSupplyChain3Date();
    }

}
