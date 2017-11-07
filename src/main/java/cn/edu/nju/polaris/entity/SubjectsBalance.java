package cn.edu.nju.polaris.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "subjects_balance")
public class SubjectsBalance {

    @Id
    @GeneratedValue
    private Long id; // id,自增主键

    @Column(name = "company_id")
    private Long companyId; // 公司id

    @Column(name = "subjects_id")
    private String subjectsId; // 会计科目id

    @Column(name = "date")
    private String date; // 期数  yyyy-mm

    @Column(name = "debit_amount")
    private Double debitAmount; // 借方总计余额

    @Column(name = "credit_amount")
    private Double creditAmount; // 贷方总计余额

    @Column(name = "balance")
    private Double balance; // 总余额

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    @Override
    public String toString() {
        return "SubjectsBalance{" +
                "id=" + id +
                ", companyId=" + companyId +
                ", subjectsId='" + subjectsId + '\'' +
                ", date='" + date + '\'' +
                ", debitAmount=" + debitAmount +
                ", creditAmount=" + creditAmount +
                ", balance=" + balance +
                '}';
    }
}
