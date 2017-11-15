package cn.edu.nju.polaris.controller;

import cn.edu.nju.polaris.entity.SupplyChain;
import cn.edu.nju.polaris.service.SupplyChainService;
import cn.edu.nju.polaris.vo.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supplychains")
public class SupplyChainController {

    private final SupplyChainService supplyChainService;

    public SupplyChainController(SupplyChainService supplyChainService) {
        this.supplyChainService = supplyChainService;
    }

    @ApiOperation(value = "获得所有供应链")
    @GetMapping("/all")
    List<SupplyChainVO> getAllSupplyChain(){
        return supplyChainService.findAll();
    }

    @ApiOperation(value = "获得该上游公司所在的所有供应链")
    @ApiImplicitParam(name = "upstreamId",value = "上游企业ID",required = true,dataType = "Long")
    @GetMapping("/up/{upstreamId}")
    List<SupplyChainVO> getAllByUpstream(@PathVariable Long upstreamId){
        return supplyChainService.findChainByUpstreamCompany(upstreamId);
    }

    @ApiOperation(value = "获得该中游公司所在的所有供应链")
    @ApiImplicitParam(name = "midstreamId",value = "中游企业ID",required = true,dataType = "Long")
    @GetMapping("/mid/{midstreamId}")
    List<SupplyChainVO> getAllByMidstream(@PathVariable Long midstreamId){
        return supplyChainService.findChainByMidstreamCompany(midstreamId);
    }

    @ApiOperation(value = "获得该下游公司所在的所有供应链")
    @ApiImplicitParam(name = "downstreamId",value = "下游企业ID",required = true,dataType = "Long")
    @GetMapping("/down/{downstreamId}")
    List<SupplyChainVO> getAllByDownstream(@PathVariable Long downstreamId){
        return supplyChainService.findChainByDownstreamCompany(downstreamId);
    }

    @ApiOperation(value = "供应商设立下游企业")
    @ApiImplicitParam(name = "vo",value = "供应链VO",required = true,dataType = "SupplyChainVO")
    @PostMapping("/up")
    void saveOneByUpstream(@RequestBody SupplyChainVO vo){
        supplyChainService.addOneChainForUpstream(vo);
    }

    @ApiOperation(value = "生产商设立上游企业、下游企业")
    @ApiImplicitParam(name = "vo",value = "供应链VO",required = true,dataType = "SupplyChainVO")
    @PostMapping("/mid")
    void saveOneByMidstream(@RequestBody SupplyChainVO vo){
        supplyChainService.addOneChainForMidStream(vo);
    }


    @ApiOperation(value = "分销商设立上游企业")
    @ApiImplicitParam(name = "vo",value = "供应链VO",required = true,dataType = "SupplyChainVO")
    @PostMapping("/down")
    void saveOneByDownstream(@RequestBody SupplyChainVO vo){
        supplyChainService.addOneChainForDownStream(vo);
    }

    @ApiOperation(value = "应收账款融资中的'应收账款对象'")
    @ApiImplicitParam(name = "cmpanyId",value = "申请融资的公司ID",required = true,dataType = "Long")
    @GetMapping("/financing/receivables/{companyId}")
    List<String> getAccountsReceivableCompanys(@PathVariable Long companyId){
        return supplyChainService.getAccountsreceivableCompanys(companyId);
    }

