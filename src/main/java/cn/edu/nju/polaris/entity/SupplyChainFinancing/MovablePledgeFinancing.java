package cn.edu.nju.polaris.entity.SupplyChainFinancing;

import javax.persistence.*;

/**
 * 动产质押融资
 */
@Entity
@Table(name = "financing_movable_pledge")
public class MovablePledgeFinancing {


    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "company_id")
    private Long companyId; // 申请融资企业id

    @Column(name = "inventory_name")
    private String inventoryName; // 库存种类

    @Column(name = "net_amount")
    private Double netAmount; // 库存净额

    @Column(name = "mortgage_amount")
    private Double mortgageAmount; // 库存质押额

    public MovablePledgeFinancing(long id2, String type, double netinventory, double inventorypledge) {
		this.companyId=id2;
		this.inventoryName=type;
		this.netAmount=netinventory;
		this.mortgageAmount=inventorypledge;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInventoryName() {
        return inventoryName;
    }

    public void setInventoryName(String inventoryName) {
        this.inventoryName = inventoryName;
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
        return "MovablePledgeFinancing{" +
                "id=" + id +
                ", companyId=" + companyId +
                ", inventoryName='" + inventoryName + '\'' +
                ", netAmount=" + netAmount +
                ", mortgageAmount=" + mortgageAmount +
                '}';
    }
}
