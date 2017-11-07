package cn.edu.nju.polaris.service;

import cn.edu.nju.polaris.vo.UserInfoVO;
import cn.edu.nju.polaris.vo.UserVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;


    @Test
    public void testAddOneUser() throws Exception {
        UserVO vo = new UserVO();
        Long companyId = accountService.findCompanyIdByName("TestCompany");
        vo.setCompanyId(companyId);
        vo.setUserName("TestAdmin");
        vo.setPassword("123456");
        vo.setType("ADMIN");
        userService.addUser(vo);
    }

    @Test
    public void testSignIn() throws Exception {
        String userName = "TestAdmin";
        String password = "123456";
        UserInfoVO infoVO = userService.signIn(userName,password);
        System.out.println(infoVO.toString());
    }

    @Test
    public void testModifyPassword() throws Exception {
        String userName = "TestAdmin";
        String oldPassword = "123456789";
        String newPassword = "123456";
        userService.modifyPassword(userName,oldPassword,newPassword);
    }
}
