package cn.edu.nju.polaris.controller;

import cn.edu.nju.polaris.service.VoucherService;
import cn.edu.nju.polaris.vo.SubjectVO;
import cn.edu.nju.polaris.vo.voucher.VoucherSearchVo;
import cn.edu.nju.polaris.vo.voucher.VoucherVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vouchers")
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService){
        this.voucherService = voucherService;
    }


    @GetMapping(value = "/{voucherId}")
    public VoucherVO getVoucher(@PathVariable String voucherId,
                                @RequestParam Long companyId){
        return voucherService.getOneVoucher(voucherId,companyId);
    }

    @PutMapping(value = "/search")
    public List<VoucherVO> getSearchedVoucher(@RequestBody VoucherSearchVo vo,
                                              @RequestParam Long companyId){
        return voucherService.getSearchedVoucher(vo,companyId);
    }
}
