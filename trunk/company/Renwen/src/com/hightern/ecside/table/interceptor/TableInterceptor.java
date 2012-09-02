package com.hightern.ecside.table.interceptor;

import com.hightern.ecside.table.bean.Table;
import com.hightern.ecside.table.core.TableModel;

public interface TableInterceptor {

    public void addTableAttributes(TableModel tableModel, Table table);
}
