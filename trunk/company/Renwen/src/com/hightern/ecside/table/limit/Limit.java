package com.hightern.ecside.table.limit;

public interface Limit {

    public FilterSet getFilterSet();

    public int getRowEnd();

    public int getRowStart();

    public Sort getSort();

    public int getPage();

    public int getCurrentRowsDisplayed();

    public int getTotalRows();

    public boolean isFiltered();

    public boolean isCleared();

    public boolean isSorted();

    public boolean isExported();

    public void setRowAttributes(int totalRows, int defaultRowsDisplayed);
}
