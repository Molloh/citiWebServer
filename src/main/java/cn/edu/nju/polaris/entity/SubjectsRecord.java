package cn.edu.nju.polaris.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "subjects_record")
public class SubjectsRecord {

    @Id
    @GeneratedValue
    private Long id; // id,自增主键

    @Column(name = "company_id")
    private Long companyId; // 公司id

    @Column(name = "voucher_id")
    private String voucherId; // 凭证id

    @Column(name = "subjects_id")
    private String subjectsId; // 会计科目id

    @Column(name = "date")
    private Timestamp date; // 记录时间

    @Column(name = "debit_amount")
    private Double debitAmount; // 借方金额

    @Column(name = "credit_amount")
    private Double creditAmount; // 贷方金额

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

    public String getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(String voucherId) {
        this.voucherId = voucherId;
    }

    public String getSubjectsId() {
        return subjectsId;
    }

    public void setSubjectsId(String subjectsId) {
        this.subjectsId = subjectsId;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
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
        return "SubjectsRecord{" +
                "id=" + id +
                ", companyId=" + companyId +
                ", voucherId='" + voucherId + '\'' +
                ", subjectsId='" + subjectsId + '\'' +
                ", date=" + date +
                ", debitAmount=" + debitAmount +
                ", creditAmount=" + creditAmount +
                '}';
    }
}
