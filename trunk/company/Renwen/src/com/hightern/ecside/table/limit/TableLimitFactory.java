package com.hightern.ecside.table.limit;

import org.apache.commons.lang.StringUtils;
import com.hightern.ecside.table.context.Context;
import com.hightern.ecside.table.core.Preferences;
import com.hightern.ecside.table.core.PreferencesConstants;
import com.hightern.ecside.table.core.TableConstants;
import com.hightern.ecside.table.core.TableModelUtils;
import com.hightern.ecside.table.core.TableProperties;

public final class TableLimitFactory extends AbstractLimitFactory {

    public TableLimitFactory(Context context) {
        this(context, TableConstants.EXTREME_COMPONENTS);
    }

    public TableLimitFactory(Context context, String tableId) {
        this(context, tableId, TableConstants.STATE_DEFAULT, null);
    }

    public TableLimitFactory(Context context, String tableId, String state, String stateAttr) {
        this.tableId = tableId;

        String prefixWithTableIds = tableId + "_";
        this.prefixWithTableId = prefixWithTableIds;

        Preferences preferences = new TableProperties();
        preferences.init(null, TableModelUtils.getPreferencesLocation(context));

        state = preferences.getPreference(PreferencesConstants.TABLE_STATE + state);

        if (StringUtils.isBlank(stateAttr)) {
            stateAttr = preferences.getPreference(PreferencesConstants.TABLE_STATE_ATTR);
        }

        this.context = context;

        this.registry = new LimitRegistry(context, tableId, prefixWithTableIds, state, stateAttr);

        this.isExported = getExported();
    }

    protected boolean showPagination() {
        return true;
    }
}
