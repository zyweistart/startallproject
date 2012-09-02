<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
<s:form action="menu_save" method="post" validate="true">
    <s:token />
    <s:hidden name="inputPage" value="/website/add_menu.jsp" />
    <table border="0" cellpadding="1" cellspacing="1" width="100%" class="tableBgColor">
        <tr>
            <td align="right" width="15%" class="leftTdBgColor">
                &nbsp;名称
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;<s:textfield name="menu.name" id="name" cssClass="textInput"></s:textfield>
            </td>
             <td align="right" width="15%" class="leftTdBgColor">
               	&nbsp;父级别
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;<s:textfield name="menu.pname" id="pname" cssClass="textInput" readonly="true"></s:textfield>
               	&nbsp;<input type="button" value="请选择" class="button"  onclick="openDialog('menu_tree.htm',300,650)">
               	<s:hidden name="menu.pid" id="pid"></s:hidden>
               	<s:hidden name="menu.level" id="level"></s:hidden>
            </td>
        </tr>
        <tr>
             <td align="right" width="15%" class="leftTdBgColor">
               &nbsp;页类型
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                 &nbsp;<s:select list="#{1:'父级类型',2:'单页类型',3:'列表类型',4:'图文类型'}" value="%{menu.pagetype}" name="menu.pagetype" cssStyle="width=130"></s:select>
            </td>
            <td align="right" width="15%" class="leftTdBgColor">
               &nbsp;排序
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                 &nbsp;<s:textfield name="menu.sort" id="sort" cssClass="textInput"></s:textfield>
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
