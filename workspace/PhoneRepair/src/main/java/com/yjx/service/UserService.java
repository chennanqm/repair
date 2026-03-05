package com.yjx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yjx.module.LoginUser;
import com.yjx.module.RegisterUser;
import com.yjx.pojo.User;
import com.yjx.unti.Result;

import java.util.Map;

public interface UserService extends IService<User> {
    LoginUser login(String usernameOrEmail, String password);
    Result<String> register(RegisterUser registerUser);

    boolean updatePassword(String userEmail, String userNewPassword);

    Result<Map<String, Object>> getAllUser(int userId, String searchKeyword,
                                           Integer pageNum, Integer pageSize,
                                           String sortField, String sortOrder);

    Result<String> updateUser(User user);

    int deleteUserByUserId(Integer userId);
}