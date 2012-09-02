package com.hightern.ecside.table.view.html.toolbar;

import com.hightern.ecside.table.core.TableModel;
import com.hightern.ecside.util.HtmlBuilder;

public class ImageItem extends AbstractItem implements ToolbarItem {

    String image;
    String disabledImage;
    String alt;

    public String getDisabledImage() {
        return disabledImage;
    }

    public void setDisabledImage(String disabledImage) {
        this.disabledImage = disabledImage;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setImage() {
    }

    public void disabled(HtmlBuilder html) {
        html.img().src(getDisabledImage()).style(getStyle()).alt(getAlt()).xclose();
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public void enabled(HtmlBuilder html, TableModel model) {
        html.a();
        html.quote();
        html.append(getAction());
        html.quote().close();

        boolean showTooltips = model.getTableHandler().getTable().isShowTooltips();
        if (showTooltips) {
            html.img().src(getImage()).style(getStyle()).title(getTooltip()).onmouseover(getOnmouseover()).onmouseout(getOnmouseout()).alt(getAlt()).xclose();
        } else {
            html.img().src(getImage()).style(getStyle()).onmouseover(getOnmouseover()).onmouseout(getOnmouseout()).alt(getAlt()).xclose();
        }

        html.aEnd();
    }
}