    @ApiOperation(value = "获得应收帐款净额（明细账的分类汇总结果）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "start",value = "开始时间，格式为YYYY-mm",required = true,dataType = "String"),
            @ApiImplicitParam(name = "end",value = "结束时间，格式为YYYY-mm",required = true,dataType = "String"),
            @ApiImplicitParam(name = "companyId",value = "申请融资的公司id",required = true,dataType = "Long")
    })
    @GetMapping("/financing/receivables/net/{companyId}")
    Double getNetReceivables(@RequestParam String start,
                             @RequestParam String end,
                             @PathVariable Long companyId){
        return supplyChainService.getNetreceivables(start,end,companyId);
    }

    @ApiOperation(value = "获得动产质押融资和保兑仓融资中的 【原材料和产品名称,以及与之相关的公司】")
    @ApiImplicitParam(name = "companyId",value = "申请融资的公司Id",required = true,dataType = "Long")
    @GetMapping("/financing/inventory/{companyId}")
    List<RaworProductAndFromVo> getRawAndProducts(@PathVariable Long companyId){
        return supplyChainService.getRawsandProducts(companyId);
    }

    @ApiOperation(value = "获得库存净额（明细账的分类汇总导入）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "start",value = "开始时间，格式为YYYY-mm",required = true,dataType = "String"),
            @ApiImplicitParam(name = "end",value = "结束时间，格式为YYYY-mm",required = true,dataType = "String"),
            @ApiImplicitParam(name = "companyId",value = "申请融资的公司id",required = true,dataType = "Long")
    })
    @GetMapping("/financing/inventory/net/{companyId}")
    Double getNetInventory(@RequestParam String start,
                           @RequestParam String end,
                           @PathVariable Long companyId){
        return supplyChainService.getNetinventory(start,end,companyId);
    }

    @ApiOperation(value = "申请应收账款融资")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyId",value = "申请融资的公司Id",required = true,dataType = "Long"),
            @ApiImplicitParam(name = "companyName",value = "应收账款对象（下拉框含应收帐款辅助信息部分的公司名称，让企业选择）",required = true,dataType = "String"),
            @ApiImplicitParam(name = "net",value = "应收帐款净额（明细账的分类汇总结果）",required = true,dataType = "Double"),
            @ApiImplicitParam(name = "mortgage",value = "应收帐款抵押额（企业自己输入）",required = true,dataType = "Double")
    })
    @PostMapping("/financing/receivables/{companyId}")
    void applyReceivablesFinancing(@PathVariable Long companyId,
                                   @RequestParam String companyName,
                                   @RequestParam Double net,
                                   @RequestParam Double mortgage){
        supplyChainService.Applyforfinancing_Accountsreceivable(companyId,companyName,net,mortgage);
    }

    @ApiOperation(value = "申请动产质押融资")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyId",value = "申请融资的公司Id",required = true,dataType = "Long"),
            @ApiImplicitParam(name = "type",value = "库存种类（下拉框含原材料和产品名称，让企业选择）",required = true,dataType = "String"),
            @ApiImplicitParam(name = "net",value = "库存净额（明细账的分类汇总导入）",required = true,dataType = "Double"),
            @ApiImplicitParam(name = "pledge",value = "库存质押额（企业自己输入）",required = true,dataType = "Double")
    })
    @PostMapping("/financing/movable/{companyId}")
    void applyMovablePledgeFinancing(@PathVariable Long companyId,
                                     @RequestParam String type,
                                     @RequestParam Double net,
                                     @RequestParam Double pledge){
        supplyChainService.Applyforfinancing_Chattelmortgage(companyId,type,net,pledge);
    }


    @ApiOperation(value = "申请保兑仓融资")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyId",value = "申请融资的公司Id",required = true,dataType = "Long"),
            @ApiImplicitParam(name = "goods",value = "计划购买货物（下拉框含原材料和产品名称，让企业选择）",required = true,dataType = "String"),
            @ApiImplicitParam(name = "from",value = "货物来源（根据原材料或产品名称自动匹配来源方,让企业选择）",required = true,dataType = "String"),
            @ApiImplicitParam(name = "money",value = "拟购货物金额（企业自己输入）",required = true,dataType = "Double"),
            @ApiImplicitParam(name = "rate",value = "保障金比例（企业自己输入）",required = true,dataType = "Double")
    })
    @PostMapping("/financing/storage/{companyId}")
    void applyConfirmingStorageFinancing(@PathVariable Long companyId,
                                         @RequestParam String goods,
                                         @RequestParam String from,
                                         @RequestParam Double money,
                                         @RequestParam Double rate){
        supplyChainService.Applyforfinancing_Confirmingwarehousefinancing(companyId,goods,from,money,rate);
    }

    @ApiOperation(value = "获得所有应收账款融资",notes = "金融机构需求")
    @GetMapping("/financing/type1")
    List<Financing1> getAllFinancing1(){
        return supplyChainService.getfinancings1();
    }

    @ApiOperation(value = "获得所有动产质押融资",notes = "金融机构需求")
    @GetMapping("/financing/type2")
    List<Financing2> getAllFinancing2(){
        return supplyChainService.getfinancings2();
    }

    @ApiOperation(value = "获得所有保兑仓融资",notes = "金融机构需求")
    @GetMapping("/financing/type3")
    List<Financing3> getAllFinancing3(){
        return supplyChainService.getfinancings3();
    }

}
