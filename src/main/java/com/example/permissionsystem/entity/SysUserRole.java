package com.example.permissionsystem.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysUserRole implements Serializable {
     private  Integer id;

     private  Integer roleId;

     private Integer userId;

}
