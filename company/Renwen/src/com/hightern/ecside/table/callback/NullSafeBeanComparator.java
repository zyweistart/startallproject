package com.hightern.ecside.table.callback;

import java.util.Comparator;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class NullSafeBeanComparator extends BeanComparator {

    private static final long serialVersionUID = 562262887637475072L;
    private Log log = LogFactory.getLog(NullSafeBeanComparator.class);
    protected boolean nullsAreHigh = true;
    protected String property;
    @SuppressWarnings("unchecked")
    protected Comparator comparator;

    @SuppressWarnings("unchecked")
    public Comparator getComparator() {
        return comparator;
    }

    @SuppressWarnings("unchecked")
	public void setComparator(Comparator comparator) {
        this.comparator = comparator;
    }

    public boolean isNullsAreHigh() {
        return nullsAreHigh;
    }

    public void setNullsAreHigh(boolean nullsAreHigh) {
        this.nullsAreHigh = nullsAreHigh;
    }

    @Override
    public String getProperty() {
        return property;
    }

    @Override
    public void setProperty(String property) {
        this.property = property;
    }

    @SuppressWarnings("unchecked")
    @Override
    public int compare(Object o1, Object o2) {

        if (property == null) {
            return this.comparator.compare(o1, o2);
        }

        Object val1 = null, val2 = null;

        try {
            try {
                val1 = PropertyUtils.getProperty(o1, property);
            } catch (NestedNullException ignored) {
            }

            try {
                val2 = PropertyUtils.getProperty(o2, property);
            } catch (NestedNullException ignored) {
            }

            if (val1 == val2 || (val1 == null && val2 == null)) {
                return -1;
            }

            if (val1 == null) {
                return this.nullsAreHigh ? 1 : -1;
            }

            if (val2 == null) {
                return this.nullsAreHigh ? -1 : 1;
            }

            return this.comparator.compare(val1, val2);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn(e);
            return 0;
        }
    }

    @SuppressWarnings("unchecked")
    public NullSafeBeanComparator(String property, Comparator c) {
        this.comparator = c;
        this.property = property;
    }

    @SuppressWarnings("unchecked")
    public NullSafeBeanComparator(String property, Comparator c,
            boolean nullAreHigh) {
        this.comparator = c;
        this.property = property;
        this.nullsAreHigh = nullAreHigh;

    }
}
