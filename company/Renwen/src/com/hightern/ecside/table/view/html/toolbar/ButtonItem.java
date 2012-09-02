package com.hightern.ecside.table.view.html.toolbar;

import com.hightern.ecside.table.core.TableModel;
import com.hightern.ecside.util.HtmlBuilder;

public class ButtonItem extends AbstractItem implements ToolbarItem {

    private String contents;

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void disabled(HtmlBuilder html) {
        html.button().disabled().close().append(getContents()).buttonEnd();
    }

    public void enabled(HtmlBuilder html, TableModel model) {
        boolean showTooltips = model.getTableHandler().getTable().isShowTooltips();
        if (showTooltips) {
            html.button();
            html.title(getTooltip());
            html.onclick(getAction());
            html.styleClass(getStyleClass()).style(getStyle());
            html.onmouseover(getOnmouseover()).onmouseout(getOnmouseout());
            html.close();
            html.append(contents);
            html.buttonEnd();
        } else {
            html.button();
            html.onclick(getAction());
            html.styleClass(getStyleClass()).style(getStyle());
            html.onmouseover(getOnmouseover()).onmouseout(getOnmouseout());
            html.close();
            html.append(contents);
            html.buttonEnd();
        }
    }
}
