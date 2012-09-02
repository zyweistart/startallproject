package com.hightern.ecside.table.calc;

import java.math.BigDecimal;
import java.util.Collection;

import com.hightern.ecside.table.bean.Column;
import com.hightern.ecside.table.core.TableModel;

public class TotalCalc implements Calc {

    @SuppressWarnings("unchecked")
    public Number getCalcResult(TableModel model, Column column) {
        Collection rows = model.getCollectionOfFilteredBeans();
        String property = column.getProperty();
        TotalValue totalValue = new TotalValue();
        CalcUtils.eachRowCalcValue(totalValue, rows, property);

        return totalValue.getTotalValue();
    }

    private static class TotalValue implements CalcHandler {

        double total = 0.00;

        public void processCalcValue(Number calcValue) {
            total += calcValue.doubleValue();
        }

        public Number getTotalValue() {
            return new BigDecimal(total);
        }
    }
}
