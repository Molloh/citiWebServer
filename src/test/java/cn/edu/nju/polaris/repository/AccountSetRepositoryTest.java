package cn.edu.nju.polaris.repository;

import cn.edu.nju.polaris.entity.AccountSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
public class AccountSetRepositoryTest {

    @Autowired
    private AccountSetRepository accountSetRepository;

    @Test
    public void test(){
        AccountSet set = accountSetRepository.findByAccountId("0274113335");
        System.out.println(set.getLocation());
    }
}
