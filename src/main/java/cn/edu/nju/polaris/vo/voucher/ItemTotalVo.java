package cn.edu.nju.polaris.vo.voucher;

import cn.edu.nju.polaris.entity.VoucherItem;
import cn.edu.nju.polaris.util.NumberToCN;

import java.util.ArrayList;

/**
 * Created by zhangzy on 2017/11/7 下午4:14
 */
public class ItemTotalVo {
    private String chineseTotal;    //总金额的中文大写数字
    private double debitAmount;     //借方金额
    private double creditAmount;    //贷方金额

    public ItemTotalVo(){
        super();
    }

    public ItemTotalVo(ArrayList<VoucherItem> itemList){
        if(itemList.size()!=0){
            double debitNumber=0.0;
            double creditNumber=0.0;

            for(int count=0;count<itemList.size();count++){
                VoucherItem oneItem=itemList.get(count);
                debitNumber=debitNumber+oneItem.getDebitAmount();
                creditNumber=creditNumber+oneItem.getCreditAmount();

            }

            this.chineseTotal=NumberToCN.number2CNMontrayUnit(creditNumber);
            this.debitAmount=debitNumber;
            this.creditAmount=creditNumber;
        }
    }


    @Override
    public String toString() {
        return "ItemTotalVo{" +
                "chineseTotal='" + chineseTotal + '\'' +
                ", debitAmount=" + debitAmount +
                ", creditAmount=" + creditAmount +
                '}';
    }

    public String getChineseTotal() {
        return chineseTotal;
    }

    public void setChineseTotal(String chineseTotal) {
        this.chineseTotal=chineseTotal;
    }

    public double getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(double debitAmount) {
        this.debitAmount=debitAmount;
    }

    public double getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(double creditAmount) {
        this.creditAmount=creditAmount;
    }

}
