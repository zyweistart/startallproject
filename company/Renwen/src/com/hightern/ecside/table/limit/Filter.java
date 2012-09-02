package com.hightern.ecside.table.limit;

import org.apache.commons.lang.builder.ToStringBuilder;

public final class Filter {

    private final String alias;
    private final String property;
    private final String value;

    public Filter(String alias, String property, String value) {
        this.alias = alias;
        this.property = property;
        this.value = value;
    }

    public String getAlias() {
        return alias;
    }

    public String getProperty() {
        return property;
    }

    public String getValue() {
        return value;
    }

    public boolean isAliased() {
        return !alias.equals(property);
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("alias", alias);
        builder.append("property", property);
        builder.append("value", value);
        return builder.toString();
    }
}
