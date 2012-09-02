package com.hightern.ecside.table.context;

import java.io.Writer;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

public final class JspPageContext implements Context {

    private PageContext pageContext;

    public JspPageContext(PageContext pageContext) {
        this.pageContext = pageContext;
    }

    public Object getApplicationInitParameter(String name) {
        return pageContext.getServletContext().getInitParameter(name);
    }

    public Object getApplicationAttribute(String name) {
        return pageContext.getServletContext().getAttribute(name);
    }

    public void setApplicationAttribute(String name, Object value) {
        pageContext.getServletContext().setAttribute(name, value);
    }

    public void removeApplicationAttribute(String name) {
        pageContext.getServletContext().removeAttribute(name);
    }

    public Object getPageAttribute(String name) {
        return pageContext.getAttribute(name);
    }

    public void setPageAttribute(String name, Object value) {
        pageContext.setAttribute(name, value);
    }

    public void removePageAttribute(String name) {
        pageContext.removeAttribute(name);
    }

    public String getParameter(String name) {
        return pageContext.getRequest().getParameter(name);
    }

    @SuppressWarnings("unchecked")
    public Map getParameterMap() {
        return pageContext.getRequest().getParameterMap();
    }

    public Object getRequestAttribute(String name) {
        return pageContext.getRequest().getAttribute(name);
    }

    public void setRequestAttribute(String name, Object value) {
        pageContext.getRequest().setAttribute(name, value);
    }

    public void removeRequestAttribute(String name) {
        pageContext.getRequest().removeAttribute(name);
    }

    public Object getSessionAttribute(String name) {
        return pageContext.getSession().getAttribute(name);
    }

    public void setSessionAttribute(String name, Object value) {
        pageContext.getSession().setAttribute(name, value);
    }

    public void removeSessionAttribute(String name) {
        pageContext.getSession().removeAttribute(name);
    }

    public Writer getWriter() {
        return pageContext.getOut();
    }

    public Locale getLocale() {
        return pageContext.getRequest().getLocale();
    }

    public String getContextPath() {
        ServletRequest request = pageContext.getRequest();
        if (request instanceof HttpServletRequest) {
            return ((HttpServletRequest) request).getContextPath();
        }

        throw new UnsupportedOperationException("There is no context path associated with the request.");
    }

    public Object getContextObject() {
        return pageContext;
    }
}
