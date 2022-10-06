package com.example.permissionsystem.controller;

import com.example.permissionsystem.entity.SysUser;
import com.example.permissionsystem.mapper.SysExportMapper;
import com.example.permissionsystem.utils.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class ExportController {
    @Autowired
    SysExportMapper sysExportMapper;
    @PostMapping("export")
    public void ExportFile(HttpServletResponse httpServletResponse) throws IOException {
        long start = System.currentTimeMillis();
        log.info("开始导出:{}", LocalDateTime.now());
        int listCount = 200000;
        int count = sysExportMapper.totalNum();
        int pageSize = count % 200000 > 0 ? (count / 200000) + 1 : count/2000000;
        ExcelUtils excelUtils = new ExcelUtils();
        SXSSFWorkbook sxssfWorkbook = null;
        ExcelUtils.init(SysUser.class);
        for (int i = 0; i < pageSize; i++) {
            List<Map<String, Object>> maps = sysExportMapper.downloadByCount(i * listCount, listCount);
            SXSSFWorkbook download = ExcelUtils.download(httpServletResponse, 200000, count, SysUser.class,maps);
            if (i==pageSize-1){
                ExcelUtils.createFile(httpServletResponse,download);
            }
        }
        long end = System.currentTimeMillis();
        log.info("导出完成:{}", LocalDateTime.now());
    }

}
