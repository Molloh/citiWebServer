package cn.edu.nju.polaris.controller;

import cn.edu.nju.polaris.entity.SubjectInitial;
import cn.edu.nju.polaris.schedule.ScheduledTask;
import cn.edu.nju.polaris.service.SubjectBalanceService;
import cn.edu.nju.polaris.service.SubjectInitialService;
import cn.edu.nju.polaris.vo.SubjectBalanceVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.Subject;
import java.util.List;

@RestController
@RequestMapping("/initial")
public class InitialController {


    private final SubjectInitialService subjectInitialService;
    private final SubjectBalanceService subjectBalanceService;

    public InitialController(SubjectInitialService subjectInitialService, SubjectBalanceService subjectBalanceService) {
        this.subjectInitialService = subjectInitialService;
        this.subjectBalanceService = subjectBalanceService;
    }

    @ApiOperation(value = "期初设置")
    @ApiImplicitParam(name = "list",value = "期初设置的列表",required = true,dataType = "List")
    @PostMapping()
    void subjectsInitial(@RequestBody List<SubjectInitial> list){
        subjectInitialService.saveSubjectInitials(list);
    }

    @ApiOperation(value = "获得期初设置")
    @ApiImplicitParam(name = "companyId",value = "公司ID",required = true,dataType = "Long")
    @GetMapping()
    List<SubjectInitial> getAllSubjectInitial(@RequestParam Long companyId){
        return subjectInitialService.getAllSubjectInitialByCompanyId(companyId);
    }


    @PostMapping("/updateBalance")
    void updateBalance(@RequestParam Long companyId,
                       @RequestParam String phase){
        subjectBalanceService.initialSubjectBalance(companyId,phase);
    }
}



