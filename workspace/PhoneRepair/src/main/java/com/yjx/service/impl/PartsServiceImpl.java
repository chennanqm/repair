package com.yjx.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjx.mapper.PartsMapper;
import com.yjx.pojo.Parts;
import com.yjx.service.PartsService;
import com.yjx.unti.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PartsServiceImpl extends ServiceImpl<PartsMapper, Parts> implements PartsService {
    @Autowired
    private PartsMapper partsMapper;
    @Override
    public Map<String, Object> list(int userId, String searchKeyword, Integer pageNum,
                                    Integer pageSize, String sortField, String sortOrder) {
        //处理分页参数
        pageNum = (pageNum == null || pageNum < 1) ? 1 : pageNum;
        pageSize = (pageSize == null || pageSize < 1) ? 10 : pageSize;
        searchKeyword = (searchKeyword == null) ? "" : searchKeyword.trim();
        //默认排序：按创建时间降序
        if(sortField == null || sortField.trim().isEmpty()) {
            sortField = "createdAt";
        }
        if(!"asc".equalsIgnoreCase(sortOrder) && !"desc".equalsIgnoreCase(sortOrder)) {
            sortOrder = "desc";
        }

        Page<Parts> page = new Page<>(pageNum, pageSize);
        Map<String, Object> responseMap = new HashMap<>();
        IPage<Parts> partsIPage = partsMapper.list(
                page, userId, searchKeyword, sortField, sortOrder
        );
        responseMap.put("pageResult", partsIPage.getRecords());
        responseMap.put("count", partsIPage.getTotal());
        return responseMap;
    }

    public Result<String> addPart(Parts parts) {
        //判断参数
        if(parts.getPartName() == null || parts.getPartName().trim().isEmpty())
            return Result.fail("配件名称不能为空", 404);
        if(parts.getPartPrice() == null || parts.getPartPrice() <= 0)
            return Result.fail("配件价格不能小于0", 404);
        if(parts.getStockQuantity() == null || parts.getStockQuantity() <= 0)
            return Result.fail("配件数量不能小于0", 404);
        if(parts.getSupplierId() == null || parts.getSupplierId() <= 0)
            return Result.fail("供应商id不能小于0", 404);

        //设置默认值
        Integer result = partsMapper.addParts(parts);
        if(result != null && result > 0) {
            return Result.success("创建配件成功");
        } else {
            return Result.fail("创建配件失败",404);
        }
    }

    @Override
    public Result<String> updatePart(Parts parts) {
        //确保partId存在
        if(parts.getPartId() == null) {
            return Result.fail("配件ID不能为空", 404);
        }
        int result = partsMapper.updateParts(parts);
        if(result > 0) {
            return Result.success("配件更新成功");
        } else {
            return Result.fail("配件更新失败，可能配件不存在", 404);
        }
    }

    @Override
    public Result<String> deletePart(Integer partId) {
        if(partId == null) {
            return Result.fail("配件ID不能为空", 404);
        }
        Parts parts = partsMapper.queryPartsByPartId(partId);
        if(parts == null) {
            return Result.fail("配件不存在", 404);
        }
        int result = partsMapper.deletePartsByPartId(partId);
        if(result > 0) {
            return Result.success("配件删除成功");
        } else {
            return Result.fail("配件删除失败", 404);
        }
    }
}
