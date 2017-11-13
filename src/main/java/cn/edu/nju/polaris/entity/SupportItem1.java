package cn.edu.nju.polaris.entity;

import cn.edu.nju.polaris.entity.MultiKeysClass.SupportItemMultiKeysClass;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * 当输入凭证中的会计科目是“材料采购、在途物资、原材料、库存商品、委托加工物资、工程物资“时
 * 的辅助信息条目
 */
@Entity
@Table(name = "support_item1")
@IdClass(SupportItemMultiKeysClass.class)
public class    SupportItem1 implements Serializable{

    @Id
    private Long companyId; //公司id

    @Id
    private String voucherId; // 凭证号

    @Id
    private Integer voucherLines; // 凭证行数

    @Id
    private Integer supportLines; // 辅助信息行数

    @Column(name = "end_side")
    private String EndSide; // 收入/发出方

    @Column(name = "subjects")
    private String subjects; // 会计科目

    @Column(name = "variety")
    private String variety; // 种类

    @Column(name = "date")
    private Timestamp date; // 时间

    @Column(name = "is_new")
    private Boolean isNew; // 是否属于新产品

    @Column(name = "is_dispatch_ontime")
    private Boolean isDispatchOntime; // 是否准时发货

    @Column(name = "is_returned_purchase")
    private Boolean isReturnedPurchase; // 是否属于退货

    @Column(name = "input_num")
    private Integer inputNum;  // 收入数量

    @Column(name = "input_unit_price")
    private Double inputUnitPrice; // 收入单价

    @Column(name = "input_amount")
    private Double inputAmount; // 收入金额

    @Column(name = "output_num")
    private Integer outputNum; // 发出数量

    @Column(name = "output_unit_price")
    private Double outputUnitPrice; // 发出单价

    @Column(name = "output_amount")
    private Double outputAmount; // 发出金额

    @Column(name = "ending_stocks")
    private Double endingStocks; // 结存量

    public String getEndSide() {
        return EndSide;
    }

    public void setEndSide(String endSide) {
        EndSide = endSide;
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

    public Integer getVoucherLines() {
        return voucherLines;
    }

    public void setVoucherLines(Integer voucherLines) {
        this.voucherLines = voucherLines;
    }

    public Integer getSupportLines() {
        return supportLines;
    }

    public void setSupportLines(Integer supportLines) {
        this.supportLines = supportLines;
    }

    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Boolean getNew() {
        return isNew;
    }

    public void setNew(Boolean aNew) {
        isNew = aNew;
    }

    public Boolean getDispatchOntime() {
        return isDispatchOntime;
    }

    public void setDispatchOntime(Boolean dispatchOntime) {
        isDispatchOntime = dispatchOntime;
    }

    public Boolean getReturnedPurchase() {
        return isReturnedPurchase;
    }

    public void setReturnedPurchase(Boolean returnedPurchase) {
        isReturnedPurchase = returnedPurchase;
    }

    public Integer getInputNum() {
        return inputNum;
    }

    public void setInputNum(Integer inputNum) {
        this.inputNum = inputNum;
    }

    public Double getInputUnitPrice() {
        return inputUnitPrice;
    }

    public void setInputUnitPrice(Double inputUnitPrice) {
        this.inputUnitPrice = inputUnitPrice;
    }

    public Double getInputAmount() {
        return inputAmount;
    }

    public void setInputAmount(Double inputAmount) {
        this.inputAmount = inputAmount;
    }

    public Integer getOutputNum() {
        return outputNum;
    }

    public void setOutputNum(Integer outputNum) {
        this.outputNum = outputNum;
    }

    public Double getOutputUnitPrice() {
        return outputUnitPrice;
    }

    public void setOutputUnitPrice(Double outputUnitPrice) {
        this.outputUnitPrice = outputUnitPrice;
    }

    public Double getOutputAmount() {
        return outputAmount;
    }

    public void setOutputAmount(Double outputAmount) {
        this.outputAmount = outputAmount;
    }

    public Double getEndingStocks() {
        return endingStocks;
    }

    public void setEndingStocks(Double endingStocks) {
        this.endingStocks = endingStocks;
    }

    @Override
    public String toString() {
        return "SupportItem1{" +
                "companyId='" + companyId + '\'' +
                ", voucherId='" + voucherId + '\'' +
                ", voucherLines=" + voucherLines +
                ", supportLines=" + supportLines +
                ", EndSide='" + EndSide + '\'' +
                ", subjects='" + subjects + '\'' +
                ", variety='" + variety + '\'' +
                ", date=" + date +
                ", isNew=" + isNew +
                ", isDispatchOntime=" + isDispatchOntime +
                ", isReturnedPurchase=" + isReturnedPurchase +
                ", inputNum=" + inputNum +
                ", inputUnitPrice=" + inputUnitPrice +
                ", inputAmount=" + inputAmount +
                ", outputNum=" + outputNum +
                ", outputUnitPrice=" + outputUnitPrice +
                ", outputAmount=" + outputAmount +
                ", endingStocks=" + endingStocks +
                '}';
    }
}
