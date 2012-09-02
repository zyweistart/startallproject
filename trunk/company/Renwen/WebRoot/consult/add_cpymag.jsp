<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
<s:form action="cpymag_save" method="post" validate="true">
    <s:token />
    <s:hidden name="inputPage" value="/consult/add_cpymag.jsp" />
    <table border="0" cellpadding="1" cellspacing="1" width="100%" class="tableBgColor">
        <tr>
          <td align="center" colspan="4" class="leftTdBgColor">
            <font size="4"><b>公司联系方式</b></font>
          </td>
        </tr>
        
         <tr>
			<td bgcolor="#FFFFFF" colspan="4">
			<c:set var="editorName" scope="request" value="cpymag.content" />
               <%@include file="../common/fckeditor.jsp" %>
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
