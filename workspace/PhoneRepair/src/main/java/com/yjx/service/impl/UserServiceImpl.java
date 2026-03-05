package com.yjx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjx.mapper.UserMapper;
import com.yjx.module.LoginUser;
import com.yjx.module.RegisterUser;
import com.yjx.pojo.User;
import com.yjx.service.UserService;
import com.yjx.unti.Md5Password;
import com.yjx.unti.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public LoginUser login(String usernameOrEmail, String password) {
        User user = this.getOne(new QueryWrapper<User>()
                .eq("user_name", usernameOrEmail)
                .or()
                .eq("user_email", usernameOrEmail));
        //判断对象是否存在
        if(user == null){
            //返回错误信息
            return null;
        }
        if(!user.getUserPasswordHash().equals(Md5Password.generateMD5(password))){
            //返回错误信息
            return null;
        }
        //返回正确信息
        LoginUser loginUser = new LoginUser();
        loginUser.setUserId(user.getUserId());
        loginUser.setUserName(user.getUserName());
        loginUser.setUserEmail(user.getUserEmail());
        loginUser.setRoleId(user.getRoleId());
        loginUser.setUserBio(user.getUserBio());
        loginUser.setUserPhone(user.getUserPhone());
        return loginUser;
    }
    @Override
    public Result<String> register(RegisterUser registerUser) {
        //判断参数是否为空
        if(registerUser.getUserName() == null||registerUser.getUserEmail() == null||registerUser.getUserPasswordHash() == null){
            return Result.fail("参数不能为空", 500);
        }
        //判断用户是否存在
        User user = this.getOne(new QueryWrapper<User>()
                .eq("user_name", registerUser.getUserEmail())
                .or()
                .eq("user_email", registerUser.getUserName()));
        //判断对象是否存在
        if(user != null){
            //返回错误信息
            return Result.fail("用户已存在", 500);
        }
        //判断密码和确认密码是否一致
        if(registerUser.getUserPasswordHash().length()<6||!registerUser.getUserPasswordHash().matches(".*[a-zA-Z]+.*")){
            return Result.fail("密码长度必须大于6，必须包含字符串", 500);
        }
        user = new User();
        user.setUserName(registerUser.getUserName());
        user.setUserEmail(registerUser.getUserEmail());
        user.setUserPasswordHash(Md5Password.generateMD5(registerUser.getUserPasswordHash()));
        user.setRoleId("2");
        //调用MyBatis-plus的save方法保存用户
        boolean save = this.save(user);
        if(save){
            return Result.success("注册成功");
        }
        return Result.fail("注册失败", 500);
    }

    @Override
    public boolean updatePassword(String userEmail, String userNewPassword) {
        if(userNewPassword.isEmpty() || userEmail == null)
            return false;
        User user = userMapper.selectByEmail(userEmail);
        if(user == null)
            return false;
        String encryptedPassword = Md5Password.generateMD5(userNewPassword);
        int updateCount = userMapper.updateUserPassword(user.getUserId(), encryptedPassword);
        return updateCount > 0;
    }

    @Override
    public Result<Map<String, Object>> getAllUser(int userId, String searchKeyword,
                                                  Integer pageNum, Integer pageSize,
                                                  String sortField, String sortOrder) {
        //判断参数是否为空
        pageNum = pageNum == null || pageNum < 1 ? 1 : pageNum;
        pageSize = pageSize == null || pageSize < 1 ? 10 : pageSize;
        searchKeyword = searchKeyword == null ? "" : searchKeyword;
        if(sortField == null || sortField.trim().isEmpty()) {
            sortField = "createdAt";
        }
        if(sortOrder == null || (!"asc".equalsIgnoreCase(sortOrder) && !"desc".equalsIgnoreCase(sortOrder))) {
            sortOrder = "desc";
        }
        Page<User> page = new Page<>(pageNum, pageSize);
        Map<String, Object> map = new HashMap<>();
        IPage<User> pageResult = userMapper.selectAllUser(page, userId, searchKeyword, sortField, sortOrder);
        map.put("count", pageResult.getTotal());
        map.put("userList", pageResult.getRecords());
        return Result.success(map);
    }

    @Override
    public Result<String> updateUser(User user) {
        //判断参数非空
        if(user.getUserId() == null)
            return Result.fail("用户id不能为空", 500);
        if(user.getUserEmail() == null)
            return Result.fail("用户邮箱不能为空", 500);
        //判断Email是否是除自己以外的在使用
        User user1 = this.getOne(new QueryWrapper<User>().eq("user_id", user.getUserId()));
        if(user1 == null)
            return Result.fail("用户不存在", 500);
        //判断Email是否被其他人使用
        if(!user1.getUserEmail().equals(user.getUserEmail())) {
            User user2 = this.getOne(new QueryWrapper<User>().eq("user_email", user.getUserEmail()));
            if(user2 != null)
                return Result.fail("邮箱已存在", 500);
        }
        int result = userMapper.updateUser(user);
        if (result > 0) {
            return Result.success("用户更新成功");
        } else {
            return Result.fail("用户更新失败", 404);
        }
    }

    @Override
    public int deleteUserByUserId(Integer userId) {
        //判断参数是否为空
        if(userId == null) {
            Result.fail("用户Id不能为空", 404);
        }
        //判断用户是否存在
        User user = userMapper.selectById(userId);
        if(user == null) {
            Result.fail("用户记录不存在", 404);
        }
        int rows = userMapper.deleteUserByUserId(userId);
        if(rows <= 0) {
            Result.fail("删除失败", 404);
        }
        return rows;
    }
}
