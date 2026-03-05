package com.yjx.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjx.mapper.SupplierMapper;
import com.yjx.mapper.UserMapper;
import com.yjx.pojo.Supplier;
import com.yjx.service.SupplierService;
import com.yjx.unti.Md5Password;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    private SupplierMapper supplierMapper;

    @Autowired
    private UserMapper userMapper;
    @Override
    public Map<String, Object> getAllSupplierManagement(String searchKeyword, Integer pageNum, Integer pageSize, String sortField, String sortOrder) {
        Page<Supplier> page = new Page<>(pageNum, pageSize);
        IPage<Supplier> supplierIPage = supplierMapper.selectSupplierByCondition(
                page,
                searchKeyword,
                sortField,
                sortOrder
        );
        Map<String, Object> result = new HashMap<>();
        result.put("supplierManagementList", supplierIPage.getRecords());
        result.put("count", supplierIPage.getTotal());
        return result;
    }

    @Override
    public boolean createSupplierManagement(Supplier supplier) {
        supplier.setSupplierManagementId(null);
        supplier.setCreatedAt(LocalDateTime.now());
        supplier.setUpdatedAt(LocalDateTime.now());
        //插入数据库，MyBatis-Plus 会自动处理自增主键
        return supplierMapper.insert(supplier) > 0;
    }

    @Override
    public boolean updateSupplierManagement(Supplier supplier){
        supplier.setUpdatedAt(LocalDateTime.now());
        return supplierMapper.updateById(supplier)>0;
    }
    @Override
    public boolean deleteSupplierManagement(Integer supplierManagementId, Integer userId, String userPasswd) {
        String password = userMapper.selectById(userId).getUserPasswordHash();
        if(password == null)
            return false;
        if(!password.equals(Md5Password.generateMD5(userPasswd)))
            return false;
        return supplierMapper.deleteByManagementId(supplierManagementId)> 0;
    }
}
