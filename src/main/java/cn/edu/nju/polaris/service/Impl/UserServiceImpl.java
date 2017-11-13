package cn.edu.nju.polaris.service.Impl;

import cn.edu.nju.polaris.entity.User;
import cn.edu.nju.polaris.exception.ResourceConflictException;
import cn.edu.nju.polaris.exception.ResourceNotFoundException;
import cn.edu.nju.polaris.repository.UserRepository;
import cn.edu.nju.polaris.service.UserService;
import cn.edu.nju.polaris.util.MD5;
import cn.edu.nju.polaris.vo.UserInfoVO;
import cn.edu.nju.polaris.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void addUser(UserVO vo) {
        User user = userRepository.findByUserName(vo.getUserName());
        if (user != null){
            throw new ResourceConflictException("该用户名已被注册!");
        }
        user = new User();
        user.setType(vo.getType());
        user.setUserName(vo.getUserName());
        user.setPassword(MD5.encrypt(vo.getPassword()));
        user.setCompanyId(vo.getCompanyId());
        userRepository.save(user);
    }

    @Override
    public UserInfoVO signIn(String userName, String password) {
        User user = userRepository.findByUserName(userName);
        if (user == null){
            throw new ResourceNotFoundException("用户名不存在");
        }
        if (!user.getPassword().equals(MD5.encrypt(password))){
            throw new ResourceNotFoundException("密码错误");
        }

        UserInfoVO vo = new UserInfoVO();
        vo.setCompanyId(user.getCompanyId());
        vo.setType(user.getType());
        vo.setUserName(user.getUserName());

        return vo;
    }

    @Override
    public boolean isUserExists(String userName) {
        User user = userRepository.findByUserName(userName);
        return user!=null;
    }

    @Override
    public void modifyPassword(String userName, String oldPassword, String newPassword) {
        User user = userRepository.findByUserName(userName);
        if (user != null){
            if (!user.getPassword().equals(MD5.encrypt(oldPassword))){
                throw new ResourceNotFoundException("密码错误");
            }
            user.setPassword(MD5.encrypt(newPassword));
            userRepository.saveAndFlush(user);
        }

    }

    @Override
    public UserInfoVO getUserInfo(String userName) {
        User user = userRepository.findByUserName(userName);
        if (user == null){
            throw new ResourceNotFoundException("用户名不存在");
        }
        UserInfoVO vo = new UserInfoVO();
        vo.setUserName(user.getUserName());
        vo.setCompanyId(user.getCompanyId());
        vo.setType(user.getType());

        return vo;
    }



}
