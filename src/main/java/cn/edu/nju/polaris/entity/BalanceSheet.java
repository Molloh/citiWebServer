package cn.edu.nju.polaris.entity;

import org.springframework.stereotype.Controller;

import javax.persistence.*;

/**
 * 资产负债表的实体类，做到一期一存
 */
@Entity
@Table(name = "sheet_balance")
public class BalanceSheet {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_id")
    private String companyId;  // 名称

    @Column(name = "period")
    private String period; // 期数 格式为 yyyy-mm

    @Column(name = "name")
    private String name; // 名称

    @Column(name = "balance")
    private Double balance; // 余额

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "BalanceSheet{" +
                "id=" + id +
                ", companyId='" + companyId + '\'' +
                ", period='" + period + '\'' +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }

    public BalanceSheet(String companyId, String period, String name, Double balance) {
        this.companyId = companyId;
        this.period = period;
        this.name = name;
        this.balance = balance;
    }
}
