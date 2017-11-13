package cn.edu.nju.polaris.vo.Inventory;

/**
 * Created by 费慧通 on 2017/11/13.
 *
 * 库存监控信息表的元组
 */
public class InventoryMonitorItemVo {
    private String variety;     //种类
    private int inventory;      //库存量
    private int safe_inventory;     //安全库存量
    private String punctual_delivery_rate;      //准时交货率(百分比形式)
    private String refund_rate;     //退货率（百分比形式）

    public InventoryMonitorItemVo(String variety, int inventory, int safe_inventory, String punctual_delivery_rate, String refund_rate) {
        this.variety = variety;
        this.inventory = inventory;
        this.safe_inventory = safe_inventory;
        this.punctual_delivery_rate = punctual_delivery_rate;
        this.refund_rate = refund_rate;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public int getSafe_inventory() {
        return safe_inventory;
    }

    public void setSafe_inventory(int safe_inventory) {
        this.safe_inventory = safe_inventory;
    }

    public String getPunctual_delivery_rate() {
        return punctual_delivery_rate;
    }

    public void setPunctual_delivery_rate(String punctual_delivery_rate) {
        this.punctual_delivery_rate = punctual_delivery_rate;
    }

    public String getRefund_rate() {
        return refund_rate;
    }

    public void setRefund_rate(String refund_rate) {
        this.refund_rate = refund_rate;
    }
}
