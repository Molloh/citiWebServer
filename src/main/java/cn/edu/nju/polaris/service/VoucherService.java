package cn.edu.nju.polaris.service;

import cn.edu.nju.polaris.entity.Voucher;
import cn.edu.nju.polaris.entity.VoucherItem;

import java.util.ArrayList;

public interface VoucherService {

    void saveVoucher(Voucher voucher);

    //voucher相关的操作
    boolean addVoucher(Voucher voucher);

    boolean deleteOneVoucher(String voucherId,String factoryId);

    boolean deleteOneFactoryAllVoucher(String factoryId);

    //只能修改 个公司id下的凭证  能改成其他公司的id号 然后需要判断修改后的voucherId是否已经被使
    boolean modifyOneVoucher(Voucher voucher,String beforeVoucherId);

    //voucherItem相关的操作
    boolean addOneItem(VoucherItem voucherItem);

    boolean addSeveralItems(ArrayList<VoucherItem> itemList);

    boolean deleteOneVoucherItems(String voucherId,String factoryId);

    boolean deleteOneItem(String voucherId,String factoryId,int line);


    // 更改一个凭证的全部的item
    boolean modifyOneAmountAllItems(String voucherId,String factoryId,ArrayList<VoucherItem> itemList);





}
