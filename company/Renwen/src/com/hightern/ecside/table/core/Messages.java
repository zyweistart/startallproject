package com.hightern.ecside.table.core;

import java.util.Locale;

import com.hightern.ecside.table.context.Context;

public interface Messages {

    public void init(Context context, Locale locale);

    public String getMessage(String code);

    public String getMessage(String code, Object[] args);
}
