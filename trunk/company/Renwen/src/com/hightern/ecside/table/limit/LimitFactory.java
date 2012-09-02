package com.hightern.ecside.table.limit;

import java.util.Map;

public interface LimitFactory {

    public int getCurrentRowsDisplayed(int totalRows, int defaultRowsDisplayed);

    public int getPage();

    public Sort getSort();

    public boolean isExported();

    public FilterSet getFilterSet();

    @SuppressWarnings("unchecked")
    public Map getSortedOrFilteredParameters(String parameter);
}
