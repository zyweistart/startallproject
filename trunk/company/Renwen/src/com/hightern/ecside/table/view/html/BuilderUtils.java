package com.hightern.ecside.table.view.html;

import org.apache.commons.lang.StringUtils;
import com.hightern.ecside.table.core.TableModel;

public class BuilderUtils {

    private BuilderUtils() {
    }

    public static boolean showPagination(TableModel model) {
        return model.getTableHandler().getTable().isShowPagination();
    }

    public static boolean showExports(TableModel model) {
        if (!model.getTableHandler().getTable().isShowExports()) {
            return false;
        }

        return model.getExportHandler().getExports().size() > 0;
    }

    public static boolean showStatusBar(TableModel model) {
        return model.getTableHandler().getTable().isShowStatusBar();
    }

    public static boolean showTitle(TableModel model) {
        return model.getTableHandler().getTable().isShowTitle();
    }

    public static boolean filterable(TableModel model) {
        return model.getTableHandler().getTable().isFilterable();
    }

    public static boolean isFirstPageEnabled(int page) {
        if (page == 1) {
            return false;
        }

        return true;
    }

    public static boolean isPrevPageEnabled(int page) {
        if (page - 1 < 1) {
            return false;
        }

        return true;
    }

    public static boolean isNextPageEnabled(int page, int totalPages) {
        if (page + 1 > totalPages) {
            return false;
        }

        return true;
    }

    public static boolean isLastPageEnabled(int page, int totalPages) {
        if (page == totalPages || totalPages == 0) {
            return false;
        }

        return true;
    }

    public static int getTotalPages(TableModel model) {
        int currentRowsDisplayed = model.getLimit().getCurrentRowsDisplayed();

        if (currentRowsDisplayed == 0) {
            currentRowsDisplayed = model.getLimit().getTotalRows();
        }

        int totalRows = model.getLimit().getTotalRows();

        int totalPages = 1;

        if (currentRowsDisplayed != 0) {
            totalPages = totalRows / currentRowsDisplayed;
        }

        if ((currentRowsDisplayed != 0) && ((totalRows % currentRowsDisplayed) > 0)) {
            totalPages++;
        }

        return totalPages;
    }

    public static String getImage(TableModel model, String imageName) {
        String imagePath = model.getTableHandler().getTable().getImagePath();

        if (StringUtils.isNotBlank(imagePath)) {
            int index = imagePath.indexOf("*.");
            return imagePath.substring(0, index) + imageName + imagePath.substring(index + 1);
        }

        return null;
    }

    public static String getForm(TableModel model) {
        String form = model.getTableHandler().getTable().getForm();
        if (StringUtils.isBlank(form)) {
            form = model.getTableHandler().getTable().getTableId();
        }

        return form;
    }
}
