<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
<s:form action="hyperlink_save" method="post" validate="true">
    <s:token />
    <s:hidden name="inputPage" value="/website/add_hyperlink.jsp" />
    <table border="0" cellpadding="1" cellspacing="1" width="100%" class="tableBgColor">
        <tr>
            <td align="right" width="15%" class="leftTdBgColor">
                &nbsp;名称
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;<s:textfield name="hyperlink.name" cssClass="textInput" cssStyle="width:50%" id="hyperlinkName"/>
            </td>
             <td align="right" width="15%" class="leftTdBgColor">
               &nbsp;链接地址
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;<s:textfield name="hyperlink.url" cssClass="textInput" cssStyle="width:50%" id="hyperlinkUrl"/>
            </td>
        </tr>
         <tr>
            <td align="right" width="15%" class="leftTdBgColor">
                &nbsp;分类
            </td>
            <td align="left" width="85%" class="rightTdBgColor" colspan="3">
                &nbsp;<s:select list="#{1:'否',2:'是'}" name="hyperlink.category" cssStyle="width=130"></s:select>
            </td>
        </tr>
    </table>
    <br>
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
            <td align="center" width="30%">
                <input type="submit" value="<fmt:message key="global.save" />" class="buttonStyle">
                <input type="reset" value="<fmt:message key="global.reset" />" class="buttonStyle">
                <input id="back" type="button" value="<fmt:message key="global.back" />" class="buttonStyle">
            </td>
        </tr>
    </table>
</s:form>
<%@ include file="/common/footer.jsp"%>
