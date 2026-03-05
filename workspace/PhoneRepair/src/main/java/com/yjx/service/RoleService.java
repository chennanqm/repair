package com.yjx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yjx.pojo.Role;
import com.yjx.unti.Result;

import java.util.List;

public interface RoleService extends IService<Role> {
    Result<List<Role>> listAllRoles();
}
