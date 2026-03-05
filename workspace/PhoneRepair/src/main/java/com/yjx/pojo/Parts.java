package com.yjx.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@TableName(value = "yjx_parts")  // 指定数据库表名
@Builder  // 构造器
@AllArgsConstructor  // 全参构造函数
@NoArgsConstructor
public class Parts {
    private Integer partId; //配件id
    private String partName; //配件名称
    private String partDescription;//配件描述
    private Double partPrice;//配件价格
    private Integer stockQuantity;//配件数量
    private Integer supplierId;//配件供应商id
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}