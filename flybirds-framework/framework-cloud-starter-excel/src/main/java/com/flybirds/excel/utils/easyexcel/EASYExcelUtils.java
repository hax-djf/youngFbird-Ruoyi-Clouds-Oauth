package com.flybirds.excel.utils.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.metadata.fill.FillWrapper;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.flybirds.excel.vo.EasyExcelObj;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import javax.servlet.http.HttpServletResponse;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author :flybirds
 *
 * easyExcel 工具类
 */
@Component
public class EASYExcelUtils {

    // 系统桌面路径
    public static final String HOME_PATH = FileSystemView.getFileSystemView().getHomeDirectory().getPath();
    // classpath 路径
    public static final String CLASS_PATH = EASYExcelUtils.class.getResource("/").getPath();
    // classpath 下 excel 文件存储路径
    public static final String SAVE_PATH = "excel" + File.separator;
    // 文件后缀
    public static final String SUFFIX = ".xlsx";

    /**
     * 自定义导出模板数据 some_sheet
     *
     * @param workbook
     * @param sheetNum
     * @param sheetName
     * @param title
     * @param rowsName
     * @param dataList
     * @param output
     * @throws Exception
     */
    public static void customExportExcelSome(XSSFWorkbook workbook, int sheetNum, String[] sheetName, String[] title, List<String[]> rowsName, List<List<Object[]>> dataList, OutputStream output, HttpServletResponse response, String fileName) throws Exception{

        //根据sheet长度，从0开始
        for (int i =0 ;i< sheetNum ;i++){
            customExport(workbook,i,title[i],rowsName.get(i),dataList.get(i),sheetName[i],output);
        }
        exportClose(workbook,response,fileName);
    }

    /**
     * 自定义导出模板数据single——sheet
     *
     * @param workbook
     * @param sheetName
     * @param title
     * @param rowsName
     * @param dataList
     * @param output
     * @throws Exception
     */
    public static void customExportExcelSingle(XSSFWorkbook workbook, String sheetName, String title, String[] rowsName, List<Object[]> dataList, OutputStream output, HttpServletResponse response, String fileName) throws Exception{
        customExport(workbook,null,title,rowsName,dataList,sheetName,output);
        exportClose(workbook,response,fileName);
    }

