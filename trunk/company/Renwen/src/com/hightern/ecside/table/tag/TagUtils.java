package com.hightern.ecside.table.tag;

import java.util.Collection;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import com.hightern.ecside.table.core.TableModel;
import com.hightern.ecside.util.ExceptionUtils;

public final class TagUtils {

    private static Log logger = LogFactory.getLog(ColumnTag.class);

    private TagUtils() {
    }

    public final static String evaluateExpressionAsString(String attributeName,
            String attribute, Tag tag, PageContext pageContext) {
        try {
            if (attribute != null) {
                attribute = (String) ExpressionEvaluatorManager.evaluate(
                        attributeName, attribute, String.class, tag,
                        pageContext);
            }
        } catch (JspException e) {
            logger.error("Could not resolve EL for [" + attributeName + "] - "
                    + ExceptionUtils.formatStackTrace(e));
        }

        return attribute;
    }

    public final static Object evaluateExpressionAsObject(String attributeName,
            Object attribute, Tag tag, PageContext pageContext) {
        try {
            if (attribute != null) {
                attribute = ExpressionEvaluatorManager.evaluate(attributeName,
                        attribute.toString(), Object.class, tag, pageContext);
            }
        } catch (JspException e) {
            logger.error("Could not resolve EL for [" + attributeName + "] - "
                    + ExceptionUtils.formatStackTrace(e));
        }

        return attribute;
    }

    @SuppressWarnings("unchecked")
    public final static Collection evaluateExpressionAsCollection(
            String attributeName, Object attribute, Tag tag,
            PageContext pageContext) {
        attribute = evaluateExpressionAsObject(attributeName, attribute, tag,
                pageContext);

        if (attribute == null || !(attribute instanceof Collection)) {
            if (logger.isDebugEnabled()) {
                logger.debug("The attribute [" + attributeName
                        + "] is null or not a Collection.");
            }
            return null;
        }

        return (Collection) attribute;
    }

    public final static Boolean evaluateExpressionAsBoolean(
            String attributeName, String attribute, Tag tag,
            PageContext pageContext) {
        attribute = evaluateExpressionAsString(attributeName, attribute, tag,
                pageContext);

        if (attribute == null) {
            return null;
        }

        return Boolean.valueOf(attribute);
    }

    public final static int evaluateExpressionAsInt(String attributeName,
            String attribute, Tag tag, PageContext pageContext) {
        attribute = evaluateExpressionAsString(attributeName, attribute, tag,
                pageContext);

        if (attribute == null) {
            return 0;
        }

        return new Integer(attribute).intValue();
    }

    public static TableModel getModel(Tag tag) {
        TableTag tableTag = (TableTag) TagSupport.findAncestorWithClass(tag,
                TableTag.class);
        return tableTag.getModel();
    }

    public static boolean isIteratingBody(Tag tag) {
        return getModel(tag).getCurrentRowBean() != null;
    }
}
