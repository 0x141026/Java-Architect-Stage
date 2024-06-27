package com.njz.utils.excelpdf;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

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
    private final float footHeight = 40; //canva绘制后的图形
    private final float offSetY = 3; //canva绘制后的图形

    private int pageNumber = 1;
    public FundDetailsPDFGenerator() {
        // 初始化字体
        try {
            baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
        } catch (IOException e) {
            e.printStackTrace();
        }
        titleFont = new Font(baseFont, 20);
        paragrapyFont = new Font(baseFont, 10);
        table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);

    }

    public Font getParagrapyFont() {
        return paragrapyFont;
    }

    public void setParagrapyFont(Font paragrapyFont) {
        this.paragrapyFont = paragrapyFont;
    }

    public Font getTitleFont() {
        return titleFont;
    }

    public void setTitleFont(Font titleFont) {
        this.titleFont = titleFont;
    }

    public BaseFont getBaseFont() {
        return baseFont;
    }

    public void setBaseFont(BaseFont baseFont) {
        this.baseFont = baseFont;
    }

    public PdfPTable getTable() {
        return table;
    }

    public void setTable(PdfPTable table) {
        this.table = table;
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
    public void onOpenDocument(PdfWriter writer, Document document) {
        // 文档打开时调用，创建表格
        total = writer.getDirectContent().createTemplate(60, 18);
        addTableHeader(table);
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

    public PdfPCell createRightAlignedCell(String content) {
        Phrase phrase = new Phrase(content, paragrapyFont);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        return cell;
    }
    // 辅助方法：创建一个自动换行的 PdfPCell
    private PdfPCell createWrappingCell(String content) {
        Phrase phrase = new Phrase(content, paragrapyFont);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setNoWrap(false);
        return cell;
    }
    public static float cmToPoints(float cm) {
        return cm * 28.35f;
    }

    public static void main(String[] args) {
        FundDetailsPDFGenerator fundDetailsPDFGenerator = new FundDetailsPDFGenerator();
        fundDetailsPDFGenerator.generate();
    }
    public void generate() {
        // 指定文件保存的路径
        String filePath = System.getProperty("java.io.tmpdir") + "资金明细详情.pdf";
        float marginPoint = cmToPoints(2.0f);
        Document document = new Document(PageSize.A4.rotate(), marginPoint, marginPoint, marginPoint, marginPoint + footHeight);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
            // 在打开文档之前设置页脚事件
            FundDetailsPDFGenerator event = new FundDetailsPDFGenerator();
            writer.setPageEvent(event);
            document.open();

            // 添加标题
            Paragraph title = new Paragraph("资金明细详情", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // 添加查询日期和查询人
            document.add(new Paragraph("查询日期：" + new Date().toString(), paragrapyFont));
            document.add(new Paragraph("查询人：小红", paragrapyFont));

            // 创建表格并设置默认单元格样式
            PdfPTable table = new PdfPTable(5); // 5 列的表格
            table.setWidthPercentage(100); // 表格宽度为页面宽度的 100%
            table.setSpacingBefore(10f); // 表格前的间距

            // 设置余额列的默认单元格样式为右对齐
            PdfPCell defaultBalanceCell = new PdfPCell();
            defaultBalanceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);

            // 添加表头
            addTableHeader(table);

            // 填充表格数据
            List<FundDetail> fundDetailsList = getFundDetailsList(); // 获取资金明细实体的列表
            for (FundDetail fundDetail : fundDetailsList) {
                // 检查是否有足够的空间添加新行，如果没有，则结束当前页并开始新页
                if (table.getTotalHeight() + table.getRowHeight(1)  > document.top() - document.bottom() - footHeight) {
                    document.add(table);
                    document.newPage();
                    table.deleteBodyRows(); // 清除已添加的行，以便在新页上重新开始
                    event.pageNumber++;
                }

                table.addCell(this.createWrappingCell(fundDetail.getTransferDate()));
                table.addCell(this.createWrappingCell(fundDetail.getPayer()));
                table.addCell(this.createWrappingCell(fundDetail.getPayerAccount()));
                table.addCell(this.createWrappingCell(fundDetail.getPayeeAccount()));

                // 使用默认样式添加余额单元格
                PdfPCell cell = this.createRightAlignedCell(String.valueOf(fundDetail.getBalance()));
                table.addCell(cell);
            }

            document.add(table);
            document.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private  void addTableHeader(PdfPTable table) {
        PdfPCell cell = this.createWrappingCell("转账日期");
        PdfPCell cell1 = this.createWrappingCell("转出人");
        PdfPCell cell2 = this.createWrappingCell("转出账号");
        PdfPCell cell3 = this.createWrappingCell("转入账号");
        table.addCell(cell);
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);

        // 余额表头右对齐
        PdfPCell balanceHeader = this.createWrappingCell("余额");
        balanceHeader.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(balanceHeader);
    }

    private static List<FundDetail> getFundDetailsList() {
        // 这里应该是从数据库或其他数据源获取资金明细实体的列表
        // 返回填充好的资金明细实体列表
        List<FundDetail> fundDetailList = new ArrayList<FundDetail>();
        for(int i = 0; i < 100; ++i) {
            FundDetail fundDetail = new FundDetail("2024010" + i, "付款人" + i, "" + i, "收款人账号" + i, 100 + i);
            fundDetailList.add(fundDetail);
        }
        return fundDetailList; // 示例代码，需要替换为实际的数据获取逻辑
    }

    // 资金明细实体类
    public static class FundDetail {
        private String transferDate;
        private String payer;
        private String payerAccount;
        private String payeeAccount;
        private double balance;

        // 构造函数、getter 和 setter 省略
        public FundDetail(String transferDate, String payer, String payerAccount, String payeeAccount, double balance) {
            this.transferDate = transferDate;
            this.payer = payer;
            this.payerAccount = payerAccount;
            this.payeeAccount = payeeAccount;
            this.balance = balance;
        }

        public String getTransferDate() {
            return transferDate;
        }

        public void setTransferDate(String transferDate) {
            this.transferDate = transferDate;
        }

        public String getPayer() {
            return payer;
        }

        public void setPayer(String payer) {
            this.payer = payer;
        }

        public String getPayerAccount() {
            return payerAccount;
        }

        public void setPayerAccount(String payerAccount) {
            this.payerAccount = payerAccount;
        }

        public String getPayeeAccount() {
            return payeeAccount;
        }

        public void setPayeeAccount(String payeeAccount) {
            this.payeeAccount = payeeAccount;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }
    }
}
