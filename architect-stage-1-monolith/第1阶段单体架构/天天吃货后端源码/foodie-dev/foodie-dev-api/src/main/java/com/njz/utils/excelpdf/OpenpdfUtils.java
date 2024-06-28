package com.njz.utils.excelpdf;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.njz.utils.excelpdf.dto.FundCapitalDetail;

import java.util.List;
import java.util.stream.IntStream;

public class OpenpdfUtils {
    /**
     * 在第一页生成表格之前依次插入段落说明文字
     * @param document
     * @param textList
     * @param paragraphFont
     */
    public static void addTextBeforeTable(Document document, List<String> textList, Font paragraphFont){
        for (String text : textList) {
            document.add(new Paragraph(text, paragraphFont));
        }
    }

    public static void addTableHeader(PdfPTable table, Font font, List<String> headers, Integer horizontalAlignment, int verticalAlignment) {
        for (int i = 0; i < headers.size(); i++) {
            addCellToTable(table, headers.get(i), font, horizontalAlignment, verticalAlignment);
        }
    }

    public static void addTableData(Document document, PdfPTable table, Font font, List<FundCapitalDetail> datas, List<Integer> horizontalAlignments, int verticalAlignment) {
        int fieldCount = FundCapitalDetail.class.getDeclaredFields().length;
        if (horizontalAlignments.size() != fieldCount) {
            throw new IllegalArgumentException("Alignments list must have exactly " + fieldCount + " elements.");
        }

        for (FundCapitalDetail detail : datas) {
            addCellToTable(table, detail.getInDate(), font, horizontalAlignments.get(0), verticalAlignment);
            addCellToTable(table, detail.getInMoney(), font, horizontalAlignments.get(1), verticalAlignment);
            addCellToTable(table, detail.getOutMoney(), font, horizontalAlignments.get(2), verticalAlignment);
            addCellToTable(table, detail.getBalance(), font, horizontalAlignments.get(3), verticalAlignment);
            addCellToTable(table, detail.getReceiveAccountNo(), font, horizontalAlignments.get(4), verticalAlignment);
            addCellToTable(table, detail.getReceiveAccountName(), font, horizontalAlignments.get(5), verticalAlignment);
            addCellToTable(table, detail.getReceiveAccountBankName(), font, horizontalAlignments.get(6), verticalAlignment);
            addCellToTable(table, detail.getDigest(), font, horizontalAlignments.get(7), verticalAlignment);
            addCellToTable(table, detail.getPostscript(), font, horizontalAlignments.get(8), verticalAlignment);
        }
        document.add(table);
    }

    private static void addCellToTable(PdfPTable table, String text, Font font, int horizontalAlignment, int verticalAlignment) {
        PdfPCell cell = createWrappingCell(text, font);
        cell.setHorizontalAlignment(horizontalAlignment);
        cell.setVerticalAlignment(verticalAlignment);
        table.addCell(cell);
    }

    private static PdfPCell createWrappingCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Paragraph(text, font));
        cell.setNoWrap(false);
        return cell;
    }
}
