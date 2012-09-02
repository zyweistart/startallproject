package com.hightern.ecside.table.core;

import java.util.Collection;
import java.util.Locale;

import com.hightern.ecside.table.bean.Column;
import com.hightern.ecside.table.bean.Export;
import com.hightern.ecside.table.bean.Row;
import com.hightern.ecside.table.bean.Table;
import com.hightern.ecside.table.context.Context;
import com.hightern.ecside.table.handler.ColumnHandler;
import com.hightern.ecside.table.handler.ExportHandler;
import com.hightern.ecside.table.handler.RowHandler;
import com.hightern.ecside.table.handler.TableHandler;
import com.hightern.ecside.table.handler.ViewHandler;
import com.hightern.ecside.table.limit.Limit;

@SuppressWarnings("unchecked")
public interface TableModel {

    public Context getContext();

    public Preferences getPreferences();

    public Messages getMessages();

    public Registry getRegistry();

    public Table getTableInstance();

    public Export getExportInstance();

    public Row getRowInstance();

    public Column getColumnInstance();

    public void addTable(Table table);

    public void addExport(Export export);

    public void addRow(Row row);

    public void addColumn(Column column);

    public void addColumns(String autoGenerateColumns);

    public void addParameter(String name, Object value);

    public TableHandler getTableHandler();

    public RowHandler getRowHandler();

    public ColumnHandler getColumnHandler();

    public ViewHandler getViewHandler();

    public ExportHandler getExportHandler();

    public Object getCurrentRowBean();

    public void setCurrentRowBean(Object bean);

    public Collection getCollectionOfBeans();

    public void setCollectionOfBeans(Collection collectionOfBeans);

    public Collection getCollectionOfFilteredBeans();

    public void setCollectionOfFilteredBeans(Collection collectionOfFilteredBeans);

    public Collection getCollectionOfPageBeans();

    public void setCollectionOfPageBeans(Collection collectionOfPageBeans);

    public Limit getLimit();

    public void setLimit(Limit limit);

    public Locale getLocale();

    public Collection execute() throws Exception;

    public void setColumnValues() throws Exception;

    public Object getViewData() throws Exception;

    public Object assemble() throws Exception;
}
