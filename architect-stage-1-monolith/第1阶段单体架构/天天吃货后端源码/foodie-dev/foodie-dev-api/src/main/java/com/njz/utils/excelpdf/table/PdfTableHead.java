package com.njz.utils.excelpdf.table;

import java.util.ArrayList;
import java.util.List;

public class PdfTableHead {
    private List<String> heads;

    public PdfTableHead(List<String> heads) {
        this.heads = heads;
    }

    public List<String> getHeads() {
        return heads;
    }

    public void setHeads(ArrayList<String> heads) {
        this.heads = heads;
    }
}
