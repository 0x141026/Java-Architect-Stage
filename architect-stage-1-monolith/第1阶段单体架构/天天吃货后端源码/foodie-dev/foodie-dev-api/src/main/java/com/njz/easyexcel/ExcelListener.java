package com.njz.easyexcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ListUtils;

import java.util.List;

public class ExcelListener extends AnalysisEventListener<ExcelDTO> {
    public static final int BATCH_COUNT = 100;
    private List<ExcelDTO> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    @Override
    public void invoke(ExcelDTO data, AnalysisContext context) {
        Integer dataNo = context.readRowHolder().getRowIndex();
        data.setDataNo(dataNo.toString());
        cachedDataList.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        System.out.println("===========全部读取完毕后：");
        System.out.println(cachedDataList.toString());
    }

    public List<ExcelDTO> getCachedDataList() {
        return cachedDataList;
    }
}
