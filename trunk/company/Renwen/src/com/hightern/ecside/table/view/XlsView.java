package com.hightern.ecside.table.view;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import com.hightern.ecside.table.bean.Column;
import com.hightern.ecside.table.calc.CalcResult;
import com.hightern.ecside.table.calc.CalcUtils;
import com.hightern.ecside.table.core.PreferencesConstants;
import com.hightern.ecside.table.core.TableModel;
import com.hightern.ecside.util.ExtremeUtils;

public class XlsView implements View {

    private static Log logger = LogFactory.getLog(XlsView.class);
    public static final int WIDTH_MULT = 800; // width per char
    public static final int MIN_CHARS = 10; // minimum char width
    public static final short DEFAULT_FONT_HEIGHT = 12;
    public static final double NON_NUMERIC = -.99999;
    public static final String DEFAULT_MONEY_FORMAT = "$###,###,##0.00";
    public static final String DEFAULT_PERCENT_FORMAT = "##0.0%";
    public static final String NBSP = "&nbsp;";
    private HSSFWorkbook wb;
    private HSSFSheet sheet;
    private HSSFPrintSetup ps;
    @SuppressWarnings("unchecked")
	private Map styles;
    private short rownum;
    private short cellnum;
    private HSSFRow hssfRow;
    private String moneyFormat;
    private String percentFormat;
    private String encoding;

    public XlsView() {
    }

    public void beforeBody(TableModel model) {
        logger.debug("XlsView.init()");
        moneyFormat = model.getPreferences().getPreference(PreferencesConstants.TABLE_EXPORTABLE + "format.money");
        if (StringUtils.isEmpty(moneyFormat)) {
            moneyFormat = DEFAULT_MONEY_FORMAT;
        }
        percentFormat = model.getPreferences().getPreference(PreferencesConstants.TABLE_EXPORTABLE + "format.percent");
        if (StringUtils.isEmpty(percentFormat)) {
            percentFormat = DEFAULT_PERCENT_FORMAT;
        }
        encoding = model.getExportHandler().getCurrentExport().getEncoding();
        wb = new HSSFWorkbook();
        sheet = wb.createSheet();
        styles = initStyles(wb);
        ps = sheet.getPrintSetup();
        sheet.setAutobreaks(true);
        ps.setFitHeight((short) 1);
        ps.setFitWidth((short) 1);
        createHeader(model);
    }

    @SuppressWarnings("deprecation")
    public void body(TableModel model, Column column) {
        if (column.isFirstColumn()) {
            rownum++;
            cellnum = 0;
            hssfRow = sheet.createRow(rownum);
        }
        String value = ExportViewUtils.parseXLS(column.getCellDisplay());
        if (!value.startsWith("<input") && value != null && value.length() > 0) {
            HSSFCell hssfCell = hssfRow.createCell(cellnum);
            setCellEncoding(hssfCell);
            if (column.isEscapeAutoFormat()) {
                writeToCellAsText(hssfCell, value, "");
            } else {
                writeToCellFormatted(hssfCell, value, "");
            }
            cellnum++;
        }
        
    }

    public Object afterBody(TableModel model) {
        if (model.getLimit().getTotalRows() != 0) {
            totals(model);
        }
        return wb;
    }

    @SuppressWarnings({"unchecked", "deprecation"})
    private void createHeader(TableModel model) {
        rownum = 0;
        cellnum = 0;
        HSSFRow row = sheet.createRow(rownum);
        List columns = model.getColumnHandler().getHeaderColumns();
        for (Iterator iter = columns.iterator(); iter.hasNext();) {
            Column column = (Column) iter.next();
            String title = column.getCellDisplay();
            if ((!title.startsWith("<img") && (!title.startsWith("<input")))) {
                HSSFCell hssfCell = row.createCell(cellnum);
                setCellEncoding(hssfCell);
                hssfCell.setCellStyle((HSSFCellStyle) styles.get("titleStyle"));
                hssfCell.setCellType(HSSFCell.CELL_TYPE_STRING);
                hssfCell.setCellValue(title);
                int valWidth = (title + "").length() * WIDTH_MULT;
                sheet.setColumnWidth(hssfCell.getCellNum(), (short) valWidth);
                cellnum++;
            }
        }
    }

