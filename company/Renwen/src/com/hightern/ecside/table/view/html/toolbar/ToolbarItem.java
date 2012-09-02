package com.hightern.ecside.table.view.html.toolbar;

import com.hightern.ecside.table.core.TableModel;
import com.hightern.ecside.util.HtmlBuilder;

public interface ToolbarItem {

    public String getAction();

    public void setAction(String action);

    public String getTooltip();

    public void setTooltip(String tooltip);

    public String getOnmouseout();

    public void setOnmouseout(String onmouseout);

    public String getOnmouseover();

    public void setOnmouseover(String onmouseover);

    public String getStyle();

    public void setStyle(String style);

    public String getStyleClass();

    public void setStyleClass(String styleClass);

    public void disabled(HtmlBuilder html);

    public void enabled(HtmlBuilder html, TableModel model);
}
