package cn.edu.nju.polaris.vo.Inventory;

/**
 * Created by 费慧通 on 2017/11/13.
 */
public class SafeInventoryRateVo {
    private String variety; //种类
    private double inventory; //库存量
    private double safe_inventory; //安全库存量

    public SafeInventoryRateVo(String variety, double inventory, double safe_inventory) {
        this.variety = variety;
        this.inventory = inventory;
        this.safe_inventory = safe_inventory;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public double getInventory() {
        return inventory;
    }

    public void setInventory(double inventory) {
        this.inventory = inventory;
    }

    public double getSafe_inventory() {
        return safe_inventory;
    }

    public void setSafe_inventory(double safe_inventory) {
        this.safe_inventory = safe_inventory;
    }
}
