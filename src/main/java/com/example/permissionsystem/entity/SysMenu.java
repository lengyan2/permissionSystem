package com.example.permissionsystem.entity;

import lombok.Data;

import java.util.Set;

@Data
public class SysMenu {

    private Integer id;

    private String code;

    private String name;

    private String  url;

    /**
     * 资源类型 1 菜单 2 按钮
     */
    private Integer type;

    private  Integer sort;

}
