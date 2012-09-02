package com.hightern.ecside.table.calc;
public final class CalcResult {

    private final String name;
    private final Number value;

    public CalcResult(String name, Number value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Number getValue() {
        return value;
    }
}
