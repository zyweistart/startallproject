package com.hightern.ecside.table.handler;

import org.apache.commons.lang.StringUtils;
import com.hightern.ecside.table.bean.Column;
import com.hightern.ecside.table.cell.Cell;
import com.hightern.ecside.table.core.TableModel;
import com.hightern.ecside.table.core.TableModelUtils;
import com.hightern.ecside.table.core.PreferencesConstants;
import com.hightern.ecside.table.view.View;

public class ViewHandler {

    private TableModel model;
    private View view;

    public ViewHandler(TableModel model) {
        this.model = model;
    }

    public View getView() {
        return view;
    }

    public void setView() throws Exception {
        boolean isExported = model.getLimit().isExported();

        String currentView = null;
        if (isExported) {
            currentView = model.getExportHandler().getCurrentExport().getView();
            String preference = model.getPreferences().getPreference(
                    PreferencesConstants.EXPORT_VIEW + currentView);
            if (StringUtils.isNotBlank(preference)) {
                currentView = preference;
            }
        } else {
            currentView = model.getTableHandler().getTable().getView();
            String preference = model.getPreferences().getPreference(
                    PreferencesConstants.TABLE_VIEW + currentView);
            if (StringUtils.isNotBlank(preference)) {
                currentView = preference;
            }
        }
        @SuppressWarnings("unchecked")
        Class classDefinition = Class.forName(currentView);
        this.view = (View) classDefinition.newInstance();
        getView().beforeBody(model);
    }

    public void addColumnValueToView(Column column) {
        Cell cell = TableModelUtils.getCell(column);
        boolean isExported = model.getLimit().isExported();
        if (!isExported) {
            column.setCellDisplay(cell.getHtmlDisplay(model, column));
        } else {
            column.setCellDisplay(cell.getExportDisplay(model, column));
        }
        getView().body(model, column);
    }
}
