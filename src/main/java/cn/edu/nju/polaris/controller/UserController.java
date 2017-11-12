package cn.edu.nju.polaris.controller;


import cn.edu.nju.polaris.service.UserService;
import cn.edu.nju.polaris.vo.UserInfoVO;
import cn.edu.nju.polaris.vo.UserVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @ApiOperation(value = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName",value = "用户名",required = true,dataType = "String"),
            @ApiImplicitParam(name = "password",value = "密码",required = true, dataType = "String")
    })
    @PostMapping("/login")
    public UserInfoVO login(@RequestParam String userName,
                            @RequestParam String password){
        return userService.signIn(userName,password);
    }

    @ApiOperation(value = "获取用户基本信息",notes = "根据用户名获得用户基本信息")
    @ApiImplicitParam(name = "userName",value = "用户名",required = true,dataType = "String")
    @GetMapping(value = "/{userName}")
    public UserInfoVO getUserInfo(@PathVariable String userName){
        return userService.getUserInfo(userName);
    }

    @ApiOperation(value = "添加新用户")
    @ApiImplicitParam(name = "userVO",value = "用户VO",required = true,dataType = "UserVO")
    @PostMapping
    public void addUser(@RequestBody UserVO userVO){
        userService.addUser(userVO);
    }
}
