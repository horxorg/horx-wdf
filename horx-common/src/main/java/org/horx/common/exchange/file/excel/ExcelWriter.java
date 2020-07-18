package org.horx.common.exchange.file.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.horx.common.report.CellStyle;
import org.horx.common.report.Font;
import org.horx.common.report.enums.Align;
import org.horx.common.report.enums.BorderStyle;
import org.horx.common.report.enums.VerticalAlign;

/**
 * 写入Excel的工具类。
 * @since 1.0
 */
public class ExcelWriter {
    protected String ext;

    protected final int colLimit;
    protected final int rowLimit;

    protected Workbook workbook = null;
    /**
     * 当前Sheet。
     */
    protected Sheet sheet;

    protected List<Integer> colCountList;

    /**
     * 当前Sheet的序号。
     */
    protected int currentSheetIndex;

    /**
     * 当前Sheet的总列数。
     */
    protected int currentColCount;

    /**
     * 当前Sheet的当前行号。
     */
    protected int currentRowIndex;

    /**
     *
     * @param ext 格式，可以为xls、xlsx，默认为xls。
     */
    public ExcelWriter(String ext) {
        if (ext == null) ext = "xls";
        else ext = ext.toLowerCase();
        if ("xlsx".equals(ext)) {
            workbook = new XSSFWorkbook();
            colLimit = 256;
            rowLimit = 65536;
        } else {
            workbook = new HSSFWorkbook();
            colLimit = 16384;
            rowLimit = 1048576;
        }
        this.ext = ext;
        colCountList = new ArrayList<Integer>();
    }

    /**
     * 设置当前Sheet。
     * @param index 当前Sheet的序号。序号从0开始。
     */
    public void setCurrentSheetIndex(int index) throws IndexOutOfBoundsException {
        if (index >= workbook.getNumberOfSheets()) throw new IndexOutOfBoundsException();
        sheet = workbook.getSheetAt(index);
        this.currentSheetIndex = index;
        if (sheet != null) {
            currentRowIndex = -1;
            currentColCount = colCountList.get(index);
        } else {
            currentRowIndex = -1;
            currentColCount = 0;
        }
    }

    /**
     * 获取当前Sheet的序号。
     * @return
     */
    public int getCurrentSheetIndex() {
        return this.currentSheetIndex;
    }

    /**
     * 创建一个Sheet并设置为当前Sheet。
     * @param name
     */
    public void createSheet(String name) {
        createSheet(name, 0);
    }

    /**
     * 创建一个Sheet并设置为当前Sheet。
     * @param name
     */
    public void createSheet(String name, int colCount) {
        int cnt = workbook.getNumberOfSheets();
        if (name == null || name.length() == 0) {
            name = "Sheet" + (cnt + 1);
        }
        sheet = workbook.createSheet(name);
        colCountList.add(colCount);
        currentSheetIndex = cnt;
        currentColCount = colCount;
        currentRowIndex = -1;
    }

    /**
     * 创建一个Sheet并设置为当前Sheet。
     * @param name
     * @param colWidth 列宽。
     */
    public void createSheet(String name, int[] colWidth) {
        int colCnt = (colWidth == null) ? 0 : colWidth.length;
        createSheet(name, colCnt);
        if (colWidth != null) {
            for (int i=0; i<colCnt; i++) {
                sheet.setColumnWidth(i, colWidth[i] * 32);
            }
        }
    }

    /**
     * 设置当前行号
     * @param rowIndex 行号（从0开始）
     */
    public void setCurrentRowIndex(int rowIndex) {
        if (sheet == null) {
            return;
        }
        this.currentRowIndex = rowIndex;
    }

    /**
     * 获取当前行的序号。
     * @return
     */
    public int getCurrentRowIndex() {
        return this.currentRowIndex;
    }

    protected Row getRow(int rowIndex) {
        if (sheet == null) {
            return null;
        }
        Row row = sheet.getRow(rowIndex);
        return row;
    }

    /**
     * 追加行。
     */
    public void appendRow() {
        createRow(sheet.getPhysicalNumberOfRows());
    }

    /**
     * 追加行。
     * @param value
     */
    public void appendRow(Object[] value) {
        createRow(sheet.getPhysicalNumberOfRows());
        setRowValue(value);
    }

    /**
     * 追加行。
     * @param value
     * @param style
     */
    public void appendRow(Object[] value, CellStyle style) {
        createRow(sheet.getPhysicalNumberOfRows());
        setRowValue(value, style);
    }

