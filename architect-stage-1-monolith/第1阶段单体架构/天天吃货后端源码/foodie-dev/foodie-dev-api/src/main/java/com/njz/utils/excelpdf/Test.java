package com.njz.utils.excelpdf;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.njz.utils.excelpdf.capitaldetail.CapitalDetailOpenpdfUtils;
import com.njz.utils.excelpdf.dto.FundCapitalDetail;
import com.njz.utils.excelpdf.table.CustomedPdfTable;
import com.njz.utils.excelpdf.table.PdfTableData;
import com.njz.utils.excelpdf.table.PdfTableHead;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
        String directoryPath = System.getProperty("java.io.tmpdir") + "capitalDetail_generatedPdf" + System.currentTimeMillis();
        String filePath = directoryPath + File.separator + "资金明细详情1.pdf";
        System.out.println(filePath);
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (!created) {
                // Handle error if directory creation fails
                System.out.println("Failed to create directory: " + directoryPath);
            }
        }

        String sealPicPath = System.getProperty("java.io.tmpdir") + "seal.png";

        float marginPoint = AbstractOpenPdfUtils.cmToPoints(2.0f);
        Document document = new Document(PageSize.A4.rotate(), marginPoint, marginPoint, marginPoint, marginPoint + footHeight);
        try(FileOutputStream fos = new FileOutputStream(filePath)) {
            BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
            Font paragraphFont = new Font(baseFont, 10);

            List<String> headers = Arrays.asList("入账时间", "入账金额", "出账金额", "余额", "对方账号", "对方户名", "对方开户行名", "摘要", "附言");

            // 必须要在打开文档之前设置事件
            String footStr = "重要提示：本明细仅限于查询账户交易流水使用，在跨行退出、日终冲帐等特殊情况下存在后续变动可能，若与实际交易不符，以银行对账单为准。文件下载后请妥善保管，如若被伪造、变造、篡改，不具有发力效力。";
            FundDetailsPDFGenerator event = new FundDetailsPDFGenerator(baseFont, paragraphFont, footHeight, footStr, sealPicPath);
            PdfWriter writer = PdfWriter.getInstance(document, fos);
            writer.setPageEvent(event);
//            writer.setEncryption(null, "1".getBytes(), PdfWriter.ALLOW_PRINTING, PdfWriter.ENCRYPTION_AES_128);
            document.open();

            // 添加标题
//            titleFont.setStyle(Font.BOLD);
            Font titleFont = new Font(baseFont, 20);
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
            AbstractOpenPdfUtils.addTextBeforeTable(document, textList, paragraphFont);
            // 创建表格并设置默认单元格样式
            PdfPTable table = new PdfPTable(headers.size());// 根据表头列数量创建table
            table.setWidthPercentage(100);// 表格宽度为页面宽度的 100%
            table.setSpacingBefore(10f);// 表格顶部和上方内容的距离

            // 填充表格数据，包括表头和和表数据
            List<FundCapitalDetail> fundDetailsList = getFundDetailsList(); // 获取资金明细实体的列表
            List<PdfTableData<FundCapitalDetail>> pdfTableDataList = new ArrayList<>();
            for (FundCapitalDetail detail: fundDetailsList) {
                PdfTableData<FundCapitalDetail> tableData = new PdfTableData<>(detail);
                pdfTableDataList.add(tableData);
            }
            PdfTableHead pdfTableHead = new PdfTableHead(headers);

            List<Integer> horizontalAlignments = Arrays.asList(Element.ALIGN_CENTER, Element.ALIGN_RIGHT, Element.ALIGN_RIGHT,
                    Element.ALIGN_RIGHT, Element.ALIGN_CENTER, Element.ALIGN_CENTER,
                    Element.ALIGN_CENTER, Element.ALIGN_CENTER, Element.ALIGN_CENTER);
            int verticalAlignment = Element.ALIGN_MIDDLE;
            CustomedPdfTable customedPdfTable = new CustomedPdfTable(pdfTableHead, pdfTableDataList, horizontalAlignments, horizontalAlignments, verticalAlignment);
            CapitalDetailOpenpdfUtils capitalDetailOpenpdfUtils = new CapitalDetailOpenpdfUtils();
            capitalDetailOpenpdfUtils.addPdfTable(table, paragraphFont, customedPdfTable, FundCapitalDetail.class);

            boolean success = document.add(table);

            document.close();
            writer.close();

        } catch (FileNotFoundException e) {
            try {
                FileUtils.deleteDirectory(new File(directoryPath));
                System.out.println("Deleted directory: " + directoryPath);
            } catch (IOException e1) {
                e1.printStackTrace();
                // 处理删除失败的情况
            }
            throw new RuntimeException(e);
        } catch (IOException e) {
            try {
                FileUtils.deleteDirectory(new File(directoryPath));
                System.out.println("Deleted directory: " + directoryPath);
            } catch (IOException e1) {
                e1.printStackTrace();
                // 处理删除失败的情况
            }
            throw new RuntimeException(e);
        } finally {
//            try {
//                FileUtils.deleteDirectory(new File(directoryPath));
//                System.out.println("Deleted directory: " + directoryPath);
//            } catch (IOException e1) {
//                e1.printStackTrace();
//                // 处理删除失败的情况
//            }
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
