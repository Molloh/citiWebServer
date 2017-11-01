package cn.edu.nju.polaris.service.Impl;

import cn.edu.nju.polaris.repository.VoucherRepository;
import cn.edu.nju.polaris.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoucherServiceImpl implements VoucherService{

    private final VoucherRepository voucherRepository;

    @Autowired
    public VoucherServiceImpl(VoucherRepository voucherRepository){
        this.voucherRepository = voucherRepository;
    }
}
