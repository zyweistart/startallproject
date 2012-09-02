package com.hightern.ecside.table.view;

import com.hightern.ecside.table.core.TableModel;
import com.hightern.ecside.table.view.html.BuilderUtils;
import com.hightern.ecside.table.view.html.TableActions;

public class DmyTableActions extends TableActions {

    public DmyTableActions(TableModel model) {
        super(model);
        model2 = model;
    }

    public String getPageSelectItemAction() {
        StringBuffer action = new StringBuffer("javascript:");
        action.append(getClearedExportTableIdParameters());
        action.append(getSelectItemFormParameter("p"));
        action.append(getOnInvokeAction());
        return action.toString();
    }

    protected String getSelectItemFormParameter(String name) {
        StringBuffer result = new StringBuffer();
        String form = BuilderUtils.getForm(model2);
        String selectedOption = "this.options[this.selectedIndex].value";
        result.append("document.forms.").append(form).append(".");
        result.append(model2.getTableHandler().prefixWithTableId()).append(name);
        result.append(".value=").append(selectedOption).append(";");
        return result.toString();
    }
    private TableModel model2;
}
