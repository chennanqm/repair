package com.yjx.controller;

import com.yjx.module.LoginUser;
import com.yjx.module.PartsQueryModule;
import com.yjx.module.RegisterUser;
import com.yjx.pojo.User;
import com.yjx.service.UserService;
import com.yjx.unti.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    //接收参数
    @PostMapping("/login")
    public Result<LoginUser> login(@RequestParam(value = "usernameOrEmail") String usernameOrEmail, @RequestParam(value = "password") String password) {
        LoginUser loginUser = userService.login(usernameOrEmail, password);
        if (loginUser == null)
            return Result.fail("用户名或密码错误", 400);
        return Result.success(loginUser);
    }
    @PostMapping("/createUser")
    public Result<String> register(@RequestBody RegisterUser registerUser) {
        return userService.register(registerUser);
    }
    @PostMapping("/updatePassword")
    public Result<String> updatePassword(
            @RequestParam String userEmail,
            @RequestParam String userNewPassword) {
        boolean isupdate = userService.updatePassword(userEmail, userNewPassword);
        if(isupdate)
            return Result.success("找回密码成功！");
        else
            return Result.fail("找回密码失败！", 500);
    }

    @GetMapping("/getAllUsers")
    public Result<Map<String, Object>> getAllUser(PartsQueryModule partsQueryModule) {
        int userId = partsQueryModule.getUserId();
        String searchKeyword = partsQueryModule.getSearchKeyword();
        Integer pageNum = partsQueryModule.getPageNum();
        Integer pageSize = partsQueryModule.getPageSize();
        String sortField = partsQueryModule.getSortField();
        String sortOrder = partsQueryModule.getSortOrder();
        return userService.getAllUser(userId, searchKeyword, pageNum, pageSize, sortField, sortOrder);
    }

    @PostMapping("updateUser")
    public Result<String> updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("/delete/{userId}")
    public Result<String> deleteUser(@PathVariable Integer userId) {
        int result = userService.deleteUserByUserId(userId);
        if (result > 0) {
            return Result.success("删除成功");
        } else {
            return Result.fail("删除失败，配件不合格", 404);
        }
    }
}
