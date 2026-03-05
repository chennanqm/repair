package com.yjx.module;

import lombok.Data;
@Data // Lombok注解，自动生成getter/setter，需导入Lombok依赖
public class RepairQueryModule {

    // 前端传递的所有参数，字段名需与前端params键完全一致
    private Integer userId;        // 用户ID（前端是number类型，后端用Integer接收）
    private Integer userRole;      // 用户角色（前端是number类型，后端用Integer接收）
    private String searchKeyword;  // 搜索关键词（模糊查询用）
    private Integer pageNum = 1;   // 当前页码，默认1（防止前端不传）
    private Integer pageSize = 10; // 每页条数，默认10（防止前端不传）
    private String sortField;      // 排序字段（如created_at）
    private String sortOrder;      // 排序顺序（asc/desc）
}