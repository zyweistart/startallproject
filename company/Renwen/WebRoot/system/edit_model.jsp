<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
<s:form action="model_save" method="post" validate="true">
	<s:hidden name="model.id" />
    <s:token />
    <s:hidden name="inputPage" value="/system/add_model.jsp" />
   <table border="0" cellpadding="1" cellspacing="1" width="100%" class="tableBgColor">
        <tr>
            <td align="right" width="15%" class="leftTdBgColor">
                <fmt:message key="model.name" /><fmt:message key="global.colon" />
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                <s:textfield name="model.name" cssClass="textInput" cssStyle="width:98%" />
            </td>
             <td align="right" width="15%" class="leftTdBgColor">
               <fmt:message key="model.url" /><fmt:message key="global.colon" />
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                <s:textfield name="model.url" cssClass="textInput" cssStyle="width:98%" />
            </td>
        </tr>
         <tr>
            <td align="right" width="15%" class="leftTdBgColor">
                <fmt:message key="model.parentName" /><fmt:message key="global.colon" />
            </td>
            <td align="left" width="35%" class="rightTdBgColor" colspan="3">
                <s:hidden name="model.parentId" id="parentId" />
                <s:textfield name="model.parentName" readonly="true" id="parentName" cssClass="textInput" cssStyle="width:75%;" />
                <input type="button" value="<fmt:message key="global.selected" />" onclick="openDialog('model_tree.htm',300,650)" class="buttonStyle"/>
            </td>
        </tr>
        <tr>
            <td align="right" width="15%" class="leftTdBgColor">
                <fmt:message key="model.scription" /><fmt:message key="global.colon" />
            </td>
            <td align="left" width="35%" class="rightTdBgColor" colspan="3">
                <s:textarea name="model.scription" cssStyle="width:80%;height:80px;" />
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
