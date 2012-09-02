package com.hightern.ecside.table.calc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.hightern.ecside.table.bean.Column;
import com.hightern.ecside.table.core.PreferencesConstants;
import com.hightern.ecside.table.core.TableCache;
import com.hightern.ecside.table.core.TableModel;
import com.hightern.ecside.util.ExtremeUtils;

public final class CalcUtils {

    private static Log logger = LogFactory.getLog(CalcUtils.class);

    private CalcUtils() {
    }

    @SuppressWarnings("unchecked")
    public static CalcResult[] getCalcResults(TableModel model, Column column) {
        List values = new ArrayList();

        String calcs[] = column.getCalc();
        for (int i = 0; i < calcs.length; i++) {
            values.add(getCalcResultsByPosition(model, column, i));
        }

        return (CalcResult[]) values.toArray(new CalcResult[values.size()]);
    }

    public static CalcResult getCalcResultsByPosition(TableModel model, Column column, int position) {
        String calcClassName = CalcUtils.getCalcClassNameByPosition(model, column, position);

        if (!isCalcClassName(calcClassName)) {
            return new CalcResult(calcClassName, null);
        }

        Calc calc = TableCache.getInstance().getCalc(calcClassName);
        return new CalcResult(calcClassName, calc.getCalcResult(model, column));
    }

    @SuppressWarnings("unchecked")
    public static String[] getFirstCalcColumnTitles(TableModel model) {
        List values = new ArrayList();

        Column column = model.getColumnHandler().getFirstCalcColumn();
        String calcs[] = column.getCalc();
        for (int i = 0; i < calcs.length; i++) {
            values.add(getFirstCalcColumnTitleByPosition(model, i));
        }

        return (String[]) values.toArray(new String[values.size()]);
    }

    public static String getFirstCalcColumnTitleByPosition(TableModel model, int position) {
        Column column = model.getColumnHandler().getFirstCalcColumn();
        String calcTitle[] = column.getCalcTitle();
        return calcTitle[position];
    }

    private static String getCalcClassNameByPosition(TableModel model, Column column, int position) {
        String calcs[] = column.getCalc();
        String calcName = calcs[position];
        String calcClassName = model.getPreferences().getPreference(PreferencesConstants.COLUMN_CALC + calcName);
        if (StringUtils.isBlank(calcClassName)) {
            calcClassName = calcName;
        }

        return calcClassName;
    }

    private static boolean isCalcClassName(String calcClassName) {
        try {
            Class.forName(calcClassName);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    public static void eachRowCalcValue(CalcHandler handler, Collection rows, String property) {
        if (rows == null) {
            return;
        }

        for (Iterator listIter = rows.iterator(); listIter.hasNext();) {
            Object row = listIter.next();
            Object value = null;

            if (ExtremeUtils.isBeanPropertyReadable(row, property)) {
                try {
                    value = PropertyUtils.getProperty(row, property);

                    if (value instanceof Number) {
                        handler.processCalcValue((Number) value);
                    } else {
                        handler.processCalcValue(getValue(property, value));
                    }
                } catch (Exception e) {
                    String errorMessage = "Problem parsing numeric value for property [" + property + "].";
                    logger.error("CalcUtils.eachCalc() - " + errorMessage);
                }
            }
        }
    }

    private static Number getValue(String property, Object value) {
        String valueAsString = String.valueOf(value);
        if (StringUtils.isNotBlank(valueAsString)) {
            try {
                return new BigDecimal(valueAsString);
            } catch (NumberFormatException e) {
                String errorMessage = "Problem parsing numeric value for property [" + property + "] with value [" + valueAsString + "].";
                logger.error("CalcUtils.getValue() - " + errorMessage);
            }
        }

        return new BigDecimal(0.00);
    }
}
