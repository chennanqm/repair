package com.yjx.module;

import lombok.Data;

@Data
public class PartsQueryModule {
    private Integer userId;
    private String searchKeyword;
    private Integer pageNum = 1;  // 设置默认值
    private Integer pageSize = 10;  // 设置默认值
    private String sortField;
    private String sortOrder;
}