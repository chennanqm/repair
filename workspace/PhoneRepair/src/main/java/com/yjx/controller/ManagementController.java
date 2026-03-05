package com.yjx.controller;

import com.yjx.module.ManagementCreateDTO;
import com.yjx.module.ManagementUpdateDTO;
import com.yjx.pojo.Management;
import com.yjx.service.ManagementService;
import com.yjx.service.RepairService;
import com.yjx.unti.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/management")
public class ManagementController {
    @Autowired
    private ManagementService managementService;
    @RequestMapping("getAllRepairManagement")
    public Result<Map<String, Object>> getAllManagement(
            @RequestParam("userId") Integer userId,
            @RequestParam("userRole") String userRole,
            @RequestParam("searchKeyword") String searchKeyword,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("sortField") String sortField,
            @RequestParam("sortOrder") String sortOrder) {
        Map<String, Object> responseMap = managementService.getAllManagement(
            userId,
            userRole,
            searchKeyword,
            pageNum,
            pageSize,
            sortField,
            sortOrder
        );
        return Result.success(responseMap);
    }
    @RequestMapping("/deleteRepairManagement")
    public Result<String> deleteRepairManagement(
            @RequestParam("repairId") Integer repairId,
            @RequestParam("userId") Integer userId,
            @RequestParam("userPasswd")  String password) {
        Boolean resultMsg = managementService.deleteRepair(repairId, userId, password);
        if (resultMsg) {
            return Result.success("订单删除成功");
        } else {
            return Result.fail("订单不存在或密码错误", 400);
        }
    }
    @RequestMapping("createRepairManagement")
    public Result<Object> createRepairManagement(@RequestBody ManagementCreateDTO createDTO) {
        Boolean isSuccess = managementService.createRepairManagement(createDTO);
        return isSuccess ? Result.success("创建成功") : Result.fail("新建失败：关联的订单ID不存在", 400);
    }

    @RequestMapping("updateRepairManagement")
    public Result<Object> updateRepairManagement(@RequestBody ManagementUpdateDTO updateDTO) {
        Boolean isSuccess = managementService.updateRepairManagement(updateDTO);
        return isSuccess ? Result.success( "更新成功") : Result.fail( "更新失败:关联的订单ID不存在", 400);
    }
}
