package cn.edu.nju.polaris.entity;

import cn.edu.nju.polaris.entity.MultiKeysClass.SupportItemMultiKeysClass;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * 当输入凭证中的会计科目时“应收账款、应付账款“时
 * 的辅助信息条目
 */
@Entity
@Table(name = "support_item2")
@IdClass(SupportItemMultiKeysClass.class)
public class SupportItem2 implements Serializable{

    @Id
    private String companyId; // 公司id

    @Id
    private String voucherId; // 凭证id

    @Id
    private Integer voucherLines; // 凭证行数

    @Id
    private Integer supportLines; // 辅助信息行数

    @Column(name = "subjects")
    private String subjects; // 会计科目

    @Column(name = "company_name")
    private String companyName; // 公司名称

    @Column(name = "debit_date")
    private Timestamp debitDate; // 借款时间

    @Column(name = "repayment_ddl")
    private Timestamp repaymentDDL; // 还款期限

    @Column(name = "amount")
    private Double amount; // 应收/付账款金额 (根据会计科目是'应收账款'还是'应付账款'来定)

    @Column(name = "discount_policy")
    private String discountPolicy; // 折扣政策

    @Column(name = "discount_ddl")
    private Timestamp discountDDL; // 折扣期限

    @Column(name = "remark")
    private String remark; // 备注

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(String voucherId) {
        this.voucherId = voucherId;
    }

    public Integer getVoucherLines() {
        return voucherLines;
    }

    public void setVoucherLines(Integer voucherLines) {
        this.voucherLines = voucherLines;
    }

    public Integer getSupportLines() {
        return supportLines;
    }

    public void setSupportLines(Integer supportLines) {
        this.supportLines = supportLines;
    }

    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDiscountPolicy() {
        return discountPolicy;
    }

    public void setDiscountPolicy(String discountPolicy) {
        this.discountPolicy = discountPolicy;
    }

    public Timestamp getDebitDate() {
        return debitDate;
    }

    public void setDebitDate(Timestamp debitDate) {
        this.debitDate = debitDate;
    }

    public Timestamp getRepaymentDDL() {
        return repaymentDDL;
    }

    public void setRepaymentDDL(Timestamp repaymentDDL) {
        this.repaymentDDL = repaymentDDL;
    }

    public Timestamp getDiscountDDL() {
        return discountDDL;
    }

    public void setDiscountDDL(Timestamp discountDDL) {
        this.discountDDL = discountDDL;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    @Override
    public String toString() {
        return "SupportItem2{" +
                "companyId='" + companyId + '\'' +
                ", voucherId='" + voucherId + '\'' +
                ", voucherLines=" + voucherLines +
                ", supportLines=" + supportLines +
                ", subjects='" + subjects + '\'' +
                ", companyName='" + companyName + '\'' +
                ", debitDate=" + debitDate +
                ", repaymentDDL=" + repaymentDDL +
                ", amount=" + amount +
                ", discountPolicy='" + discountPolicy + '\'' +
                ", discountDDL=" + discountDDL +
                ", remark='" + remark + '\'' +
                '}';
    }
}