    /**
     * 追加行。
     * @param value
     * @param styles
     */
    public void appendRow(Object[] value, CellStyle[] styles) {
        createRow(sheet.getPhysicalNumberOfRows());
        setRowValue(value, styles);
    }

    protected Row createRow(int index) {
        Row row = sheet.createRow(index);
        row.setHeightInPoints(-1);
        for (int i=0; i < currentColCount; i++) {
            row.createCell(i);
        }
        currentRowIndex = index;
        return row;
    }

    public void setRowHeightInPoints(float height) {
        Row row = sheet.getRow(currentRowIndex);
        row.setHeightInPoints(height);
    }

    public void setRowValue(Object[] value) {
        if (currentColCount <= 0 || value == null || value.length == 0) {
            return;
        }
        Row row = sheet.getRow(currentRowIndex);

        for (int i=0; i<value.length; i++) {
            Cell cell = row.getCell(i);
            if (cell == null) {
                cell = row.createCell(i);
            }

            Object v = null;
            if (value != null && value.length > i) {
                v = value[i];
            }
            setCellValue(cell, v);
        }
    }

    public void setRowValue(Object[] value, CellStyle style) {
        if (currentColCount <= 0 || value == null || value.length == 0) {
            return;
        }
        Row row = sheet.getRow(currentRowIndex);

        for (int i=0; i<value.length; i++) {
            Cell cell = row.getCell(i);
            if (cell == null) {
                cell = row.createCell(i);
            }

            Object v = null;
            if (value != null && value.length > i) {
                v = value[i];
            }
            setCellValue(cell, v);

            if (style != null) {
                org.apache.poi.ss.usermodel.CellStyle cellStyle = convertCellStyle(style);
                if (cellStyle != null) {
                    cell.setCellStyle(cellStyle);
                }
            }
        }
    }

    public void setRowValue(Object[] value, CellStyle[] styles) {
        if (currentColCount <= 0 || value == null || value.length == 0) {
            return;
        }
        Row row = sheet.getRow(currentRowIndex);

        for (int i=0; i<value.length; i++) {
            Cell cell = row.getCell(i);
            if (cell == null) {
                cell = row.createCell(i);
            }

            Object v = null;
            if (value != null && value.length > i) {
                v = value[i];
            }
            setCellValue(cell, v);

            if (styles != null && styles.length > i) {
                org.apache.poi.ss.usermodel.CellStyle cellStyle = convertCellStyle(styles[i]);
                if (cellStyle != null) {
                    cell.setCellStyle(cellStyle);
                }
            }
        }
    }

    /**
     * 设置单元格内容。
     * @param colIndex
     * @param value
     */
    public void setCellValue(int colIndex, Object value) {
        setCellValue(colIndex, value, null);
    }

    /**
     * 设置单元格内容。
     * @param colIndex
     * @param value
     * @param cellStyle
     */
    public void setCellValue(int colIndex, Object value, CellStyle cellStyle) {
        Cell cell =  getCell(colIndex);
        if (cell == null) {
            return;
        }
        setCellValue(cell, value);
        org.apache.poi.ss.usermodel.CellStyle poiCellStyle = convertCellStyle(cellStyle);
        if (cellStyle != null) {
            cell.setCellStyle(poiCellStyle);
        }
    }

    /**
     * 设置单元格内容。
     * @param colIndex
     * @param value
     * @param cellStyle
     * @param rowSpan
     * @param colSpan
     */
    public void setCellValue(int colIndex, Object value, CellStyle cellStyle, int rowSpan, int colSpan) {
        Cell cell =  getCell(colIndex);
        if (cell == null) {
            return;
        }
        setCellValue(cell, value);
        mergeCell(colIndex, rowSpan, colSpan);
        org.apache.poi.ss.usermodel.CellStyle poiCellStyle = convertCellStyle(cellStyle);
        if (poiCellStyle != null) {
            if (rowSpan == 1) {
                for (int i = colIndex; i < colIndex + colSpan; i++) {
                    getCell(i).setCellStyle(poiCellStyle);
                }
            } else {
                int tempRowIndex = currentRowIndex;
                for (int i = tempRowIndex; i < tempRowIndex + rowSpan; i++) {
                    setCurrentRowIndex(i);
                    for (int j = colIndex; j < colIndex + colSpan; j++) {
                        getCell(j).setCellStyle(poiCellStyle);
                    }
                }
                currentRowIndex = tempRowIndex;
            }
        }

    }

