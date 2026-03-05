package com.yjx.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjx.mapper.ManagementMapper;
import com.yjx.mapper.RepairMapper;
import com.yjx.mapper.UserMapper;
import com.yjx.module.ManagementCreateDTO;
import com.yjx.module.ManagementUpdateDTO;
import com.yjx.pojo.Management;
import com.yjx.pojo.Repair;
import com.yjx.pojo.User;
import com.yjx.service.ManagementService;
import com.yjx.unti.Md5Password;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ManagementServiceImpl extends ServiceImpl<ManagementMapper, Management> implements ManagementService {
    @Autowired
    private ManagementMapper managementMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RepairMapper repairMapper;

    @Override
    public Map<String, Object> getAllManagement(
            Integer userId, String userRole, String searchKeyword,
            Integer pageNum, Integer pageSize, String sortField,
            String sortOrder) {
        //处理分页参数
        if (pageNum == null)
            pageNum = 1;
        if (pageSize == null)
            pageSize = 10;
        //处理排序参数
        if(sortField == null)
            sortField = "createdAt";
        if(sortOrder == null)
            sortOrder = "desc";
        //处理关键词
        if(searchKeyword == null)
            searchKeyword = "";

        Page<Management> page = new Page<>(pageNum, pageSize);
        IPage<Management> pageResult = managementMapper.selectAllRepairManagement(
                page,
                userId,
                searchKeyword,
                sortField,
                sortOrder
        );
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("count", pageResult.getTotal());
        responseMap.put("repairManagementList", pageResult.getRecords());
        return responseMap;
    }
    @Override
    public Boolean deleteRepair(Integer repairId, Integer userId, String password) {
        if(repairId == null || userId == null || password == null) {
            return false;
        }
        User user = userMapper.selectById(userId);
        if(user == null)
            return false;
        if(!Md5Password.generateMD5(password).equals(user.getUserPasswordHash()))
            return false;
        Integer deleteCount=managementMapper.deleteById(repairId);
        return deleteCount > 0? true : false;
    }

    @Override
    public Boolean createRepairManagement(ManagementCreateDTO createDTO) {
        if(createDTO.getRepairRequestId() == null
        || createDTO.getRepairNotes() == null
        || createDTO.getTechnicianId() == null)
            return false;

        //判断订单id是否存在Request的表中
        Repair repair = repairMapper.selectById(createDTO.getRepairRequestId());
        if(repair == null)
            return false;
        Management management = new Management();
        management.setRepairRequestId(createDTO.getRepairRequestId());
        management.setTechnicianId(createDTO.getTechnicianId());
        management.setRepairNotes(createDTO.getRepairNotes());
        management.setRepairNotes(createDTO.getRepairNotes());
        management.setRepairPrice("100");
        management.setPaymentStatus("待支付");
        management.setCreatedAt(LocalDateTime.now());
        management.setUpdatedAt(LocalDateTime.now());
        return this.save(management);
    }

    private static final List<String> VALID_PAYMENT_STATUS = Arrays.asList("待支付", "支付中", "支付完成", "支付异常");

    @Override
    public Boolean updateRepairManagement(ManagementUpdateDTO updateDTO) {
        //做参数校验，校验订单ID
        if(updateDTO.getRepairId() == null)
            return false;
        if(updateDTO.getRepairPrice() == null)
            return false;
        //如果支付状态不是VALID_PAYMENT_STATUS中的一种，则返回false
        if(!VALID_PAYMENT_STATUS.contains(updateDTO.getPaymentStatus()))
            return false;
        //维修描述不能为空
        if(updateDTO.getRepairNotes() == null)
            return false;
        //技术人员不能为空
        if(updateDTO.getTechnicianId() == null)
            return false;
        //判断user表中是否存在该ID
        User Technician = userMapper.selectById(updateDTO.getTechnicianId());
        if(Technician == null)
            return false;
        //构建更新对象
        Management management = new Management();
        management.setRepairId(updateDTO.getRepairId());
        management.setRepairPrice(updateDTO.getRepairPrice().toString());
        management.setPaymentStatus(updateDTO.getPaymentStatus());
        management.setRepairNotes(updateDTO.getRepairNotes());
        management.setTechnicianId(updateDTO.getTechnicianId().toString());
        management.setUpdatedAt(LocalDateTime.now());
        //调用MybatisPlus的updateById方法
        int updateCount = managementMapper.updateById(management);
        return updateCount > 0;
    }
}
