package com.yjx.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
@Data
@TableName(value = "yjx_repair_request")  // 指定数据库表名
@Builder  // 构造器
@AllArgsConstructor  // 全参构造函数
@NoArgsConstructor  // 无参构造函数
public class Repair {
    private Integer requestId;
    private Integer userId;
    private Integer receptionistId;
    private String phoneModel;
    private String phoneIssueDescription;
    private Integer requestStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    // 以下字段为非数据库字段
    @TableField(exist = false)  // 关键：告诉MyBatis-Plus该字段不存在于数据库表中
    private String receptionistName;
}