    /**
     * 设置单元格样式。
     * @param colIndex
     * @param cellStyle
     */
    public void setCellStyle(int colIndex, CellStyle cellStyle) {
        Cell cell =  getCell(colIndex);
        if (cell == null) {
            return;
        }
        org.apache.poi.ss.usermodel.CellStyle poiCellStyle = convertCellStyle(cellStyle);
        if (cellStyle != null) {
            cell.setCellStyle(poiCellStyle);
        }
    }

    /**
     * 合并单元格。
     * @param colIndex
     * @param rowSpan
     * @param colSpan
     */
    public void mergeCell(int colIndex, int rowSpan, int colSpan) {
        Cell cell =  getCell(colIndex);
        if (cell == null) {
            return;
        }
        if (rowSpan < 1) {
            rowSpan = 1;
        }
        if (colSpan < 1) {
            colSpan = 1;
        }
        mergeRegion(currentRowIndex, currentRowIndex + rowSpan - 1, colIndex, colIndex + colSpan - 1);
    }

    /**
     * 合并单元格。
     * @param startRowIndex
     * @param endRowIndex
     * @param startColIndex
     * @param endColIndex
     */
    public void mergeRegion(int startRowIndex, int endRowIndex, int startColIndex, int endColIndex) {
        sheet.addMergedRegion(new CellRangeAddress(startRowIndex, endRowIndex, startColIndex, endColIndex));
    }

    protected Cell getCell(int colIndex) {
        Row row = sheet.getRow(currentRowIndex);
        if (row == null) {
            return null;
        }
        return row.getCell(colIndex);
    }

    public int getRowLimit() {
        return rowLimit;
    }

    public int getColLimit() {
        return colLimit;
    }

    /**
     * 输出Excel。
     * @param os
     * @throws IOException
     */
    public void write(OutputStream os) throws IOException {
        workbook.write(os);
    }

    protected void setCellValue(Cell cell, Object value) {
        if (value == null) {
            return;
        }
        if (value instanceof Boolean) {
            cell.setCellValue((Boolean)value);
        } else if (value instanceof Calendar) {
            cell.setCellValue((Calendar)value);
        } else if (value instanceof Date) {
            cell.setCellValue((Date)value);
        } else if (value instanceof Number) {
            Number num = (Number)value;
            cell.setCellValue(num.doubleValue());
        } else {
            cell.setCellValue(value.toString());
        }
    }

    protected Map<CellStyle, org.apache.poi.ss.usermodel.CellStyle> cellStyleMap;

    /**
     * 转为为POI格式的CellStyle。
     * @param cellStyle
     * @return
     */
    protected org.apache.poi.ss.usermodel.CellStyle convertCellStyle(CellStyle cellStyle) {
        if (cellStyle == null) {
            return null;
        }

        if (cellStyleMap != null && cellStyleMap.containsKey(cellStyle)) {
            return cellStyleMap.get(cellStyle);
        }

        org.apache.poi.ss.usermodel.CellStyle poiStyle = workbook.createCellStyle();

        // 转换水平对齐方式
        Align align = cellStyle.getAlign();
        if (align != null) {
            switch (align) {
                case LEFT :
                    poiStyle.setAlignment(HorizontalAlignment.LEFT);
                    break;
                case CENTER :
                    poiStyle.setAlignment(HorizontalAlignment.CENTER);
                    break;
                case RIGHT :
                    poiStyle.setAlignment(HorizontalAlignment.RIGHT);
            }
        }

        // 转换垂直对齐方式
        VerticalAlign valign = cellStyle.getVerticalAlign();
        if (valign != null) {
            switch (valign) {
                case TOP :
                    poiStyle.setVerticalAlignment(VerticalAlignment.TOP);
                    break;
                case MIDDLE :
                    poiStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                    break;
                case BOTTOM :
                    poiStyle.setVerticalAlignment(VerticalAlignment.BOTTOM);
            }
        }

        // 转换字体
        org.apache.poi.ss.usermodel.Font poiFont = convertFont(cellStyle.getFont());
        if (poiFont != null) {
            poiStyle.setFont(poiFont);
        }

        // 转换边框
        poiStyle.setBorderLeft(convertBorderStyle(cellStyle.getLeftBorder()));
        poiStyle.setBorderRight(convertBorderStyle(cellStyle.getRightBorder()));
        poiStyle.setBorderTop(convertBorderStyle(cellStyle.getTopBorder()));
        poiStyle.setBorderBottom(convertBorderStyle(cellStyle.getBottomBorder()));
        poiStyle.setLeftBorderColor(cellStyle.getLeftBorderColor());
        poiStyle.setRightBorderColor(cellStyle.getRightBorderColor());
        poiStyle.setTopBorderColor(cellStyle.getTopBorderColor());
        poiStyle.setBottomBorderColor(cellStyle.getBottomBorderColor());

        // 转换颜色
        poiStyle.setFillBackgroundColor(cellStyle.getBackgroundColor());

        // 转换WrapText
        poiStyle.setWrapText(cellStyle.isWrapText());

        String dataFormat = cellStyle.getDataFormat();
        if (dataFormat != null && dataFormat.length() > 0) {
            poiStyle.setDataFormat(workbook.createDataFormat().getFormat(dataFormat));
        }

        if (cellStyleMap == null) {
            cellStyleMap = new HashMap<>();
        }
        cellStyleMap.put(cellStyle, poiStyle);
        return poiStyle;
    }

