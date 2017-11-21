package cn.edu.nju.polaris.controller;

import cn.edu.nju.polaris.service.IndustryService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/industry")
public class IndustryController {

    private final IndustryService industryService;


    public IndustryController(IndustryService industryService) {
        this.industryService = industryService;
    }


    @ApiOperation(value = "获得所有一级行业")
    @GetMapping()
    List<String> findAllFirstIndustry(){
        return industryService.findAllFirstIndustry();
    }

    @ApiOperation(value = "获得所有二级行业")
    @ApiImplicitParam(name = "firstIndustry",value = "一级行业名称",required = true,dataType = "String")
    @GetMapping("/{firstIndustry}")
    List<String> findAllSecondIndustry(@PathVariable String firstIndustry){
        return industryService.findAllSecondIndustryByFirstIndustry(firstIndustry);
    }
}
