package cn.edu.nju.polaris.controller;

import cn.edu.nju.polaris.entity.SupplyChain;
import cn.edu.nju.polaris.service.SupplyChainService;
import cn.edu.nju.polaris.vo.SupplyChainVO;
import io.swagger.annotations.ApiImplicitParam;
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
}
