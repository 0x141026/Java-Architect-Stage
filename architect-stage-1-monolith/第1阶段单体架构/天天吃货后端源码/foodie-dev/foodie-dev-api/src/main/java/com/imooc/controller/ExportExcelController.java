package com.imooc.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@ApiIgnore
@RestController
public class ExportExcelController {


    @RequestMapping("/easyexcel-export")
    public void exportExcel(HttpServletResponse response) throws IOException {
        // 模拟用户数据
        List<UserData> userList = getUserData();

        // 获取列名或序号，这里假设要导出第1和第3列
        List<String> selectedColumnsName = ListUtils.newArrayList("emailAddress", "age");
        List<Integer> selectedColumnsIndex = ListUtils.newArrayList(Integer.valueOf(1));

        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=user_data.xlsx");

        UserData userData = new UserData();
        changeIndex(userData, "name", 2);
        changeIndex(userData, "age", 0);
        changeIndex(userData, "emailAddress", 1);
        // 创建ExcelWriter
        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), userData.getClass())
//                .includeColumnFiledNames(selectedColumnsName)
//                .excludeColumnIndexes(selectedColumnsIndex)
                .build();

        // 创建WriteSheet
        WriteSheet writeSheet = EasyExcel.writerSheet("用户数据").build();

        // 写入数据到指定列
        excelWriter.write(userList, writeSheet);
        System.out.println("写入成功");
        // 关闭ExcelWriter，输出流会自动关闭
        excelWriter.finish();
    }

    // 模拟获取用户数据的方法
    private List<UserData> getUserData() {
        List<UserData> userList = new ArrayList<>();
        // 添加一些用户数据
        for (int i = 0; i <5000; i++) {
            userList.add(new UserData("John", i, "john@example.com"));
        }
        return userList;
    }

    private static void changeIndex(Object object, String fieldName, int newIndex) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            if (field.isAnnotationPresent(ExcelProperty.class)) {
                ExcelProperty excelPropertyAnnotation = field.getAnnotation(ExcelProperty.class);
                Field indexField = ExcelProperty.class.getDeclaredField("index");
                indexField.setAccessible(true);
                indexField.set(excelPropertyAnnotation, newIndex);
            }

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
