package com.hightern.ecside.table.view.html.toolbar;

import com.hightern.ecside.table.core.TableModel;
import com.hightern.ecside.util.HtmlBuilder;

public class TextItem extends AbstractItem implements ToolbarItem {

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void disabled(HtmlBuilder html) {
        html.span().close().append(getText()).spanEnd();
    }

    public void enabled(HtmlBuilder html, TableModel model) {
        html.a();
        html.quote();
        html.append(getAction());
        html.quote().close();

        boolean showTooltips = model.getTableHandler().getTable().isShowTooltips();
        if (showTooltips) {
            html.span().title(getTooltip());
            html.styleClass(getStyleClass()).style(getStyle());
            html.onmouseover(getOnmouseover()).onmouseout(getOnmouseout());
            html.close();
            html.append(getText());
            html.spanEnd();
        } else {
            html.span();
            html.styleClass(getStyleClass()).style(getStyle());
            html.onmouseover(getOnmouseover()).onmouseout(getOnmouseout());
            html.close();
            html.append(getText());
            html.spanEnd();
        }

        html.aEnd();
    }
}
