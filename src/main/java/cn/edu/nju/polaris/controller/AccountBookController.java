package cn.edu.nju.polaris.controller;

import cn.edu.nju.polaris.service.AccountBooksBlService;
import cn.edu.nju.polaris.vo.accountBook.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/accountbook")
public class AccountBookController {

    private final AccountBooksBlService accountBooksService;


    public AccountBookController(AccountBooksBlService accountBooksService) {
        this.accountBooksService = accountBooksService;
    }

    /*** 明细账 ***/
    @ApiOperation(value = "获得全部有数据记录的科目编号")
    @ApiImplicitParam(name = "companyId",value = "公司ID",required = true,dataType = "Long")
    @GetMapping("/details/subjects")
    ArrayList<String> getAllExistedSubjectId(@RequestParam Long companyId){
        return accountBooksService.getAllExistedSubjectId(companyId);
    }

    @ApiOperation(value = "获得一个科目的明细账")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "subjectId",value = "科目ID",required = true,dataType = "String"),
            @ApiImplicitParam(name = "companyId",value = "公司ID",required = true,dataType = "Long"),
            @ApiImplicitParam(name = "startPeriod",value = "开始的会计期间",required = true,dataType = "String"),
            @ApiImplicitParam(name = "endPeriod",value = "结束的会计期间",required = true,dataType = "String"),
    })
    @GetMapping("/details/{subjectId}")
    DetailAccountVo getOneSubjectDetail(@PathVariable String subjectId,
                                        @RequestParam Long companyId,
                                        @RequestParam String startPeriod,
                                        @RequestParam String endPeriod){
        return accountBooksService.getOneSubjectDetail(subjectId,new BookSearchVo(startPeriod,endPeriod,null,null,0,0),companyId);
    }


    /*** 总账 ***/
    @ApiOperation(value = "获得全部科目的总账")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyId",value = "公司ID",required = true,dataType = "Long"),
            @ApiImplicitParam(name = "startPeriod",value = "开始的会计期间",required = true,dataType = "String"),
            @ApiImplicitParam(name = "endPeriod",value = "结束的会计期间",required = true,dataType = "String"),
            @ApiImplicitParam(name = "startSubjectId",value = "起始科目",required = true,dataType = "String"),
            @ApiImplicitParam(name = "endSubjectId",value = "结束科目",required = true,dataType = "String"),
            @ApiImplicitParam(name = "lowLevel",value = "小的科目级别",required = true,dataType = "int"),
            @ApiImplicitParam(name = "highLevel",value = "大的科目级别",required = true,dataType = "int")
    })
    @GetMapping("/total")
    ArrayList<TotalAccountVo> getAllSubjectTotal(@RequestParam Long companyId,
                                                 @RequestParam String startPeriod,
                                                 @RequestParam String endPeriod,
                                                 @RequestParam String startSubjectId,
                                                 @RequestParam String endSubjectId,
                                                 @RequestParam int lowLevel,
                                                 @RequestParam int highLevel){
        return accountBooksService.getAllSubjectTotal(new BookSearchVo(startPeriod,endPeriod,startSubjectId,endSubjectId,lowLevel,highLevel),companyId);
    }

    @ApiOperation(value = "获得单个科目的总账")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "subjectId",value = "科目ID",required = true,dataType = "String"),
            @ApiImplicitParam(name = "companyId",value = "公司ID",required = true,dataType = "Long"),
            @ApiImplicitParam(name = "startPeriod",value = "开始的会计期间",required = true,dataType = "String"),
            @ApiImplicitParam(name = "endPeriod",value = "结束的会计期间",required = true,dataType = "String"),
    })
    @GetMapping("/total/{subjectId}")
    TotalAccountVo getOneSubjectTotal(@PathVariable String subjectId,
                                      @RequestParam Long companyId,
                                      @RequestParam String startPeriod,
                                      @RequestParam String endPeriod){
        return accountBooksService.getOneSubjectTotal(subjectId,new BookSearchVo(startPeriod,endPeriod,"","",0,0),companyId);
    }

    /*** 科目余额表 ***/
    @ApiOperation(value = "获取部分科目的科目余额表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyId",value = "公司ID",required = true,dataType = "Long"),
            @ApiImplicitParam(name = "startPeriod",value = "开始的会计期间",required = true,dataType = "String"),
            @ApiImplicitParam(name = "endPeriod",value = "结束的会计期间",required = true,dataType = "String"),
            @ApiImplicitParam(name = "startSubjectId",value = "起始科目",required = true,dataType = "String"),
            @ApiImplicitParam(name = "endSubjectId",value = "结束科目",required = true,dataType = "String"),
            @ApiImplicitParam(name = "lowLevel",value = "小的科目级别",required = true,dataType = "int"),
            @ApiImplicitParam(name = "highLevel",value = "大的科目级别",required = true,dataType = "int")
    })
    @GetMapping("/subjects/balance")
    ArrayList<BalanceTableOneClause> getBalanceTableAllClauses(@RequestParam Long companyId,
                                                               @RequestParam String startPeriod,
                                                               @RequestParam String endPeriod,
                                                               @RequestParam String startSubjectId,
                                                               @RequestParam String endSubjectId,
                                                               @RequestParam int lowLevel,
                                                               @RequestParam int highLevel){
        return accountBooksService.getBalanceTableAllClauses(new BookSearchVo(startPeriod,endPeriod,startSubjectId,endSubjectId,lowLevel,highLevel),companyId);
    }

    /*** 科目汇总表 ***/
    @ApiOperation(value = "获取部分科目的科目汇总表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyId",value = "公司ID",required = true,dataType = "Long"),
            @ApiImplicitParam(name = "startPeriod",value = "开始的会计期间",required = true,dataType = "String"),
            @ApiImplicitParam(name = "endPeriod",value = "结束的会计期间",required = true,dataType = "String"),
            @ApiImplicitParam(name = "startSubjectId",value = "起始科目",required = true,dataType = "String"),
            @ApiImplicitParam(name = "endSubjectId",value = "结束科目",required = true,dataType = "String"),
            @ApiImplicitParam(name = "lowLevel",value = "小的科目级别",required = true,dataType = "int"),
            @ApiImplicitParam(name = "highLevel",value = "大的科目级别",required = true,dataType = "int")
    })
    @GetMapping("/subjects/total")
    ArrayList<GatherTableOneClause> getGatherTableAllClauses(@RequestParam Long companyId,
                                                             @RequestParam String startPeriod,
                                                             @RequestParam String endPeriod,
                                                             @RequestParam String startSubjectId,
                                                             @RequestParam String endSubjectId,
                                                             @RequestParam int lowLevel,
                                                             @RequestParam int highLevel){
        return accountBooksService.getGatherTableAllClauses(new BookSearchVo(startPeriod,endPeriod,startSubjectId,endSubjectId,lowLevel,highLevel),companyId);
    }


    @ApiOperation(value = "获得开始科目和结束科目之间所有有改变记录的科目的id和name")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startSubjects",value = "开始科目",required = true,dataType = "String"),
            @ApiImplicitParam(name = "endSubject",value = "结束科目",required = true,dataType = "String"),
            @ApiImplicitParam(name = "companyId",value = "公司ID",required = true,dataType = "Long")
    })
    @GetMapping("/subjects")
    ArrayList<SubjectIdAndNameVo> getBetweenSubject(@RequestParam String startSubjects,
                                                    @RequestParam String endSubject,
                                                    @RequestParam Long companyId){
        return accountBooksService.getBetweenSubject(startSubjects,endSubject,companyId);
    }
}
