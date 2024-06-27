package com.njz.easyexcel;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data
public class ExcelQuery {
    @NotNull(message = "文件不允许为空！")
    private MultipartFile file;

    @NotNull(message = "mark不允许为空！")
    private String mark;
}
