package cn.edu.nju.polaris.service.Impl;

import cn.edu.nju.polaris.entity.BalanceSheet;
import cn.edu.nju.polaris.entity.Voucher;
import cn.edu.nju.polaris.repository.BalanceSheetRepository;
import cn.edu.nju.polaris.repository.VoucherRepository;
import cn.edu.nju.polaris.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoucherServiceImpl implements VoucherService{

    private final VoucherRepository voucherRepository;

    @Autowired
    public VoucherServiceImpl(VoucherRepository voucherRepository,BalanceSheetRepository balanceSheetRepository){
        this.voucherRepository = voucherRepository;
    }


    @Override
    public void saveVoucher(Voucher voucher) {
        voucherRepository.save(voucher);
    }
}