    protected Map<Font, org.apache.poi.ss.usermodel.Font> fontMap;

    /**
     * 转换为POI的字体。
     * @param font
     * @return
     */
    protected org.apache.poi.ss.usermodel.Font convertFont(Font font) {
        if (font == null) {
            return null;
        }
        if (fontMap != null && fontMap.containsKey(font)) {
            return fontMap.get(font);
        }

        org.apache.poi.ss.usermodel.Font poiFont = workbook.createFont();
        if (font.isBold()) {
            poiFont.setBold(true);
        }
        if (font.getSizeInPoints() != null) {
            poiFont.setFontHeightInPoints(font.getSizeInPoints());
        }
        if (font.getColor() != null) {
            poiFont.setColor(font.getColor());
        }
        if (font.getFontName() != null) {
            poiFont.setFontName(font.getFontName());
        }
        if (font.isItalic()) {
            poiFont.setItalic(true);
        }
        if (font.isUnderline()) {
            poiFont.setUnderline((byte)1);
        }

        if (fontMap == null) {
            fontMap = new HashMap<>();
        }
        fontMap.put(font, poiFont);
        return poiFont;
    }

    /**
     * 转换BorderStyle。
     * @param borderStyle
     * @return
     */
    protected org.apache.poi.ss.usermodel.BorderStyle convertBorderStyle(BorderStyle borderStyle) {
        org.apache.poi.ss.usermodel.BorderStyle result = org.apache.poi.ss.usermodel.BorderStyle.NONE;
        switch (borderStyle) {
            case THIN :
                result = org.apache.poi.ss.usermodel.BorderStyle.THIN;
                break;
            case MEDIUM :
                result = org.apache.poi.ss.usermodel.BorderStyle.MEDIUM;
                break;
            case DASHED :
                result = org.apache.poi.ss.usermodel.BorderStyle.DASHED;
                break;
            case HAIR :
                result = org.apache.poi.ss.usermodel.BorderStyle.HAIR;
                break;
            case THICK :
                result = org.apache.poi.ss.usermodel.BorderStyle.THICK;
                break;
            case DOUBLE :
                result = org.apache.poi.ss.usermodel.BorderStyle.DOUBLE;
                break;
            case DOTTED :
                result = org.apache.poi.ss.usermodel.BorderStyle.DOTTED;
                break;
            case MEDIUM_DASHED :
                result = org.apache.poi.ss.usermodel.BorderStyle.MEDIUM_DASHED;
                break;
            case DASH_DOT :
                result = org.apache.poi.ss.usermodel.BorderStyle.DASH_DOT;
                break;
            case MEDIUM_DASH_DOT :
                result = org.apache.poi.ss.usermodel.BorderStyle.MEDIUM_DASH_DOT;
                break;
            case DASH_DOT_DOT :
                result = org.apache.poi.ss.usermodel.BorderStyle.DASH_DOT_DOT;
                break;
            case MEDIUM_DASH_DOT_DOT :
                result = org.apache.poi.ss.usermodel.BorderStyle.MEDIUM_DASH_DOT_DOT;
                break;
            case SLANTED_DASH_DOT :
                result = org.apache.poi.ss.usermodel.BorderStyle.SLANTED_DASH_DOT;
                break;
        }
        return result;
    }
}
