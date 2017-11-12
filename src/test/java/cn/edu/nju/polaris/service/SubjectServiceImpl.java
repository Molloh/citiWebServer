package cn.edu.nju.polaris.service;

import cn.edu.nju.polaris.vo.SubjectVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
public class SubjectServiceImpl {

    @Autowired
    SubjectService subjectService;

    @Test
    public void testFindAll() throws Exception {
        List<SubjectVO> list = subjectService.findAllSubject();
        for (SubjectVO subjectVO : list){
            System.out.println(subjectVO.toString());
        }
    }
}
