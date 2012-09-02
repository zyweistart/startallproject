package com.hightern.ecside.table.interceptor;

import com.hightern.ecside.table.bean.Column;
import com.hightern.ecside.table.core.TableModel;

public interface ColumnInterceptor {

    public void addColumnAttributes(TableModel tableModel, Column column);

    public void modifyColumnAttributes(TableModel model, Column column);
}
