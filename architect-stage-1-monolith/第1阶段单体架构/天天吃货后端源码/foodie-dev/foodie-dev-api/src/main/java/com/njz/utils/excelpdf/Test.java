package com.njz.utils.excelpdf;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.njz.utils.excelpdf.dto.FundCapitalDetail;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {
    /**
     * 页面底部页边到页脚文字画布的顶部的距离，用来写页脚文字的高度
     */
    private final float footHeight = 40; //canva绘制后的图形
    public static void main(String[] args) {
        Test test = new Test();
        test.generateDetail();
    }
    public void generateDetail() {
        // 指定文件保存的路径
        String filePath = System.getProperty("java.io.tmpdir") + "资金明细详情1.pdf";
        String sealPicPath = System.getProperty("java.io.tmpdir") + "seal.png";

        float marginPoint = OpenpdfUtils.cmToPoints(2.0f);
        Document document = new Document(PageSize.A4.rotate(), marginPoint, marginPoint, marginPoint, marginPoint + footHeight);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
            BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
            Font titleFont = new Font(baseFont, 20);
            Font paragraphFont = new Font(baseFont, 10);

            List<String> headers = Arrays.asList("入账时间", "入账金额", "出账金额", "余额", "对方账号", "对方户名", "对方开户行名", "摘要", "附言");
            List<Integer> dataAlignments = Arrays.asList(Element.ALIGN_CENTER, Element.ALIGN_RIGHT, Element.ALIGN_RIGHT,
                    Element.ALIGN_RIGHT, Element.ALIGN_CENTER, Element.ALIGN_CENTER,
                    Element.ALIGN_CENTER, Element.ALIGN_CENTER, Element.ALIGN_CENTER);

            // 必须要在打开文档之前设置事件
            String footStr = "重要提示：本明细仅限于查询账户交易流水使用，在跨行退出、日终冲帐等特殊情况下存在后续变动可能，若与实际交易不符，以银行对账单为准。文件下载后请妥善保管，如若被伪造、变造、篡改，不具有发力效力。";
            FundDetailsPDFGenerator event = new FundDetailsPDFGenerator(baseFont, titleFont, paragraphFont, footHeight, footStr, sealPicPath);
            writer.setPageEvent(event);
            document.open();

            // 添加标题
//            titleFont.setStyle(Font.BOLD);
            Paragraph title = new Paragraph("中国工商银行账户明细清单", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // 添加表格上面部分文字
            List<String> textList = Arrays.asList(
                    "组合名称：南方宝元",
                    "组合账号：22222222222222222",
                    "账户期初余额：100",
                    "查询时间：20220101-20221010",
                    "金额币种：元"
            );
            OpenpdfUtils.addTextBeforeTable(document, textList, paragraphFont);
            // 创建表格并设置默认单元格样式
            PdfPTable table = new PdfPTable(headers.size());// 根据表头列数量创建table
            table.setWidthPercentage(100);// 表格宽度为页面宽度的 100%
            table.setSpacingBefore(10f);// 表格顶部和上方内容的距离
            // 第一页插入表头
            OpenpdfUtils.addTableHeader(table, paragraphFont, headers, Element.ALIGN_CENTER, Element.ALIGN_CENTER);
            // 填充表格数据
            List<FundCapitalDetail> fundDetailsList = getFundDetailsList(); // 获取资金明细实体的列表
            OpenpdfUtils.addTableData(document, table, paragraphFont, fundDetailsList, dataAlignments, Element.ALIGN_MIDDLE);
            document.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<FundCapitalDetail> getFundDetailsList() {
        // 这里应该是从数据库或其他数据源获取资金明细实体的列表
        // 返回填充好的资金明细实体列表
        List<FundCapitalDetail> fundDetailList = new ArrayList<FundCapitalDetail>();
        for(int i = 0; i < 100; ++i) {
            FundCapitalDetail fundDetail = new FundCapitalDetail("2024010" + i, "1000000" + i, "2000000" + i,
                    "5000" + i, "0000000000" + i, "对方账户名" + i,
                    "对方开户行名-测试测试测" + i, "摘要" + i, "附言" + i);
            fundDetailList.add(fundDetail);
        }
        return fundDetailList; // 示例代码，需要替换为实际的数据获取逻辑
    }
}
