package cn.edu.nju.polaris.entity.MultiKeysClass;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class VoucherItemMultiKeysClass implements Serializable{

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "voucher_id")
    private String voucherId;

    @Column(name = "line")
    private Integer line;

    public VoucherItemMultiKeysClass() {
    }

    public VoucherItemMultiKeysClass(Long companyId, String voucherId, Integer line) {
        this.companyId = companyId;
        this.voucherId = voucherId;
        this.line = line;
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

    public int getLines() {
        return line;
    }

    public void setLines(int line) {
        this.line = line;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((companyId == null) ? 0 : companyId.hashCode());
        result = PRIME * result + ((voucherId == null) ? 0 : voucherId.hashCode());
        result = PRIME * result + ((line == null) ? 0 : line.hashCode());
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

        final VoucherItemMultiKeysClass other = (VoucherItemMultiKeysClass)obj;
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

        if (line == null){
            if (other.line != null){
                return false;
            }
        }else if(!line.equals(other.line)){
            return false;
        }
        return true;
    }
}
