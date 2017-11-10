package cn.edu.nju.polaris.entity.SupplyChainFinancing;

import javax.persistence.*;

/**
 * 应收账款融资
 */
@Entity
@Table(name = "financing_receivables")
public class ReceivablesFinancing {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "company_id")
    private Long companyId; // 申请融资企业id

    @Column(name = "company_name")
    private String companyName; // 应收帐款对象

    @Column(name = "net_amount")
    private Double netAmount; // 应收帐款对象

    @Column(name = "mortgage_amount")
    private Double mortgageAmount; // 应收帐款抵押额

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Double getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(Double netAmount) {
        this.netAmount = netAmount;
    }

    public Double getMortgageAmount() {
        return mortgageAmount;
    }

    public void setMortgageAmount(Double mortgageAmount) {
        this.mortgageAmount = mortgageAmount;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "ReceivablesFinancing{" +
                "id=" + id +
                ", companyId=" + companyId +
                ", companyName='" + companyName + '\'' +
                ", netAmount=" + netAmount +
                ", mortgageAmount=" + mortgageAmount +
                '}';
    }
}
