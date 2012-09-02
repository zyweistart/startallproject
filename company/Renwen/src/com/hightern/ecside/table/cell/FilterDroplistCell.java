package com.hightern.ecside.table.cell;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.comparators.NullComparator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.hightern.ecside.table.bean.Column;
import com.hightern.ecside.table.core.TableConstants;
import com.hightern.ecside.table.core.TableModel;
import com.hightern.ecside.table.view.html.TableActions;
import com.hightern.ecside.util.ExtremeUtils;
import com.hightern.ecside.util.HtmlBuilder;

public class FilterDroplistCell implements Cell {

    private static Log logger = LogFactory.getLog(FilterDroplistCell.class);

    public String getExportDisplay(TableModel model, Column column) {
        return null;
    }

    @SuppressWarnings("unchecked")
    public String getHtmlDisplay(TableModel model, Column column) {
        HtmlBuilder html = new HtmlBuilder();

        if (!column.isFilterable()) {
            html.append("");
        } else {
            Collection filterOptions = column.getFilterOptions();
            if (filterOptions == null || filterOptions.isEmpty()) {
                filterOptions = getFilterDropList(model, column);
            }
            html.append(dropListHtml(model, column, filterOptions));
        }

        return html.toString();
    }

    @SuppressWarnings("unchecked")
    protected Collection getFilterDropList(TableModel model, Column column) {
        List droplist = new ArrayList();

        Set options = new HashSet();

        Collection beans = model.getCollectionOfBeans();
        for (Iterator iter = beans.iterator(); iter.hasNext();) {
            Object bean = iter.next();
            try {
                Object obj = getFilterOption(column, bean);
                if ((obj != null) && !options.contains(obj)) {
                    droplist.add(new Option(obj));
                    options.add(obj);
                }
            } catch (Exception e) {
                logger.debug("Problems getting the droplist.", e);
            }
        }

        BeanComparator comparator = new BeanComparator("label", new NullComparator());
        Collections.sort(droplist, comparator);

        return droplist;
    }

    protected Object getFilterOption(Column column, Object bean)
            throws Exception {
        return PropertyUtils.getProperty(bean, column.getProperty());
    }

    @SuppressWarnings("unchecked")
    protected String dropListHtml(TableModel model, Column column, Collection droplist) {
        HtmlBuilder html = new HtmlBuilder();

        html.td(2).close();

        html.newline();
        html.tabs(2);
        html.select().name(model.getTableHandler().prefixWithTableId() + TableConstants.FILTER + column.getAlias());

        StringBuffer onkeypress = new StringBuffer();
        onkeypress.append(new TableActions(model).getFilterAction());
        html.onchange(onkeypress.toString());

        html.close();

        html.newline();
        html.tabs(2);
        html.option().value("").close();
        html.optionEnd();

        Locale locale = model.getLocale();

        for (Iterator iter = droplist.iterator(); iter.hasNext();) {
            FilterOption filterOption = (FilterOption) iter.next();
            String value = String.valueOf(filterOption.getValue());
            String label = String.valueOf(filterOption.getLabel());

            if (column.isDate()) {
                value = ExtremeUtils.formatDate(column.getParse(), column.getFormat(), filterOption.getValue(), locale);
            }

            html.newline();
            html.tabs(2);
            html.option().value(value);

            if (value.equals(column.getValueAsString())) {
                html.selected();
            }

            html.close();
            html.append(label);
            html.optionEnd();
        }

        html.newline();
        html.tabs(2);
        html.selectEnd();

        html.tdEnd();

        return html.toString();
    }

    protected static class Option implements FilterOption {

        private final Object label;
        private final Object value;

        public Option(Object obj) {
            this.label = obj;
            this.value = obj;
        }

        public Option(Object label, Object value) {
            this.label = label;
            this.value = value;
        }

        public Object getLabel() {
            return label;
        }

        public Object getValue() {
            return value;
        }
    }
}
