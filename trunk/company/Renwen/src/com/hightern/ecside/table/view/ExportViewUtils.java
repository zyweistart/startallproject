package com.hightern.ecside.table.view;

import org.apache.commons.lang.StringUtils;

public class ExportViewUtils {

    public static String parseXLS(String value) {
        if (StringUtils.isBlank(value)) {
            return "";
        }
        value = replaceNonBreakingSpaces(value);
        return value;
    }

    public static String parsePDF(String value) {
        if (StringUtils.isBlank(value)) {
            return "";
        }
        value = replaceNonBreakingSpaces(value);
        value = escapeChars(value);
        return value;
    }

    public static String parseCSV(String value) {
        if (StringUtils.isBlank(value)) {
            return "";
        }
        value = replaceNonBreakingSpaces(value);
        return value;
    }

    public static String replaceNonBreakingSpaces(String value) {
        if (StringUtils.isBlank(value)) {
            return "";
        }
        if (StringUtils.contains(value, "&nbsp;")) {
            value = StringUtils.replace(value, "&nbsp;", "");
        }
        return value;
    }

    public static String escapeChars(String value) {
        if (StringUtils.isBlank(value)) {
            return "";
        }
        if (StringUtils.contains(value, "&")) {
            value = StringUtils.replace(value, "&", "&#38;");
        }
        if (StringUtils.contains(value, ">")) {
            value = StringUtils.replace(value, ">", "&gt;");
        }
        if (StringUtils.contains(value, "<")) {
            value = StringUtils.replace(value, "<", "&lt;");
        }
        return value;
    }
}
