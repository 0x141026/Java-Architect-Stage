package com.njz.utils.excelpdf;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.njz.utils.excelpdf.table.CustomedPdfTable;
import com.njz.utils.excelpdf.table.PdfTableData;
import com.njz.utils.excelpdf.table.PdfTableHead;

import java.io.IOException;
import java.util.List;

public abstract class AbstractOpenPdfUtils<T> {

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
    public void addPdfTable(PdfPTable table, Font font, CustomedPdfTable<T> customedPdfTable, Class<T> clazz) {
        PdfTableHead pdfTableHead = customedPdfTable.getTableHead();
        List<String> heads = pdfTableHead.getHeads();
        List<PdfTableData<T>> pdfTableDataList = customedPdfTable.getTableDataList();
        if (heads.size() != clazz.getDeclaredFields().length) {
            throw new RuntimeException("表格的表头列数和表格的内容列数不相等！");
        }
        this.addTableHeader(table, font, pdfTableHead, (Integer) customedPdfTable.getTableHeadHorizontalAlignments().get(0), customedPdfTable.getVerticalAlignment());
        this.addTableData(table, font, pdfTableDataList, customedPdfTable.getTableDataHorizontalAlignments(), customedPdfTable.getVerticalAlignment());
    }

    public static void addTableHeader(PdfPTable table, Font font, PdfTableHead pdfTableHead, Integer horizontalAlignment, int verticalAlignment) {
        List<String> headers = pdfTableHead.getHeads();
        for (int i = 0; i < headers.size(); i++) {
            addCellToTable(table, headers.get(i), font, horizontalAlignment, verticalAlignment, null);
        }
    }

    public abstract void addTableData(PdfPTable table, Font font, List<PdfTableData<T>> tableDataList, List<Integer> horizontalAlignments, int verticalAlignment);

    public static void addCellToTable(PdfPTable table, String text, Font font, int horizontalAlignment, int verticalAlignment, Float borderWidth) {
        PdfPCell cell = createCustomedStyleCell(text, font, horizontalAlignment, verticalAlignment, borderWidth);
        table.addCell(cell);
    }

    public static PdfPCell createCustomedStyleCell(String text, Font font, int horizontalAlignment, int verticalAlignment, Float borderWidth) {
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
