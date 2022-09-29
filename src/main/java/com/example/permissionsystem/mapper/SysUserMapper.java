package com.example.permissionsystem.mapper;

import com.example.permissionsystem.entity.SysRole;
import com.example.permissionsystem.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.management.relation.Role;
import java.util.List;
import java.util.Set;

@Mapper
public interface SysUserMapper {

    /**
     * 通过用户名找到用户信息
     */
    SysUser findByUsername(@Param("username") String  username);

    Set<SysRole> getRoles(@Param("userId") Integer userId);
}
