package com.yjx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.github.pagehelper.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page; // 使用MP的Page
import com.yjx.module.ReceptionistVO;
import com.yjx.pojo.Repair;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RepairMapper extends BaseMapper<Repair> {
    /**
     * 分页查询维修单：支持权限过滤（userId=1/3查全部，其他查自己）、搜索、排序
     * @param page 分页参数（页码、每页条数）
     * @param userId 当前登录用户ID（用于权限过滤）
     * @param searchKeyword 搜索关键词（模糊查手机型号、问题描述）
     * @param sortField 排序字段（如createdAt、requestId）
     * @param sortOrder 排序方向（asc/desc）
     * @return 分页结果（含数据列表和总条数）
     */
    @Select("""
    SELECT
        rr.*,
        u.user_name AS receptionistName
    FROM yjx_repair_request rr
    LEFT JOIN yjx_user u ON rr.receptionist_id = u.user_id
    LEFT JOIN yjx_user cu ON cu.user_id = #{userId}
    WHERE 1=1
    AND ( cu.role_id IN (1,3) OR rr.user_id = #{userId} )
    AND ( rr.phone_model LIKE CONCAT('%', #{searchKeyword}, '%')
          OR rr.phone_issue_description LIKE CONCAT('%', #{searchKeyword}, '%') )
    ORDER BY
        ${sortField} ${sortOrder}
    """)
    IPage<Repair> selectRepairByCondition( Page<Repair> page,                // MP分页对象（自动处理limit和offset）
                                           @Param("userId") Integer userId,            // 权限过滤用的用户ID
                                           @Param("searchKeyword") String searchKeyword, // 搜索关键词
                                           @Param("sortField") String sortField,       // 排序字段
                                           @Param("sortOrder") String sortOrder );       // 排序方向);
    @Select("""
    SELECT
        user_id AS userId,
        user_name AS userName
    FROM yjx_user
    WHERE role_id = 3
    GROUP BY user_id, user_name
    """)
    List<ReceptionistVO> getAllReceptionist();

    @Delete("""
    DELETE FROM yjx_repair_request r
    WHERE r.request_id = #{repairId}
    AND (
        EXISTS (
            SELECT 1 FROM yjx_user u
            JOIN yjx_role ro ON u.role_id = ro.role_id
            WHERE u.user_id = #{userId}
            AND ro.role_id IN (1, 3)
        )
        OR r.user_id = #{userId}
    )
""")
    int deleteRepairByIdAndUserId(Integer repairId, Integer userId);

    @Select("SELECT * FROM yjx_repair_request WHERE request_id = #{requestId}")
    Repair selectById(Integer requestId);
}
