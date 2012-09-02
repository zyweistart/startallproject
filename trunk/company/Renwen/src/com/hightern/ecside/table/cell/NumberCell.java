package com.hightern.ecside.table.cell;

import java.util.Locale;

import com.hightern.ecside.table.bean.Column;
import com.hightern.ecside.table.core.TableModel;
import com.hightern.ecside.util.ExtremeUtils;

public class NumberCell extends FormattedCell {

    protected String formatColumnValue(TableModel model, Column column) {
        Locale locale = model.getLocale();
        String value = column.getPropertyValueAsString();
        return ExtremeUtils.formatNumber(column.getFormat(), value, locale);
    }
}
