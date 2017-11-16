package cn.edu.nju.polaris.controller;

import cn.edu.nju.polaris.service.InventoryItemService;
import cn.edu.nju.polaris.service.InventoryManagementService;
import cn.edu.nju.polaris.vo.Inventory.*;
import cn.edu.nju.polaris.vo.InventoryItemVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryItemService inventoryItemService;
    private final InventoryManagementService inventoryManagementService;

    public InventoryController(InventoryItemService inventoryItemService, InventoryManagementService inventoryManagementService) {
        this.inventoryItemService = inventoryItemService;
        this.inventoryManagementService = inventoryManagementService;
    }


    @ApiOperation(value = "保存一个产品/原材料")
    @ApiImplicitParam(name = "vo",value = "产品/原材料itemVO",required = true,dataType = "InventoryItemVO")
    @PostMapping("/item")
    void saveOneInventoryItem(@RequestBody InventoryItemVO vo){
        inventoryItemService.saveOneInventoryItem(vo);
    }

    @ApiOperation(value = "删除一个产品/原材料")
    @ApiImplicitParams({
            @ApiImplicitParam
    })
    @DeleteMapping("/item/{name}")
    void deleteOneInventoryItem(@PathVariable String name,
                                @RequestParam String category){
        inventoryItemService.deleteOneInventoryItem(new InventoryItemVO(category,name));
    }

    @ApiOperation(value = "获得所有记录的原材料")
    @GetMapping("/item/material")
    List<InventoryItemVO> findAllMaterial(){
        return inventoryItemService.findAllMaterial();
    }

    @ApiOperation(value = "获得所有记录的产品")
    @GetMapping("/item/product")
    List<InventoryItemVO> findAllProduct(){
        return inventoryItemService.findAllProduct();
    }

    @ApiOperation(value = "得到公司截至某一时间原材料库存监控信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyId",value = "公司ID",required = true,dataType = "Long"),
            @ApiImplicitParam(name = "time",value = "截止时间(YYYY-MM-DD)",required = true,dataType = "String")
    })
    @GetMapping("/stock/material")
    ArrayList<InventoryMonitorItemVo> getRawMaterialInventoryMonitorItem(@RequestParam Long companyId,
                                                                         @RequestParam String time){
        return inventoryManagementService.getRawMaterialInventoryMonitorItem(companyId,time);
    }

    @ApiOperation(value = "得到公司截至某一时间产品库存监控信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyId",value = "公司ID",required = true,dataType = "Long"),
            @ApiImplicitParam(name = "time",value = "截止时间(YYYY-MM-DD)",required = true,dataType = "String")
    })
    @GetMapping("/stock/product")
    ArrayList<InventoryMonitorItemVo> getProductInventoryMonitorItem(@RequestParam Long companyId,
                                                                     @RequestParam String time){
        return inventoryManagementService.getProductInventoryMonitorItem(companyId,time);
    }


    @ApiOperation(value = "原材料/产品库存量与时间的关系")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyId",value = "公司ID",required = true,dataType = "Long"),
            @ApiImplicitParam(name = "variety",value = "种类",required = true,dataType = "String"),
            @ApiImplicitParam(name = "time",value = "截止时间(YYYY-MM-DD)",required = true,dataType = "String")
    })
    @GetMapping("/relation/stock/{variety}")
    ArrayList<InventoryChangeVo> getInventoryChange(@RequestParam Long companyId,
                                                    @PathVariable String variety,
                                                    @RequestParam String time){
        return inventoryManagementService.getInventoryChange(companyId,variety,time);
    }

    @ApiOperation(value = "原材料/产品准时交货率与时间的关系")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyId",value = "公司ID",required = true,dataType = "Long"),
            @ApiImplicitParam(name = "variety",value = "种类",required = true,dataType = "String"),
            @ApiImplicitParam(name = "time",value = "截止时间(YYYY-MM-DD)",required = true,dataType = "String")
    })
    @GetMapping("/relation/delivery/{variety}")
    ArrayList<PunctualDeliveryRateChangeVo> getPunctualDeliveryRateChange(@RequestParam Long companyId,
                                                                          @PathVariable String variety,
                                                                          @RequestParam String time){
        return inventoryManagementService.getPunctualDeliveryRateChange(companyId,variety,time);
    }

    @ApiOperation(value = "原材料/产品退货率与时间的关系")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyId",value = "公司ID",required = true,dataType = "Long"),
            @ApiImplicitParam(name = "variety",value = "种类",required = true,dataType = "String"),
            @ApiImplicitParam(name = "time",value = "截止时间(YYYY-MM-DD)",required = true,dataType = "String")
    })
    @GetMapping("/relation/refund/{variety}")
    ArrayList<RefundRateChangeVo> getRefundRateChange(@RequestParam Long companyId,
                                                      @PathVariable String variety,
                                                      @RequestParam String time){
        return inventoryManagementService.getRefundRateChange(companyId,variety,time);
    }

    @ApiOperation(value = "原材料库存量与安全库存量的关系")
    @ApiImplicitParam(name = "companyId",value = "公司ID",required = true,dataType = "Long")
    @GetMapping("/relation/safe/material")
    ArrayList<SafeInventoryRateVo> getRawMaterialSafeInventoryRate(@RequestParam Long companyId){
        return inventoryManagementService.getRawMaterialSafeInventoryRate(companyId);
    }

    @ApiOperation(value = "产品库存量与安全库存量的关系")
    @ApiImplicitParam(name = "companyId",value = "公司ID",required = true,dataType = "Long")
    @GetMapping("/relation/safe/product")
    ArrayList<SafeInventoryRateVo> getProductSafeInventoryRate(@RequestParam Long companyId){
        return inventoryManagementService.getProductSafeInventoryRate(companyId);
    }
}
