package cn.edu.nju.polaris.vo.voucher;

/**
 * 输入凭证中的会计科目时“应收账款、应付账款“
 * Created by zhangzy on 2017/11/10 上午8:11
 */
public class SupportItemTwoVo {
    private String companyId;   //公司编号
    private String voucherId;   //凭证编号
    private int voucherLines;   //凭证行数
    private int supportLines;   //辅助信息行数
    private String subjectId;   //科目编号
    private String companyName; //公司名称
    private String debitDate;   //借款时间
    private String repayLimit;  //还款期限
    private double amount;  //应收/付账款金额
    private String discountPolicy;  //折扣政策
    private String discountLimit;   //折扣期限
    private String remark;  //备注

    public SupportItemTwoVo(){

    }


    @Override
    public String toString() {
        return "SupportItemTwoVo{" +
                "companyId='" + companyId + '\'' +
                ", voucherId='" + voucherId + '\'' +
                ", voucherLines=" + voucherLines +
                ", supportLines=" + supportLines +
                ", subjectId='" + subjectId + '\'' +
                ", companyName='" + companyName + '\'' +
                ", debitDate='" + debitDate + '\'' +
                ", repayLimit='" + repayLimit + '\'' +
                ", amount=" + amount +
                ", discountPolicy='" + discountPolicy + '\'' +
                ", discountLimit='" + discountLimit + '\'' +
                ", remark='" + remark + '\'' +
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

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId=subjectId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName=companyName;
    }

    public String getDebitDate() {
        return debitDate;
    }

    public void setDebitDate(String debitDate) {
        this.debitDate=debitDate;
    }

    public String getRepayLimit() {
        return repayLimit;
    }

    public void setRepayLimit(String repayLimit) {
        this.repayLimit=repayLimit;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount=amount;
    }

    public String getDiscountPolicy() {
        return discountPolicy;
    }

    public void setDiscountPolicy(String discountPolicy) {
        this.discountPolicy=discountPolicy;
    }

    public String getDiscountLimit() {
        return discountLimit;
    }

    public void setDiscountLimit(String discountLimit) {
        this.discountLimit=discountLimit;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark=remark;
    }
}
