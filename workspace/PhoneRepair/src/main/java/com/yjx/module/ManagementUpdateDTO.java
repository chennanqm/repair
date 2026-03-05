package com.yjx.module;

import lombok.Data;

@Data // Lombok自动生成getter/setter
public class ManagementUpdateDTO {
    // 订单ID：必填，且为正数（确保能定位到有效订单）
    private Integer repairId;
    // 维修价格：可选（允许不更新价格，前端不传则不更新）
    private Double repairPrice;
    // 支付状态：可选（必须是数据库enum义的有效值，如“待支付”“已支付”）
    private String paymentStatus;
    // 维修备注：可选（允许为空，但不能是纯空格）
    private String repairNotes;
    // 维修人员ID：必填（用于权限校验，确保是当前用户操作）
    private Integer technicianId;
}