package com.hightern.ecside.table.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.hightern.ecside.table.bean.Column;
import com.hightern.ecside.table.callback.FilterRowsCallback;
import com.hightern.ecside.table.callback.RetrieveRowsCallback;
import com.hightern.ecside.table.callback.SortRowsCallback;
import com.hightern.ecside.table.cell.Cell;
import com.hightern.ecside.table.context.Context;
import com.hightern.ecside.table.limit.Limit;
import com.hightern.ecside.table.limit.Sort;
import com.hightern.ecside.util.ExtremeUtils;

public final class TableModelUtils {

    private static Log logger = LogFactory.getLog(TableModelUtils.class);

    private TableModelUtils() {
    }

    @SuppressWarnings("unchecked")
    static Collection retrieveRows(TableModel model) throws Exception {
        RetrieveRowsCallback retrieveRowsCallback = TableCache.getInstance().getRetrieveRowsCallback(model);

        return retrieveRowsCallback.retrieveRows(model);
    }

    @SuppressWarnings("unchecked")
    static Collection filterRows(TableModel model, Collection rows)
            throws Exception {
        FilterRowsCallback filterRowsCallback = TableCache.getInstance().getFilterRowsCallback(model);

        return filterRowsCallback.filterRows(model, rows);
    }

    @SuppressWarnings("unchecked")
    static Collection sortRows(TableModel model, Collection rows)
            throws Exception {
        SortRowsCallback sortRowsCallback = TableCache.getInstance().getSortRowsCallback(model);

        return sortRowsCallback.sortRows(model, rows);
    }

    /**
     * Retrieve the current page of rows.
     *
     * @param rows
     *            The Collection of Beans after filtering and sorting.
     * @return The current page of rows.
     */
    @SuppressWarnings("unchecked")
    static Collection getCurrentRows(TableModel model, Collection rows) {
        Limit limit = model.getLimit();

        int rowStart = limit.getRowStart();
        int rowEnd = limit.getRowEnd();

        // Normal case. Using Limit and paginating for a specific set of rows.
        if (rowStart >= rows.size()) {
            if (logger.isDebugEnabled()) {
                logger.debug("The Limit row start is >= items.size(). Return the items available.");
            }

            return rows;
        }

        if (rowEnd > rows.size()) {
            if (logger.isWarnEnabled()) {
                logger.warn("The Limit row end is > items.size(). Return as many items as possible.");
            }

            rowEnd = rows.size();
        }

        Collection results = new ArrayList();
        for (int i = rowStart; i < rowEnd; i++) {
            Object bean = ((List) rows).get(i);
            results.add(bean);
        }

        return results;
    }

    public static boolean isSorted(TableModel model, String alias) {
        Sort sort = model.getLimit().getSort();

        if (sort.isSorted() && alias.equals(sort.getAlias())) {
            return true;
        }

        return false;
    }

    /**
     * Determine whether or not this is a resource bundle key. It is a resource
     * bundle key if the value has a '.' character in it.
     *
     * @param value
     *            The value that will be inspected to find out if resource key
     * @return True if this is a resource bundle key
     */
    public static boolean isResourceBundleProperty(String value) {
        if (StringUtils.contains(value, ".")) {
            return true;
        }

        return false;
    }

    public static Locale getLocale(Context context, Preferences preferences,
            String locale) {
        if (StringUtils.isEmpty(locale)) {
            locale = preferences.getPreference(PreferencesConstants.TABLE_LOCALE);
        }

        if (StringUtils.isBlank(locale)) {
            return context.getLocale();
        }

        Locale result = null;

        String parts[] = StringUtils.split(locale, "_");
        String language = parts[0];
        if (parts.length == 2) {
            String country = parts[1];
            result = new Locale(language, country);
        } else {
            result = new Locale(language, "");
        }

        return result;
    }

    public static String getPreferencesLocation(Context context) {
        String result = (String) context.getApplicationInitParameter(TableConstants.PREFERENCES_LOCATION);
        if (StringUtils.isNotBlank(result)) {
            return result;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("There are no custom preferences defined. You need to include the context-param "
                    + "extremecomponentsPreferencesLocation in the web.xml to include custom preferences.");
        }

        return null;
    }

