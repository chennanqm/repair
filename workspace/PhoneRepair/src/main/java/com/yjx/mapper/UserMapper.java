package com.yjx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjx.pojo.User;
import org.apache.ibatis.annotations.*;

public interface UserMapper extends BaseMapper<User> {
    @Select("SELECT * FROM yjx_user WHERE user_id = #{userId}")
    User selectById(@Param("userId")Integer repairId);
    @Select("""
        SELECT
            user_id,
            user_email,
            user_phone,
            user_password_hash
        FROM yjx_user
        WHERE user_email = #{email}
    """)
    User selectByEmail(@Param("email") String email);
    @Update("""
        UPDATE yjx_user
        SET
            user_password_hash = #{password}
        WHERE user_id = #{userId}
    """)
    int updateUserPassword(@Param("userId") Integer userId, @Param("password") String password);
    //查询所有用户
    @Select("""
    SELECT 
        user_id AS userId,
        user_name AS userName,
        user_email AS userEmail,
        user_password_hash AS userPasswordHash,
        role_id AS roleId,
        user_bio AS userBio,
        user_phone AS userPhone,
        user_gender AS userGender,
        user_last_active AS userLastActive,
        user_created_at AS userCreatedAt,
        user_status AS userStatus
    FROM yjx_user
    WHERE 1=1
    -- 用户ID筛选：仅当userId不为null时添加条件
    AND ( 1=1 )
    -- 关键词搜索：仅当searchKeyword不为空时添加模糊匹配条件
    AND ( 
        #{searchKeyword} IS NULL OR #{searchKeyword} = '' OR
        user_name LIKE CONCAT('%', #{searchKeyword}, '%') OR
        user_email LIKE CONCAT('%', #{searchKeyword}, '%') OR
        user_phone LIKE CONCAT('%', #{searchKeyword}, '%')
    )
    -- 排序：与维修管理查询逻辑完全一致
    ORDER BY 
        CASE WHEN #{sortField} IS NOT NULL AND #{sortField} != '' 
             THEN CASE #{sortField} 
                  WHEN 'userName' THEN user_name 
                  WHEN 'userCreatedAt' THEN user_created_at 
                  WHEN 'userLastActive' THEN user_last_active 
                  WHEN 'userStatus' THEN user_status 
                  ELSE user_created_at END 
        ELSE user_created_at END 
        ${sortOrder != null && 'asc'.equals(sortOrder.toLowerCase()) ? 'ASC' : 'DESC'}
""")
    IPage<User> selectAllUser(Page<User> page, int userId, String searchKeyword,
                              String sortField, String sortOrder);
    @Update("UPDATE yjx_user " +
            "SET user_name = #{userName}, " +
            "role_id = #{roleId}, " +
            "user_bio = #{userBio}, " +
            "user_phone = #{userPhone} " +
            "WHERE user_id = #{userId}")
    int updateUser(User user);
    @Delete("delete from yjx_user where user_id =#{userId}")
    int deleteUserByUserId(Integer userId);
}
