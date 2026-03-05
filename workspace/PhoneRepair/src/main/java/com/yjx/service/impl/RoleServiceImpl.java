package com.yjx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjx.mapper.RoleMapper;
import com.yjx.pojo.Role;
import com.yjx.service.RoleService;
import com.yjx.unti.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public Result<List<Role>> listAllRoles() {
        List<Role> roles = roleMapper.listAllRoles();
        return Result.success(roles);
    }
}