    public static String getMessagesLocation(Context context) {
        String result = (String) context.getApplicationInitParameter(TableConstants.MESSAGES_LOCATION);
        if (StringUtils.isNotBlank(result)) {
            return result;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("There are no custom messages defined. You need to include the context-param "
                    + "extremecomponentsMessagesLocation in the web.xml to include custom messages.");
        }

        return null;
    }

    public static Cell getCell(Column column) {
        Cell cell = TableCache.getInstance().getCell(column.getCell());
        return cell;
    }

    public static Cell getFilterCell(Column column, Object value) {
        Cell cell = TableCache.getInstance().getCell(column.getFilterCell());
        column.setValue(value);
        return cell;
    }

    public static Cell getHeaderCell(Column column, Object value) {
        Cell cell = TableCache.getInstance().getCell(column.getHeaderCell());
        column.setValue(value);
        return cell;
    }

    public static String getInterceptColumnPreference(TableModel model,
            String intercept) {
        return getInterceptPreference(model, intercept,
                PreferencesConstants.COLUMN_INTERCEPTOR);
    }

    public static String getInterceptRowPreference(TableModel model,
            String intercept) {
        return getInterceptPreference(model, intercept,
                PreferencesConstants.ROW_INTERCEPTOR);
    }

    public static String getInterceptPreference(TableModel model,
            String intercept, String interceptPreference) {
        String result;

        if (StringUtils.isNotBlank(intercept)) {
            result = model.getPreferences().getPreference(
                    interceptPreference + intercept);
            if (StringUtils.isBlank(result)) {
                result = intercept;
            }
        } else {
            result = model.getPreferences().getPreference(
                    interceptPreference + TableConstants.DEFAULT_INTERCEPT);
        }

        return result;
    }

    public static String getAlias(String alias, String property) {
        if (StringUtils.isBlank(alias) && StringUtils.isNotBlank(property)) {
            return property;
        }

        return alias;
    }

    public static Object getColumnPropertyValue(Object bean, String property) {
        Object result = null;

        try {
            if (ExtremeUtils.isBeanPropertyReadable(bean, property)) {
                result = PropertyUtils.getProperty(bean, property);
            }
        } catch (Exception e) {
            if (logger.isDebugEnabled()) {
                logger.debug("Could not find the property [" + property
                        + "]. Either the bean or property is null");
            }
        }

        return result;
    }

    public static String getAutoGenerateColumnsPreference(TableModel model,
            String autoGenerateColumns) {
        String result = autoGenerateColumns;

        if (StringUtils.isNotBlank(autoGenerateColumns)) {
            result = model.getPreferences().getPreference(
                    PreferencesConstants.COLUMNS_AUTO_GENERATE_COLUMNS
                    + autoGenerateColumns);
            if (StringUtils.isBlank(result)) {
                result = autoGenerateColumns;
            }
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    public static Messages getMessages(TableModel model) {
        String messages = model.getPreferences().getPreference(
                PreferencesConstants.MESSAGES);
        try {
            Class classDefinition = Class.forName(messages);
            return (Messages) classDefinition.newInstance();

        } catch (Exception e) {
            String msg = "Could not create the messages [" + messages
                    + "]. The class was not found or does not exist.";
            logger.error(msg, e);
            throw new IllegalStateException(msg);
        }
    }

    /**
     * The value needs to be a String[]. A String, Null, or List will be
     * converted to a String[]. In addition it will attempt to do a String
     * conversion for other object types.
     *
     * @param value
     *            The value to convert to an String[]
     * @return A String[] value.
     */
    @SuppressWarnings("unchecked")
    public static String[] getValueAsArray(Object value) {
        if (value == null) {
            return new String[]{}; // put in a placeholder
        }

        if (value instanceof String[]) {
            return (String[]) value;
        } else if (value instanceof List) {
            List valueList = (List) value;
            return (String[]) valueList.toArray(new String[valueList.size()]);
        }

        return new String[]{value.toString()};
    }
}
