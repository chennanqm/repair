package com.yjx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yjx.pojo.Parts;
import com.yjx.unti.Result;

import java.util.Map;

public interface PartsService extends IService<Parts> {
    Map<String, Object> list(int userId, String searchKeyword, Integer pageNum,
                             Integer pageSize, String sortField, String sortOrder);

    Result<String> addPart(Parts parts);

    Result<String> updatePart(Parts parts);

    Result<String> deletePart(Integer partId);
}
