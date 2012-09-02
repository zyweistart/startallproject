/**
 * Copyright (c) 2009. All rights reserved. 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.kernel.action;

import java.util.List;

import org.jfree.chart.JFreeChart;

import com.hightern.kernel.model.BaseModel;

/**
 * 报表的基本控制器
 * 
 * @author Stone
 * @param <T>
 */
public class ReportAction<T extends BaseModel> extends BaseAction<T> {
	
	private static final long serialVersionUID = 1L;
	public static final String JFREECHART = "jfreechart"; // Jfreechart返回的类型
	public static final String JASPERREPORTS = "jasperreport"; // 报表返回类型
	private String height; // JfreeChart高度
	private String width; // JfreeChart宽度
	private String imageType;// JfreeChart类型
	@SuppressWarnings("rawtypes")
	private List reportDatas; // 报表的数据
	private JFreeChart chart; // 图形报表
	private String location;// JasperReport路径
	private String format;// JasperReport输出格式
	
	public JFreeChart getChart() {
		return chart;
	}
	
	public void setChart(JFreeChart chart) {
		this.chart = chart;
	}
	
	public String getFormat() {
		if (format == null || format.length() <= 0) {
			format = "HTML";
		}
		return format;
	}
	
	public void setFormat(String format) {
		this.format = format;
	}
	
	public String getHeight() {
		if (height == null || height.length() <= 0) {
			height = "450";
		}
		return height;
	}
	
	public void setHeight(String height) {
		this.height = height;
	}
	
	public String getImageType() {
		return imageType;
	}
	
	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	@SuppressWarnings("rawtypes")
	public List getReportDatas() {
		return reportDatas;
	}
	
	@SuppressWarnings("rawtypes")
	public void setReportDatas(List reportDatas) {
		this.reportDatas = reportDatas;
	}
	
	public String getWidth() {
		if (width == null || width.length() <= 0) {
			width = "650";
		}
		return width;
	}
	
	public void setWidth(String width) {
		this.width = width;
	}
}
