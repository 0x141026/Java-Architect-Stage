package com.njz.utils.excelpdf.table;

import java.util.List;

public class CustomedPdfTable<T> {
    private PdfTableHead tableHead;

    private List<PdfTableData<T>> tableDataList;

    List<Integer> tableHeadHorizontalAlignments;

    List<Integer> tableDataHorizontalAlignments;

    int verticalAlignment;

    public CustomedPdfTable(PdfTableHead tableHead, List<PdfTableData<T>> tableDataList, List<Integer> tableHeadHorizontalAlignments, List<Integer> tableDataHorizontalAlignments, int verticalAlignment) {
        this.tableHead = tableHead;
        this.tableDataList = tableDataList;
        this.tableHeadHorizontalAlignments = tableHeadHorizontalAlignments;
        this.tableDataHorizontalAlignments = tableDataHorizontalAlignments;
        this.verticalAlignment = verticalAlignment;
    }

    public List<Integer> getTableHeadHorizontalAlignments() {
        return tableHeadHorizontalAlignments;
    }

    public void setTableHeadHorizontalAlignments(List<Integer> tableHeadHorizontalAlignments) {
        this.tableHeadHorizontalAlignments = tableHeadHorizontalAlignments;
    }

    public List<Integer> getTableDataHorizontalAlignments() {
        return tableDataHorizontalAlignments;
    }

    public void setTableDataHorizontalAlignments(List<Integer> tableDataHorizontalAlignments) {
        this.tableDataHorizontalAlignments = tableDataHorizontalAlignments;
    }

    public int getVerticalAlignment() {
        return verticalAlignment;
    }

    public void setVerticalAlignment(int verticalAlignment) {
        this.verticalAlignment = verticalAlignment;
    }

    public PdfTableHead getTableHead() {
        return tableHead;
    }

    public void setTableHead(PdfTableHead tableHead) {
        this.tableHead = tableHead;
    }

    public List<PdfTableData<T>> getTableDataList() {
        return tableDataList;
    }

    public void setTableDataList(List<PdfTableData<T>> tableDataList) {
        this.tableDataList = tableDataList;
    }
}
