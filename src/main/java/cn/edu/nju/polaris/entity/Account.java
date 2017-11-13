package cn.edu.nju.polaris.entity;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue
    private Long id; // 公司id 自动生成

    @Column(name = "company_name")
    private String companyName; // 公司名称

    @Column(name = "location")
    private String location; // 公司住址

    @Column(name = "scale")
    private String scale;// 公司规模

    @Column(name = "supply_chain_index")
    private String supplyChainIndex; // 公司所处供应链位置

    @Column(name = "active_time")
    private Timestamp activeTime; // 账套启用时间

    @Column(name = "first_industry")
    private String firstIndustry; // 一级行业

    @Column(name = "second_industry")
    private String secondIndustry; // 二级行业

    @Column(name = "email")
    private String email;// 公司联系email 必填！

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSupplyChainIndex() {
        return supplyChainIndex;
    }

    public void setSupplyChainIndex(String supplyChainIndex) {
        this.supplyChainIndex = supplyChainIndex;
    }

    public Timestamp getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(Timestamp activeTime) {
        this.activeTime = activeTime;
    }

    public String getFirstIndustry() {
        return firstIndustry;
    }

    public void setFirstIndustry(String firstIndustry) {
        this.firstIndustry = firstIndustry;
    }

    public String getSecondIndustry() {
        return secondIndustry;
    }

    public void setSecondIndustry(String secondIndustry) {
        this.secondIndustry = secondIndustry;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", location='" + location + '\'' +
                ", scale='" + scale + '\'' +
                ", supplyChainIndex='" + supplyChainIndex + '\'' +
                ", activeTime=" + activeTime +
                ", firstIndustry='" + firstIndustry + '\'' +
                ", secondIndustry='" + secondIndustry + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
