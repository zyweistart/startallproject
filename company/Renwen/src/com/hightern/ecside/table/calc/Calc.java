package com.hightern.ecside.table.calc;

import com.hightern.ecside.table.bean.Column;
import com.hightern.ecside.table.core.TableModel;

public interface Calc {

    public Number getCalcResult(TableModel model, Column column);
}
