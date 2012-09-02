package com.hightern.ecside.table.core;

import com.hightern.ecside.table.context.Context;

public interface Preferences {

    public void init(Context context, String preferencesLocation);

    public String getPreference(String code);
}
