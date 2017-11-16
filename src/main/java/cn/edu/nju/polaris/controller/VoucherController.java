package cn.edu.nju.polaris.controller;

import cn.edu.nju.polaris.entity.VoucherItem;
import cn.edu.nju.polaris.service.VoucherService;
import cn.edu.nju.polaris.vo.SubjectVO;
import cn.edu.nju.polaris.vo.voucher.ItemTotalVo;
import cn.edu.nju.polaris.vo.voucher.VoucherSearchVo;
import cn.edu.nju.polaris.vo.voucher.VoucherVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/vouchers")
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService){
        this.voucherService = voucherService;
    }


    @ApiOperation(value = "新增一个凭证")
    @ApiImplicitParam(name = "vo",value = "凭证vo",required = true,dataType = "VoucherVO")
    @PostMapping("")
    public void addOneVoucher(@RequestBody VoucherVO vo){
        voucherService.saveOneVoucher(vo);
    }



    @ApiOperation(value = "获得凭证信息",notes = "根据公司id和凭证号获得凭证信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "voucherId",value = "凭证字",required = true,dataType = "String"),
            @ApiImplicitParam(name = "companyId",value = "公司id",required = true,dataType = "Long")
    })
    @GetMapping(value = "/{voucherId}")
    public VoucherVO getVoucher(@PathVariable String voucherId,
                                @RequestParam Long companyId){
        return voucherService.getOneVoucher(voucherId,companyId);
    }

    @ApiOperation(value = "获得当前凭证所有条目的合计")
    @ApiImplicitParam(name = "itemList",value = "所有凭证条目",required = true,dataType = "ArrayList")
    @PostMapping(value = "/total")
    public ItemTotalVo getVoucherTotal(@RequestBody ArrayList<VoucherItem> itemList){
        return voucherService.getVoucherTotal(itemList);
    }

    @ApiOperation(value = "获得当前月份的会计科目的余额")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "subjectId",value = "科目ID",required = true,dataType = "String"),
            @ApiImplicitParam(name = "companyId",value = "公司ID",required = true,dataType = "Long"),
            @ApiImplicitParam(name = "time",value = "时间（yyyy-mm）",required = true,dataType = "String")
    })
    @GetMapping("/subjects/balance/{subjectId}/{time}")
    public double getOneSubjectBalance(@PathVariable String subjectId,
                                       @RequestParam Long companyId,
                                       @PathVariable String time){
        return voucherService.getOneSubjectBalance(subjectId,companyId,time);
    }

    @ApiOperation(value = "获得当前时期的全部凭证信息")
    @ApiImplicitParam(name = "companyId",value = "公司ID",required = true,dataType = "Long")
    @GetMapping("/current/{companyId}")
    public ArrayList<VoucherVO> getCurrentPeriodVouchers(@PathVariable Long companyId){
        return voucherService.getCurrentPeriodAllVoucher(companyId);
    }

    @ApiOperation(value = "根据搜索条件获得凭证信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyId",value = "公司id",required = true,dataType = "Long"),
            @ApiImplicitParam(name = "startPeriod",value = "会计期间开始时期",required = true,dataType = "String"),
            @ApiImplicitParam(name = "endPeriod",value = "会计期间结束时期",required = true,dataType = "String"),
            @ApiImplicitParam(name = "character",value = "凭证字",required = false,defaultValue = "全部",dataType = "String"),
            @ApiImplicitParam(name = "maker",value = "制单人",required = false,defaultValue = "全部",dataType = "String"),
            @ApiImplicitParam(name = "abstracts",value = "摘要",required = false,defaultValue = "",dataType = "String"),
            @ApiImplicitParam(name = "subjectId",value = "科目id",required = false,defaultValue = "",dataType = "String"),
            @ApiImplicitParam(name = "lowPrice",value = "金额下限",required = false,defaultValue = "-1.0",dataType = "Double"),
            @ApiImplicitParam(name = "highPrice",value = "金额上限",required = false,defaultValue = "-1.0",dataType = "Double"),
            @ApiImplicitParam(name = "lowVoucherNumber",value = "凭证号下限",required = false,defaultValue = "-1",dataType = "Integer"),
            @ApiImplicitParam(name = "highVoucherNumber",value = "凭证号上限",required = false,defaultValue = "-1",dataType = "Integer"),
            @ApiImplicitParam(name = "sortOrder",value = "排序方式",required = false,defaultValue = "0",dataType = "Integer")
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

    @ApiOperation(value = "删除一条凭证")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "voucherId",value = "凭证ID",required = true,dataType = "String"),
            @ApiImplicitParam(name = "companyId",value = "公司ID",required = true,dataType = "Long")
    })
    @DeleteMapping("{voucherId}")
    public void deleteOneVoucher(@PathVariable String voucherId,
                                 @RequestParam Long companyId){
        voucherService.deleteOneVoucherVo(voucherId,companyId);
    }

    @ApiOperation(value = "修改一条凭证信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vo",value = "凭证VO",required = true,dataType = "VoucherVO"),
            @ApiImplicitParam(name = "voucherId",value = "凭证id",required = true,dataType = "String")
    })
    @PutMapping("{voucherId}")
    public void modifyOneVoucher(@RequestBody VoucherVO vo,
                                 @PathVariable String voucherId){
        voucherService.amendOneVoucher(vo,voucherId);
    }

    @ApiOperation(value = "获得当前的辅助信息一种的某种类的结存量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyId",value = "公司ID",required = true,dataType = "Long"),
            @ApiImplicitParam(name = "variety",value = "种类",required = true,dataType = "String")
    })
    @GetMapping("/stock/{variety}")
    public double getCurrentVarietyEndingStock(@RequestParam Long companyId,
                                               @PathVariable String variety){
        return voucherService.getCurrentVarietyEndingStocks(companyId,variety);
    }

    @ApiOperation(value = "根据输入的凭证字获得当前最新的凭证号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "voucherChar",value = "凭证字",required = true,dataType = "String"),
            @ApiImplicitParam(name = "companyId",value = "公司ID",required = true,dataType = "Long")
    })
    @GetMapping("/num/{voucherChar}")
    public int getCurrentNumber(@PathVariable String voucherChar,
                                @RequestParam Long companyId){
        return voucherService.getCurrentNumber(voucherChar,companyId);
    }

    @ApiOperation(value = "获得全部的会计期间")
    @ApiImplicitParam(name = "companyId",value = "公司ID",required = true,dataType = "Long")
    @GetMapping("/period")
    public ArrayList<String> getAllPeriod(@RequestParam Long companyId){
        return voucherService.getAllExistedPeriod(companyId);
    }

    @ApiOperation(value = "获得全部的制单人信息")
    @ApiImplicitParam(name = "companyId",value = "公司ID",required = true,dataType = "Long")
    @GetMapping("/maker")
    public ArrayList<String> getAllMaker(@RequestParam Long companyId){
        return voucherService.getAllExistedMaker(companyId);
    }

    @ApiOperation(value = "获得全部的科目编号")
    @GetMapping("/subjectid")
    public ArrayList<String> getAllSubjectId(){
        return voucherService.getAllSubjectId();
    }
}
