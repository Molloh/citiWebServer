package cn.edu.nju.polaris.controller;

import cn.edu.nju.polaris.service.VoucherService;
import cn.edu.nju.polaris.vo.SubjectVO;
import cn.edu.nju.polaris.vo.voucher.VoucherSearchVo;
import cn.edu.nju.polaris.vo.voucher.VoucherVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vouchers")
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService){
        this.voucherService = voucherService;
    }


    @ApiOperation(value = "获得凭证信息",notes = "根据公司id和凭证号获得凭证信息")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "voucherId",name = "凭证字",required = true,dataType = "String"),
            @ApiImplicitParam(value = "companyId",name = "公司id",required = true,dataType = "Long")
    })
    @GetMapping(value = "/{voucherId}")
    public VoucherVO getVoucher(@PathVariable String voucherId,
                                @RequestParam Long companyId){
        return voucherService.getOneVoucher(voucherId,companyId);
    }

    @ApiOperation(value = "根据搜索条件获得凭证信息")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "companyId",name = "公司id",required = true,dataType = "Long"),
            @ApiImplicitParam(value = "startPeriod",name = "会计期间开始时期",required = true,dataType = "String"),
            @ApiImplicitParam(value = "endPeriod",name = "会计期间结束时期",required = true,dataType = "String"),
            @ApiImplicitParam(value = "character",name = "凭证字",required = false,defaultValue = "全部",dataType = "String"),
            @ApiImplicitParam(value = "maker",name = "制单人",required = false,defaultValue = "全部",dataType = "String"),
            @ApiImplicitParam(value = "abstracts",name = "摘要",required = false,defaultValue = "",dataType = "String"),
            @ApiImplicitParam(value = "subjectId",name = "科目id",required = false,defaultValue = "",dataType = "String"),
            @ApiImplicitParam(value = "lowPrice",name = "金额下限",required = false,defaultValue = "-1.0",dataType = "Double"),
            @ApiImplicitParam(value = "highPrice",name = "金额上限",required = false,defaultValue = "-1.0",dataType = "Double"),
            @ApiImplicitParam(value = "lowVoucherNumber",name = "凭证号下限",required = false,defaultValue = "-1",dataType = "Integer"),
            @ApiImplicitParam(value = "highVoucherNumber",name = "凭证号上限",required = false,defaultValue = "-1",dataType = "Integer"),
            @ApiImplicitParam(value = "sortOrder",name = "排序方式",required = false,defaultValue = "0",dataType = "Integer")
    })
    @GetMapping(value = "/search")
    public List<VoucherVO> getSearchedVoucher(@RequestParam Long companyId,
                                              @RequestParam String startPeriod,
                                              @RequestParam String endPeriod,
                                              @RequestParam(required = false,defaultValue = "全部") String character,
                                              @RequestParam(required = false,defaultValue = "全部") String maker,
                                              @RequestParam(required = false,defaultValue = "") String abstracts,
                                              @RequestParam(required = false,defaultValue = "") String subjectsId,
                                              @RequestParam(required = false,defaultValue = "-1.0") Double lowPrice,
                                              @RequestParam(required = false,defaultValue = "-1.0") Double highPrice,
                                              @RequestParam(required = false,defaultValue = "-1") Integer lowVoucherNumber,
                                              @RequestParam(required = false,defaultValue = "-1") Integer highVoucherNumber,
                                              @RequestParam(required = false,defaultValue = "0") Integer sortOrder){
        VoucherSearchVo vo = new VoucherSearchVo();
        vo.setStartPeriod(startPeriod);
        vo.setEndPeriod(endPeriod);
        vo.setCharacter(character);
        vo.setMaker(maker);
        vo.setAbstracts(abstracts);
        vo.setSubjectId(subjectsId);
        vo.setLowPrice(lowPrice);
        vo.setHighPrice(highPrice);
        vo.setLowVoucherNumber(lowVoucherNumber);
        vo.setHighVoucherNumber(highVoucherNumber);
        vo.setSortOrder(sortOrder);
        return voucherService.getSearchedVoucher(vo,companyId);
    }
}
