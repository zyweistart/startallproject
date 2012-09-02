package com.hightern.ecside.table.filter;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.hightern.ecside.table.context.Context;
import com.hightern.ecside.table.core.TableConstants;

public final class ExportFilterUtils {

    private static Log logger = LogFactory.getLog(ExportFilterUtils.class);

    private ExportFilterUtils() {
    }

    public static boolean isExported(Context context) {
        return StringUtils.isNotBlank(getTableId(context));
    }

    public static String getExportFileName(Context context) {
        String tableId = getTableId(context);
        if (StringUtils.isNotBlank(tableId)) {
            String exportFileNameStr = tableId + "_" + TableConstants.EXPORT_FILE_NAME;
            String exportFileName = context.getParameter(exportFileNameStr);
            if (logger.isDebugEnabled()) {
                logger.debug("eXtremeTable export file name [" + exportFileNameStr + "] is [" + exportFileName + "]");
            }
            return exportFileName;
        }
        return null;
    }

    public static String getTableId(Context context) {
        return context.getParameter(TableConstants.EXPORT_TABLE_ID);
    }
}
