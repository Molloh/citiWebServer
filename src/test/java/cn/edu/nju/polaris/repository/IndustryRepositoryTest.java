package cn.edu.nju.polaris.repository;


import org.aspectj.lang.annotation.RequiredTypes;
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
public class IndustryRepositoryTest {

    @Autowired
    private IndustryRepository industryRepository;

    @Test
    public void testFindFirstIndustry(){
        List<String> list = industryRepository.findAllFirstIndustry();
        for (String s : list){
            System.out.println(s);
        }
    }

    @Test
    public void testFindSecondIndustry(){
        List<String> list = industryRepository.findAllSecondIndustryByFirstIndustry("工业");
        for (String s : list){
            System.out.println(s);
        }
    }
}
