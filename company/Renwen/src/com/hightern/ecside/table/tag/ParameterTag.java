package com.hightern.ecside.table.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.hightern.ecside.util.ExceptionUtils;

public class ParameterTag extends TagSupport {

    private static final long serialVersionUID = 1L;
    private String name;
    private Object value;

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public int doEndTag() throws JspException {
        try {
            if (TagUtils.isIteratingBody(this)) {
                return EVAL_PAGE;
            }

            String names = TagUtils.evaluateExpressionAsString("name", this.name, this, pageContext);
            Object values = TagUtils.evaluateExpressionAsObject("value", this.value, this, pageContext);

            if (values == null) {
                values = pageContext.getRequest().getParameterValues(names);
            }

            TagUtils.getModel(this).addParameter(names, values);

        } catch (Exception e) {
            throw new JspException("ParameterTag.doEndTag() Problem: " + ExceptionUtils.formatStackTrace(e));
        }

        return EVAL_PAGE;
    }

    @Override
    public void release() {
        name = null;
        value = null;
        super.release();
    }
}