    private void writeToCellAsText(HSSFCell cell, String value, String styleModifier) {
        if (value.trim().equals(NBSP)) {
            value = "";
        }
        cell.setCellStyle((HSSFCellStyle) styles.get("textStyle" + styleModifier));
        fixWidthAndPopulate(cell, NON_NUMERIC, value);
    }

    private void writeToCellFormatted(HSSFCell cell, String value, String styleModifier) {
        double numeric = NON_NUMERIC;
        try {
            numeric = Double.parseDouble(value);
        } catch (Exception e) {
            numeric = NON_NUMERIC;
        }
        if (value.startsWith("$") || value.endsWith("%") || value.startsWith("($")) {
            boolean moneyFlag = (value.startsWith("$") || value.startsWith("($"));
            boolean percentFlag = value.endsWith("%");
            value = StringUtils.replace(value, "$", "");
            value = StringUtils.replace(value, "%", "");
            value = StringUtils.replace(value, ",", "");
            value = StringUtils.replace(value, "(", "-");
            value = StringUtils.replace(value, ")", "");
            try {
                numeric = Double.parseDouble(value);
            } catch (Exception e) {
                numeric = NON_NUMERIC;
            }
            cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
            if (moneyFlag) {
                cell.setCellStyle((HSSFCellStyle) styles.get("moneyStyle" + styleModifier));
            } else if (percentFlag) {
                numeric = numeric / 100;
                cell.setCellStyle((HSSFCellStyle) styles.get("percentStyle" + styleModifier));
            }
        } else if (numeric != NON_NUMERIC) {
            cell.setCellStyle((HSSFCellStyle) styles.get("numericStyle" + styleModifier));
        } else {
            if (value.trim().equals(NBSP)) {
                value = "";
            }
            cell.setCellStyle((HSSFCellStyle) styles.get("textStyle"
                    + styleModifier));
        }

        fixWidthAndPopulate(cell, numeric, value);
    }

    @SuppressWarnings("deprecation")
    private void fixWidthAndPopulate(HSSFCell cell, double numeric, String value) {
        int valWidth = 0;
        if (numeric != NON_NUMERIC) {
            cell.setCellValue(numeric);
            valWidth = (cell.getNumericCellValue() + "$,.").length() * WIDTH_MULT;
        } else {
            cell.setCellValue(value);
            valWidth = (cell.getStringCellValue() + "").length() * WIDTH_MULT;
            if (valWidth < (WIDTH_MULT * MIN_CHARS)) {
                valWidth = WIDTH_MULT * MIN_CHARS;
            }
        }
        if (valWidth > sheet.getColumnWidth(cell.getCellNum())) {
            sheet.setColumnWidth(cell.getCellNum(), (short) valWidth);
        }
    }

    @SuppressWarnings("unchecked")
    private Map initStyles(HSSFWorkbook wb) {
        return initStyles(wb, DEFAULT_FONT_HEIGHT);
    }

