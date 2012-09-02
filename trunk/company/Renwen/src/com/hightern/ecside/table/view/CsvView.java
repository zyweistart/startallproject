package com.hightern.ecside.table.view;

import org.apache.commons.lang.StringUtils;
import com.hightern.ecside.table.bean.Column;
import com.hightern.ecside.table.bean.Export;
import com.hightern.ecside.table.core.TableModel;

public class CsvView implements View {

    public final static String DELIMITER = "delimiter";
    final static String DEFAULT_DELIMITER = ",";
    private StringBuffer plainData = new StringBuffer();

    public void beforeBody(TableModel model) {
    }

    public void body(TableModel model, Column column) {
        Export export = model.getExportHandler().getCurrentExport();
        String delimiter = export.getAttributeAsString(DELIMITER);
        if (StringUtils.isBlank(delimiter)) {
            delimiter = DEFAULT_DELIMITER;
        }

        String value = ExportViewUtils.parseCSV(column.getCellDisplay());
        if (!value.startsWith("<input") && value != null && value.length() > 0) {
            plainData.append(value.trim());
            plainData.append(delimiter);
            if (column.isLastColumn()) {
                plainData.append("\r\n");
            }
        }
    }

    public Object afterBody(TableModel model) {
        return plainData.toString();
    }
}