    protected static void exportClose(XSSFWorkbook workbook, HttpServletResponse response, String fileName) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName == null ? "easyexcel" + SUFFIX :fileName + SUFFIX, "utf-8"));
        response.setHeader("Access-Control-Expose-Headers", "content-Disposition");
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 自定义数据导出操作
     *
     * @param workbook
     * @param sheetNum
     * @param title
     * @param rowName
     * @param dataList
     * @param sheetName
     * @param out
     * @throws Exception
     */
    private static void customExport(XSSFWorkbook workbook, Integer sheetNum, String title, String[] rowName, List<Object[]> dataList, String sheetName, OutputStream out) throws Exception {
        try {
            //创建工作表
            XSSFSheet sheet = workbook.createSheet();
            //创建sheet的名称
            workbook.setSheetName(sheetNum==null ? 0 : sheetNum, sheetName == null ?"列表导出":sheetName);
            // 产生表格标题行
            XSSFRow rowm = sheet.createRow(0);
            //设置高度
            rowm.setHeightInPoints(20);
            //创建表格标题列
            XSSFCell cellTiltle = rowm.createCell(0);
            // 获取列头样式对象
            XSSFCellStyle columnTopStyle = getColumnTopStyle(workbook);
            // 获取单元格样式对象
            XSSFCellStyle style = getStyle(workbook,1);
            XSSFCellStyle style2 = getStyle(workbook,0); // 获取单元格样式对象
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, (rowName.length - 1)));
            cellTiltle.setCellStyle(columnTopStyle);    //设置标题行样式
            cellTiltle.setCellValue(title);     //设置标题行值

            int columnNum = rowName.length;     // 定义所需列数
            XSSFRow rowRowName = sheet.createRow(2); // 在索引2的位置创建行(最顶端的行开始的第二行)
            // 将列头设置到sheet的单元格中
            //设置头样式
            for (int n = 0; n < columnNum; n++) {
                XSSFCell cellRowName = rowRowName.createCell(n); // 创建列头对应个数的单元格
                cellRowName.setCellType(CellType.STRING); // 设置列头单元格的数据类型
                XSSFRichTextString text = new XSSFRichTextString(rowName[n]);
                cellRowName.setCellValue(text); // 设置列头单元格的值
                cellRowName.setCellStyle(style); // 设置列头单元格样式
            }
            // 将查询出的数据设置到sheet对应的单元格中
            for (int i = 0; i < dataList.size(); i++) {
                Object[] obj = dataList.get(i);   // 遍历每个对象
                XSSFRow row = sheet.createRow(i + 3);   // 创建所需的行数
                if(i == dataList.size() -1 && "合计".equals(obj[0])){
                    for (int j = 0; j < obj.length; j++) {
                        XSSFCell cell = null;
                        if (j == 0) {
                            row.createCell(j, CellType.STRING).setCellValue(obj[j].toString());
                        } else {
                            cell = row.createCell(j, CellType.STRING);
                            if (!"".equals(obj[j]) && obj[j] != null) {
                                cell.setCellValue(obj[j].toString());
                            }else{
                                cell.setCellValue(" ");
                            }
                        }
                        cell.setCellStyle(style); // 设置单元格样式
                        // 合并日期占两行(4个参数，分别为起始行，结束行，起始列，结束列)
                        CellRangeAddress region = new CellRangeAddress(dataList.size()+2, dataList.size()+2, 0, 1);
                        sheet.addMergedRegion(region);
                    }
                }else{
                    for (int j = 0; j < obj.length; j++) {
                        XSSFCell cell = null;   // 设置单元格的数据类型
                        if (j == 0) {
                            cell = row.createCell(j, CellType.STRING);
                            cell.setCellValue(i + 1);
                        } else {
                            cell = row.createCell(j, CellType.STRING);
                            if (!"".equals(obj[j]) && obj[j] != null) {
                                cell.setCellValue(obj[j].toString());
                            }else{
                                cell.setCellValue(" ");
                            }
                        }
                        cell.setCellStyle(style2); // 设置单元格样式
                    }
                }
            }

            // 让列宽随着导出的列长自动适应
            for (int colNum = 0; colNum < columnNum; colNum++) {
                int columnWidth = sheet.getColumnWidth(colNum) / 256;
                for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                    XSSFRow currentRow;
                    // 当前行未被使用过
                    if (sheet.getRow(rowNum) == null) {
                        currentRow = sheet.createRow(rowNum);
                    } else {
                        currentRow = sheet.getRow(rowNum);
                    }
                    if (currentRow.getCell(colNum) != null) {
                        XSSFCell currentCell = currentRow.getCell(colNum);
                        if (currentCell.getCellTypeEnum() == CellType.STRING) {
                            int length = currentCell.getStringCellValue()
                                    .getBytes().length;
                            if (columnWidth < length) {
                                columnWidth = length;
                            }
                        }
                    }
                }
                if (colNum == 0) {
                    sheet.setColumnWidth(colNum, (columnWidth - 2) * 256);
                } else {
                    sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 自定义 -> 标题设置
     *
     * @param workbook
     * @return
     */
    protected static XSSFCellStyle getColumnTopStyle(XSSFWorkbook workbook) {
        // 设置字体
        XSSFFont font = workbook.createFont();
        // 设置字体大小
        font.setFontHeightInPoints((short) 20);
        //加粗
        font.setBold(true);
        // 设置字体名字
        font.setFontName("微软雅黑");
        XSSFCellStyle style = workbook.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        // 设置底边框颜色;
        style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        // 设置左边框;
        style.setBorderLeft(BorderStyle.THIN);
        // 设置左边框颜色;
        style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        // 设置右边框;
        style.setBorderRight(BorderStyle.THIN);
        // 设置右边框颜色;
        style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        // 设置顶边框;
        style.setBorderTop(BorderStyle.THIN);
        // 设置顶边框颜色;
        style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        // 在样式用应用设置的字体;
        style.setFont(font);
        // 设置自动换行;
        style.setWrapText(false);
        // 设置水平对齐的样式为居中对齐;
        style.setAlignment(HorizontalAlignment.CENTER);
        // 设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        //设置背景色
        return style;

    }

    /**
     *  自定义 -> 列数据信息单元格样式
     *
     * @param workbook
     * @param i
     * @return
     */
    protected static XSSFCellStyle getStyle(XSSFWorkbook workbook, int i) {
        // 设置字体
        XSSFFont font = workbook.createFont();
        // 设置字体大小
        font.setFontHeightInPoints((short)10);
        if(i==1){
            font.setBold(true);
        }
        // 设置字体名字
        font.setFontName("Courier New");
        // 设置样式;
        XSSFCellStyle style = workbook.createCellStyle();
        // 设置底边框;
        style.setBorderBottom(BorderStyle.THIN);
        // 设置底边框颜色;
        style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        // 设置左边框;
        style.setBorderLeft(BorderStyle.THIN);
        // 设置左边框颜色;
        style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        // 设置右边框;
        style.setBorderRight(BorderStyle.THIN);
        // 设置右边框颜色;
        style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        // 设置顶边框;
        style.setBorderTop(BorderStyle.THIN);
        //设置顶边框颜色;
        style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        // 在样式用应用设置的字体;
        style.setFont(font);
        // 设置自动换行;
        style.setWrapText(false);
        // 设置水平对齐的样式为居中对齐;
        style.setAlignment(HorizontalAlignment.CENTER);
        // 设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }


    /**
     * 数据模板导出->适用于复杂的表格处理操作
     *
     * @param easyExcelObj
     * @return
     */
    public static String modelExport(EasyExcelObj easyExcelObj) {
            String path = easyExcelObj.getExportPath();
            // 如果导出路径为空 默认使用系统桌面路径
            if (StringUtils.isEmpty(path)) {
                path = HOME_PATH;
            }
            // 导出路径
            String exportPath = path + File.separator + easyExcelObj.getExportName() + SUFFIX;
            // 模板所在位置路径
            String templateFileName = CLASS_PATH + SAVE_PATH + easyExcelObj.getTemplateName() + SUFFIX;

            ExcelWriter excelWriter = EasyExcel.write(exportPath).withTemplate(templateFileName).build();
            WriteSheet writeSheet = EasyExcel.writerSheet().build();
            if (easyExcelObj.getData() != null) {
                // 单表多数据导出 模板格式为 {.属性}
                for (Object d : easyExcelObj.getData()) {
                    excelWriter.fill(d, writeSheet);
                }
            } else if (easyExcelObj.getMultiListMap() != null) {
                // 多表多数据导出
                for (Map.Entry<String, Object> map : easyExcelObj.getMultiListMap().entrySet()) {
                    // 设置列表后续还有数据
                    FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
                    if (map.getValue() instanceof Collection) {
                        // 多表导出必须使用 FillWrapper 模板格式为 {key.属性}
                        excelWriter.fill(new FillWrapper(map.getKey(), (Collection<?>)map.getValue()), fillConfig, writeSheet);
                    } else {
                        excelWriter.fill(map.getValue(),writeSheet);
                    }
                }
            } else {
                throw new IllegalArgumentException("数据为空");
            }
            // 结束构建
            excelWriter.finish();
            return exportPath;
        }
    /**
     * 导出 Excel ：一个 sheet，带表头.
     *
     * @param response  HttpServletResponse
     * @param  data      数据 list，每个元素为一个 BaseRowModel
     * @param fileName  导出的文件名
     * @param sheetName 导入文件的 sheet 名
     * @param model     映射实体类，Excel 模型
     * @throws Exception 异常
     */

    public static void writeExcel(HttpServletResponse response, List<? extends Object> data, String fileName, String sheetName, Class model) throws Exception {
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        //设置表头居中对齐
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        // 颜色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 10);
        // 字体
        headWriteCellStyle.setWriteFont(headWriteFont);
        headWriteCellStyle.setWrapped(true);
        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        //设置内容靠中对齐
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(getOutputStream(fileName, response), model).excelType(ExcelTypeEnum.XLSX).sheet(sheetName).registerWriteHandler(horizontalCellStyleStrategy)
        //最大长度自适应 目前没有对应算法优化 建议注释掉不用 会出bug
        .doWrite(data);

    }

    /**
     * 导出文件时为Writer生成OutputStream.
     *
     * @param fileName 文件名
     * @param response response
     * @return ""
     */
    private static OutputStream getOutputStream(String fileName, HttpServletResponse response) throws Exception {
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf8");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");
            response.setHeader("Pragma", "public");
            response.setHeader("Cache-Control", "no-store");
            response.addHeader("Cache-Control", "max-age=0");
            return response.getOutputStream();
        } catch (IOException e) {
            throw new Exception("导出excel表格失败!", e);
        }
    }

}

