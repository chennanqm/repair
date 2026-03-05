package com.yjx.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@TableName(value = "yjx_repair_management")  // 指定数据库表名
@Builder  // 构造器
@AllArgsConstructor  // 全参构造函数
@NoArgsConstructor  // 无参构造函数
public class Management {
    private Integer repairId;
    private Integer repairRequestId;
    private String technicianId;
    private String repairPrice;
    private String paymentStatus;
    private String repairNotes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    // 2. 新增非数据库字段（从关联表获取，前端必需）
    @TableField(exist = false)  // 标记：该字段不在 yjx_repair_management 表中
    private String phoneModel;  // 手机型号（来自 yjx_repair_request.phone_model）
    @TableField(exist = false)
    private String statusName;  // 维修状态名称（来自 yjx_repair_request.request_status，转文字）
    @TableField(exist = false)
    private String userName;    // 订单用户名（来自 yjx_user.user_name）
}
