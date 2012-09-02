package com.hightern.ecside.table.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.hightern.ecside.table.bean.Export;
import com.hightern.ecside.table.core.TableModel;
import com.hightern.ecside.table.interceptor.ExportInterceptor;
import com.hightern.ecside.util.ExceptionUtils;

public class ExportTag extends TagSupport implements ExportInterceptor {

    private static final long serialVersionUID = 1L;
    private String encoding;
    private String fileName;
    private String imageName;
    private String interceptor;
    private String text;
    private String tooltip;
    private String view;
    private String viewResolver;

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setInterceptor(String interceptor) {
        this.interceptor = interceptor;
    }

    public void setView(String view) {
        this.view = view;
    }

    public void setViewResolver(String viewResolver) {
        this.viewResolver = viewResolver;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    @Override
    public int doEndTag() throws JspException {
        if (TagUtils.isIteratingBody(this)) {
            return EVAL_PAGE;
        }

        try {
            TableModel model = TagUtils.getModel(this);

            Export export = new Export(model);
            export.setEncoding(TagUtils.evaluateExpressionAsString("encoding", this.encoding, this, pageContext));
            export.setFileName(TagUtils.evaluateExpressionAsString("fileName", this.fileName, this, pageContext));
            export.setImageName(TagUtils.evaluateExpressionAsString("imageName", this.imageName, this, pageContext));
            export.setInterceptor(TagUtils.evaluateExpressionAsString("interceptor", this.interceptor, this, pageContext));
            export.setText(TagUtils.evaluateExpressionAsString("text", this.text, this, pageContext));
            export.setTooltip(TagUtils.evaluateExpressionAsString("tooltip", this.tooltip, this, pageContext));
            export.setView(TagUtils.evaluateExpressionAsString("view", view, this, pageContext));
            export.setViewResolver(TagUtils.evaluateExpressionAsString("viewResolver", this.viewResolver, this, pageContext));

            addExportAttributes(model, export);
            model.addExport(export);
        } catch (Exception e) {
            throw new JspException("ExportTag.doEndTag() Problem: " + ExceptionUtils.formatStackTrace(e));
        }

        return EVAL_PAGE;
    }

    public void addExportAttributes(TableModel model, Export export) {
    }

    @Override
    public void release() {
        encoding = null;
        fileName = null;
        imageName = null;
        interceptor = null;
        view = null;
        viewResolver = null;
        text = null;
        tooltip = null;
        super.release();
    }
}
