package cn.edu.nju.polaris.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "subjects_initial")
public class SubjectInitial {

    @Id
    @GeneratedValue
    private Long id; // id,自增主键

    @Column(name = "company_id")
    private Long companyId; // 公司id

    @Column(name = "subjects_id")
    private String subjectsId; // 会计科目id

    @Column(name = "balance")
    private Double balance; // 期初余额

    @Column(name = "debit_amount")
    private Double debitAmount; // 借方金额

    @Column(name = "credit_amount")
    private Double creditAmount; // 贷方金额

    @Column(name = "year_balance")
    private Double yearBalance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }


    public String getSubjectsId() {
        return subjectsId;
    }

    public void setSubjectsId(String subjectsId) {
        this.subjectsId = subjectsId;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(Double debitAmount) {
        this.debitAmount = debitAmount;
    }

    public Double getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(Double creditAmount) {
        this.creditAmount = creditAmount;
    }

    public Double getYearBalance() {
        return yearBalance;
    }

    public void setYearBalance(Double yearBalance) {
        this.yearBalance = yearBalance;
    }

    @Override
    public String toString() {
        return "SubjectInitial{" +
                "id=" + id +
                ", companyId=" + companyId +
                ", subjectsId='" + subjectsId + '\'' +
                ", balance=" + balance +
                ", debitAmount=" + debitAmount +
                ", creditAmount=" + creditAmount +
                ", yearBalance=" + yearBalance +
                '}';
    }
}
