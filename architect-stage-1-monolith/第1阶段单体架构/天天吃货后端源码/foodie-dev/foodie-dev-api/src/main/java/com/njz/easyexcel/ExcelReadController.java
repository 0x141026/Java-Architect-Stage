package com.njz.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.SyncReadListener;
import com.njz.http.ResponseBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/easyexcel/read")
@Slf4j
//@Validated
public class ExcelReadController {

    @RequestMapping("readExcel")
    ResponseBean<Void> read(@Valid ExcelQuery qry) throws IOException {
        ExcelListener excelListener = new ExcelListener();
        EasyExcel.read(qry.getFile().getInputStream(), ExcelDTO.class, excelListener)
                .sheet(0)
                .doRead();
        System.out.println("没有被阻塞住！");
        System.out.println(excelListener.getCachedDataList().toString());
        return ResponseBean.ok("success","1");
    }

    @RequestMapping("readExcelSync")
    ResponseBean<Void> readExcelSync(@Valid ExcelQuery qry) throws IOException {
        List<ExcelDTO> list = EasyExcel.read(qry.getFile().getInputStream(), ExcelDTO.class, new SyncReadListener())
                .sheet(0)
                .doReadSync();
        System.out.println("没有被阻塞住！");
        System.out.println(list.toString());
        return ResponseBean.ok("success","1");
    }

}
