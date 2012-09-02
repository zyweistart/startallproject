package com.hightern.ecside.table.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.hightern.ecside.table.context.Context;

public class TableProperties implements Preferences {

    private static Log logger = LogFactory.getLog(TableProperties.class);
    public final static String EXTREMECOMPONENTS_PROPERTIES = "extremecomponents.properties";
    public final static String EXTREMETABLE_PROPERTIES = "extremetable.properties";
    private Properties properties = new Properties();

    public void init(Context context, String preferencesLocation) {
        try {
            properties.load(this.getClass().getResourceAsStream(EXTREMETABLE_PROPERTIES));
            if (StringUtils.isNotBlank(preferencesLocation)) {
                InputStream input = this.getClass().getResourceAsStream(preferencesLocation);
                if (input != null) {
                    properties.load(input);
                }
            }
        } catch (IOException e) {
            if (logger.isErrorEnabled()) {
                logger.error("Could not load the eXtremeTable preferences.", e);
            }
        }
    }

    /**
     * Get the default property.
     */
    public String getPreference(String name) {
        return (String) properties.get(name);
    }
}
