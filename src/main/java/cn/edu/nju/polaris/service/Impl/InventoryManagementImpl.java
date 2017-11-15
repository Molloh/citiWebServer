package cn.edu.nju.polaris.service.Impl;

import cn.edu.nju.polaris.entity.SafeInventory;
import cn.edu.nju.polaris.entity.SupplyChain;
import cn.edu.nju.polaris.entity.SupportItem1;
import cn.edu.nju.polaris.repository.AccountRepository;
import cn.edu.nju.polaris.repository.SafeInventoryRepository;
import cn.edu.nju.polaris.repository.SupportItem1Repository;
import cn.edu.nju.polaris.service.InventoryManagementService;
import cn.edu.nju.polaris.vo.Inventory.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 费慧通 on 2017/11/13.
 */
@Service
public class InventoryManagementImpl implements InventoryManagementService {

    private final SupportItem1Repository supportItem1Repository;
    private final SafeInventoryRepository safeInventoryRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public InventoryManagementImpl(SupportItem1Repository supportItem1Repository, SafeInventoryRepository safeInventoryRepository, AccountRepository accountRepository){
        this.supportItem1Repository = supportItem1Repository;
        this.safeInventoryRepository = safeInventoryRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public ArrayList<InventoryMonitorItemVo> getRawMaterialInventoryMonitorItem(long company_id, String time) {
        List<SupportItem1> list1 = supportItem1Repository.findAllRawMaterial(company_id,time);
        List<SafeInventory> list2 = safeInventoryRepository.findAllMaterialSafeInventory(company_id);
        return getInventoryMonitor(list1,list2);
    }

    @Override
    public ArrayList<InventoryMonitorItemVo> getProductInventoryMonitorItem(long company_id, String time) {
        List<SupportItem1> list1 = supportItem1Repository.findAllProduct(company_id,time);
        List<SafeInventory> list2 = safeInventoryRepository.findAllProductSafeInventory(company_id);
        return getInventoryMonitor(list1,list2);
    }

    @Override
    public ArrayList<InventoryChangeVo> getInventoryChange(long company_id, String variety, String time) {
        Timestamp ts = Timestamp.valueOf(time);
        List<SupportItem1> list = supportItem1Repository.findOneMaterial(company_id,ts,variety);

        ArrayList<InventoryChangeVo> result = new ArrayList<>();

        int first1 = list.get(0).getDate().getYear()+1900;
        int first2 = list.get(0).getDate().getMonth()+1;
        int year = 0;
        int month = 0;
        double inventory = 0;
        for(int i=0;i<list.size();i++){
            SupportItem1 po = list.get(i);
            year = po.getDate().getYear()+1900;
            month = po.getDate().getMonth()+1;
            if(year==first1&&month==first2){
                inventory = po.getEndingStocks();
            }else{
                result.add(new InventoryChangeVo(getDate(year,month), inventory));
                year = po.getDate().getYear()+1900;
                month = po.getDate().getMonth()+1;
                inventory = po.getEndingStocks();
            }
        }
        result.add(new InventoryChangeVo(getDate(year,month), inventory));
        return result;
    }

    @Override
    public ArrayList<PunctualDeliveryRateChangeVo> getPunctualDeliveryRateChange(long company_id, String variety, String time) {
        List<SupportItem1> list = supportItem1Repository.findOneMaterial(company_id,Timestamp.valueOf(time),variety);

        ArrayList<PunctualDeliveryRateChangeVo> result = new ArrayList<>();

        int first1 = list.get(0).getDate().getYear()+1900;
        int first2 = list.get(0).getDate().getMonth()+1;
        int year = 0;
        int month = 0;

        int trade = 0;
        int delivery_on_time = 0;
        DecimalFormat df = new DecimalFormat("0.00");
        for(int i=0;i<list.size();i++){
            SupportItem1 po = list.get(i);
            year = po.getDate().getYear()+1900;
            month = po.getDate().getMonth()+1;
            if(year==first1&&month==first2){
                trade++;
                if(po.getDispatchOntime()){
                    delivery_on_time++;
                }
            }else{
                double punctual_delivery_rate = Double.valueOf(df.format(delivery_on_time/trade));
                result.add(new PunctualDeliveryRateChangeVo(getDate(year,month),punctual_delivery_rate));
                year = po.getDate().getYear()+1900;
                month = po.getDate().getMonth()+1;
                trade = 1;
                delivery_on_time = 0;
                if(po.getDispatchOntime()){
                    delivery_on_time++;
                }
            }
        }
        double punctual_delivery_rate = Double.valueOf(df.format(delivery_on_time/trade));
        result.add(new PunctualDeliveryRateChangeVo(getDate(year,month),punctual_delivery_rate));
        return result;
    }

    @Override
    public ArrayList<RefundRateChangeVo> getRefundRateChange(long company_id, String variety, String time) {
        List<SupportItem1> list = supportItem1Repository.findOneMaterial(company_id,Timestamp.valueOf(time),variety);

        ArrayList<RefundRateChangeVo> result = new ArrayList<>();

        int first1 = list.get(0).getDate().getYear()+1900;
        int first2 = list.get(0).getDate().getMonth()+1;
        int year = 0;
        int month = 0;

        int trade = 0;
        int back = 0;
        DecimalFormat df = new DecimalFormat("0.00");
        for(int i=0;i<list.size();i++){
            SupportItem1 po = list.get(i);
            year = po.getDate().getYear()+1900;
            month = po.getDate().getMonth()+1;
            if(year==first1&&month==first2){
                trade++;
                if(po.getReturnedPurchase()){
                    back++;
                }
            }else{
                double refund_rate = Double.valueOf(df.format(back/trade));
                result.add(new RefundRateChangeVo(getDate(year,month),refund_rate));
                year = po.getDate().getYear()+1900;
                month = po.getDate().getMonth()+1;
                trade = 1;
                back = 0;
                if(po.getDispatchOntime()){
                    back++;
                }
            }
        }
        double refund_rate = Double.valueOf(df.format(back/trade));
        result.add(new RefundRateChangeVo(getDate(year,month),refund_rate));
        return result;
    }

    @Override
    public ArrayList<SafeInventoryRateVo> getRawMaterialSafeInventoryRate(long company_id) {
        List<SupportItem1> list1 = supportItem1Repository.findNewestRawMaterial(company_id);
        List<SafeInventory> list2 = safeInventoryRepository.findAllMaterialSafeInventory(company_id);
        ArrayList<SafeInventoryRateVo> result = new ArrayList<>();
        for(int i=0;i<list1.size();i++){
            SupportItem1 po = list1.get(i);
            String variety = po.getVariety();
            result.add(new SafeInventoryRateVo(variety,po.getEndingStocks(),getSafeInventoryByVariety(list2,variety)));
        }
        return result;
    }

    @Override
    public ArrayList<SafeInventoryRateVo> getProductSafeInventoryRate(long company_id) {
        List<SupportItem1> list1 = supportItem1Repository.findNewestProduct(company_id);
        List<SafeInventory> list2 = safeInventoryRepository.findAllProductSafeInventory(company_id);
        ArrayList<SafeInventoryRateVo> result = new ArrayList<>();
        for(int i=0;i<list1.size();i++){
            SupportItem1 po = list1.get(i);
            String variety = po.getVariety();
            result.add(new SafeInventoryRateVo(variety,po.getEndingStocks(),getSafeInventoryByVariety(list2,variety)));
        }
        return result;
    }

    @Override
    public ArrayList<InventoryAppraisalVo> getRawMaterialInventoryAppraisal(long company_id1, long company_id2, String time, ArrayList<String> variety) {
        String company_name = accountRepository.findById(company_id2).getCompanyName();
        List<SupportItem1> list = supportItem1Repository.findAllByCompanyIdAndEndSideAndDate(company_id1,company_name,time);
        ArrayList<InventoryAppraisalVo> result = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("0.00");

        for(int i=0;i<variety.size();i++){
            ArrayList<SupportItem1> current = getSupportItemByVariety(list,variety.get(i));
            int trade = 0;
            int delivery_on_time = 0;
            int back = 0;
            for(int j=0;j<current.size();j++){
                SupportItem1 temp = current.get(i);
                trade++;
                if(temp.getDispatchOntime()){
                    delivery_on_time++;
                }
                if(temp.getReturnedPurchase()){
                    back++;
                }
            }
            double punctual_delivery_rate = Double.valueOf(df.format(delivery_on_time/trade));
            double refund_rate = Double.valueOf(df.format(back/trade));
            result.add(new InventoryAppraisalVo(variety.get(i),punctual_delivery_rate,refund_rate));
        }
        return result;
    }

    @Override
    public ArrayList<InventoryAppraisalVo> getProductInventoryAppraisal(long company_id1, long company_id2, String time, ArrayList<String> variety) {
        return getRawMaterialInventoryAppraisal(company_id1,company_id2,time,variety);
    }

    @Override
    public double getCurrentInventory(long company_id1, long company_id2, String time) {
        List<SafeInventory> list1 = safeInventoryRepository.findAllMaterialSafeInventory(company_id1);
        return 0;
    }

    private ArrayList<InventoryMonitorItemVo> getInventoryMonitor(List<SupportItem1> list1, List<SafeInventory> list2){
        ArrayList<InventoryMonitorItemVo> result = new ArrayList<>();
        ArrayList<String> varieties = getAllVariety(list1);

        DecimalFormat df = new DecimalFormat("0.00");
        for(int k=0;k<varieties.size();k++){
            String first_raw_material = varieties.get(k);
            int trade = 0;  //交易次数
            int delivery_on_time = 0;  //准时交货次数
            int back = 0;   //退货次数
            double inventory = 0;
            for(int i=0;i<list1.size();i++){
                SupportItem1 po = list1.get(i);
                String variety = po.getVariety();
                if(first_raw_material.equals(variety)){
                    trade++;
                    if(po.getDispatchOntime()){
                        delivery_on_time++;
                    }
                    if(po.getReturnedPurchase()){
                        back++;
                    }
                    inventory = po.getEndingStocks();
                }
            }
            //准时交货率
            String punctual_delivery_rate = df.format(delivery_on_time/trade*100)+"%";
            //退货率
            String refund_rate = df.format(back/trade*100)+"%";
            result.add(new InventoryMonitorItemVo(first_raw_material,inventory,getSafeInventoryByVariety(list2,first_raw_material),punctual_delivery_rate,refund_rate));
        }
        return result;
    }

    /**
     * 根据交易数据得到其中所有的原材料/产品种类
     * @param list
     * @return
     */
    private ArrayList<String> getAllVariety(List<SupportItem1> list){
        ArrayList<String> result = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            String variety = list.get(i).getVariety();
            if(!result.contains(variety)){
                result.add(variety);
            }
        }
        return result;
    }

    /**
     * 得到一个原材料/产品的安全库存量
     * @param list
     * @param variety
     * @return
     */
    private double getSafeInventoryByVariety(List<SafeInventory> list, String variety){
        for(int i=0;i<list.size();i++){
            SafeInventory po = list.get(i);
            if(po.getName().equals(variety)){
                return po.getInventory();
            }
        }
        return 0;
    }

    /**
     * 得到YYYY-MM格式的时间
     * @param year
     * @param month
     * @return
     */
    private String getDate(int year, int month){
        if(month<10){
            return year+"-0"+month;
        }else{
            return year+"-"+month;
        }
    }

    /**
     * 从所有交易中获得一个原材料/产品的交易数据
     * @param list
     * @param variety
     * @return
     */
    private ArrayList<SupportItem1> getSupportItemByVariety(List<SupportItem1> list,String variety){
        ArrayList<SupportItem1> result = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            SupportItem1 current = list.get(i);
            if(current.getVariety().equals(variety)){
                result.add(current);
            }
        }
        return result;
    }
}
