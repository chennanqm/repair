package com.yjx.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("yjx_supplier_management")
public class Supplier {
    private Integer supplierManagementId;
    private Integer supplierId;
    private Integer partId;
    private Integer supplyQuantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    // 以下为关联表字段（非数据库表字段，用于前端展示）
    private String supplierName; // 来自 yjx_supplier_management 表的供应商名称
    private String partName;     // 来自 yjx_parts 表的配件名称
}