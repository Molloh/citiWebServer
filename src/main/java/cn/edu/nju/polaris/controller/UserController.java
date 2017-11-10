package cn.edu.nju.polaris.controller;


import cn.edu.nju.polaris.service.UserService;
import cn.edu.nju.polaris.vo.UserInfoVO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }



    @PostMapping("/login")
    public UserInfoVO login(@RequestParam String userId,
                            @RequestParam String password){
        return userService.signIn(userId,password);
    }


    @GetMapping(value = "/{userName}")
    public UserInfoVO getUserInfo(@PathVariable String userName){
        return userService.getUserInfo(userName);
    }
}
