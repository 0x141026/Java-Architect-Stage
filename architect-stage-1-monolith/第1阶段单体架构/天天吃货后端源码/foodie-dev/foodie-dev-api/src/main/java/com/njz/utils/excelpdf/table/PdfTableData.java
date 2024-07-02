package com.njz.utils.excelpdf.table;

public class PdfTableData<T> {
    private T dataObj;

    public PdfTableData(T dataObj) {
        this.dataObj = dataObj;
    }

    public T getDataObj() {
        return dataObj;
    }

    public void setDataObj(T dataObj) {
        this.dataObj = dataObj;
    }
}
