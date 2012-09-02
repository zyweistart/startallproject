package com.hightern.ecside.table.tag;

import org.apache.commons.lang.StringUtils;
import com.hightern.ecside.table.bean.Export;
import com.hightern.ecside.table.core.TableConstants;
import com.hightern.ecside.table.core.TableModel;
import com.hightern.ecside.table.view.PdfView;
import com.hightern.ecside.table.view.html.BuilderConstants;

public class ExportPdfTag extends ExportTag {

    private static final long serialVersionUID = 1L;
    private String headerBackgroundColor;
    private String headerTitle;
    private String headerColor;

    public void setHeaderBackgroundColor(String headerBackgroundColor) {
        this.headerBackgroundColor = headerBackgroundColor;
    }

    public void setHeaderColor(String headerColor) {
        this.headerColor = headerColor;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    @Override
    public void addExportAttributes(TableModel model, Export export) {
        if (StringUtils.isBlank(export.getView())) {
            export.setView(TableConstants.VIEW_PDF);
        }

        if (StringUtils.isBlank(export.getViewResolver())) {
            export.setViewResolver(TableConstants.VIEW_PDF);
        }

        if (StringUtils.isBlank(export.getImageName())) {
            export.setImageName(TableConstants.VIEW_PDF);
        }

        if (StringUtils.isBlank(export.getText())) {
            export.setText(BuilderConstants.TOOLBAR_PDF_TEXT);
        }

        export.addAttribute(PdfView.HEADER_BACKGROUND_COLOR, TagUtils.evaluateExpressionAsString("headerBackgroundColor", headerBackgroundColor, this, pageContext));
        export.addAttribute(PdfView.HEADER_COLOR, TagUtils.evaluateExpressionAsString("headerColor", headerColor, this, pageContext));
        export.addAttribute(PdfView.HEADER_TITLE, TagUtils.evaluateExpressionAsString("headerTitle", headerTitle, this, pageContext));
    }

    @Override
    public void release() {
        headerBackgroundColor = null;
        headerTitle = null;
        headerColor = null;
        super.release();
    }
}
