package com.hightern.ecside.util;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.commons.lang.StringUtils;

public class HtmlBuilder {

    private Writer writer;

    public HtmlBuilder() {
        this.writer = new StringWriter();
    }

    public HtmlBuilder(Writer writer) {
        this.writer = writer;
    }

    public void flushBuilder() {
        try {
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeBuilder() {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private HtmlBuilder write(String text) {
        try {
            writer.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public int length() {
        return writer.toString().length();
    }

    public HtmlBuilder append(Object text) {
        if (text != null) {
            write(text.toString());
        }

        return this;
    }

    public HtmlBuilder append(String text) {
        write(text);

        return this;
    }

    public HtmlBuilder format(int tabs, int newlines) {
        tabs(tabs);
        newlines(newlines);

        return this;
    }

    public HtmlBuilder tabs(int tabs) {
        for (int i = 0; i < tabs; i++) {
            tab();
        }

        return this;
    }

    public HtmlBuilder newlines(int newlines) {
        for (int i = 0; i < newlines; i++) {
            newline();
        }

        return this;
    }

    public HtmlBuilder tab() {
        write("\t");

        return this;
    }

    public HtmlBuilder newline() {
        write("\n");

        return this;
    }

    public HtmlBuilder close() {
        write(">");

        return this;
    }

    public HtmlBuilder xclose() {
        write("/>");

        return this;
    }

    public HtmlBuilder table(int tabs) {
        newline();
        tabs(tabs);
        write("<table");

        return this;
    }

    public HtmlBuilder tableEnd(int tabs) {
        newline();
        tabs(tabs);
        write("</table>");

        return this;
    }

    public HtmlBuilder button() {
        write("<button");
        return this;
    }

    public HtmlBuilder buttonEnd() {
        write("</button>");

        return this;
    }

    public HtmlBuilder tr(int tabs) {
        newline();
        tabs(tabs);
        write("<tr");

        return this;
    }

    public HtmlBuilder trEnd(int tabs) {
        newline();
        tabs(tabs);
        write("</tr>");

        return this;
    }

    public HtmlBuilder th(int tabs) {
        newline();
        tabs(tabs);
        write("<th");

        return this;
    }

    public HtmlBuilder thEnd() {
        write("</th>");

        return this;
    }

    public HtmlBuilder td(int tabs) {
        newline();
        tabs(tabs);
        write("<td");

        return this;
    }

    public HtmlBuilder tdEnd() {
        write("</td>");

        return this;
    }

    public HtmlBuilder input() {
        write("<input");

        return this;
    }

    public HtmlBuilder type(String type) {
        if (StringUtils.isNotBlank(type)) {
            write(" type=\"").write(type).write("\" ");
        }

        return this;
    }

    public HtmlBuilder input(String type) {
        write("<input type=\"").write(type).write("\" ");

        return this;
    }

    public HtmlBuilder select() {
        write("<select");

        return this;
    }

    public HtmlBuilder selectEnd() {
        write("</select>");

        return this;
    }

    public HtmlBuilder option() {
        write("<option");

        return this;
    }

    public HtmlBuilder optionEnd() {
        write("</option>");

        return this;
    }

    public HtmlBuilder form() {
        newline();
        write("<form");
        return this;
    }

    public HtmlBuilder formEnd() {
        newline();
        write("</form>");
        return this;
    }

    public HtmlBuilder name(String name) {
        if (StringUtils.isNotBlank(name)) {
            write(" name=\"").write(name).write("\" ");
        }

        return this;
    }

    public HtmlBuilder value(String value) {
        if (StringUtils.isNotBlank(value)) {
            write(" value=\"").write(value).write("\" ");
        } else {
            write(" value=\"").write("\" ");
        }

        return this;
    }

    public HtmlBuilder title(String title) {
        if (StringUtils.isNotBlank(title)) {
            write(" title=\"").write(title).write("\" ");
        }

        return this;
    }

    public HtmlBuilder action(String action) {
        write(" action=\"");
        if (StringUtils.isNotBlank(action)) {
            write(action);
        }
        write("\" ");

        return this;
    }

    public HtmlBuilder method(String method) {
        if (StringUtils.isNotBlank(method)) {
            write(" method=\"").write(method).write("\" ");
        }

        return this;
    }

    public HtmlBuilder enctype(String enctype) {
        if (StringUtils.isNotBlank(enctype)) {
            write(" enctype=\"").write(enctype).write("\" ");
        }

        return this;
    }

    public HtmlBuilder onchange(String onchange) {
        if (StringUtils.isNotBlank(onchange)) {
            write(" onchange=\"").write(onchange).write("\" ");
        }

        return this;
    }

    public HtmlBuilder onsubmit(String onsubmit) {
        if (StringUtils.isNotBlank(onsubmit)) {
            write(" onsubmit=\"").write(onsubmit).write("\" ");
        }

        return this;
    }

    public HtmlBuilder onclick(String onclick) {
        if (StringUtils.isNotBlank(onclick)) {
            write(" onclick=\"").write(onclick).write("\" ");
        }

        return this;
    }

    public HtmlBuilder onmouseover(String onmouseover) {
        if (StringUtils.isNotBlank(onmouseover)) {
            write(" onmouseover=\"").write(onmouseover).write("\" ");
        }

        return this;
    }

    public HtmlBuilder onmouseout(String onmouseout) {
        if (StringUtils.isNotBlank(onmouseout)) {
            write(" onmouseout=\"").write(onmouseout).write("\" ");
        }

        return this;
    }

    public HtmlBuilder onkeypress(String onkeypress) {
        if (StringUtils.isNotBlank(onkeypress)) {
            write(" onkeypress=\"").write(onkeypress).write("\" ");
        }

        return this;
    }

    public HtmlBuilder id(String id) {
        if (StringUtils.isNotBlank(id)) {
            write(" id=\"").write(id).write("\" ");
        }

        return this;
    }

    public HtmlBuilder styleClass(String styleClass) {
        if (StringUtils.isNotBlank(styleClass)) {
            write(" class=\"").write(styleClass).write("\" ");
        }

        return this;
    }

    public HtmlBuilder style(String style) {
        if (StringUtils.isNotBlank(style)) {
            write(" style=\"").write(style).write("\" ");
        }

        return this;
    }

    public HtmlBuilder width(String width) {
        if (StringUtils.isNotBlank(width)) {
            write(" width=\"").write(width).write("\" ");
        }

        return this;
    }

    public HtmlBuilder align(String align) {
        if (StringUtils.isNotBlank(align)) {
            write(" align=\"").write(align).write("\" ");
        }

        return this;
    }

    public HtmlBuilder valign(String valign) {
        if (StringUtils.isNotBlank(valign)) {
            write(" valign=\"").write(valign).write("\" ");
        }

        return this;
    }

    public HtmlBuilder border(String border) {
        if (StringUtils.isNotBlank(border)) {
            write(" border=\"").write(border).write("\" ");
        }

        return this;
    }

    public HtmlBuilder cellPadding(String cellPadding) {
        if (StringUtils.isNotBlank(cellPadding)) {
            write(" cellpadding=\"").write(cellPadding).write("\" ");
        }

        return this;
    }

    public HtmlBuilder cellSpacing(String cellSpacing) {
        if (StringUtils.isNotBlank(cellSpacing)) {
            write(" cellspacing=\"").write(cellSpacing).write("\" ");
        }

        return this;
    }

    public HtmlBuilder colSpan(String colspan) {
        if (StringUtils.isNotBlank(colspan)) {
            write(" colspan=\"").write(colspan).write("\" ");
        }

        return this;
    }

    public HtmlBuilder rowSpan(String rowspan) {
        if (StringUtils.isNotBlank(rowspan)) {
            write(" rowspan=\"").write(rowspan).write("\" ");
        }

        return this;
    }

    public HtmlBuilder size(String size) {
        if (StringUtils.isNotBlank(size)) {
            write(" size=\"").write(size).write("\" ");
        }

        return this;
    }

    public HtmlBuilder span() {
        write("<span");

        return this;
    }

    public HtmlBuilder spanEnd() {
        write("</span>");

        return this;
    }

    public HtmlBuilder div() {
        write("<div");

        return this;
    }

    public HtmlBuilder divEnd() {
        write("</div>");

        return this;
    }

    public HtmlBuilder param(String name, String value) {
        append(name);
        equals();
        append(value);

        return this;
    }

    public HtmlBuilder a(String href) {
        append("<a href=");
        quote();
        append(href);
        quote();

        return this;
    }

    public HtmlBuilder a() {
        write("<a href=");

        return this;
    }

    public HtmlBuilder aEnd() {
        write("</a>");

        return this;
    }

    public HtmlBuilder bold() {
        write("<b>");

        return this;
    }

    public HtmlBuilder boldEnd() {
        write("</b>");

        return this;
    }

    public HtmlBuilder quote() {
        write("\"");

        return this;
    }

    public HtmlBuilder question() {
        write("?");

        return this;
    }

    public HtmlBuilder equals() {
        write("=");

        return this;
    }

    public HtmlBuilder ampersand() {
        write("&");

        return this;
    }

    public HtmlBuilder img() {
        write("<img");

        return this;
    }

    public HtmlBuilder src(String src) {
        if (StringUtils.isNotBlank(src)) {
            write(" src=\"").write(src).write("\" ");
        }

        return this;
    }

    public HtmlBuilder alt(String alt) {
        if (StringUtils.isNotBlank(alt)) {
            write(" alt=\"").write(alt).write("\" ");
        }

        return this;
    }

    public HtmlBuilder img(String src) {
        write("<img src=\"").write(src).write("\" style=\"border:0\"/> ");

        return this;
    }

    public HtmlBuilder img(String img, String tooltip) {
        write("<img src=\"").write(img).write("\" style=\"border:0\"");

        if (tooltip != null) {
            write(" title=\"").write(tooltip).write("\">");
        }

        return this;
    }

    public HtmlBuilder textarea() {
        write("<textarea");

        return this;
    }

    public HtmlBuilder textareaEnd() {
        write("</textarea>");

        return this;
    }

    public HtmlBuilder cols(String cols) {
        if (StringUtils.isNotBlank(cols)) {
            write(" cols=\"").write(cols).write("\" ");
        }

        return this;
    }

    public HtmlBuilder rows(String rows) {
        if (StringUtils.isNotBlank(rows)) {
            write(" rows=\"").write(rows).write("\" ");
        }

        return this;
    }

    public HtmlBuilder checked() {
        write(" checked=\"checked\"");

        return this;
    }

    public HtmlBuilder selected() {
        write(" selected=\"selected\"");

        return this;
    }

    public HtmlBuilder readonly() {
        write(" readonly=\"readonly\"");

        return this;
    }

    public HtmlBuilder nbsp() {
        write("&#160;");

        return this;
    }

    public HtmlBuilder comment(String comment) {
        if (StringUtils.isNotBlank(comment)) {
            write(" <!-- ").write(comment).write(" -->");
        }

        return this;
    }

    public HtmlBuilder ul() {
        write("<ul>");

        return this;
    }

    public HtmlBuilder ulEnd() {
        write("</ul>");

        return this;
    }

    public HtmlBuilder li(String text) {
        if (StringUtils.isNotBlank(text)) {
            write("<li>").write(text).write("</li>");
        }

        return this;
    }

    public HtmlBuilder br() {
        write("<br/>");

        return this;
    }

    public HtmlBuilder disabled() {
        write(" disabled=\"disabled\" ");

        return this;
    }

    public HtmlBuilder nowrap() {
        write(" nowrap=\"nowrap\" ");

        return this;
    }

    public HtmlBuilder maxlength(String maxlength) {
        if (StringUtils.isNotBlank(maxlength)) {
            write(" maxlength=\"").write(maxlength).write("\" ");
        }

        return this;
    }

    public HtmlBuilder tbody(int tabs) {
        newline();
        tabs(tabs);
        write("<tbody");

        return this;
    }

    public HtmlBuilder tbodyEnd(int tabs) {
        newline();
        tabs(tabs);
        write("</tbody>");

        return this;
    }

    public HtmlBuilder thead(int tabs) {
        newline();
        tabs(tabs);
        write("<thead");

        return this;
    }

    public HtmlBuilder theadEnd(int tabs) {
        newline();
        tabs(tabs);
        write("</thead>");

        return this;
    }

    public HtmlBuilder p() {
        write("<p");

        return this;
    }

    public HtmlBuilder pEnd() {
        write("</p>");

        return this;
    }

    public HtmlBuilder h1() {
        write("<h1");

        return this;
    }

    public HtmlBuilder h1End() {
        write("</h1>");

        return this;
    }

    public HtmlBuilder h2() {
        write("<h2");

        return this;
    }

    public HtmlBuilder h2End() {
        write("</h2>");

        return this;
    }

    public HtmlBuilder h3() {
        write("<h3");

        return this;
    }

    public HtmlBuilder h3End() {
        write("</h3>");

        return this;
    }

    public HtmlBuilder h4() {
        write("<h4");

        return this;
    }

    public HtmlBuilder h4End() {
        write("</h4>");

        return this;
    }

    public HtmlBuilder h5() {
        write("<h5");

        return this;
    }

    public HtmlBuilder h5End() {
        write("</h5>");

        return this;
    }

    @Override
    public String toString() {
        return writer.toString();
    }
}
