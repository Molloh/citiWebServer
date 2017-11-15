package cn.edu.nju.polaris.vo;


/**
 * Created by 费慧通 on 2017/8/7.
 *
 * 资产负债表的元组
 */
public class BalanceSheetItemVo {
    private String property_name;   //资产名称
    private int Line_No;    //行次
    private double ending_balance;  //期末余额
    private double beginning_balance;   //年初余额

    public BalanceSheetItemVo(String property_name, int line_No, double ending_balance, double beginning_balance) {
        this.property_name = property_name;
        Line_No = line_No;
        this.ending_balance = ending_balance;
        this.beginning_balance = beginning_balance;
    }

    public String getProperty_name() {
        return property_name;
    }

    public void setProperty_name(String property_name) {
        this.property_name = property_name;
    }

    public int getLine_No() {
        return Line_No;
    }

    public void setLine_No(int line_No) {
        Line_No = line_No;
    }

    public double getEnding_balance() {
        return ending_balance;
    }

    public void setEnding_balance(double ending_balance) {
        this.ending_balance = ending_balance;
    }

    public double getBeginning_balance() {
        return beginning_balance;
    }

    public void setBeginning_balance(double beginning_balance) {
        this.beginning_balance = beginning_balance;
    }
}
