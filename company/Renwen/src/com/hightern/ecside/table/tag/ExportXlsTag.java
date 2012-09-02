package com.hightern.ecside.table.tag;

import org.apache.commons.lang.StringUtils;
import com.hightern.ecside.table.bean.Export;
import com.hightern.ecside.table.core.TableConstants;
import com.hightern.ecside.table.core.TableModel;
import com.hightern.ecside.table.view.html.BuilderConstants;

public class ExportXlsTag extends ExportTag {

    private static final long serialVersionUID = 1L;

    @Override
    public void addExportAttributes(TableModel model, Export export) {
        if (StringUtils.isBlank(export.getView())) {
            export.setView(TableConstants.VIEW_XLS);
        }

        if (StringUtils.isBlank(export.getViewResolver())) {
            export.setViewResolver(TableConstants.VIEW_XLS);
        }

        if (StringUtils.isBlank(export.getImageName())) {
            export.setImageName(TableConstants.VIEW_XLS);
        }

        if (StringUtils.isBlank(export.getText())) {
            export.setText(BuilderConstants.TOOLBAR_XLS_TEXT);
        }
    }
}
