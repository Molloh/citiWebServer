package cn.edu.nju.polaris.controller;

import cn.edu.nju.polaris.service.VoucherService;
import cn.edu.nju.polaris.vo.voucher.VoucherVO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vouchers")
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService){
        this.voucherService = voucherService;
    }

}
