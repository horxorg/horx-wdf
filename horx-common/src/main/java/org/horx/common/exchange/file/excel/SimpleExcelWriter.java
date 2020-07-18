package org.horx.common.exchange.file.excel;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import org.horx.common.report.enums.Align;

/**
 * 对于简单Excel样式的写入。
 * @since 1.0
 */
public class SimpleExcelWriter extends ExcelWriter {
    protected String caption;
    protected String[] header;
    protected int[] width;
    protected Align[] colAlign;
    protected int rowStart;
    protected int colStart;


    protected CellStyle captionStyle;
    protected CellStyle headerStyle;
    protected CellStyle[] bodyStyles;

    public SimpleExcelWriter(String ext, int[] width, Align[] colAlign, String caption, String[] header) {
        this(ext, width, colAlign, 0, 0, caption, header);
    }

    public SimpleExcelWriter(String ext, int[] width, Align[] colAlign, int rowStart, int colStart, String caption, String[] header) {
        super(ext);
        this.caption = caption;
        this.header = header;
        this.width = width;
        this.colAlign = colAlign;
        if (header != null) {
            currentColCount = header.length;
        }

        if (rowStart >= 0) {
            this.rowStart = rowStart;
        }
        if (colStart >= 0) {
            this.colStart = colStart;
        }

        createSheet(null);
        createCaptionStyle();
        createHeaderStyle();
        createBodyStyle();
        createCaption();
        createHeader();
    }

    @Override
    public void createSheet(String name) {
        int cnt = workbook.getNumberOfSheets();
        if (name == null || name.length() == 0) {
            name = "Sheet" + (cnt + 1);
        }
        sheet = workbook.createSheet(name);
        for (int i=colStart; i<colStart + currentColCount; i++) {
            int w = 20;
            if (width != null && width.length > i - colStart) {
                w = width[i - colStart];
            }
            sheet.setColumnWidth(i, w*32);
        }
    }

    protected void createCaptionStyle() {
        captionStyle = workbook.createCellStyle();
        captionStyle.setAlignment(HorizontalAlignment.CENTER);
        captionStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        Font captionFont = workbook.createFont();
        captionFont.setBold(true);
        captionFont.setFontHeightInPoints((short)18);
        captionStyle.setFont(captionFont);
    }

    protected void createHeaderStyle() {
        headerStyle = workbook.createCellStyle();
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short)12);
        headerStyle.setFont(headerFont);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
    }

    protected void createBodyStyle() {
        if (currentColCount <= 0) {
            return;
        }
        bodyStyles = new CellStyle[currentColCount];
        int alignCount = (colAlign == null) ? 0 : colAlign.length;
        for (int i=0; i<currentColCount; i++) {
            CellStyle bodyStyle = workbook.createCellStyle();

            Align align = Align.CENTER;
            if (alignCount > i) {
                align = colAlign[i];
            }
            if (align == Align.LEFT) {
                bodyStyle.setAlignment(HorizontalAlignment.LEFT);
            } else if (align == Align.CENTER) {
                bodyStyle.setAlignment(HorizontalAlignment.CENTER);
            } else if (align == Align.RIGHT) {
                bodyStyle.setAlignment(HorizontalAlignment.RIGHT);
            }
            bodyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            Font bodyFont = workbook.createFont();
            bodyFont.setFontHeightInPoints((short)12);
            bodyStyle.setFont(bodyFont);
            bodyStyle.setBorderBottom(BorderStyle.THIN);
            bodyStyle.setBorderRight(BorderStyle.THIN);
            bodyStyle.setBorderLeft(BorderStyle.THIN);
            bodyStyle.setBorderTop(BorderStyle.THIN);
            bodyStyle.setWrapText(true);

            bodyStyles[i] = bodyStyle;
        }
    }


    protected void createCaption() {
        if (currentColCount <= 0) {
            return;
        }
        if (rowStart > 0) {
            for (int i=0; i<rowStart; i++) {
                createRow(i);
            }
        }
        currentRowIndex = rowStart;
        Row row = createRow(rowStart);
        row.setHeightInPoints((short)20);
        Cell cell = row.getCell(colStart);
        cell.setCellStyle(captionStyle);
        cell.setCellValue(caption);
        sheet.addMergedRegion(new CellRangeAddress(rowStart, rowStart, colStart, colStart + currentColCount - 1));

    }

    protected void createHeader() {
        if (currentColCount <= 0) {
            return;
        }
        currentRowIndex = currentRowIndex + 1;
        Row row = createRow(currentRowIndex);
        row.setHeightInPoints((short)18);
        for (int i=colStart; i<colStart + currentColCount; i++) {
            Cell cell = row.getCell(i);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(header[i - colStart]);
        }
    }

    @Override
    public void appendRow(Object[] value) {
        if (currentColCount <= 0) {
            return;
        }
        currentRowIndex = currentRowIndex + 1;
        Row row = createRow(currentRowIndex);
        for (int i=colStart; i<colStart + currentColCount; i++) {
            Cell cell = row.getCell(i);
            cell.setCellStyle(bodyStyles[i - colStart]);
            Object v = null;
            if (value != null && value.length > i - colStart) {
                v = value[i - colStart];
            }
            setCellValue(cell, v);
        }
    }

    @Override
    public void appendRow(Object[] value, org.horx.common.report.CellStyle style) {
        if (currentColCount <= 0) {
            return;
        }
        currentRowIndex = currentRowIndex + 1;
        Row row = createRow(currentRowIndex);
        for (int i=colStart; i<colStart + currentColCount; i++) {
            Cell cell = row.getCell(i);
            cell.setCellStyle(bodyStyles[i - colStart]);
            Object v = null;
            if (value != null && value.length > i - colStart) {
                v = value[i - colStart];
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

    @Override
    public void appendRow(Object[] value, org.horx.common.report.CellStyle[] styles) {
        if (currentColCount <= 0) {
            return;
        }
        currentRowIndex = currentRowIndex + 1;
        Row row = createRow(currentRowIndex);
        for (int i=colStart; i<colStart + currentColCount; i++) {
            Cell cell = row.getCell(i);
            cell.setCellStyle(bodyStyles[i - colStart]);
            Object v = null;
            if (value != null && value.length > i - colStart) {
                v = value[i - colStart];
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
}
