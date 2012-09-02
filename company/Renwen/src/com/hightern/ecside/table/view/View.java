package com.hightern.ecside.table.view;

import com.hightern.ecside.table.bean.Column;
import com.hightern.ecside.table.core.TableModel;

public interface View {

    public void beforeBody(TableModel model);

    public void body(TableModel model, Column column);

    public Object afterBody(TableModel model);
}
