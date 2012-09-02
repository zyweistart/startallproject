package com.hightern.ecside.table.bean;

import com.hightern.ecside.table.core.TableModel;

public class Export extends Attributes {

    private TableModel model;
    private String encoding;
    private String fileName;
    private String imageName;
    private String interceptor;
    private String view;
    private String viewResolver;
    private String text;
    private String tooltip;

    public Export(TableModel model) {
        this.model = model;
    }

    public void defaults() {
        this.encoding = ExportDefaults.getEncoding(model, encoding);
        this.text = ExportDefaults.getText(model, text);
        this.tooltip = ExportDefaults.getTooltip(model, tooltip);
        this.viewResolver = ExportDefaults.getviewResolver(model, viewResolver);
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getInterceptor() {
        return interceptor;
    }

    public void setInterceptor(String interceptor) {
        this.interceptor = interceptor;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getViewResolver() {
        return viewResolver;
    }

    public void setViewResolver(String viewResolver) {
        this.viewResolver = viewResolver;
    }
}
