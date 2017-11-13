package cn.edu.nju.polaris.vo.Inventory;

/**
 * Created by 费慧通 on 2017/11/13.
 */
public class SafeInventoryRateVo {
    private String variety; //种类
    private int inventory; //库存量
    private int safe_inventory; //安全库存量

    public SafeInventoryRateVo(String variety, int inventory, int safe_inventory){
        this.variety = variety;
        this.inventory = inventory;
        this.safe_inventory = safe_inventory;
    }

    public String getVariety() {
        return variety;
    }

    public int getInventory() {
        return inventory;
    }

    public int getSafe_inventory() {
        return safe_inventory;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public void setSafe_inventory(int safe_inventory) {
        this.safe_inventory = safe_inventory;
    }
}
