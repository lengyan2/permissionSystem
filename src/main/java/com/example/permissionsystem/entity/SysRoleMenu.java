package com.example.permissionsystem.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysRoleMenu implements Serializable {

    private  Integer id;

    private Integer roleId;

    private Integer menuId;
}
