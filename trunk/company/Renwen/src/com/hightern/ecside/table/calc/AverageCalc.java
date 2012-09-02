package com.hightern.ecside.table.calc;

import java.math.BigDecimal;
import java.util.Collection;

import com.hightern.ecside.table.bean.Column;
import com.hightern.ecside.table.core.TableModel;

public class AverageCalc implements Calc {

    @SuppressWarnings("unchecked")
    public Number getCalcResult(TableModel model, Column column) {
        Collection rows = model.getCollectionOfFilteredBeans();
        String property = column.getProperty();
        AverageValue totalValue = new AverageValue(rows.size());
        CalcUtils.eachRowCalcValue(totalValue, rows, property);

        return totalValue.getAverageValue();
    }

    private static class AverageValue implements CalcHandler {

        private double total = 0.00;
        private double rowCount;

        public AverageValue(double rowCount) {
            this.rowCount = rowCount;
        }

        public void processCalcValue(Number calcValue) {
            total += calcValue.doubleValue();
        }

        public Number getAverageValue() {
            if (rowCount > 0) {
                return new BigDecimal(total / rowCount);
            }

            return new BigDecimal(0.00);
        }
    }
}
