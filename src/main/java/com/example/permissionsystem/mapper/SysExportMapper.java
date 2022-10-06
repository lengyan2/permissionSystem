package com.example.permissionsystem.mapper;

import com.example.permissionsystem.entity.SysUser;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysExportMapper {

    // 导出数据的总行数
    int totalNum();
    // 分页查询数据
    @MapKey("id")
    List<Map<String,Object>> downloadByCount(@Param("currentSize") int currentSize,
                                  @Param("pageSize") int pageSize);
}
