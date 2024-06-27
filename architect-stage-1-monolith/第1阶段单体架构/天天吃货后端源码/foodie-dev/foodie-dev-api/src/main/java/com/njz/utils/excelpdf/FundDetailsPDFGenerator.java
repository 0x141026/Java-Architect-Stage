package com.njz.utils.excelpdf;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.njz.utils.excelpdf.dto.FundDetail;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FundDetailsPDFGenerator extends PdfPageEventHelper{
    private Font paragrapyFont;
    private Font titleFont;
    private BaseFont baseFont;
    private PdfPTable table;
    private PdfTemplate total;
    private float footHeight;
    private final float offSetY = 3; //canva绘制后的图形

    public FundDetailsPDFGenerator(BaseFont baseFont, Font titleFont, Font paragrapyFont, PdfPTable table, float footHeight) {
        // 初始化字体
        this.baseFont = baseFont;
        this.titleFont = titleFont;
        this.paragrapyFont = paragrapyFont;
        this.table = table;
        this.footHeight = footHeight;
    }

    /**
     * 在文档打开时调用。这是在写入内容之前第一次调用Document.open()时触发的。
     * 可以用于初始化全局资源，如全局字体或样式设置。
     * @param writer
     * @param document
     */
    @Override
    public void onOpenDocument(PdfWriter writer, Document document) {
        // 文档打开时调用，创建表格
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
        footerText.addElement(new Paragraph("此账单如被修改，不具有法律效力。聂建洲聂建洲聂建洲聂建洲111111111111聂建洲聂建洲聂建洲222222222聂建洲聂建洲聂建洲33333333就斤斤计较经济斤斤计较男男女女男男女女男男女女男女"
                , paragrapyFont));
        footerText.go();
        // 添加页码
        String pageNumberString = String.format("页码 %d / ", writer.getPageNumber());
        Phrase pageNumberPhrase = new Phrase(pageNumberString, paragrapyFont);
        float center = (document.right() - document.left()) / 2 + document.leftMargin();
        ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT,
                pageNumberPhrase,
                center,
                document.bottom() - paragrapyFont.getSize() - footHeight,
                0);

        cb.addTemplate(total, center, document.bottom() - paragrapyFont.getSize() - offSetY - footHeight);
    }

    @Override
    public void onCloseDocument(PdfWriter writer, Document document) {
        // 文档关闭时调用，设置总页数
        total.beginText();
        total.setFontAndSize(baseFont, paragrapyFont.getSize());
        total.setTextMatrix(0, offSetY);
        String totalPageString = "共" + (writer.getPageNumber() - 1) + "页";
        total.showText(totalPageString);
        total.endText();
    }
//    private void addTableHeader(PdfPTable table, Font font) {
//        PdfPCell cell = this.createWrappingCell("转账日期", font);
//        PdfPCell cell1 = this.createWrappingCell("转出人", font);
//        PdfPCell cell2 = this.createWrappingCell("转出账号", font);
//        PdfPCell cell3 = this.createWrappingCell("转入账号", font);
//        table.addCell(cell);
//        table.addCell(cell1);
//        table.addCell(cell2);
//        table.addCell(cell3);
//
//        // 余额表头右对齐
//        PdfPCell balanceHeader = this.createWrappingCell("余额", font);
//        balanceHeader.setHorizontalAlignment(Element.ALIGN_RIGHT);
//        table.addCell(balanceHeader);
//    }
//
//    // 辅助方法：创建一个自动换行的 PdfPCell
//    private PdfPCell createWrappingCell(String content, Font font) {
//        Phrase phrase = new Phrase(content, font);
//        PdfPCell cell = new PdfPCell(phrase);
//        cell.setNoWrap(false);
//        return cell;
//    }
//    public static void main(String[] args) {
//        FundDetailsPDFGenerator fundDetailsPDFGenerator = new FundDetailsPDFGenerator();
//        fundDetailsPDFGenerator.generate();
//    }
//    public void generate() {
//        // 指定文件保存的路径
//        String filePath = System.getProperty("java.io.tmpdir") + "资金明细详情.pdf";
//        float marginPoint = cmToPoints(2.0f);
//        Document document = new Document(PageSize.A4.rotate(), marginPoint, marginPoint, marginPoint, marginPoint + footHeight);
//        try {
//            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
//            // 在打开文档之前设置页脚事件
//            FundDetailsPDFGenerator event = new FundDetailsPDFGenerator();
//            writer.setPageEvent(event);
//            document.open();
//
//            // 添加标题
//            Paragraph title = new Paragraph("资金明细详情", titleFont);
//            title.setAlignment(Element.ALIGN_CENTER);
//            document.add(title);
//
//            // 添加查询日期和查询人
//            document.add(new Paragraph("查询日期：" + new Date().toString(), paragrapyFont));
//            document.add(new Paragraph("查询人：小红", paragrapyFont));
//
//            // 创建表格并设置默认单元格样式
////            PdfPTable table = new PdfPTable(5); // 5 列的表格
////            table.setWidthPercentage(100); // 表格宽度为页面宽度的 100%
////            table.setSpacingBefore(10f); // 表格前的间距
//
//            // 设置余额列的默认单元格样式为右对齐
//            PdfPCell defaultBalanceCell = new PdfPCell();
//            defaultBalanceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
////            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
//
//            // 添加表头
//            addTableHeader(table);
//
//            // 填充表格数据
//            List<FundDetail> fundDetailsList = getFundDetailsList(); // 获取资金明细实体的列表
//            for (FundDetail fundDetail : fundDetailsList) {
//                table.addCell(this.createWrappingCell(fundDetail.getTransferDate()));
//                table.addCell(this.createWrappingCell(fundDetail.getPayer()));
//                table.addCell(this.createWrappingCell(fundDetail.getPayerAccount()));
//                table.addCell(this.createWrappingCell(fundDetail.getPayeeAccount()));
//
//                // 使用默认样式添加余额单元格
//                PdfPCell cell = this.createRightAlignedCell(String.valueOf(fundDetail.getBalance()));
//                table.addCell(cell);
//            }
//
//            document.add(table);
//            document.close();
//            writer.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }




}
