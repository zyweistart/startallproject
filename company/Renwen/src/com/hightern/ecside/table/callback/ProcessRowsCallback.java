package com.hightern.ecside.table.callback;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.comparators.NullComparator;
import org.apache.commons.collections.comparators.ReverseComparator;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.hightern.ecside.table.bean.Table;
import com.hightern.ecside.table.core.RetrievalUtils;
import com.hightern.ecside.table.core.TableConstants;
import com.hightern.ecside.table.core.TableModel;
import com.hightern.ecside.table.limit.Sort;

public class ProcessRowsCallback implements RetrieveRowsCallback,
        FilterRowsCallback, SortRowsCallback {

    private static Log logger = LogFactory.getLog(ProcessRowsCallback.class);

    @SuppressWarnings("unchecked")
    public Collection retrieveRows(TableModel model) throws Exception {
        Table table = model.getTableHandler().getTable();
        return RetrievalUtils.retrieveCollection(model.getContext(), table.getItems(), table.getScope());
    }

    @SuppressWarnings("unchecked")
    public Collection filterRows(TableModel model, Collection rows) throws
            Exception {
        boolean filtered = model.getLimit().isFiltered();
        boolean cleared = model.getLimit().isCleared();

        if (!filtered || cleared) {
            return rows;
        }

        if (filtered) {
            Collection collection = new ArrayList();
            FilterPredicate filterPredicate = new FilterPredicate(model);
            CollectionUtils.select(rows, filterPredicate, collection);

            return collection;
        }

        return rows;
    }

    @SuppressWarnings("unchecked")
    public Collection sortRows(TableModel model, Collection rows) throws
            Exception {
        boolean sorted = model.getLimit().isSorted();

        if (!sorted) {
            return rows;
        }

        Sort sort = model.getLimit().getSort();
        String property = sort.getProperty();
        String sortOrder = sort.getSortOrder();

        if (StringUtils.contains(property, ".")) {
            try {
                if (sortOrder.equals(TableConstants.SORT_ASC)) {
                    Collections.sort((List) rows, new NullSafeBeanComparator(property, new NullComparator()));
                } else if (sortOrder.equals(TableConstants.SORT_DESC)) {
                    NullSafeBeanComparator reversedNaturalOrderBeanComparator = new NullSafeBeanComparator(property,
                            new ReverseComparator(new NullComparator()));
                    Collections.sort((List) rows, reversedNaturalOrderBeanComparator);
                }
            } catch (NoClassDefFoundError e) {
                String msg = "The column property [" + property + "] is nested and requires BeanUtils 1.7 or greater for proper sorting.";
                logger.error(msg);
                throw new NoClassDefFoundError(msg); //just rethrow so it is not hidden
            }
        } else {
            if (sortOrder.equals(TableConstants.SORT_ASC)) {
                BeanComparator comparator = new BeanComparator(property, new NullComparator());
                Collections.sort((List) rows, comparator);
            } else if (sortOrder.equals(TableConstants.SORT_DESC)) {
                BeanComparator reversedNaturalOrderBeanComparator = new BeanComparator(property, new ReverseComparator(new NullComparator()));
                Collections.sort((List) rows, reversedNaturalOrderBeanComparator);
            }
        }

        return rows;
    }
}
