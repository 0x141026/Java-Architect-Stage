package com.njz.utils.excelpdf;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.njz.utils.excelpdf.dto.FundCapitalDetail;

import java.io.IOException;
import java.util.List;

public class OpenpdfUtils {

    public static void sealOnPdf(PdfWriter writer, Document document, String sealPath) throws IOException {
        Image image = Image.getInstance(sealPath);

        Rectangle pageSize = document.getPageSize();
        image.scaleAbsolute(120, 120);
        float x = document.leftMargin();
        // x、y是图片左下角在文档中的位置
        image.setAbsolutePosition(x - 40, pageSize.getHeight() - image.getScaledHeight() - document.topMargin());
        PdfContentByte content = writer.getDirectContentUnder();
        // 添加图片
        content.addImage(image);
    }
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
            addCellToTable(table, headers.get(i), font, horizontalAlignment, verticalAlignment, null);
        }
    }

    public static void addTableData(Document document, PdfPTable table, Font font, List<FundCapitalDetail> datas, List<Integer> horizontalAlignments, int verticalAlignment) {
        int fieldCount = FundCapitalDetail.class.getDeclaredFields().length;
        if (horizontalAlignments.size() != fieldCount) {
            throw new IllegalArgumentException("Alignments list must have exactly " + fieldCount + " elements.");
        }

        for (FundCapitalDetail detail : datas) {
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
        document.add(table);
    }

    private static void addCellToTable(PdfPTable table, String text, Font font, int horizontalAlignment, int verticalAlignment, Float borderWidth) {
        PdfPCell cell = createCustomedStyleCell(text, font, horizontalAlignment, verticalAlignment, borderWidth);
        table.addCell(cell);
    }

    private static PdfPCell createCustomedStyleCell(String text, Font font, int horizontalAlignment, int verticalAlignment, Float borderWidth) {
        PdfPCell cell = new PdfPCell(new Paragraph(text, font));
        cell.setNoWrap(false);
        cell.setHorizontalAlignment(horizontalAlignment);
        cell.setVerticalAlignment(verticalAlignment);
        if (!(borderWidth == null)) {
            cell.setBorderWidth(borderWidth);
        }
        return cell;
    }

    public static float cmToPoints(float cm) {
        return cm * 28.35f;
    }
}
