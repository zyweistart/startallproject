package com.hightern.ecside.table.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import com.hightern.ecside.table.core.Preferences;

public class XlsViewResolver implements ViewResolver {

    public void resolveView(ServletRequest request, ServletResponse response, Preferences preferences, Object viewData)
            throws Exception {
        HSSFWorkbook wb = (HSSFWorkbook) viewData;
        wb.write(response.getOutputStream());
    }
}
