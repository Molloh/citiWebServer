package cn.edu.nju.polaris.controller;

import cn.edu.nju.polaris.service.SubjectBalanceService;
import cn.edu.nju.polaris.vo.SubjectBalanceVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/initial")
public class InitialController {


    private final SubjectBalanceService subjectBalanceService;


    public InitialController(SubjectBalanceService subjectBalanceService) {
        this.subjectBalanceService = subjectBalanceService;
    }

    @ApiOperation(value = "期初设置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyId",value = "公司ID",required = true,dataType = "Long"),
            @ApiImplicitParam(name = "phase",value = "期数",required = true,dataType = "String")
    })
    @PostMapping()
    void initialSubjectBalance(@RequestParam Long companyId,
                               @RequestParam String phase){
        subjectBalanceService.initialSubjectBalance(companyId,phase);
    }


    @ApiOperation(value = "保存科目余额")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vo",value = "科目余额VO",required = true,dataType = "SubjectBalanceVO")
    })
    @PostMapping("/new")
    void saveOneSubjectBalance(@RequestBody SubjectBalanceVO vo){
        subjectBalanceService.saveOneSubjectBalance(vo);
    }
}


