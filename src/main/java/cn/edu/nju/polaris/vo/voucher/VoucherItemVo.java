package cn.edu.nju.polaris.vo.voucher;


import java.util.List;

/**
 * Created by zhangzy on 2017/11/7 下午3:44
 */
public class VoucherItemVo {

    private long company_id;  //公司编号
    private String voucher_id;  //凭证编号
    private int lines;  //凭证中的行数
    private String abstracts;   //摘要
    private String subjectId;   //会计科目编号
    private double debitAmount;    //借方金额
    private double creditAmount;   //贷方金额
    private List<SupportItemOneVo> supportOneList; //辅助信息一的列表
    private List<SupportItemTwoVo> supportTwoList; //辅助信息二的列表


    public VoucherItemVo(){
        super();
    }

    @Override
    public String toString() {
        return "VoucherItemVo{" +
                "company_id=" + company_id +
                ", voucher_id='" + voucher_id + '\'' +
                ", lines=" + lines +
                ", abstracts='" + abstracts + '\'' +
                ", subjectId='" + subjectId + '\'' +
                ", debitAmount=" + debitAmount +
                ", creditAmount=" + creditAmount +
                ", supportOneList=" + supportOneList +
                ", supportTwoList=" + supportTwoList +
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

    public int getLines() {
        return lines;
    }

    public void setLines(int lines) {
        this.lines=lines;
    }

    public String getAbstracts() {
        return abstracts;
    }

    public void setAbstracts(String abstracts) {
        this.abstracts=abstracts;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId=subjectId;
    }

    public double getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(double debitAmount) {
        this.debitAmount=debitAmount;
    }

    public double getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(double creditAmount) {
        this.creditAmount=creditAmount;
    }

    public List<SupportItemOneVo> getSupportOneList() {
        return supportOneList;
    }

    public void setSupportOneList(List<SupportItemOneVo> supportOneList) {
        this.supportOneList=supportOneList;
    }

    public List<SupportItemTwoVo> getSupportTwoList() {
        return supportTwoList;
    }

    public void setSupportTwoList(List<SupportItemTwoVo> supportTwoList) {
        this.supportTwoList=supportTwoList;
    }




}