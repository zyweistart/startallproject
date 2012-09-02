package com.hightern.kernel.util;

import java.util.List;

public class Table {
	
	private String entityName;
	private List<Column> columns;
	
	public String getEntityName() {
		return entityName;
	}
	
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	
	public List<Column> getColumns() {
		return columns;
	}
	
	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}
}
