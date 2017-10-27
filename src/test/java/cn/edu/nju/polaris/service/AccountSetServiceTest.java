package cn.edu.nju.polaris.service;

import cn.edu.nju.polaris.entity.AccountSet;
import cn.edu.nju.polaris.vo.AccountSetVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
public class AccountSetServiceTest {

    @Autowired
    AccountSetService accountSetService;

    @Test
    public void test(){
        AccountSetVO vo = accountSetService.getAccountSet("0274113335");
        assertEquals(vo.location,"南大");
    }
}
