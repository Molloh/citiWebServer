package cn.edu.nju.polaris.repository;


import cn.edu.nju.polaris.entity.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void testFindByCompanyName() throws Exception {
        Account account = accountRepository.findByCompanyName("TestCompany");
        System.out.println(account.toString());
    }

    @Test
    public void testFindByEmail() throws Exception {
        Account account = accountRepository.findByEmail("testCompany@163.com");
        System.out.println(account.toString());
    }

    @Test
    public void testFindById() throws Exception {
        Account account = accountRepository.findById(1L);
        System.out.println(account.toString());
    }
}
