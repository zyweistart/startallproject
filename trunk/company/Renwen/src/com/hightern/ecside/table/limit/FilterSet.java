package com.hightern.ecside.table.limit;

import org.apache.commons.lang.builder.ToStringBuilder;
import com.hightern.ecside.table.core.TableConstants;

public class FilterSet {

    private String action;
    private Filter[] filters;

    public FilterSet() {
    }

    public FilterSet(String action, Filter filters[]) {
        this.action = action;
        this.filters = filters;
    }

    public boolean isFiltered() {
        return (action != null && action.equals(TableConstants.FILTER_ACTION) && filters != null && filters.length > 0);
    }

    public boolean isCleared() {
        return action != null && action.equals(TableConstants.CLEAR_ACTION);
    }

    public String getAction() {
        return action;
    }

    public Filter[] getFilters() {
        return filters;
    }

    public String getFilterValue(String alias) {
        for (int i = 0; i < filters.length; i++) {
            Filter filter = filters[i];
            if (filter.getAlias().equals(alias)) {
                return filter.getValue();
            }
        }

        return "";
    }

    public Filter getFilter(String alias) {
        for (int i = 0; i < filters.length; i++) {
            Filter filter = filters[i];
            if (filter.getAlias().equals(alias)) {
                return filter;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("action", action);

        if (filters != null) {
            for (int i = 0; i < filters.length; i++) {
                Filter filter = filters[i];
                builder.append(filter.toString());
            }
        }

        return builder.toString();
    }
}
