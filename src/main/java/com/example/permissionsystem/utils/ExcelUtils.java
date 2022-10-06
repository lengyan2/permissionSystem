package com.example.permissionsystem.utils;

import com.example.permissionsystem.mapper.SysExportMapper;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ExcelUtils {



    public static SXSSFWorkbook sxssfWorkbook ;
    public static SXSSFSheet sxssfSheet;
    public static List<String> headList ;
    public static void init(Class<?> tClass){
        sxssfWorkbook = new SXSSFWorkbook();
        sxssfSheet = sxssfWorkbook.createSheet();
        headList = new ArrayList<>();
        SXSSFRow headrow = sxssfSheet.createRow(0);
        Field[] fields = tClass.getDeclaredFields();

        for (Field field : fields) {
            String name = field.getName();
            headList.add(name);
        }
        for (int j = 0; j < headList.size(); j++) {
            headrow.createCell(j).setCellValue(headList.get(j));
        }
    }
    /**
     *
     * @param httpServletResponse
     * @param listCount 一次读取的数量
     * @param totalCount 导出的总行数
     */
    public static SXSSFWorkbook  download(HttpServletResponse httpServletResponse, int listCount, int totalCount, Class<?> tClass, List<Map<String, Object>> sysUsers) throws IOException {


        // 遍历数据库查询到的数据

            for (int j = 0; j < sysUsers.size(); j++) {
                SXSSFRow dataRow = sxssfSheet.createRow(sxssfSheet.getLastRowNum()+1);
                for (int k = 0; k < headList.size(); k++) {
                    Object o = sysUsers.get(j).get(headList.get(k));
                    String s = o.toString();
                    dataRow.createCell(k).setCellValue(s);
                }
            }

        return sxssfWorkbook;
        }
    public static void createFile(HttpServletResponse response,SXSSFWorkbook sxssfWorkbook) throws IOException {
        String filename = "1111";
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel");
        //设置成xlsx格式
        response.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode( filename+ ".xlsx","UTF-8"));
        //创建输出流
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.flush();
        //写入数据
        sxssfWorkbook.write(outputStream);
        //关闭流
        outputStream.close();
        sxssfWorkbook.close();
    }



}