    @SuppressWarnings({"unchecked"})
    private Map initStyles(HSSFWorkbook wb, short fontHeight) {
        Map result = new HashMap();
        HSSFCellStyle titleStyle = wb.createCellStyle();
        HSSFCellStyle textStyle = wb.createCellStyle();
        HSSFCellStyle boldStyle = wb.createCellStyle();
        HSSFCellStyle numericStyle = wb.createCellStyle();
        HSSFCellStyle numericStyleBold = wb.createCellStyle();
        HSSFCellStyle moneyStyle = wb.createCellStyle();
        HSSFCellStyle moneyStyleBold = wb.createCellStyle();
        HSSFCellStyle percentStyle = wb.createCellStyle();
        HSSFCellStyle percentStyleBold = wb.createCellStyle();
        HSSFCellStyle moneyStyle_Totals = wb.createCellStyle();
        HSSFCellStyle naStyle_Totals = wb.createCellStyle();
        HSSFCellStyle numericStyle_Totals = wb.createCellStyle();
        HSSFCellStyle percentStyle_Totals = wb.createCellStyle();
        HSSFCellStyle textStyle_Totals = wb.createCellStyle();
        result.put("titleStyle", titleStyle);
        result.put("textStyle", textStyle);
        result.put("boldStyle", boldStyle);
        result.put("numericStyle", numericStyle);
        result.put("numericStyleBold", numericStyleBold);
        result.put("moneyStyle", moneyStyle);
        result.put("moneyStyleBold", moneyStyleBold);
        result.put("percentStyle", percentStyle);
        result.put("percentStyleBold", percentStyleBold);

        // Add to export totals
        result.put("moneyStyle_Totals", moneyStyle_Totals);
        result.put("naStyle_Totals", naStyle_Totals);
        result.put("numericStyle_Totals", numericStyle_Totals);
        result.put("percentStyle_Totals", percentStyle_Totals);
        result.put("textStyle_Totals", textStyle_Totals);

        HSSFDataFormat format = wb.createDataFormat();

        // Global fonts
        HSSFFont font = wb.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        font.setColor(HSSFColor.BLACK.index);
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setFontHeightInPoints(fontHeight);

        HSSFFont fontBold = wb.createFont();
        fontBold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        fontBold.setColor(HSSFColor.BLACK.index);
        fontBold.setFontName(HSSFFont.FONT_ARIAL);
        fontBold.setFontHeightInPoints(fontHeight);

        // Money Style
        moneyStyle.setFont(font);
        moneyStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        moneyStyle.setBottomBorderColor(HSSFColor.BLACK.index);
        moneyStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        moneyStyle.setLeftBorderColor(HSSFColor.BLACK.index);
        moneyStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        moneyStyle.setRightBorderColor(HSSFColor.BLACK.index);
        moneyStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        moneyStyle.setTopBorderColor(HSSFColor.BLACK.index);
        moneyStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        moneyStyle.setDataFormat(format.getFormat(moneyFormat));

        // Money Style Bold
        moneyStyleBold.setFont(fontBold);
        moneyStyleBold.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        moneyStyleBold.setBottomBorderColor(HSSFColor.BLACK.index);
        moneyStyleBold.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        moneyStyleBold.setLeftBorderColor(HSSFColor.BLACK.index);
        moneyStyleBold.setBorderRight(HSSFCellStyle.BORDER_THIN);
        moneyStyleBold.setRightBorderColor(HSSFColor.BLACK.index);
        moneyStyleBold.setBorderTop(HSSFCellStyle.BORDER_THIN);
        moneyStyleBold.setTopBorderColor(HSSFColor.BLACK.index);
        moneyStyleBold.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        moneyStyleBold.setDataFormat(format.getFormat(moneyFormat));

        // Percent Style
        percentStyle.setFont(font);
        percentStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        percentStyle.setBottomBorderColor(HSSFColor.BLACK.index);
        percentStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        percentStyle.setLeftBorderColor(HSSFColor.BLACK.index);
        percentStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        percentStyle.setRightBorderColor(HSSFColor.BLACK.index);
        percentStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        percentStyle.setTopBorderColor(HSSFColor.BLACK.index);
        percentStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        percentStyle.setDataFormat(format.getFormat(percentFormat));

        // Percent Style Bold
        percentStyleBold.setFont(fontBold);
        percentStyleBold.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        percentStyleBold.setBottomBorderColor(HSSFColor.BLACK.index);
        percentStyleBold.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        percentStyleBold.setLeftBorderColor(HSSFColor.BLACK.index);
        percentStyleBold.setBorderRight(HSSFCellStyle.BORDER_THIN);
        percentStyleBold.setRightBorderColor(HSSFColor.BLACK.index);
        percentStyleBold.setBorderTop(HSSFCellStyle.BORDER_THIN);
        percentStyleBold.setTopBorderColor(HSSFColor.BLACK.index);
        percentStyleBold.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        percentStyleBold.setDataFormat(format.getFormat(percentFormat));

        // Standard Numeric Style
        numericStyle.setFont(font);
        numericStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        numericStyle.setBottomBorderColor(HSSFColor.BLACK.index);
        numericStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        numericStyle.setLeftBorderColor(HSSFColor.BLACK.index);
        numericStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        numericStyle.setRightBorderColor(HSSFColor.BLACK.index);
        numericStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        numericStyle.setTopBorderColor(HSSFColor.BLACK.index);
        numericStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);

