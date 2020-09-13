package org.horx.common.exchange.file.excel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.horx.common.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 读取Excel的工具类
 * @since 1.0
 */
public class ExcelReader implements AutoCloseable {
    private static final Logger logger = LoggerFactory.getLogger(ExcelReader.class);

    private InputStream is;

    private Workbook workbook;

    /**
     * 当前Sheet。
     */
    private Sheet sheet;

    /**
     * 当前Sheet的序号。
     */
    private int currentSheetIndex;

    /**
     * 当前Sheet的总行数。
     */
    private int currentSheetRows;

    /**
     * 当前行。
     */
    private Row currentRow;

    /**
     * 当前行的序号。
     */
    private int currentRowIndex;


    /**
     * 构造方法。
     * @param fileName Excel文件绝对路径。
     * @throws IOException
     */
    public ExcelReader(String fileName) throws IOException {
        if (fileName == null) {
            throw new IOException("文件不存在");
        }
        is = new FileInputStream(fileName);
        if (fileName.toLowerCase().endsWith(".xls")) {
            workbook = new HSSFWorkbook(is);
        } else if (fileName.toLowerCase().endsWith(".xlsx")) {
            workbook = new XSSFWorkbook(is);
        } else {
            throw new IOException("文件格式不支持");
        }
        setCurrentSheetIndex(0);
    }

    /**
     * 构造方法。
     * @param fileName Excel文件绝对路径。
     * @param currentSheetIndex 当前Sheet的序号。序号从0开始。
     * @throws IOException
     */
    public ExcelReader(String fileName, int currentSheetIndex) throws IOException {
        this(fileName);
        setCurrentSheetIndex(currentSheetIndex);
    }

    /**
     * 构造方法。
     * @param fileName Excel文件名。
     * @throws IOException
     */
    public ExcelReader(String fileName, InputStream is) throws IOException {
        if (fileName.toLowerCase().endsWith(".xls")) {
            workbook = new HSSFWorkbook(is);
        } else if (fileName.toLowerCase().endsWith(".xlsx")) {
            workbook = new XSSFWorkbook(is);
        } else {
            throw new IOException("文件格式不支持");
        }
        setCurrentSheetIndex(0);
        this.is = is;
    }

