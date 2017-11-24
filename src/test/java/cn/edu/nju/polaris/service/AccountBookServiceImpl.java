package cn.edu.nju.polaris.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
public class AccountBookServiceImpl {

    @Autowired
    AccountBooksBlService accountBooksBlService;

    @Test
    public void test() throws Exception {
        accountBooksBlService.updateSubjectBalanceTable(1L);
    }
}
