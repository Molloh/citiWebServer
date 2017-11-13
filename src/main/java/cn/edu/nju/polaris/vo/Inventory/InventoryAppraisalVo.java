package cn.edu.nju.polaris.vo.Inventory;

/**
 * Created by 费慧通 on 2017/11/13.
 */
public class InventoryAppraisalVo {
    private String inventory; //种类
    private double punctual_delivery_rate;      //准时交货率
    private double refund_rate;     //退货率

    public InventoryAppraisalVo(String inventory, double punctual_delivery_rate, double refund_rate) {
        this.inventory = inventory;
        this.punctual_delivery_rate = punctual_delivery_rate;
        this.refund_rate = refund_rate;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public double getPunctual_delivery_rate() {
        return punctual_delivery_rate;
    }

    public void setPunctual_delivery_rate(double punctual_delivery_rate) {
        this.punctual_delivery_rate = punctual_delivery_rate;
    }

    public double getRefund_rate() {
        return refund_rate;
    }

    public void setRefund_rate(double refund_rate) {
        this.refund_rate = refund_rate;
    }
}
