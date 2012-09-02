package com.hightern.ecside.table.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.hightern.ecside.table.bean.Row;
import com.hightern.ecside.table.core.TableModel;
import com.hightern.ecside.table.interceptor.RowInterceptor;
import com.hightern.ecside.util.ExceptionUtils;

public class RowTag extends TagSupport implements RowInterceptor {

    private static final long serialVersionUID = 1L;
    private String highlightClass;
    private String highlightRow;
    private String interceptor;
    private String onclick;
    private String onmouseout;
    private String onmouseover;
    private String style;
    private String styleClass;

    public void setHighlightClass(String highlightClass) {
        this.highlightClass = highlightClass;
    }

    public void setHighlightRow(String showHighlight) {
        this.highlightRow = showHighlight;
    }

    public void setInterceptor(String interceptor) {
        this.interceptor = interceptor;
    }

    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

    public void setOnmouseout(String onmouseout) {
        this.onmouseout = onmouseout;
    }

    public void setOnmouseover(String onmouseover) {
        this.onmouseover = onmouseover;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            TableModel model = TagUtils.getModel(this);

            if (TagUtils.isIteratingBody(this)) {
                Row row = model.getRowHandler().getRow();
                row.setOnclick(TagUtils.evaluateExpressionAsString("onclick", onclick, this, pageContext));
                row.setOnmouseout(TagUtils.evaluateExpressionAsString("onmouseout", onmouseout, this, pageContext));
                row.setOnmouseover(TagUtils.evaluateExpressionAsString("onmouseover", onmouseover, this, pageContext));

                modifyRowAttributes(model, row);
                model.getRowHandler().modifyRowAttributes();
            } else {
                Row row = new Row(model);
                row.setHighlightClass(TagUtils.evaluateExpressionAsString("highlightClass", this.highlightClass, this, pageContext));
                row.setHighlightRow(TagUtils.evaluateExpressionAsBoolean("highlightRow", this.highlightRow, this, pageContext));
                row.setInterceptor(TagUtils.evaluateExpressionAsString("interceptor", this.interceptor, this, pageContext));
                row.setOnclick(TagUtils.evaluateExpressionAsString("onclick", onclick, this, pageContext));
                row.setOnmouseout(TagUtils.evaluateExpressionAsString("onmouseout", onmouseout, this, pageContext));
                row.setOnmouseover(TagUtils.evaluateExpressionAsString("onmouseover", onmouseover, this, pageContext));
                row.setStyle(TagUtils.evaluateExpressionAsString("style", style, this, pageContext));
                row.setStyleClass(TagUtils.evaluateExpressionAsString("styleClass", styleClass, this, pageContext));

                addRowAttributes(model, row);
                model.addRow(row);
            }
        } catch (Exception e) {
            throw new JspException("RowTag.doStartTag() Problem: " + ExceptionUtils.formatStackTrace(e));
        }

        return EVAL_BODY_INCLUDE;
    }

    public void addRowAttributes(TableModel model, Row row) {
    }

    public void modifyRowAttributes(TableModel model, Row row) {
    }

    @Override
    public void release() {
        highlightClass = null;
        highlightRow = null;
        interceptor = null;
        onclick = null;
        onmouseout = null;
        onmouseover = null;
        style = null;
        styleClass = null;
        super.release();
    }
}
