package cn.edu.nju.polaris.repository;


import cn.edu.nju.polaris.entity.SubjectsBalance;
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
public class SubjectsBalanceRepositoryTest {

    @Autowired
    private SubjectsBalanceRepository subjectsBalanceRepository;


    @Test
    public void testFindByCompanyIdAndDate() throws Exception {
        List<SubjectsBalance> list = subjectsBalanceRepository.findByCompanyIdAndDate(1L,"2017-09");
        for (SubjectsBalance subjectsBalance : list){
            System.out.println(subjectsBalance.toString());
        }
    }
}
