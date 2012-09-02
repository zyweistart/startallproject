package com.hightern.ecside.util;

import java.io.File;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

public class MimeUtils {

    public final static String MIMETYPES_PROPERTIES = "mimeTypes.properties";
    private static Properties properties;

    public static String getFileMimeType(File file) {
        if (file == null) {
            return null;
        }

        return getFileMimeType(file.getName());
    }

    public static String getFileMimeType(String fileName) {
        if (StringUtils.isBlank(fileName) || (fileName.indexOf(".") == -1)) {
            return null;
        }

        fileName = fileName.substring(fileName.lastIndexOf("."));

        return getExtensionMimeType(fileName);
    }

    public static String getExtensionMimeType(String extension) {
        String result = null;

        if (StringUtils.isBlank(extension)) {
            return result;
        }

        init();

        extension = extension.toLowerCase();

        if (!extension.startsWith(".")) {
            extension = "." + extension;
        }

        result = (String) properties.get(extension);

        return result;
    }

    private static void init() {
        if (properties != null) {
            return;
        }

        try {
            properties = new Properties();
            properties.load(new MimeUtils().getClass().getResourceAsStream(
                    MIMETYPES_PROPERTIES));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
