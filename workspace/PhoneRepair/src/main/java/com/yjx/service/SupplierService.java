package com.yjx.service;

import com.yjx.pojo.Supplier;

import java.util.Map;

public interface SupplierService {
    Map<String, Object> getAllSupplierManagement(String searchKeyword, Integer pageNum, Integer pageSize, String sortField, String sortOrder);

    boolean createSupplierManagement(Supplier supplier);

    boolean updateSupplierManagement(Supplier supplier);

    boolean deleteSupplierManagement(Integer supplierManagementId, Integer userId, String userPasswd);
}
