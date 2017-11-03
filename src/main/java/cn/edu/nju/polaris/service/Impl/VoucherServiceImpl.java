package cn.edu.nju.polaris.service.Impl;

import cn.edu.nju.polaris.entity.BalanceSheet;
import cn.edu.nju.polaris.entity.Voucher;
import cn.edu.nju.polaris.entity.VoucherItem;
import cn.edu.nju.polaris.repository.BalanceSheetRepository;
import cn.edu.nju.polaris.repository.VoucherRepository;
import cn.edu.nju.polaris.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class VoucherServiceImpl implements VoucherService{

    private final VoucherRepository voucherRepository;

    @Autowired
    public VoucherServiceImpl(VoucherRepository voucherRepository){
        this.voucherRepository = voucherRepository;
    }


    @Override
    public void saveVoucher(Voucher voucher) {
        voucherRepository.save(voucher);
    }

    @Override
    public boolean addVoucher(Voucher voucher) {
        return false;
    }

    @Override
    public boolean deleteOneVoucher(String voucherId, String factoryId) {
        return false;
    }

    @Override
    public boolean deleteOneFactoryAllVoucher(String factoryId) {
        return false;
    }

    @Override
    public boolean modifyOneVoucher(Voucher voucher, String beforeVoucherId) {
        return false;
    }

    @Override
    public boolean addOneItem(VoucherItem voucherItem) {
        return false;
    }

    @Override
    public boolean addSeveralItems(ArrayList<VoucherItem> itemList) {
        return false;
    }

    @Override
    public boolean deleteOneVoucherItems(String voucherId, String factoryId) {
        return false;
    }

    @Override
    public boolean deleteOneItem(String voucherId, String factoryId, int line) {
        return false;
    }

    @Override
    public boolean modifyOneAmountAllItems(String voucherId, String factoryId, ArrayList<VoucherItem> itemList) {
        return false;
    }
}
