package com.hightern.ecside.table.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.hightern.ecside.table.core.TableModel;
import com.hightern.ecside.util.ExceptionUtils;

public class ColumnsTag extends TagSupport {

    private static Log logger = LogFactory.getLog(ColumnsTag.class);
    private static final long serialVersionUID = 1L;
    private String autoGenerateColumns;

    public void setAutoGenerateColumns(String autoGenerateColumns) {
        this.autoGenerateColumns = autoGenerateColumns;
    }

    @Override
    public int doEndTag() throws JspException {
        try {
            TableModel model = TagUtils.getModel(this);

            if (!TagUtils.isIteratingBody(this)) {
                String autoGenerateColumnss = TagUtils.evaluateExpressionAsString("autoGenerateColumns", this.autoGenerateColumns, this, pageContext);
                model.addColumns(autoGenerateColumnss);
            } else {
                model.setColumnValues();
            }

            return EVAL_PAGE;
        } catch (Exception e) {
            logger.error(ExceptionUtils.formatStackTrace(e));
            throw new JspException("ColumnsTag.doEndTag() Problem: " + ExceptionUtils.formatStackTrace(e));
        }
    }

    @Override
    public void release() {
        autoGenerateColumns = null;
        super.release();
    }
}
