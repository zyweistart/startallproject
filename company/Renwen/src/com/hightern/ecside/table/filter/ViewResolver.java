package com.hightern.ecside.table.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.hightern.ecside.table.core.Preferences;
public interface ViewResolver {

    public void resolveView(ServletRequest request, ServletResponse response, Preferences preferences, Object viewData)
            throws Exception;
}
