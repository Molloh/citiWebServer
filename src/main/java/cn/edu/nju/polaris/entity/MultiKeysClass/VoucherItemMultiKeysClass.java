package cn.edu.nju.polaris.entity.MultiKeysClass;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class VoucherItemMultiKeysClass implements Serializable{

    @Column(name = "company_id")
    private String companyId;

    @Column(name = "voucher_id")
    private String voucherId;

    @Column(name = "lines")
    private Integer lines;

    public VoucherItemMultiKeysClass() {
    }

    public VoucherItemMultiKeysClass(String companyId, String voucherId, int lines) {
        this.companyId = companyId;
        this.voucherId = voucherId;
        this.lines = lines;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(String voucherId) {
        this.voucherId = voucherId;
    }

    public int getLines() {
        return lines;
    }

    public void setLines(int lines) {
        this.lines = lines;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((companyId == null) ? 0 : companyId.hashCode());
        result = PRIME * result + ((voucherId == null) ? 0 : voucherId.hashCode());
        result = PRIME * result + ((lines == null) ? 0 : lines.hashCode());
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

        if (lines == null){
            if (other.lines != null){
                return false;
            }
        }else if(!lines.equals(other.lines)){
            return false;
        }
        return true;
    }
}
