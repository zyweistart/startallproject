package com.hightern.ecside.table.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.hightern.ecside.table.core.Preferences;

public class CsvViewResolver implements ViewResolver {

    public void resolveView(ServletRequest request, ServletResponse response, Preferences preferences, Object viewData)
            throws Exception {
        byte[] contents = ((String) viewData).getBytes();
        response.setContentLength(contents.length);
        response.getOutputStream().write(contents);
    }
}
