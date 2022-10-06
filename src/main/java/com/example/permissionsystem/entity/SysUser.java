package com.example.permissionsystem.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

/**
 * @Author daiyuanjing
 * @Date 2022/9/28
 */
@Data
public class SysUser  implements Serializable {
    private Integer id;

    private String username;

    private String password;

    private String mobile;

    private Integer enabled;

    private Date createTime;

    private Date UpdateTime;



}
