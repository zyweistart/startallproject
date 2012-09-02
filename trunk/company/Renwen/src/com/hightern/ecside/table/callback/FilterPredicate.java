package com.hightern.ecside.table.callback;

import java.util.Iterator;
import java.util.Locale;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.hightern.ecside.table.bean.Column;
import com.hightern.ecside.table.core.TableModel;
import com.hightern.ecside.util.ExtremeUtils;

public final class FilterPredicate implements Predicate {

    private static Log logger = LogFactory.getLog(FilterPredicate.class);
    private TableModel model;

    public FilterPredicate(TableModel model) {
        this.model = model;
    }

    @SuppressWarnings("unchecked")
    public boolean evaluate(Object bean) {
        boolean match = false;

        try {
            for (Iterator iter = model.getColumnHandler().getColumns().iterator(); iter.hasNext();) {
                Column column = (Column) iter.next();
                String alias = column.getAlias();
                String filterValue = model.getLimit().getFilterSet().getFilterValue(alias);

                if (StringUtils.isEmpty(filterValue)) {
                    continue;
                }

                String property = column.getProperty();
                Object value = PropertyUtils.getProperty(bean, property);

                if (value == null) {
                    continue;
                }

                if (column.isDate()) {
                    Locale locale = model.getLocale();
                    value = ExtremeUtils.formatDate(column.getParse(), column.getFormat(), value, locale);
                } else if (column.isCurrency()) {
                    Locale locale = model.getLocale();
                    value = ExtremeUtils.formatNumber(column.getFormat(), value, locale);
                }

                if (!isSearchMatch(value.toString(), filterValue)) {
                    match = false; // as soon as fail just short circuit

                    break;
                }

                match = true;
            }
        } catch (Exception e) {
            logger.error("FilterPredicate.evaluate() had problems", e);
        }

        return match;
    }

    private boolean isSearchMatch(String value, String search) {
        value = value.toLowerCase().trim();
        search = search.toLowerCase().trim();

        if (search.startsWith("*") && value.endsWith(StringUtils.replace(search, "*", ""))) {
            return true;
        } else if (search.endsWith("*") && value.startsWith(StringUtils.replace(search, "*", ""))) {
            return true;
        } else if (StringUtils.contains(value, search)) {
            return true;
        }

        return false;
    }
}
