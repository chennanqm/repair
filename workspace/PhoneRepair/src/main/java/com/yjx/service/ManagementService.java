package com.yjx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yjx.module.ManagementCreateDTO;
import com.yjx.module.ManagementUpdateDTO;
import com.yjx.pojo.Management;

import java.util.Map;

public interface ManagementService extends IService<Management> {
    Map<String, Object> getAllManagement(Integer userId, String userRole, String searchKeyword, Integer pageNum, Integer pageSize, String sortField, String sortOrder);

    Boolean deleteRepair(Integer repairId, Integer userId, String password);

    Boolean createRepairManagement(ManagementCreateDTO createDTO);

    Boolean updateRepairManagement(ManagementUpdateDTO updateDTO);
}
