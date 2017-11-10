package cn.edu.nju.polaris.entity;


import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import sun.rmi.runtime.Log;

import javax.persistence.*;

@Entity
@Table(name = "inventory_safe")
public class SafeInventory {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "name")
    private String name; // 原材料/产品名称

    @Column(name = "inventory")
    private Double inventory; //   原材料/产品 库存量

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getInventory() {
        return inventory;
    }

    public void setInventory(Double inventory) {
        this.inventory = inventory;
    }

    @Override
    public String toString() {
        return "SafeInventory{" +
                "id=" + id +
                ", companyId=" + companyId +
                ", name='" + name + '\'' +
                ", inventory=" + inventory +
                '}';
    }
}
