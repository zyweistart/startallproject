package com.hightern.ecside.table.handler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.hightern.ecside.table.bean.Export;
import com.hightern.ecside.table.core.PreferencesConstants;
import com.hightern.ecside.table.core.TableCache;
import com.hightern.ecside.table.core.TableConstants;
import com.hightern.ecside.table.core.TableModel;
import com.hightern.ecside.table.core.TableModelUtils;

public class ExportHandler {

    private static Log logger = LogFactory.getLog(ExportHandler.class);
    private TableModel model;
    @SuppressWarnings("unchecked")
    private List exports = new ArrayList();

    public ExportHandler(TableModel model) {
        this.model = model;
    }

    @SuppressWarnings("unchecked")
    public void addExport(Export export) {
        exports.add(export);
        addExportAttributes(export);
        export.defaults();
    }

    public void addExportAttributes(Export export) {
        String interceptor = TableModelUtils.getInterceptPreference(model,
                export.getInterceptor(),
                PreferencesConstants.EXPORT_INTERCEPTOR);
        export.setInterceptor(interceptor);
        TableCache.getInstance().getExportInterceptor(interceptor).addExportAttributes(model, export);
    }

    @SuppressWarnings("unchecked")
    public Export getExport(String view) {
        for (Iterator iter = exports.iterator(); iter.hasNext();) {
            Export export = (Export) iter.next();

            if (export.getView().equals(view)) {
                return export;
            }
        }

        return null;
    }

    public Export getCurrentExport() {
        String prefixWithTableId = model.getTableHandler().prefixWithTableId();
        String exportView = model.getRegistry().getParameter(
                prefixWithTableId + TableConstants.EXPORT_VIEW);

        Export export = getExport(exportView);
        if (export == null) {
            String msg = "There is no export defined. This commonly happens if you do not "
                    + "declare the export (Export or ExportTag) before the row and columns.";
            logger.error(msg);
            throw new IllegalStateException(msg);
        }

        return export;
    }

    @SuppressWarnings("unchecked")
    public List getExports() {
        return exports;
    }

    /**
     * @deprecated This functionality is available on the Limit.
     */
    public boolean isExported() {
        return model.getExportHandler().isExported();
    }

    /**
     * @deprecated Specific logic to building html. Moved to
     *             BuilderUtils.showExports.
     */
    public boolean showExports() {
        if (!model.getTableHandler().getTable().isShowExports()) {
            return false;
        }

        return getExports().size() > 0;
    }
}
