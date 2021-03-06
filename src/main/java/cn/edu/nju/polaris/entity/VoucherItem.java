package cn.edu.nju.polaris.entity;

import cn.edu.nju.polaris.entity.MultiKeysClass.VoucherItemMultiKeysClass;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "voucher_item")
@IdClass(VoucherItemMultiKeysClass.class)
public class VoucherItem implements Serializable{

    @Id
    private Long companyId;     //公司id

    @Id
    private String voucherId;     //凭证号

    @Id
    private int line;            //凭证行数

    @Column(name = "abstract")
    private String description;   //摘要

    @Column(name = "subjects")
    private String subjects;      //会计科目

    @Column(name = "debit_amount")
    private double debitAmount;   //借方金额

    @Column(name = "credit_amount")
    private double creditAmount;  //贷方金额

    @ManyToOne(optional = false)
    @JoinColumns({
            @JoinColumn(name = "company_id",referencedColumnName = "company_id",insertable = false,updatable = false),
            @JoinColumn(name = "voucher_id",referencedColumnName = "voucher_id",insertable = false,updatable = false)
    })
    private Voucher voucher;

    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(String voucherId) {
        this.voucherId = voucherId;
    }

    public int getLines() {
        return line;
    }

    public void setLines(int lines) {
        this.line = lines;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }

    public double getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(double debitAmount) {
        this.debitAmount = debitAmount;
    }

    public double getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(double creditAmount) {
        this.creditAmount = creditAmount;
    }

    @Override
    public String toString() {
        return "VoucherItem{" +
                "companyId='" + companyId + '\'' +
                ", voucherId='" + voucherId + '\'' +
                ", lines=" + line +
                ", description='" + description + '\'' +
                ", subjects='" + subjects + '\'' +
                ", debitAmount=" + debitAmount +
                ", creditAmount=" + creditAmount +
                '}';
    }
}
