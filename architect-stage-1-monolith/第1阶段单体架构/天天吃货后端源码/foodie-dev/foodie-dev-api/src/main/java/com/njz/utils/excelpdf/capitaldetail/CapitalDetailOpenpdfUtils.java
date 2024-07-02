package com.njz.utils.excelpdf.capitaldetail;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPTable;
import com.njz.utils.excelpdf.AbstractOpenPdfUtils;
import com.njz.utils.excelpdf.dto.FundCapitalDetail;
import com.njz.utils.excelpdf.table.PdfTableData;

import java.util.List;

public class CapitalDetailOpenpdfUtils extends AbstractOpenPdfUtils<FundCapitalDetail> {
    @Override
    public void addTableData(PdfPTable table, Font font, List<PdfTableData<FundCapitalDetail>> customedTable, List<Integer> horizontalAlignments, int verticalAlignment) {
        int fieldCount = FundCapitalDetail.class.getDeclaredFields().length;
        if (horizontalAlignments.size() != fieldCount) {
            throw new IllegalArgumentException("Alignments list must have exactly " + fieldCount + " elements.");
        }

        for (PdfTableData tableData : customedTable) {
            FundCapitalDetail detail = (FundCapitalDetail) tableData.getDataObj();
            addCellToTable(table, detail.getInDate(), font, horizontalAlignments.get(0), verticalAlignment, null);
            addCellToTable(table, detail.getInMoney(), font, horizontalAlignments.get(1), verticalAlignment, null);
            addCellToTable(table, detail.getOutMoney(), font, horizontalAlignments.get(2), verticalAlignment, null);
            addCellToTable(table, detail.getBalance(), font, horizontalAlignments.get(3), verticalAlignment, null);
            addCellToTable(table, detail.getReceiveAccountNo(), font, horizontalAlignments.get(4), verticalAlignment, null);
            addCellToTable(table, detail.getReceiveAccountName(), font, horizontalAlignments.get(5), verticalAlignment,null);
            addCellToTable(table, detail.getReceiveAccountBankName(), font, horizontalAlignments.get(6), verticalAlignment, null);
            addCellToTable(table, detail.getDigest(), font, horizontalAlignments.get(7), verticalAlignment, null);
            addCellToTable(table, detail.getPostscript(), font, horizontalAlignments.get(8), verticalAlignment, null);
        }
    }
}
