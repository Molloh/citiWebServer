package cn.edu.nju.polaris.vo;

public class SupplyChainVO {

    public String upstreamCompany;

    public String midstreamCompany;

    public String downstreamCompany;

    public SupplyChainVO(String upstreamCompany, String midstreamCompany, String downstreamCompany) {
        this.upstreamCompany = upstreamCompany;
        this.midstreamCompany = midstreamCompany;
        this.downstreamCompany = downstreamCompany;
    }

    public SupplyChainVO() {
    }
}
