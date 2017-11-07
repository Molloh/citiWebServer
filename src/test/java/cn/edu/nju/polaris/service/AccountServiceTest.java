package cn.edu.nju.polaris.service;

import cn.edu.nju.polaris.vo.AccountInfoVO;
import cn.edu.nju.polaris.vo.AccountVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Table;
import java.sql.Date;
import java.sql.Timestamp;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
public class AccountServiceTest {

    @Autowired
    AccountService accountService;

    @Test
    public void testSignUp() throws Exception {
        AccountVO vo = new AccountVO();
        vo.setCompanyName("TestCompany");
        vo.setLocation("南京");
        vo.setActiveTime(Timestamp.valueOf("2017-10-10 12:00:00"));
        vo.setFirstIndustry("工业");
        vo.setSecondIndustry("煤炭工业");
        vo.setEmail("testCompany@163.com");
        accountService.signUp(vo);
    }

    @Test
    public void testModify(){
        AccountInfoVO infoVO = new AccountInfoVO();
        infoVO.setEmail("testCompany@163.com");
        infoVO.setLocation("南京");
        infoVO.setFirstIndustry("工业");
        infoVO.setSecondIndustry("煤炭工业");
        infoVO.setScale("中型");
        accountService.saveAccount(new Long(32),infoVO);
    }
}
