package cn.edu.nju.polaris.vo.voucher;

import java.util.ArrayList;


public class VoucherVO {
    private long company_id;  //公司编号
    private String voucher_id;  //凭证编号
    private String date;  //凭证日期
    private String remark;  //备注
    private String voucher_maker;   //制单人
    private ArrayList<VoucherItemVo> itemList;  //凭证条目的列表
    private ItemTotalVo totalVo;    //条目总计
    private SupportItemOneVo supportOneVo;  //辅助信息一 不存在则为null
    private SupportItemTwoVo supportItemTwoVo;  //辅助信息二 存不存在则为null

    public VoucherVO(){
        super();
    }

    @Override
    public String toString() {
        return "VoucherVO{" +
                "company_id=" + company_id +
                ", voucher_id='" + voucher_id + '\'' +
                ", date='" + date + '\'' +
                ", remark='" + remark + '\'' +
                ", voucher_maker='" + voucher_maker + '\'' +
                ", itemList=" + itemList +
                ", totalVo=" + totalVo +
                ", supportOneVo=" + supportOneVo +
                ", supportItemTwoVo=" + supportItemTwoVo +
                '}';
    }

    public long getCompany_id() {
        return company_id;
    }

    public void setCompany_id(long company_id) {
        this.company_id=company_id;
    }

    public String getVoucher_id() {
        return voucher_id;
    }

    public void setVoucher_id(String voucher_id) {
        this.voucher_id=voucher_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date=date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark=remark;
    }

    public String getVoucher_maker() {
        return voucher_maker;
    }

    public void setVoucher_maker(String voucher_maker) {
        this.voucher_maker=voucher_maker;
    }

    public ArrayList<VoucherItemVo> getItemList() {
        return itemList;
    }

    public void setItemList(ArrayList<VoucherItemVo> itemList) {
        this.itemList=itemList;
    }

    public ItemTotalVo getTotalVo() {
        return totalVo;
    }

    public void setTotalVo(ItemTotalVo totalVo) {
        this.totalVo=totalVo;
    }

    public SupportItemOneVo getSupportOneVo() {
        return supportOneVo;
    }

    public void setSupportOneVo(SupportItemOneVo supportOneVo) {
        this.supportOneVo=supportOneVo;
    }

    public SupportItemTwoVo getSupportItemTwoVo() {
        return supportItemTwoVo;
    }

    public void setSupportItemTwoVo(SupportItemTwoVo supportItemTwoVo) {
        this.supportItemTwoVo=supportItemTwoVo;
    }




}
