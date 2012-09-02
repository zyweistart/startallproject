package com.hightern.ecside.table.resource;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.hightern.ecside.table.context.Context;
import com.hightern.ecside.table.core.Messages;
import com.hightern.ecside.table.core.TableModelUtils;

public class TableResourceBundle implements Messages {

    private static Log logger = LogFactory.getLog(TableResourceBundle.class);
    public final static String EXTREMETABLE_RESOURCE_BUNDLE = "com.hightern.ecside.table.resource.extremetableResourceBundle";
    private ResourceBundle customResourceBundle;
    private ResourceBundle defaultResourceBundle;
    private Locale locale;

    public void init(Context context, Locale locale) {
        this.locale = locale;
        defaultResourceBundle = findResourceBundle(EXTREMETABLE_RESOURCE_BUNDLE, locale);
        String messagesLocation = TableModelUtils.getMessagesLocation(context);
        if (StringUtils.isNotBlank(messagesLocation)) {
            customResourceBundle = findResourceBundle(messagesLocation, locale);
        }
    }

    private ResourceBundle findResourceBundle(String resourceBundleLocation, Locale locale) {
        try {
            return ResourceBundle.getBundle(resourceBundleLocation, locale, getClass().getClassLoader());
        } catch (MissingResourceException e) {
            if (logger.isErrorEnabled()) {
                logger.error("The resource bundle [ " + resourceBundleLocation + "] was not found. Make sure the path and resource name is correct.", e);
            }
        }

        return null;
    }

    /**
     * Get the resource property.
     */
    public String getMessage(String code) {
        return getMessage(code, null);
    }

    /**
     * Get the resource property.
     */
    public String getMessage(String code, Object[] args) {
        String result = findResource(customResourceBundle, code);

        if (result == null) {
            result = findResource(defaultResourceBundle, code);
        }

        if (result != null && args != null) {
            MessageFormat formatter = new MessageFormat("");
            formatter.setLocale(locale);
            formatter.applyPattern(result);
            result = formatter.format(args);
        }

        return result;
    }

    private String findResource(ResourceBundle resourceBundle, String code) {
        String result = null;

        if (resourceBundle == null) {
            return result;
        }

        try {
            result = resourceBundle.getString(code);
        } catch (MissingResourceException e) {
            // nothing we can do so eat the message
        }

        return result;
    }
}
