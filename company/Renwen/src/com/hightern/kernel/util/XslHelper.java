/**
 * Copyright (c) 2009. All rights reserved. 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.kernel.util;

import java.io.InputStream;
import java.util.Arrays;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;

public class XslHelper {
	
	/**
	 * 判断Excel是否与模版中的字段匹配
	 * 
	 * @param excel
	 *            上传的Excel文档
	 * @param headers
	 *            模版中的列
	 * @param row
	 *            第几行
	 * @return true:false
	 */
	public static boolean checkFormat(InputStream excel, String[] headers, int row) {
		HSSFWorkbook hssFWorkbook;
		try {
			hssFWorkbook = new HSSFWorkbook(excel);
			final HSSFSheet sheet = hssFWorkbook.getSheetAt(0);
			final HSSFRow rows = sheet.getRow(row);
			final int cellNumber = rows.getLastCellNum();
			final String[] cells = new String[headers.length];
			for (int i = 0; i < cellNumber; i++) {
				if (rows.getCell(i) != null && rows.getCell(i).toString().length() > 0) {
					cells[i] = rows.getCell(i).toString();
				}
			}
			if (Arrays.equals(cells, headers)) {
				return true;
			} else {
				return false;
			}
		} catch (final Exception e) {
			return false;
		}
	}
	
	/**
	 * 检测必填字段
	 * 
	 * @param cell
	 * @return
	 */
	public static boolean checkNeed(String cell) {
		if (cell.equals("") || cell == null) { return false; }
		return true;
	}
	
	/**
	 * 将String 转为Integer
	 * 
	 * @param s
	 * @return
	 */
	public static Integer stringToInteger(String s) {
		final Double d = Double.parseDouble(s);
		return d.intValue();
	}
	
	/**
	 * 消除小数点
	 * 
	 * @param s
	 * @return
	 */
	public static String trimDo(String s) {
		if (s.indexOf(".") > 0) {
			s = s.substring(0, s.indexOf("."));
		}
		return s;
	}
	
	/**
	 * 自动调整列宽
	 * 
	 * @param sheet
	 * @param num
	 */
	public static void autoSizeColumn(HSSFSheet sheet, int num) {
		for (int i = 0; i <= num; i++) {
			sheet.autoSizeColumn((short) i);
		}
	}
	
	/**
	 * 设置字体
	 * 
	 * @param workbook
	 * @param color
	 * @param height
	 *            字体大小
	 * @return
	 */
	public static HSSFFont setFont(HSSFWorkbook workbook, int height) {
		final HSSFFont font = workbook.createFont();
		font.setColor((short) 0);
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) height);
		return font;
	}
	
	/**
	 * 设置样式
	 * 
	 * @param workbook
	 * @param font
	 *            字体大小
	 * @param align
	 *            对齐方式
	 * @return HSSFCellStyle
	 */
	public static HSSFCellStyle setCellStyle(HSSFWorkbook workbook, HSSFFont font, int align) {
		final HSSFCellStyle normal = workbook.createCellStyle();
		normal.setFont(font);
		normal.setAlignment((short) align);
		normal.setVerticalAlignment(CellStyle.VERTICAL_TOP); // 垂直居中
		normal.setBorderBottom(CellStyle.BORDER_THIN);
		normal.setBorderLeft(CellStyle.BORDER_THIN);
		normal.setBorderRight(CellStyle.BORDER_THIN);
		normal.setBorderTop(CellStyle.BORDER_THIN);
		normal.setAlignment(CellStyle.ALIGN_CENTER);
		normal.setWrapText(true);
		return normal;
	}
}
