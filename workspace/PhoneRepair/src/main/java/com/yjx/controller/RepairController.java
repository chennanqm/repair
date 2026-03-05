package com.yjx.controller;

import com.yjx.module.ReceptionistVO;
import com.yjx.module.RepairQueryModule;
import com.yjx.pojo.Repair;
import com.yjx.service.RepairService;
import com.yjx.unti.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/repair")
public class RepairController {

    @Autowired
    private RepairService repairService;

    @RequestMapping("getAllRepair")
    public Result<Map<String, Object>> getAllRepair(RepairQueryModule repairQueryModule){
        return repairService.getRepairListByCondition(repairQueryModule);
    }
    @GetMapping("/getAllReceptionist")
    public Result<List<ReceptionistVO>> getAllReceptionist(){
        List<ReceptionistVO> receptionistVOList = repairService.getAllReceptionist();
        return Result.success(receptionistVOList);
    }
    @PostMapping("/createRepair")
    public Result<String> createRepair(@RequestBody Repair repair){
        boolean isCreated = repairService.createRepair(repair);
        if(isCreated) {
            return Result.success("创建成功");
        } else {
            return Result.fail("创建失败", 500);
        }
    }
    @PostMapping("/deleteRepair")
    public Result<String> deleteRepair(
            @RequestParam("repairId") Integer repairId,
            @RequestParam("userId") Integer userId,
            @RequestParam("password")  String password) {
        boolean isDelete = repairService.deleteRepair(repairId, userId, password);
        if (isDelete) {
            return Result.success("删除成功");
        } else {
            return Result.fail("删除失败", 500);
        }
    }
}
