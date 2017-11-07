package cn.edu.nju.polaris.repository;


import cn.edu.nju.polaris.entity.IndustryIndex;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
public class IndustryIndexRepositoryTest {

    @Autowired
    private IndustryIndexRepository industryIndexRepository;

    @Test
    public void testFindOne(){
        IndustryIndex index = industryIndexRepository.findByCategoryAndFirstIndustryAndSecondIndustryAndScaleAndIndexNameLike("财务预警","工业","煤炭工业","中型企业","净资产收益率（％）");
        System.out.println(index.toString());
    }
}
