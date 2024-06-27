package com.njz.utils.excelpdf;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.njz.utils.excelpdf.dto.FundDetail;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FundDetailsPDFGenerator extends PdfPageEventHelper {
    private Font paragraphFont;
    private Font titleFont;
    private BaseFont baseFont;
    private PdfPTable table;
    private PdfTemplate total;
    private float footHeight;
    private final float offSetY = 3; //canva绘制后的图形
    private String footStr;

    public FundDetailsPDFGenerator(BaseFont baseFont, Font titleFont, Font paragraphFont, float footHeight, String footStr) {
        // 初始化字体
        this.baseFont = baseFont;
        this.titleFont = titleFont;
        this.paragraphFont = paragraphFont;
        this.footHeight = footHeight;
        this.footStr = footStr;

        // 创建表格并设置默认单元格样式
        this.table = new PdfPTable(5);// 5列的表格
        table.setWidthPercentage(100);// 表格宽度为页面宽度的 100%
        table.setSpacingBefore(10f);// 表格前的间距
        addTableHeader(table, paragraphFont);
    }

    /**
     * 在文档打开时调用。这是在写入内容之前第一次调用Document.open()时触发的。
     * 可以用于初始化全局资源，如全局字体或样式设置。
     * @param writer
     * @param document
     */
    @Override
    public void onOpenDocument(PdfWriter writer, Document document) {
        // 文档打开时创建总页数模板
        total = writer.getDirectContent().createTemplate(60, 18);
    }
    @Override
    public void onStartPage(PdfWriter writer, Document document) {
        // 每页开始时调用，如果不是第一页，则添加表头
        if (writer.getPageNumber() > 1) {
            document.add(table);
        }
    }
    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        // 每页结束时调用，添加页脚
        PdfContentByte cb = writer.getDirectContent();
        // 设置页脚文本的坐标
        //document.bottom() 方法返回当前页面的底部边界的 Y 坐标
        // 获取页面大小和边距
        float left = document.left();
        float right = document.right();
        float bottom = document.bottom() - footHeight;
        float top = document.bottom() + footHeight - footHeight;; // 根据字体大小设置顶部坐标

        ColumnText footerText = new ColumnText(cb);
        footerText.setSimpleColumn(left, bottom, right, top);
        footerText.addElement(new Paragraph(footStr, paragraphFont));
        footerText.go();
        // 添加页码
        String pageNumberString = String.format("页码 %d / ", writer.getPageNumber());
        Phrase pageNumberPhrase = new Phrase(pageNumberString, paragraphFont);
        float center = (document.right() - document.left()) / 2 + document.leftMargin();
        ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT,
                pageNumberPhrase,
                center,
                document.bottom() - paragraphFont.getSize() - footHeight,
                0);

        cb.addTemplate(total, center, document.bottom() - paragraphFont.getSize() - offSetY - footHeight);
    }

    @Override
    public void onCloseDocument(PdfWriter writer, Document document) {
        // 文档关闭时调用，设置总页数
        total.beginText();
        total.setFontAndSize(baseFont, paragraphFont.getSize());
        total.setTextMatrix(0, offSetY);
        String totalPageString = "共" + (writer.getPageNumber() - 1) + "页";
        total.showText(totalPageString);
        total.endText();
    }
    private void addTableHeader(PdfPTable table, Font font) {
        PdfPCell cell = this.createWrappingCell("转账日期", font);
        PdfPCell cell1 = this.createWrappingCell("转出人", font);
        PdfPCell cell2 = this.createWrappingCell("转出账号", font);
        PdfPCell cell3 = this.createWrappingCell("转入账号", font);
        table.addCell(cell);
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);

        // 余额表头右对齐
        PdfPCell balanceHeader = this.createWrappingCell("余额", font);
        balanceHeader.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(balanceHeader);
    }

    // 辅助方法：创建一个自动换行的 PdfPCell
    private PdfPCell createWrappingCell(String content, Font font) {
        Phrase phrase = new Phrase(content, font);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setNoWrap(false);
        return cell;
    }
}
