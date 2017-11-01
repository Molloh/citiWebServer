package cn.edu.nju.polaris.entity.MultiKeysClass;

import cn.edu.nju.polaris.entity.Voucher;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class VoucherMultiKeysClass implements Serializable{

    @Column(name = "company_id")
    private String companyId;

    @Column(name = "voucher_id")
    private String voucherId;

    public VoucherMultiKeysClass() {
    }

    public VoucherMultiKeysClass(String companyId, String voucherId) {
        this.companyId = companyId;
        this.voucherId = voucherId;
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

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((companyId == null) ? 0 : companyId.hashCode());
        result = PRIME * result + ((voucherId == null) ? 0 : voucherId.hashCode());
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


        final VoucherMultiKeysClass other = (VoucherMultiKeysClass)obj;
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
        return true;
    }
}
