package cn.edu.nju.polaris.entity.MultiKeysClass;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class SupportItemMultiKeysClass implements Serializable{

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "voucher_id")
    private String voucherId;

    @Column(name = "voucher_item_lines")
    private Integer voucherLines;

    @Column(name = "support_item_lines")
    private Integer supportLines;

    public SupportItemMultiKeysClass() {
    }


    public SupportItemMultiKeysClass(Long companyId, String voucherId, Integer voucherLines, Integer supportLines) {

        this.companyId = companyId;
        this.voucherId = voucherId;
        this.voucherLines = voucherLines;
        this.supportLines = supportLines;
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

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((companyId == null) ? 0 : companyId.hashCode());
        result = PRIME * result + ((voucherId == null) ? 0 : voucherId.hashCode());
        result = PRIME * result + ((voucherLines == null) ? 0 : voucherLines.hashCode());
        result = PRIME * result + ((supportLines == null) ? 0 : supportLines.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if (obj == null){
            return false;
        }
        if (getClass() != obj.getClass()){
            return false;
        }

        final SupportItemMultiKeysClass other = (SupportItemMultiKeysClass)obj;
        if (companyId == null){
            if (other.companyId != null){
                return false;
            }
        }else if(!companyId.equals(other.companyId)){
            return false;
        }

        if (voucherId == null){
            if (other.voucherId != null){
                return false;
            }
        }else if(!voucherId.equals(other.voucherId)){
            return false;
        }

        if (voucherLines == null){
            if (other.voucherLines != null){
                return false;
            }
        }else if(!voucherLines.equals(other.voucherLines)){
            return false;
        }

        if (supportLines == null){
            if (other.supportLines != null){
                return false;
            }
        }else if(!supportLines.equals(other.supportLines)){
            return false;
        }
        return true;
    }
}