    /**
     * 设置当前Sheet。
     * @param index 当前Sheet的序号。序号从0开始。
     */
    public void setCurrentSheetIndex(int index) {
        sheet = workbook.getSheetAt(index);
        this.currentSheetIndex = index;
        if (sheet != null) {
            currentRowIndex = 0;
            currentSheetRows = sheet.getLastRowNum() + 1;//sheet.getPhysicalNumberOfRows(); //sheet.getLastRowNum() - sheet.getFirstRowNum();
            currentRow = sheet.getRow(0);
        } else {
            currentRowIndex = 0;
            currentRow = null;
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
        currentRow = getRow(rowIndex);
        this.currentRowIndex = rowIndex;
    }

    /**
     * 获取当前Sheet的序号。
     * @return
     */
    public int getCurrentSheetIndex() {
        return this.currentSheetIndex;
    }

    /**
     * 获取当前行的序号。
     * @return
     */
    public int getCurrentRowIndex() {
        return this.currentRowIndex;
    }

    /**
     * 获取Excel中Sheet的总个数。
     * @return
     */
    public int getNumberOfSheets() {
        return workbook.getNumberOfSheets();
    }

    /**
     * 获取Sheet的名称。
     * @param sheetIndex
     * @return
     */
    public String getSheetName(int sheetIndex) {
        return workbook.getSheetName(sheetIndex);
    }

    /**
     * 获取当前Sheet的总行数。
     * @return
     */
    public int getNumberOfRows() {
        if (sheet == null) {
            return 0;
        }
        return this.currentSheetRows;
    }

    /**
     * 下一行。
     * @return
     */
    public boolean nextRow() {
        if (this.currentRowIndex < this.currentSheetRows - 1) {
            this.currentRowIndex++;
            this.currentRow = getRow(this.currentRowIndex);
            return true;
        }
        return false;
    }

    public int getNumberOfCells() {
        return (currentRow == null) ? 0 : currentRow.getLastCellNum() + 1;
    }

    /**
     * 获取单元格的字符串值。
     * @param rowIndex 行号
     * @param colIndex 列号
     * @return
     */
    public String getString(int rowIndex, int colIndex) {
        Cell cell = getCell(rowIndex, colIndex);
        return getString(cell);
    }

    /**
     * 获取当前行指定列号的单元格的字符串值。
     * @param colIndex 列号。
     * @return
     */
    public String getString(int colIndex) {
        Cell cell = getCell(colIndex);
        return getString(cell);
    }

    private String getString(Cell cell) {
        if (cell == null) {
            return null;
        }
        CellType cellType = cell.getCellType();
        String result = null;
        switch (cellType) {
            case BLANK :
                result = "";
                break;
            case BOOLEAN :
                result = String.valueOf(cell.getBooleanCellValue());
                break;
            case ERROR :
                result = String.valueOf(cell.getErrorCellValue());
                break;
            case FORMULA :
                if (DateUtil.isCellDateFormatted(cell)) {
                    double d = cell.getNumericCellValue();
                    Date date = DateUtil.getJavaDate(d);
                    result = DateUtils.format(date, "yyyy-MM-dd HH:mm:ss");
                } else {
                    result = String.valueOf(cell.getNumericCellValue());
                    if (result != null && result.endsWith(".0")) {
                        result = result.substring(0, result.lastIndexOf('.'));
                    }
                }
                boolean ok = true;

                if (!ok) {
                    result = cell.getStringCellValue();
                    ok = true;
                }
                if (!ok) {
                    result = String.valueOf(cell.getNumericCellValue());
                }
                break;
            case NUMERIC :
                if (DateUtil.isCellDateFormatted(cell)) {
                    double d = cell.getNumericCellValue();
                    Date date = DateUtil.getJavaDate(d);
                    result = DateUtils.format(date, "yyyy-MM-dd HH:mm:ss");
                } else {
                    result = String.valueOf(cell.getNumericCellValue());
                    if (result != null && result.endsWith(".0")) {
                        result = result.substring(0,  result.lastIndexOf('.'));
                    }
                }
                break;
            default:
                result = cell.getStringCellValue();
        }
        return result;
    }

    /**
     * 获取Date类型数据。
     * @param rowIndex 行号
     * @param colIndex 列号
     * @return Date类型数据。如果不存在或解析错误，返回null。
     */
    public Date getDate(int rowIndex, int colIndex) {
        Cell cell = getCell(rowIndex, colIndex);
        return getDate(cell);
    }

    /**
     * 获取当前行指定列号的单元格的Date类型数据。
     * @param colIndex 列号
     * @return Date类型数据。如果不存在或解析错误，返回null。
     */
    public Date getDate(int colIndex) {
        Cell cell = getCell(colIndex);
        return getDate(cell);
    }

    private Date getDate(Cell cell) {
        if (cell == null) return null;
        Date result = null;
        CellType cellType = cell.getCellType();
        if (cellType == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
            double d = cell.getNumericCellValue();
            result = DateUtil.getJavaDate(d);
        } else {
            String str = getString(cell);
            try {
                result = DateUtils.parse(str);
            } catch (Exception e) {
                logger.warn("解析日期错误", e);
            }
        }
        return result;
    }

    /**
     * 获取Boolean类型数据。
     * @param rowIndex 行号
     * @param colIndex 列号
     * @return Boolean类型数据。如果不存在或解析错误，返回null。
     */
    public Boolean getBoolean(int rowIndex, int colIndex) {
        Cell cell = getCell(rowIndex, colIndex);
        return getBoolean(cell);
    }

    /**
     * 获取当前行指定列号的单元格的Boolean类型数据。
     * @param colIndex 列号
     * @return Boolean类型数据。如果不存在或解析错误，返回null。
     */
    public Boolean getBoolean(int colIndex) {
        Cell cell = getCell(colIndex);
        return getBoolean(cell);
    }

    private Boolean getBoolean(Cell cell) {
        if (cell == null) return null;
        Boolean result = null;
        CellType cellType = cell.getCellType();
        if (cellType == CellType.BOOLEAN) {
            result = cell.getBooleanCellValue();
        } else {
            String str = getString(cell);
            result = Boolean.parseBoolean(str);
        }
        return result;
    }

    /**
     * 获取Double类型数据。
     * @param rowIndex 行号
     * @param colIndex 列号
     * @return Double类型数据。如果不存在或解析错误，返回null。
     */
    public Double getDouble(int rowIndex, int colIndex) {
        Cell cell = getCell(rowIndex, colIndex);
        return getDouble(cell);
    }

    /**
     * 获取当前行指定列号的单元格的Double类型数据。
     * @param colIndex 列号
     * @return Double类型数据。如果不存在或解析错误，返回null。
     */
    public Double getDouble(int colIndex) {
        Cell cell = getCell(colIndex);
        return getDouble(cell);
    }

    private Double getDouble(Cell cell) {
        if (cell == null) return null;
        Double result = null;
        CellType cellType = cell.getCellType();
        if (cellType == CellType.NUMERIC) {
            result = cell.getNumericCellValue();
        } else {
            String str = getString(cell);
            result = Double.parseDouble(str);
        }
        return result;
    }

    /**
     * 获取Integer类型数据。
     * @param rowIndex 行号
     * @param colIndex 列号
     * @return Integer类型数据。如果不存在或解析错误，返回null。
     */
    public Integer getInteger(int rowIndex, int colIndex) {
        Double num = getDouble(rowIndex, colIndex);
        if (num == null) {
            return null;
        }
        Integer result = num.intValue();
        return result;
    }

    /**
     * 获取当前行指定列号的单元格的Integer类型数据。
     * @param colIndex 列号
     * @return Integer类型数据。如果不存在或解析错误，返回null。
     */
    public Integer getInteger(int colIndex) {
        Double num = getDouble(colIndex);
        if (num == null) {
            return null;
        }
        Integer result = num.intValue();
        return result;
    }

    /**
     * 获取Long类型数据。
     * @param rowIndex 行号
     * @param colIndex 列号
     * @return Long类型数据。如果不存在或解析错误，返回null。
     */
    public Long getLong(int rowIndex, int colIndex) {
        Double num = getDouble(rowIndex, colIndex);
        if (num == null) {
            return null;
        }
        Long result = num.longValue();
        return result;
    }

    /**
     * 获取当前行指定列号的单元格的Long类型数据。
     * @param colIndex 列号
     * @return Long类型数据。如果不存在或解析错误，返回null。
     */
    public Long getLong(int colIndex) {
        Double num = getDouble(colIndex);
        if (num == null) {
            return null;
        }
        Long result = num.longValue();
        return result;
    }

    /**
     * 获取Float类型数据。
     * @param rowIndex 行号
     * @param colIndex 列号
     * @return Float类型数据。如果不存在或解析错误，返回null。
     */
    public Float getFloat(int rowIndex, int colIndex) {
        Double num = getDouble(rowIndex, colIndex);
        if (num == null) {
            return null;
        }
        return num.floatValue();
    }

    /**
     * 获取当前行指定列号的单元格的Float类型数据。
     * @param colIndex 列号
     * @return Float类型数据。如果不存在或解析错误，返回null。
     */
    public Float getFloat(int colIndex) {
        Double num = getDouble(colIndex);
        if (num == null) {
            return null;
        }
        return num.floatValue();
    }

    private Row getRow(int rowIndex) {
        if (sheet == null) {
            return null;
        }
        Row row = sheet.getRow(rowIndex);
        return row;
    }

    private Cell getCell(int rowIndex, int colIndex) {
        Row row = getRow(rowIndex);
        if (row == null) {
            return null;
        }
        Cell cell = row.getCell(colIndex);
        return cell;
    }

    private Cell getCell(int colIndex) {
        if (this.currentRow == null) {
            return null;
        }
        return this.currentRow.getCell(colIndex);
    }

    /**
     * 判断是否是已合并的单元格。
     * @param rowIndex
     * @param colIndex
     * @return
     */
    public boolean isMerged(int rowIndex, int colIndex) {
        for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            if (range.isInRange(rowIndex, colIndex)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取合并单元格的rowspan、colspan。
     * 当rowIndex、colIndex是合并单元的第一个单元格时，返回单元格的rowspan、colspan；如果是被合并的单元格，返回[0, 0]。非合并单元格返回[1, 1]。
     * @param rowIndex
     * @param colIndex
     * @return [rowspan, colspan]
     */
    public int[] getCellMergedSpan(int rowIndex, int colIndex) {
        for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            if (range.isInRange(rowIndex, colIndex)) {
                if (range.getFirstRow() == rowIndex && range.getFirstColumn() == colIndex) {
                    return new int[]{range.getLastRow() - range.getFirstRow() + 1, range.getLastColumn() - range.getFirstColumn() + 1};
                } else {
                    return new int[]{0, 0};
                }
            }
        }

        return new int[] {1, 1};
    }

    /**
     * 关闭Excel文件。
     */
    @Override
    public void close() {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                logger.error("关闭Excel文件失败", e);
            }
        }
    }
}
