package com.njz.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExcelDTO {
    @ExcelProperty(index = 0, value = "1")
    private String name;

    @ExcelProperty(index = 1, value = "2")
    private String age;

    @ExcelProperty(index = 2, value = "3")
    private String gender;

    @ExcelProperty(index = 3, value = "4")
    private String major;

    @ExcelProperty(index = 4, value = "5")
    private String cash;

    @ExcelProperty(index = 5, value = "6")
    private String remark;

    private String dataNo;

    @Override
    public String toString() {
        return "ExcelDTO{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", gender='" + gender + '\'' +
                ", major='" + major + '\'' +
                ", cash='" + cash + '\'' +
                ", remark='" + remark + '\'' +
                ", dataNo='" + dataNo + '\'' +
                '}';
    }
}
