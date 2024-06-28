package com.njz.utils.excelpdf;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FundDetailsPDFGenerator extends PdfPageEventHelper {
    private final float offSetY = 3; //canva绘制后的图形
    private Font paragraphFont;
    private Font titleFont;
    private BaseFont baseFont;
    private PdfPTable table;
    private PdfTemplate totalPage;
    private String sealPath;
    /**
     * 页脚的高度
     */
    private float footHeight;
    /**
     * 页脚的文字
     */
    private String footText;

    public FundDetailsPDFGenerator(BaseFont baseFont, Font titleFont, Font paragraphFont, float footHeight, String footText, String sealPath) {
        // 初始化字体
        this.baseFont = baseFont;
        this.titleFont = titleFont;
        this.paragraphFont = paragraphFont;
        this.footHeight = footHeight;
        this.footText = footText;
        this.sealPath = sealPath;
        List<String> headers = Arrays.asList("入账时间", "入账金额", "出账金额", "余额", "对方账号", "对方户名", "对方开户行名", "摘要", "附言");
        List<Integer> alignments = Arrays.asList(Element.ALIGN_CENTER, Element.ALIGN_RIGHT, Element.ALIGN_RIGHT,
                Element.ALIGN_RIGHT, Element.ALIGN_CENTER, Element.ALIGN_CENTER,
                Element.ALIGN_CENTER, Element.ALIGN_CENTER, Element.ALIGN_CENTER);
        // 创建表格并设置默认单元格样式
        this.table = new PdfPTable(headers.size());// 5列的表格
        table.setWidthPercentage(100);// 表格宽度为页面宽度的 100%
        table.setSpacingBefore(10f);// 表格前的间距
        OpenpdfUtils.addTableHeader(table, paragraphFont, headers, Element.ALIGN_CENTER, Element.ALIGN_CENTER);
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
        totalPage = writer.getDirectContent().createTemplate(60, 18);
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
        footerText.addElement(new Paragraph(footText, paragraphFont));
        footerText.go();
        // 添加页码
        String pageNumberString = String.format("页码 %d / ", writer.getPageNumber());
        Phrase pageNumberPhrase = new Phrase(pageNumberString, paragraphFont);
        float centerPosition = (document.right() - document.left()) / 2 + document.leftMargin();
        ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, pageNumberPhrase,
                centerPosition, document.bottom() - paragraphFont.getSize() - footHeight, 0);

        // 添加总页数画布到指定位置
        cb.addTemplate(totalPage, centerPosition, document.bottom() - paragraphFont.getSize() - offSetY - footHeight);

        try {
            OpenpdfUtils.sealOnPdf(writer, document, sealPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCloseDocument(PdfWriter writer, Document document) {
        // 文档关闭时调用，显示总页数
        totalPage.beginText();
        totalPage.setFontAndSize(baseFont, paragraphFont.getSize());
        totalPage.setTextMatrix(0, offSetY);
        String totalPageString = "共" + (writer.getPageNumber() - 1) + "页";
        totalPage.showText(totalPageString);
        totalPage.endText();
    }
}