        // Standard Numeric Style Bold
        numericStyleBold.setFont(fontBold);
        numericStyleBold.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        numericStyleBold.setBottomBorderColor(HSSFColor.BLACK.index);
        numericStyleBold.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        numericStyleBold.setLeftBorderColor(HSSFColor.BLACK.index);
        numericStyleBold.setBorderRight(HSSFCellStyle.BORDER_THIN);
        numericStyleBold.setRightBorderColor(HSSFColor.BLACK.index);
        numericStyleBold.setBorderTop(HSSFCellStyle.BORDER_THIN);
        numericStyleBold.setTopBorderColor(HSSFColor.BLACK.index);
        numericStyleBold.setAlignment(HSSFCellStyle.ALIGN_RIGHT);

        // Title Style
        titleStyle.setFont(fontBold);
        titleStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBottomBorderColor(HSSFColor.BLACK.index);
        titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        titleStyle.setLeftBorderColor(HSSFColor.BLACK.index);
        titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        titleStyle.setRightBorderColor(HSSFColor.BLACK.index);
        titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        titleStyle.setTopBorderColor(HSSFColor.BLACK.index);
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        // Standard Text Style
        textStyle.setFont(font);
        textStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        textStyle.setBottomBorderColor(HSSFColor.BLACK.index);
        textStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        textStyle.setLeftBorderColor(HSSFColor.BLACK.index);
        textStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        textStyle.setRightBorderColor(HSSFColor.BLACK.index);
        textStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        textStyle.setTopBorderColor(HSSFColor.BLACK.index);
        textStyle.setWrapText(true);

        // Standard Text Style
        boldStyle.setFont(fontBold);
        boldStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        boldStyle.setBottomBorderColor(HSSFColor.BLACK.index);
        boldStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        boldStyle.setLeftBorderColor(HSSFColor.BLACK.index);
        boldStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        boldStyle.setRightBorderColor(HSSFColor.BLACK.index);
        boldStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        boldStyle.setTopBorderColor(HSSFColor.BLACK.index);
        boldStyle.setWrapText(true);

        // Money Style Total
        moneyStyle_Totals.setFont(fontBold);
        moneyStyle_Totals.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        moneyStyle_Totals.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        moneyStyle_Totals.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        moneyStyle_Totals.setBottomBorderColor(HSSFColor.BLACK.index);
        moneyStyle_Totals.setBorderTop(HSSFCellStyle.BORDER_THIN);
        moneyStyle_Totals.setTopBorderColor(HSSFColor.BLACK.index);
        moneyStyle_Totals.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        moneyStyle_Totals.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        moneyStyle_Totals.setDataFormat(format.getFormat(moneyFormat));

