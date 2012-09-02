package com.hightern.ecside.table.tag;

import org.apache.commons.lang.StringUtils;
import com.hightern.ecside.table.bean.Export;
import com.hightern.ecside.table.core.TableConstants;
import com.hightern.ecside.table.core.TableModel;
import com.hightern.ecside.table.view.CsvView;
import com.hightern.ecside.table.view.html.BuilderConstants;

public class ExportCsvTag extends ExportTag {

    private static final long serialVersionUID = 1L;
    private String delimiter;

    public String getDelimiter() {
        return TagUtils.evaluateExpressionAsString("delimiter", delimiter, this, pageContext);
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    public void addExportAttributes(TableModel model, Export export) {
        if (StringUtils.isBlank(export.getView())) {
            export.setView(TableConstants.VIEW_CSV);
        }

        if (StringUtils.isBlank(export.getViewResolver())) {
            export.setViewResolver(TableConstants.VIEW_CSV);
        }

        if (StringUtils.isBlank(export.getImageName())) {
            export.setImageName(TableConstants.VIEW_CSV);
        }

        if (StringUtils.isBlank(export.getText())) {
            export.setText(BuilderConstants.TOOLBAR_CSV_TEXT);
        }

        export.addAttribute(CsvView.DELIMITER, TagUtils.evaluateExpressionAsString("delimiter", delimiter, this, pageContext));
    }

    @Override
    public void release() {
        delimiter = null;
        super.release();
    }
}
