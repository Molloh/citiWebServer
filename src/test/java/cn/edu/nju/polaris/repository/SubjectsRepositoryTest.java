package cn.edu.nju.polaris.repository;


import cn.edu.nju.polaris.entity.Subjects;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Table;
import java.util.List;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
public class SubjectsRepositoryTest {

    @Autowired
    private SubjectsRepository subjectsRepository;

    @Test
    public void testFindOne(){
        Subjects subjects = subjectsRepository.findBySubjectsId("1012");
        System.out.println(subjects.toString());
    }

    @Test
    public void testFindAll(){
        List<Subjects> lists = subjectsRepository.findAll();
        for (Subjects subjects : lists){
            System.out.println(subjects.toString());
        }
    }
}
