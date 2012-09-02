package com.hightern.ecside.table.bean;

import java.util.HashMap;
import java.util.Map;

public abstract class Attributes {

    @SuppressWarnings({"rawtypes" })
    private Map attr = new HashMap();

    public Object getAttribute(String key) {
        return attr.get(key);
    }

    public String getAttributeAsString(String key) {
        Object value = attr.get(key);
        if (value != null) {
            return String.valueOf(value);
        }

        return null;
    }

    public int getAttributeAsInt(String key) {
        Object value = attr.get(key);
        if (value != null) {
            return Integer.parseInt(String.valueOf(value));
        }

        return 0;
    }

    @SuppressWarnings("unchecked")
    public void addAttribute(String key, Object value) {
        attr.put(key, value);
    }
}
