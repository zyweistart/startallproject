package com.hightern.ecside.table.view;

import com.hightern.ecside.table.core.TableModel;
import com.hightern.ecside.util.HtmlBuilder;

public class LimitView extends HtmlView {

    @Override
    protected void toolbar(HtmlBuilder html, TableModel model) {
        new LimitToolbar(html, model).layout();
    }
}
