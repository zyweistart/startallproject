package com.hightern.ecside.table.interceptor;

import com.hightern.ecside.table.bean.Export;
import com.hightern.ecside.table.core.TableModel;

public interface ExportInterceptor {

    public void addExportAttributes(TableModel tableModel, Export export);
}
