package com.hightern.ecside.table.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

public class ExportFilter extends AbstractExportFilter {

    private boolean responseHeadersSetBeforeDoFilter;

    public void init(FilterConfig filterConfig) throws ServletException {
        String responseHeadersSetBeforeDoFilters = filterConfig.getInitParameter("responseHeadersSetBeforeDoFilter");
        if (StringUtils.isNotBlank(responseHeadersSetBeforeDoFilters)) {
            this.responseHeadersSetBeforeDoFilter = new Boolean(responseHeadersSetBeforeDoFilters).booleanValue();
        }
    }

    public void destroy() {
    }

    protected void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain, String exportFileName)
            throws IOException, ServletException {
        if (responseHeadersSetBeforeDoFilter) {
            setResponseHeaders((HttpServletResponse) response, exportFileName);
        }
        chain.doFilter(request, new ExportResponseWrapper((HttpServletResponse) response));
        if (!responseHeadersSetBeforeDoFilter) {
            setResponseHeaders((HttpServletResponse) response, exportFileName);
        }
    }
}
