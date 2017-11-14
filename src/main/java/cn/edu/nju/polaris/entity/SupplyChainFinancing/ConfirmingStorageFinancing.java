package cn.edu.nju.polaris.entity.SupplyChainFinancing;

import javax.persistence.*;

/**
 * 保兑仓融资
 */
@Entity
@Table(name = "financing_confirm_storage")
public class ConfirmingStorageFinancing {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "company_id")
    private Long companyId; // 申请融资企业id

    private String cargo; //计划购买货物

    private String origin;  // 货物来源

    private Double amount; // 拟购货物金额

    private Double proportion; // 保障金比例


    public ConfirmingStorageFinancing(long id2, String goods, String from, double money, double rate) {
		this.companyId=id2;
		this.cargo=goods;
		this.origin=from;
		this.amount=money;
		this.proportion=rate;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getProportion() {
        return proportion;
    }

    public void setProportion(Double proportion) {
        this.proportion = proportion;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "ConfirmingStorageFinancing{" +
                "id=" + id +
                ", companyId=" + companyId +
                ", cargo='" + cargo + '\'' +
                ", origin='" + origin + '\'' +
                ", amount=" + amount +
                ", proportion=" + proportion +
                '}';
    }
}
