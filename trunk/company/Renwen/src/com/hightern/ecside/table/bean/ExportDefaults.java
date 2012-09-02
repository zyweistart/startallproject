package com.hightern.ecside.table.bean;

import org.apache.commons.lang.StringUtils;
import com.hightern.ecside.table.core.PreferencesConstants;
import com.hightern.ecside.table.core.TableModel;
import com.hightern.ecside.table.core.TableModelUtils;

final class ExportDefaults {

    static String getEncoding(TableModel model, String encoding) {
        if (StringUtils.isBlank(encoding)) {
            return model.getPreferences().getPreference(PreferencesConstants.EXPORT_ENCODING);
        }

        return encoding;
    }

    static String getText(TableModel model, String text) {
        if (TableModelUtils.isResourceBundleProperty(text)) {
            String resourceValue = model.getMessages().getMessage(text);
            if (resourceValue != null) {
                return resourceValue;
            }
        }

        return text;
    }

    static String getTooltip(TableModel model, String tooltip) {
        if (TableModelUtils.isResourceBundleProperty(tooltip)) {
            String resourceValue = model.getMessages().getMessage(tooltip);
            if (resourceValue != null) {
                return resourceValue;
            }
        }

        return tooltip;
    }

    static String getviewResolver(TableModel model, String viewResolver) {
        String result = null;

        if (StringUtils.isNotBlank(viewResolver)) {
            result = model.getPreferences().getPreference(PreferencesConstants.EXPORT_VIEW_RESOLVER + viewResolver);
            if (StringUtils.isBlank(result)) {
                result = viewResolver;
            }
        }

        return result;
    }
}
