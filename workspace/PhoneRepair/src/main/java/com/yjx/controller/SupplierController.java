package com.yjx.controller;

import com.yjx.pojo.Supplier;
import com.yjx.service.SupplierService;
import com.yjx.unti.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/supplier")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    @GetMapping("/getAllSupplierManagement")
    public Result<Map<String, Object>> getAllSupplierManagement(@RequestParam(required = false) Integer page, @RequestParam(required = false) String searchKeyword,
                                                                @RequestParam(defaultValue = "1") Integer pageNum,
                                                                @RequestParam(defaultValue = "10")  Integer pageSize,
                                                                @RequestParam(defaultValue = "created_at") String sortField,
                                                                @RequestParam(defaultValue = "desc") String sortOrder) {
        try {
            //参数合法性校验，排序字段限制
            if(!"supplier_management_id".equals(sortField) && !"supplier_id".equals(sortField) && !"part_id".equals(sortField) && !"created_at".equals(sortField)) {
                return Result.fail("非法的排序字段", 400);
            }

            Map<String, Object> result = supplierService.getAllSupplierManagement(
                    searchKeyword,
                    pageNum,
                    pageSize,
                    sortField,
                    sortOrder
            );
            //使用你的success方法，并转入data
            return Result.success(result);
        } catch (Exception e) {
            return Result.fail("服务器异常，获取数据失败", 500);
        }
    }

    @PostMapping("createSupplierManagement")
    public Result<String> createSupplierManagement(@RequestBody Supplier supplier) {
        boolean result = supplierService.createSupplierManagement(supplier);
        if(result)
            return Result.success(null);
        else return Result.fail("新增失败，关联的供应商ID不存在", 400);
    }

    @PostMapping("updateSupplierManagement")
    public Result<String> updateSupplierManagement(@RequestBody Supplier supplier) {
        boolean result = supplierService.updateSupplierManagement(supplier);
        if(result)
            return Result.success(null);
        else
            return Result.fail("更新失败，关联的供应商ID不存在", 400);
    }

    @PostMapping("deleteSupplierManagement")
    public Result<String> deleteSupplierManagement(@RequestParam Integer supplierManagementId,
                                                   @RequestParam Integer userId,
                                                   @RequestParam String userPasswd)
    {
        boolean result = supplierService.deleteSupplierManagement(supplierManagementId, userId, userPasswd);
        if(result)
            return Result.success(null);
        else
            return Result.fail("删除失败：密码错误或关联的供应商ID不存在", 400);
    }
}
