package cn.edu.nju.polaris.vo.Inventory;

/**
 * Created by 费慧通 on 2017/11/13.
 */
public class InventoryChangeVo {
    private String time; //YYYY-MM-DD
    private int inventory; //库存量

    public InventoryChangeVo(String time, int inventory){
        this.time = time;
        this.inventory = inventory;
    }

    public String getTime() {
        return time;
    }

    public int getInventory() {
        return inventory;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }
}
