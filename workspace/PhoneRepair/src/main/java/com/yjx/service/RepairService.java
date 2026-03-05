package com.yjx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yjx.module.ReceptionistVO;
import com.yjx.module.RepairQueryModule;
import com.yjx.pojo.Repair;
import com.yjx.unti.Result;

import java.util.List;
import java.util.Map;

public interface RepairService extends IService<Repair> {
    Result<Map<String, Object>> getRepairListByCondition(RepairQueryModule repairQueryModule);
    List<ReceptionistVO> getAllReceptionist();
    boolean createRepair(Repair repair);

    boolean deleteRepair(Integer repairId, Integer userId, String password);
}
