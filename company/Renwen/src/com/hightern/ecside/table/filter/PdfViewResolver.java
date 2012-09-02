package com.hightern.ecside.table.filter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.avalon.framework.logger.ConsoleLogger;
import org.apache.avalon.framework.logger.Logger;
import org.apache.fop.apps.Driver;
import org.apache.fop.apps.Options;
import org.apache.fop.messaging.MessageHandler;
import com.hightern.ecside.table.core.Preferences;
import org.xml.sax.InputSource;

public class PdfViewResolver implements ViewResolver {

    private Logger log = null;
    private final static String USERCONFIG_LOCATION = "exportPdf.userconfigLocation";

    public void resolveView(ServletRequest request, ServletResponse response, Preferences preferences, Object viewData)
            throws Exception {
        InputStream is = new ByteArrayInputStream(((String) viewData).getBytes("UTF-8"));

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Driver driver = new Driver(new InputSource(is), out);

        if (log == null) {
            log = new ConsoleLogger(ConsoleLogger.LEVEL_WARN);
            MessageHandler.setScreenLogger(log);
        }

        String userconfigLocation = preferences.getPreference(USERCONFIG_LOCATION);
        if (userconfigLocation != null) {
            InputStream input = this.getClass().getResourceAsStream(userconfigLocation);
            if (input != null) {
                new Options(input);
            }
        }

        driver.setLogger(log);
        driver.setRenderer(Driver.RENDER_PDF);
        driver.run();

        byte[] contents = out.toByteArray();
        response.setContentLength(contents.length);
        response.getOutputStream().write(contents);
    }
}
