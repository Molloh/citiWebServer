package cn.edu.nju.polaris.entity;

import cn.edu.nju.polaris.entity.MultiKeysClass.VoucherMultiKeysClass;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "voucher")
@IdClass(VoucherMultiKeysClass.class)
public class Voucher implements Serializable{


    @Id
    private Long companyId;       //公司id

    @Id
    private String voucherId;       //凭证号

    @Column(name = "date")
    private Timestamp date;            //日期

    @Column(name = "remark")
    private String remark;          //备注

    @Column(name = "voucher_maker")
    private String voucherMaker;    //制单人


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

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getVoucherMaker() {
        return voucherMaker;
    }

    public void setVoucherMaker(String voucherMaker) {
        this.voucherMaker = voucherMaker;
    }


    @Override
    public String toString() {
        return "Voucher{" +
                "companyId='" + companyId + '\'' +
                ", voucherId='" + voucherId + '\'' +
                ", date=" + date +
                ", remark='" + remark + '\'' +
                ", voucherMaker='" + voucherMaker + '\'' +
                '}';
    }
}
