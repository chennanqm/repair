package com.yjx.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjx.mapper.RepairMapper;
import com.yjx.mapper.UserMapper;
import com.yjx.module.ReceptionistVO;
import com.yjx.module.RepairQueryModule;
import com.yjx.pojo.Repair;
import com.yjx.pojo.User;
import com.yjx.service.RepairService;
import com.yjx.service.UserService;
import com.yjx.unti.Md5Password;
import com.yjx.unti.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RepairServiceImpl extends ServiceImpl<RepairMapper, Repair> implements RepairService {
    @Autowired
    private RepairMapper repairMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @Override
    public Result<Map<String, Object>> getRepairListByCondition(RepairQueryModule repairQueryModule) {
        //处理分页参数
        int pageNum = repairQueryModule.getPageNum() ==  null ? 1 : repairQueryModule.getPageNum();
        int pageSize = repairQueryModule.getPageSize() ==  null ? 10 : repairQueryModule.getPageSize();

        //处理关键词和排序
        String searchKeyword = repairQueryModule.getSearchKeyword() == null ? "" : repairQueryModule.getSearchKeyword();
//        String sortField = repairQueryModule.getSortField() == null ? "createdAt" : repairQueryModule.getSortField();
        String sortField;
        if (repairQueryModule.getSortField() == null || repairQueryModule.getSortField().equals("createdAT"))
            sortField = "created_at";
        else if(repairQueryModule.getSortField().equals("requestId"))
            sortField = "request_id";
        else
            sortField = "created_at";

        String sortOrder = repairQueryModule.getSortOrder() == null ? "desc" : repairQueryModule.getSortOrder();

        //使用 MyBatis-Plus 的 Page 对象（关键修改）
        IPage<Repair> page = new Page<>(pageNum, pageSize);

        //调用 mapper 方法
        IPage<Repair> pageResult = repairMapper.selectRepairByCondition(
                (Page<Repair>) page,
                repairQueryModule.getUserId(),
                searchKeyword,
                sortField,
                sortOrder
        );

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("count", pageResult.getTotal());
        responseMap.put("repairRequest", pageResult.getRecords());
        return Result.success(responseMap);
    }
    @Override
    public List<ReceptionistVO> getAllReceptionist() {
        List<ReceptionistVO> list = repairMapper.getAllReceptionist();
        return list;
    }
    @Override
    public boolean createRepair(Repair repair) {
        //参数的非空校验
        if (repair.getUserId() == null)
            return false;
        if (repair.getPhoneModel() == null)
            return false;
        if(repair.getPhoneIssueDescription() == null)
            return false;

        repair.setRequestStatus(1);
        repair.setCreatedAt(LocalDateTime.now());
        repair.setUpdatedAt(LocalDateTime.now());

        return save(repair);
    }
    @Override
    public boolean deleteRepair(Integer repairId, Integer userId, String password) {
        //判断repairId是否为空
        if(repairId == null || userId == null || password == null)
            return false;
        User user = userMapper.selectById(userId);
        if(user == null)
            return false;
        if(!Md5Password.generateMD5(password).equals(user.getUserPasswordHash()))
            return false;
        //删除
        Boolean result = repairMapper.deleteRepairByIdAndUserId(repairId, userId) > 0   ;
        return result;
    }
}
