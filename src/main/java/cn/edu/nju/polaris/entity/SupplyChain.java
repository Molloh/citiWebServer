package cn.edu.nju.polaris.entity;

import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Table(name = "supply_chain")
public class SupplyChain {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "upper_name")
    private String upstreamCompany;

    @Column(name = "middle_name")
    private String midstreamCompany;

    @Column(name = "down_name")
    private String downstreamCompany;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUpstreamCompany() {
        return upstreamCompany;
    }

    public void setUpstreamCompany(String upstreamCompany) {
        this.upstreamCompany = upstreamCompany;
    }

    public String getMidstreamCompany() {
        return midstreamCompany;
    }

    public void setMidstreamCompany(String midstreamCompany) {
        this.midstreamCompany = midstreamCompany;
    }

    public String getDownstreamCompany() {
        return downstreamCompany;
    }

    public void setDownstreamCompany(String downstreamCompany) {
        this.downstreamCompany = downstreamCompany;
    }


    @Override
    public String toString() {
        return "SupplyChain{" +
                "id=" + id +
                ", upstreamCompany='" + upstreamCompany + '\'' +
                ", midstreamCompany='" + midstreamCompany + '\'' +
                ", downstreamCompany='" + downstreamCompany + '\'' +
                '}';
    }
}
