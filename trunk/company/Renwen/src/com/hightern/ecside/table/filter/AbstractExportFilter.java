package com.hightern.ecside.table.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import com.hightern.ecside.table.context.Context;
import com.hightern.ecside.table.context.HttpServletRequestContext;
import com.hightern.ecside.table.core.Preferences;
import com.hightern.ecside.table.core.TableConstants;
import com.hightern.ecside.table.core.TableModelUtils;
import com.hightern.ecside.table.core.TableProperties;
import com.hightern.ecside.util.MimeUtils;

public abstract class AbstractExportFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        Context context = new HttpServletRequestContext((HttpServletRequest) request);
        boolean isExported = ExportFilterUtils.isExported(context);
        if (isExported) {
            String exportFileName = ExportFilterUtils.getExportFileName(context);
            doFilterInternal(request, response, chain, exportFileName);
            handleExport((HttpServletRequest) request, (HttpServletResponse) response, context);
        } else {
            chain.doFilter(request, response);
        }
    }

    @SuppressWarnings("unchecked")
    protected void handleExport(HttpServletRequest request, HttpServletResponse response, Context context) {
        try {
            Object viewData = request.getAttribute(TableConstants.VIEW_DATA);
            if (viewData != null) {
                Preferences preferences = new TableProperties();
                preferences.init(null, TableModelUtils.getPreferencesLocation(context));
                String viewResolver = (String) request.getAttribute(TableConstants.VIEW_RESOLVER);
                Class classDefinition = Class.forName(viewResolver);
                ViewResolver handleExportViewResolver = (ViewResolver) classDefinition.newInstance();
                handleExportViewResolver.resolveView(request, response, preferences, viewData);
                response.getOutputStream().flush();
                response.getOutputStream().close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void setResponseHeaders(HttpServletResponse response, String exportFileName) {
        String mimeType = MimeUtils.getFileMimeType(exportFileName);
        if (StringUtils.isNotBlank(mimeType)) {
            response.setContentType(mimeType);
        }

        response.setHeader("Content-Disposition", "attachment;filename=\"" + exportFileName + "\"");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        response.setDateHeader("Expires", (System.currentTimeMillis() + 1000));
    }

    protected abstract void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain, String exportFileName)
            throws IOException, ServletException;
}