        // n/a Style Total
        naStyle_Totals.setFont(fontBold);
        naStyle_Totals.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        naStyle_Totals.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        naStyle_Totals.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        naStyle_Totals.setBottomBorderColor(HSSFColor.BLACK.index);
        naStyle_Totals.setBorderTop(HSSFCellStyle.BORDER_THIN);
        naStyle_Totals.setTopBorderColor(HSSFColor.BLACK.index);
        naStyle_Totals.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        naStyle_Totals.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        // Numeric Style Total
        numericStyle_Totals.setFont(fontBold);
        numericStyle_Totals.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        numericStyle_Totals.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        numericStyle_Totals.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        numericStyle_Totals.setBottomBorderColor(HSSFColor.BLACK.index);
        numericStyle_Totals.setBorderTop(HSSFCellStyle.BORDER_THIN);
        numericStyle_Totals.setTopBorderColor(HSSFColor.BLACK.index);
        numericStyle_Totals.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        numericStyle_Totals.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        // Percent Style Total
        percentStyle_Totals.setFont(fontBold);
        percentStyle_Totals.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        percentStyle_Totals.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        percentStyle_Totals.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        percentStyle_Totals.setBottomBorderColor(HSSFColor.BLACK.index);
        percentStyle_Totals.setBorderTop(HSSFCellStyle.BORDER_THIN);
        percentStyle_Totals.setTopBorderColor(HSSFColor.BLACK.index);
        percentStyle_Totals.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        percentStyle_Totals.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        percentStyle_Totals.setDataFormat(format.getFormat(percentFormat));

        // Text Style Total
        textStyle_Totals.setFont(fontBold);
        textStyle_Totals.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        textStyle_Totals.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        textStyle_Totals.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        textStyle_Totals.setBottomBorderColor(HSSFColor.BLACK.index);
        textStyle_Totals.setBorderTop(HSSFCellStyle.BORDER_THIN);
        textStyle_Totals.setTopBorderColor(HSSFColor.BLACK.index);
        textStyle_Totals.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        textStyle_Totals.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        return result;
    }

    @SuppressWarnings({"deprecation", "unchecked"})
    public void totals(TableModel model) {
        Column firstCalcColumn = model.getColumnHandler().getFirstCalcColumn();
        if (firstCalcColumn != null) {
            int rows = firstCalcColumn.getCalc().length;
            for (int i = 0; i < rows; i++) {
                rownum++;
                HSSFRow row = sheet.createRow(rownum);
                cellnum = 0;
                for (Iterator iter = model.getColumnHandler().getColumns().iterator(); iter.hasNext();) {
                    Column column = (Column) iter.next();
                    if (column.isFirstColumn()) {
                        String calcTitle = CalcUtils.getFirstCalcColumnTitleByPosition(model, i);
                        HSSFCell cell = row.createCell(cellnum);
                        setCellEncoding(cell);
                        if (column.isEscapeAutoFormat()) {
                            writeToCellAsText(cell, calcTitle, "_Totals");
                        } else {
                            writeToCellFormatted(cell, calcTitle, "_Totals");
                        }
                        cellnum++;
                        continue;
                    }

                    if (column.isCalculated()) {
                        CalcResult calcResult = CalcUtils.getCalcResultsByPosition(model, column, i);
                        Number value = calcResult.getValue();
                        HSSFCell cell = row.createCell(cellnum);
                        setCellEncoding(cell);
                        if (value != null) {
                            if (column.isEscapeAutoFormat()) {
                                writeToCellAsText(cell, value.toString(),
                                        "_Totals");
                            } else {
                                writeToCellFormatted(cell, ExtremeUtils.formatNumber(column.getFormat(),
                                        value, model.getLocale()),
                                        "_Totals");
                            }
                        } else {
                            cell.setCellStyle((HSSFCellStyle) styles.get("naStyle_Totals"));
                            cell.setCellValue("n/a");
                        }
                        cellnum++;
                    } else {
                        HSSFCell cell = row.createCell(cellnum);
                        setCellEncoding(cell);
                        writeToCellFormatted(cell, "", "_Totals");
                        cellnum++;
                    }
                }
            }
        }

    }

    // add to set Cell encoding
    // 由于目前采用POI3.6,暂将之前版本的编码设置方式屏蔽掉
    private void setCellEncoding(HSSFCell cell) {
        if (encoding.equalsIgnoreCase("UTF")) {
        } else if (encoding.equalsIgnoreCase("UNICODE")) {
        }
    }
}
