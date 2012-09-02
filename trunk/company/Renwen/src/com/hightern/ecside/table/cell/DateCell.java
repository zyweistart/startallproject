package com.hightern.ecside.table.cell;

import java.util.Locale;

import com.hightern.ecside.table.bean.Column;
import com.hightern.ecside.table.core.TableModel;
import com.hightern.ecside.util.ExtremeUtils;

public class DateCell extends FormattedCell {

    protected String formatColumnValue(TableModel model, Column column) {
        Locale locale = model.getLocale();
        return ExtremeUtils.formatDate(column.getParse(), column.getFormat(), column.getPropertyValue(), locale);
    }
}
