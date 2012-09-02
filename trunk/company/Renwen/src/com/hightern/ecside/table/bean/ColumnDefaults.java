package com.hightern.ecside.table.bean;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import com.hightern.ecside.table.core.RetrievalUtils;
import com.hightern.ecside.table.core.MessagesConstants;
import com.hightern.ecside.table.core.PreferencesConstants;
import com.hightern.ecside.table.core.TableConstants;
import com.hightern.ecside.table.core.TableModel;
import com.hightern.ecside.table.core.TableModelUtils;
import com.hightern.ecside.util.ExtremeUtils;

final class ColumnDefaults {

    static String getCell(TableModel model, String cell) {
        String result;

        if (StringUtils.isNotBlank(cell)) {
            result = model.getPreferences().getPreference(PreferencesConstants.COLUMN_CELL + cell);
            if (StringUtils.isBlank(result)) {
                result = cell;
            }
        } else {
            result = model.getPreferences().getPreference(PreferencesConstants.COLUMN_CELL + TableConstants.CELL_DISPLAY);
        }

        return result;
    }

    static String getFilterCell(TableModel model, String filterCell) {
        String result;

        if (StringUtils.isNotBlank(filterCell)) {
            result = model.getPreferences().getPreference(PreferencesConstants.COLUMN_FILTER_CELL + filterCell);
            if (StringUtils.isBlank(result)) {
                result = filterCell;
            }
        } else {
            result = model.getPreferences().getPreference(PreferencesConstants.COLUMN_FILTER_CELL + TableConstants.CELL_FILTER);
        }

        return result;
    }

    static String getHeaderCell(TableModel model, String headerCell) {
        String result;

        if (StringUtils.isNotBlank(headerCell)) {
            result = model.getPreferences().getPreference(PreferencesConstants.COLUMN_HEADER_CELL + headerCell);
            if (StringUtils.isBlank(result)) {
                result = headerCell;
            }
        } else {
            result = model.getPreferences().getPreference(PreferencesConstants.COLUMN_HEADER_CELL + TableConstants.CELL_HEADER);
        }

        return result;
    }

    static String getParse(TableModel model, Column column, String parse) {
        if (StringUtils.isNotBlank(parse)) {
            return parse;
        }

        if (column.isDate()) {
            return model.getPreferences().getPreference(PreferencesConstants.COLUMN_PARSE + TableConstants.DATE);
        }

        return parse;
    }

    /**
     * If this is a named format then it should be in the resource bundle. For
     * backwards compatibility check the properties file also.
     */
    static String getFormat(TableModel model, Column column, String format) {
        String result = getFormatInResourceBundle(model, column, format);
        if (StringUtils.isBlank(result)) {
            result = getFormatInProperties(model, column, format);
        }

        if (StringUtils.isNotBlank(result)) {
            return result;
        }

        return format;
    }

    static String getFormatInResourceBundle(TableModel model, Column column, String format) {
        if (StringUtils.isNotBlank(format) && isNamedFormat(format)) {
            return model.getMessages().getMessage(MessagesConstants.COLUMN_FORMAT + format);
        }

        if (StringUtils.isBlank(format)) {
            if (column.isCurrency()) {
                return model.getMessages().getMessage(MessagesConstants.COLUMN_FORMAT + TableConstants.CURRENCY);
            } else if (column.isDate()) {
                return model.getMessages().getMessage(MessagesConstants.COLUMN_FORMAT + TableConstants.DATE);
            }
        }

        return null;
    }

    static String getFormatInProperties(TableModel model, Column column, String format) {
        if (StringUtils.isNotBlank(format) && isNamedFormat(format)) {
            return model.getPreferences().getPreference(PreferencesConstants.COLUMN_FORMAT + format);
        }

        if (StringUtils.isBlank(format)) {
            if (column.isCurrency()) {
                return model.getPreferences().getPreference(PreferencesConstants.COLUMN_FORMAT + TableConstants.CURRENCY);
            } else if (column.isDate()) {
                return model.getPreferences().getPreference(PreferencesConstants.COLUMN_FORMAT + TableConstants.DATE);
            }
        }

        return null;
    }

    /**
     * If the format contains any of these formats then it is custom format
     * doing inline.
     */
    static boolean isNamedFormat(String format) {
        char args[] = {'#', '/', '-'};
        if (StringUtils.containsNone(format, args)) {
            return true;
        }

        return false;
    }

    static Boolean isSortable(TableModel model, Boolean sortable) {
        if (sortable == null) {
            return new Boolean(model.getTableHandler().getTable().isSortable());
        }

        return sortable;
    }

    static Boolean isFilterable(TableModel model, Boolean filterable) {
        if (filterable == null) {
            return new Boolean(model.getTableHandler().getTable().isFilterable());
        }

        return filterable;
    }

    static String getTitle(TableModel model, String title, String property) {
        if (StringUtils.isEmpty(title)) {
            return ExtremeUtils.camelCaseToWord(property);
        }

        if (TableModelUtils.isResourceBundleProperty(title)) {
            String resourceValue = model.getMessages().getMessage(title);
            if (resourceValue != null) {
                return resourceValue;
            }
        }

        return title;
    }

    static String getHeaderClass(TableModel model, String headerClass) {
        if (StringUtils.isBlank(headerClass)) {
            return model.getPreferences().getPreference(PreferencesConstants.TABLE_HEADER_CLASS);
        }

        return headerClass;
    }

    static String getAlias(String alias, String property) {
        if (StringUtils.isBlank(alias) && StringUtils.isNotBlank(property)) {
            return property;
        }

        return alias;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static String[] getCalcTitle(TableModel model, String[] calcTitle) {
        List results = new ArrayList();

        if (calcTitle == null) {
            return new String[]{};
        }

        for (int i = 0; i < calcTitle.length; i++) {
            String title = calcTitle[i];

            if (TableModelUtils.isResourceBundleProperty(title)) {
                String resourceValue = model.getMessages().getMessage(title);
                if (resourceValue == null) {
                    resourceValue = title;
                }

                if (StringUtils.isNotBlank(resourceValue)) {
                    results.add(resourceValue);
                }
            } else {
                results.add(title);
            }
        }

        return (String[]) results.toArray(new String[results.size()]);
    }

    static Boolean isEscapeAutoFormat(TableModel model, Boolean escapeAutoFormat) {
        if (escapeAutoFormat == null) {
            return Boolean.valueOf(model.getPreferences().getPreference(PreferencesConstants.COLUMN_ESCAPE_AUTO_FORMAT));
        }

        return escapeAutoFormat;
    }

    static Object getFilterOptions(TableModel model, Object filterOptions) {
        try {
            if (filterOptions != null) {
                return RetrievalUtils.retrieveCollection(model.getContext(), filterOptions);
            }
        } catch (Exception e) {
        }

        return null;
    }
}
