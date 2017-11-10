package cn.edu.nju.polaris.service;

import cn.edu.nju.polaris.entity.User;
import cn.edu.nju.polaris.vo.UserInfoVO;
import cn.edu.nju.polaris.vo.UserVO;

public interface UserService {

    /**
     * 添加新用户
     * @param vo
     */
    void addUser(UserVO vo);

    /**
     * 登录
     * @param userName
     * @param password
     */
    UserInfoVO signIn(String userName, String password);

    /**
     * 根据用户名判断是否存在该用户
     * @param userName
     * @return
     */
    boolean isUserExists(String userName);

    /**
     * 修改密码
     * @param userName
     * @param oldPassword
     * @param newPassword
     */
    void modifyPassword(String userName,String oldPassword,String newPassword);


    /**
     * 根据
     * @param userName
     * @return
     */
    UserInfoVO getUserInfo(String userName);
}
