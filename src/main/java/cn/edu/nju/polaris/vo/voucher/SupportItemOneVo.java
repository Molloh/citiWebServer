package cn.edu.nju.polaris.vo.voucher;

/**
 * 输入凭证中的会计科目是“材料采购、在途物资、原材料、库存商品、委托加工物资、工程物资“
 * Created by zhangzy on 2017/11/10 上午8:11
 */
public class SupportItemOneVo {

    private String companyId;   //公司编号
    private String voucherId;   //凭证编号
    private int voucherLines;   //凭证行数
    private int supportLines;   //辅助信息行数
    private String endSide; //收入方/发出方 做成一个下拉框
    private String subjectId;   //科目编号
    private String variety; //种类
    private String date;    //日期
    private Boolean isNew;  //是否属于新产品
    private Boolean isDispatchOnTime;   //是否准时发货
    private Boolean isReturnedPurchase; //是否属于退货
    private int inputNum;   //收入数量
    private double inputUnitPrice;  //收入单价
    private double inputAmount; //收入金额
    private int outputNum;  //发出数量
    private double outputUnitPrice; //发出单价
    private double outputAmount;    //发出金额
    private double endingStocks;    //结存量

    public SupportItemOneVo(){

    }

    @Override
    public String toString() {
        return "SupportItemOneVo{" +
                "companyId='" + companyId + '\'' +
                ", voucherId='" + voucherId + '\'' +
                ", voucherLines=" + voucherLines +
                ", supportLines=" + supportLines +
                ", endSide='" + endSide + '\'' +
                ", subjectId='" + subjectId + '\'' +
                ", variety='" + variety + '\'' +
                ", date='" + date + '\'' +
                ", isNew=" + isNew +
                ", isDispatchOnTime=" + isDispatchOnTime +
                ", isReturnedPurchase=" + isReturnedPurchase +
                ", inputNum=" + inputNum +
                ", inputUnitPrice=" + inputUnitPrice +
                ", inputAmount=" + inputAmount +
                ", outputNum=" + outputNum +
                ", outputUnitPrice=" + outputUnitPrice +
                ", outputAmount=" + outputAmount +
                ", endingStocks=" + endingStocks +
                '}';
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId=companyId;
    }

    public String getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(String voucherId) {
        this.voucherId=voucherId;
    }

    public int getVoucherLines() {
        return voucherLines;
    }

    public void setVoucherLines(int voucherLines) {
        this.voucherLines=voucherLines;
    }

    public int getSupportLines() {
        return supportLines;
    }

    public void setSupportLines(int supportLines) {
        this.supportLines=supportLines;
    }

    public String getEndSide() {
        return endSide;
    }

    public void setEndSide(String endSide) {
        this.endSide=endSide;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId=subjectId;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety=variety;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date=date;
    }

    public Boolean getNew() {
        return isNew;
    }

    public void setNew(Boolean aNew) {
        isNew=aNew;
    }

    public Boolean getDispatchOnTime() {
        return isDispatchOnTime;
    }

    public void setDispatchOnTime(Boolean dispatchOnTime) {
        isDispatchOnTime=dispatchOnTime;
    }

    public Boolean getReturnedPurchase() {
        return isReturnedPurchase;
    }

    public void setReturnedPurchase(Boolean returnedPurchase) {
        isReturnedPurchase=returnedPurchase;
    }

    public int getInputNum() {
        return inputNum;
    }

    public void setInputNum(int inputNum) {
        this.inputNum=inputNum;
    }

    public double getInputUnitPrice() {
        return inputUnitPrice;
    }

    public void setInputUnitPrice(double inputUnitPrice) {
        this.inputUnitPrice=inputUnitPrice;
    }

    public double getInputAmount() {
        return inputAmount;
    }

    public void setInputAmount(double inputAmount) {
        this.inputAmount=inputAmount;
    }

    public int getOutputNum() {
        return outputNum;
    }

    public void setOutputNum(int outputNum) {
        this.outputNum=outputNum;
    }

    public double getOutputUnitPrice() {
        return outputUnitPrice;
    }

    public void setOutputUnitPrice(double outputUnitPrice) {
        this.outputUnitPrice=outputUnitPrice;
    }

    public double getOutputAmount() {
        return outputAmount;
    }

    public void setOutputAmount(double outputAmount) {
        this.outputAmount=outputAmount;
    }

    public double getEndingStocks() {
        return endingStocks;
    }

    public void setEndingStocks(double endingStocks) {
        this.endingStocks=endingStocks;
    }



}
