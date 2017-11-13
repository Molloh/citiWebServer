package cn.edu.nju.polaris.controller;

import cn.edu.nju.polaris.service.SubjectService;
import cn.edu.nju.polaris.vo.SubjectVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @ApiOperation(value = "获取所有会计科目信息")
    @GetMapping
    public List<SubjectVO> getAllSubjects(){
        return subjectService.findAllSubject();
    }
}
