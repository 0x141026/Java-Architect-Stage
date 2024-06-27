package com.imooc.controller;

import com.imooc.mapper.FileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

@ApiIgnore
@RestController
public class FileController {
    @Autowired
    FileMapper fileMapper;

    @PostMapping(value = "getFile")
    public void downloadFile(
            @RequestParam(value = "id") String id,
            HttpServletResponse httpServletResponse,
            HttpServletRequest httpServletRequest
    ) {
        InputStream content = fileMapper.selectContent(id);
        try(BufferedInputStream bufferedInputStream = new BufferedInputStream(content)) {
            httpServletResponse.setContentType("application/octet-stream");
            httpServletResponse.setHeader("Content-Disposition", "attachment;filename=" + "a.zip");
            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
            FileCopyUtils.copy(bufferedInputStream, servletOutputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
