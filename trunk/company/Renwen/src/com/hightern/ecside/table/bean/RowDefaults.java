package com.hightern.ecside.table.bean;

import org.apache.commons.lang.StringUtils;
import com.hightern.ecside.table.core.TableModel;
import com.hightern.ecside.table.core.PreferencesConstants;

final class RowDefaults {

    private RowDefaults() {
    }

    static String getHighlightClass(TableModel model, String highlightClass) {
        if (StringUtils.isEmpty(highlightClass)) {
            return model.getPreferences().getPreference(PreferencesConstants.ROW_HIGHLIGHT_CLASS);
        }

        return highlightClass;
    }

    static Boolean isHighlightRow(TableModel model, Boolean highlightRow) {
        if (highlightRow == null) {
            return Boolean.valueOf(model.getPreferences().getPreference(PreferencesConstants.ROW_HIGHLIGHT_ROW));
        }

        return highlightRow;
    }
}
