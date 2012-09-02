package com.hightern.ecside.table.core;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.hightern.ecside.table.context.Context;
import com.hightern.ecside.util.ExtremeUtils;

public class RetrievalUtils {

    private static Log logger = LogFactory.getLog(RetrievalUtils.class);

    public static Object retrieve(Context context, String name) {
        return retrieve(context, name, null);
    }

    public static Object retrieve(Context context, String name, String scope) {
        if (StringUtils.isNotBlank(scope)) {
            if (scope.equalsIgnoreCase(TableConstants.PAGE_SCOPE)) {
                return context.getPageAttribute(name);
            } else if (scope.equalsIgnoreCase(TableConstants.REQUEST_SCOPE)) {
                return context.getRequestAttribute(name);
            } else if (scope.equalsIgnoreCase(TableConstants.SESSION_SCOPE)) {
                return context.getSessionAttribute(name);
            } else if (scope.equalsIgnoreCase(TableConstants.APPLICATION_SCOPE)) {
                return context.getApplicationAttribute(name);
            }
        }

        Object value = context.getPageAttribute(name);
        if (value == null) {
            value = context.getRequestAttribute(name);
        }
        if (value == null) {
            value = context.getSessionAttribute(name);
        }
        if (value == null) {
            value = context.getApplicationAttribute(name);
        }

        return value;
    }

    @SuppressWarnings("unchecked")
    public static Collection retrieveCollection(Context context,
            Object collection) throws Exception {
        return retrieveCollection(context, collection, null);
    }

    @SuppressWarnings("unchecked")
    public static Collection retrieveCollection(Context context,
            Object collection, String scope) throws Exception {
        if (collection instanceof Collection) {
            return (Collection) collection;
        } else if (collection instanceof Map) {
            return ((Map) collection).values();
        } else if (collection instanceof String) {
            return retrieveCollectionFromScope(context, String.valueOf(collection), scope);
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("Could not find the Collection.");
            }
            return Collections.EMPTY_LIST;
        }
    }

    @SuppressWarnings("unchecked")
    static Collection retrieveCollectionFromScope(Context context,
            String collection, String scope) throws Exception {
        Collection results = null;

        if (StringUtils.isBlank(collection) || "null".equals(collection)) {
            if (logger.isDebugEnabled()) {
                logger.debug("The collection is not defined.");
            }

            return Collections.EMPTY_LIST;
        }

        if (StringUtils.contains(collection, ".")) {
            results = retrieveNestedCollection(context, collection, scope);
        } else {
            results = retrieveCollectionAsObject(context, collection, scope);
        }

        if (results == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("Could not find the Collection.");
            }

            return Collections.EMPTY_LIST;
        }

        return results;
    }

    @SuppressWarnings("unchecked")
    static Collection retrieveNestedCollection(Context context,
            String collection, String scope) throws Exception {
        String split[] = StringUtils.split(collection, ".");
        Object obj = RetrievalUtils.retrieve(context, split[0], scope);
        String collectionToFind = StringUtils.substringAfter(collection, ".");
        if (ExtremeUtils.isBeanPropertyReadable(obj, collectionToFind)) {
            obj = PropertyUtils.getProperty(obj, collectionToFind);
        }

        if (!(obj instanceof Collection)) {
            if (logger.isDebugEnabled()) {
                logger.debug("The object is not of type Collection.");
            }

            return Collections.EMPTY_LIST;
        }

        return (Collection) obj;
    }

    @SuppressWarnings("unchecked")
    static Collection retrieveCollectionAsObject(Context context,
            String collection, String scope) throws Exception {
        Object obj = RetrievalUtils.retrieve(context, collection, scope);
        if (obj instanceof Collection) {
            return (Collection) obj;
        } else if (obj instanceof Map) {
            return ((Map) obj).values();
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("The object is not of type Collection.");
            }

            return Collections.EMPTY_LIST;
        }
    }
}
