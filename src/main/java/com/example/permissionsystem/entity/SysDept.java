package com.example.permissionsystem.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysDept implements Serializable {

    private Integer id;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 父级部门id
     */
    private Integer pid;
}
