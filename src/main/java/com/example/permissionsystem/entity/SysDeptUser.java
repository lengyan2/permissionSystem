package com.example.permissionsystem.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysDeptUser implements Serializable {
    private Integer id;

    private Integer did; // 部门id

    private Integer uid; // 用户id

}
