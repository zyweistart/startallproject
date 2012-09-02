package com.hightern.ecside.table.filter;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public final class ExportResponseWrapper extends HttpServletResponseWrapper {

    private ByteArrayOutputStream output;
    private int contentLength;
    private String contentType;

    public ExportResponseWrapper(HttpServletResponse response) {
        super(response);
        output = new ByteArrayOutputStream();
    }

    public byte[] getData() {
        return output.toByteArray();
    }

    @Override
    public ServletOutputStream getOutputStream() {
        return new FilterServletOutputStream(output);
    }

    @Override
    public PrintWriter getWriter() {
        return new PrintWriter(getOutputStream(), true);
    }

    @Override
    public void setContentLength(int length) {
        this.contentLength = length;
        super.setContentLength(length);
    }

    public int getContentLength() {
        return contentLength;
    }

    @Override
    public void setContentType(String type) {
        this.contentType = type;
        super.setContentType(type);
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    private static class FilterServletOutputStream extends ServletOutputStream {

        private DataOutputStream stream;

        public FilterServletOutputStream(OutputStream output) {
            stream = new DataOutputStream(output);
        }

        public void write(int b) throws IOException {
            stream.write(b);
        }

        public void write(byte[] b) throws IOException {
            stream.write(b);
        }

        public void write(byte[] b, int off, int len) throws IOException {
            stream.write(b, off, len);
        }
    }
}
