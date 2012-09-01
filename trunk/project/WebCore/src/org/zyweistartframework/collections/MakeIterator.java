package org.zyweistartframework.collections;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MakeIterator {

    public static boolean isIterable(Object object) {
        if (object == null) {
            return false;
        }
        if (object instanceof Map) {
            return true;
        } else if (object instanceof Iterable) {
            return true;
        } else if (object.getClass().isArray()) {
            return true;
        } else if (object instanceof Enumeration) {
            return true;
        } else if (object instanceof Iterator) {
            return true;
        } else {
            return false;
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static Iterator convert(Object value) {
        Iterator iterator;
        if (value instanceof Iterator) {
            return (Iterator) value;
        }
        if (value instanceof Map) {
            value = ((Map) value).entrySet();
        }
        if (value == null) {
            return null;
        }
        if (value instanceof Iterable) {
            iterator = ((Iterable) value).iterator();
        } else if (value.getClass().isArray()) {
        	ArrayList list = new ArrayList(Array.getLength(value));
            for (int j = 0; j < Array.getLength(value); j++) {
                list.add(Array.get(value, j));
            }
            iterator = list.iterator();
        } else if (value instanceof Enumeration) {
            Enumeration enumeration = (Enumeration) value;
            ArrayList list = new ArrayList();
            while (enumeration.hasMoreElements()) {
                list.add(enumeration.nextElement());
            }
            iterator = list.iterator();
        } else {
            List list = new ArrayList(1);
            list.add(value);
            iterator = list.iterator();
        }
        return iterator;
    }
    
}