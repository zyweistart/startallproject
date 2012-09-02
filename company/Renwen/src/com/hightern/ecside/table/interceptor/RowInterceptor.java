package com.hightern.ecside.table.interceptor;

import com.hightern.ecside.table.bean.Row;
import com.hightern.ecside.table.core.TableModel;

public interface RowInterceptor {

    public void addRowAttributes(TableModel tableModel, Row row);

    public void modifyRowAttributes(TableModel model, Row row);
}
