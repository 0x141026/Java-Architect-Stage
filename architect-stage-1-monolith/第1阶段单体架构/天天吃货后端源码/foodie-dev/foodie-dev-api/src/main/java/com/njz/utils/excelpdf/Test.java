package com.njz.utils.excelpdf;

import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.njz.utils.excelpdf.dto.FundDetail;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Test {
    private final float footHeight = 40; //canva绘制后的图形

    public static float cmToPoints(float cm) {
        return cm * 28.35f;
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.generate();
    }
    public PdfPCell createRightAlignedCell(String content, Font font) {
        Phrase phrase = new Phrase(content, font);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        return cell;
    }
    // 辅助方法：创建一个自动换行的 PdfPCell
    private PdfPCell createWrappingCell(String content, Font font) {
        Phrase phrase = new Phrase(content, font);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setNoWrap(false);
        return cell;
    }
    public void generate() {
        // 指定文件保存的路径
        String filePath = System.getProperty("java.io.tmpdir") + "资金明细详情1.pdf";
        float marginPoint = cmToPoints(2.0f);
        Document document = new Document(PageSize.A4.rotate(), marginPoint, marginPoint, marginPoint, marginPoint + footHeight);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));

            BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
            Font titleFont = new Font(baseFont, 20);
            Font paragrapyFont = new Font(baseFont, 10);
            // 创建表格并设置默认单元格样式
            PdfPTable table = new PdfPTable(5);// 5列的表格
            table.setWidthPercentage(100);// 表格宽度为页面宽度的 100%
            table.setSpacingBefore(10f);// 表格前的间距
            addTableHeader(table, paragrapyFont);
            // 在打开文档之前设置页脚事件
            String footStr = "此账单如被修改，不具有法律效力。聂建洲聂建洲聂建洲聂建洲111111111111聂建洲聂建洲聂建洲222222222聂建洲聂建洲聂建洲33333333就斤斤计较经济斤斤计较男男女女男男女女男男女女男女";
            FundDetailsPDFGenerator event = new FundDetailsPDFGenerator(baseFont, titleFont, paragrapyFont, footHeight, footStr);
            writer.setPageEvent(event);
            document.open();

            // 添加标题
            Paragraph title = new Paragraph("资金明细详情", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // 添加查询日期和查询人
            document.add(new Paragraph("查询日期：" + new Date().toString(), paragrapyFont));
            document.add(new Paragraph("查询人：小红", paragrapyFont));

            // 设置余额列的默认单元格样式为右对齐
            PdfPCell defaultBalanceCell = new PdfPCell();
            defaultBalanceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);

            // 填充表格数据
            List<FundDetail> fundDetailsList = getFundDetailsList(); // 获取资金明细实体的列表
            for (FundDetail fundDetail : fundDetailsList) {
                table.addCell(this.createWrappingCell(fundDetail.getTransferDate(), paragrapyFont));
                table.addCell(this.createWrappingCell(fundDetail.getPayer(), paragrapyFont));
                table.addCell(this.createWrappingCell(fundDetail.getPayerAccount(), paragrapyFont));
                table.addCell(this.createWrappingCell(fundDetail.getPayeeAccount(), paragrapyFont));

                // 使用默认样式添加余额单元格
                PdfPCell cell = this.createRightAlignedCell(String.valueOf(fundDetail.getBalance()), paragrapyFont);
                table.addCell(cell);
            }

            document.add(table);
            document.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
}
