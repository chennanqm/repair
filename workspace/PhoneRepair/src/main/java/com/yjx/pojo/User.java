package com.yjx.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data  // 自动生成getter和setter方法
@TableName(value = "yjx_user")  // 指定数据库表名
@Builder  // 构造器
@AllArgsConstructor  // 全参构造函数
@NoArgsConstructor  // 无参构造函数
public class User implements Serializable {
    private Integer userId;
    private String userName;
    private String userEmail;
    private String userPasswordHash;
    private String roleId;
    private String userBio;
    private String userPhone;
    private String userGender;
    private LocalDateTime userLastActive;
    private LocalDateTime userCreatedAt;
    private String userStatus;
}