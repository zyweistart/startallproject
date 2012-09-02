package com.hightern.ecside.table.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.hightern.ecside.table.context.Context;
import com.hightern.ecside.table.context.ServletRequestContext;
import com.opensymphony.module.sitemesh.filter.PageFilter;
@SuppressWarnings("deprecation")
public class SitemeshPageFilter extends PageFilter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        Context context = new ServletRequestContext(request);
        if (ExportFilterUtils.isExported(context)) {
            chain.doFilter(request, response); // Don't Decorate
        } else {
            super.doFilter(request, response, chain); // Decorate
        }
    }
}
