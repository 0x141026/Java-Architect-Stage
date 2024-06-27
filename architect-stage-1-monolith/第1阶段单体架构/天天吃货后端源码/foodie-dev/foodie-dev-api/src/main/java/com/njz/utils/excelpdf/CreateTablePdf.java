package com.njz.utils.excelpdf;

import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;

public class CreateTablePdf {
    public static void main1(String[] args) {
        System.out.println(System.getProperty("java.io.tmpdir"));
        System.out.println(System.getProperty("java.io.tmpdir"));

    }
    public static void main(String[] args) {
        Document document = new Document(PageSize.A4);
        try {
            String path = System.getProperty("java.io.tmpdir");
            PdfWriter.getInstance(document, new FileOutputStream(path + "TableExample.pdf"));
            document.open();

            // 添加标题
            document.add(new Paragraph("中国工商银行账户明细清单"));

            // 创建表格，假设有5列
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100); // 宽度100%填充
            table.setSpacingBefore(10f); // 前间距
            table.setSpacingAfter(10f); // 后间距

            // 定义表格的列宽度
            float[] columnWidths = {1f, 1f, 1f, 1f, 1f};
            table.setWidths(columnWidths);

            // 创建表格的表头
            Font font = getFont();
            PdfPCell header1 = new PdfPCell(new Phrase("入账时间", font));
            PdfPCell header2 = new PdfPCell(new Paragraph("收入金额", font));
            PdfPCell header3 = new PdfPCell(new Paragraph("支出金额", font));
            PdfPCell header4 = new PdfPCell(new Paragraph("对方账号", font));
            PdfPCell header5 = new PdfPCell(new Paragraph("摘要", font));

            table.addCell(header1);
            table.addCell(header2);
            table.addCell(header3);
            table.addCell(header4);
            table.addCell(header5);

            // 添加数据到表格
            table.addCell("2024-04-01");
            table.addCell("1,000.00");
            table.addCell("0.00");
            table.addCell("123456789");
            table.addCell("演示");

            // 可以添加更多的单元格...

            // 将表格添加到文档中
            document.add(table);

            // 关闭文档
            document.close();
            System.out.println("Table created successfully..");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Font getFont() throws IOException {
        BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
        Font font = new Font(baseFont);
        return font;
    }
}
