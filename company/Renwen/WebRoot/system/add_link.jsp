<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/add_link.js"></script>
<s:form action="link_save" method="post" validate="true" id="ec">
    <s:token />
    <s:hidden name="inputPage" value="/system/add_link.jsp" />
    <table border="0" cellpadding="1" cellspacing="1" width="100%" class="tableBgColor">
        <tr>
            <td align="right" width="15%" class="leftTdBgColor">
                &nbsp;<fmt:message key="link.name"></fmt:message>
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;<s:textfield name="link.name" id="name" cssClass="textInput"></s:textfield>
            </td>
            </tr>
            <tr>
             <td align="right" width="15%" class="leftTdBgColor">
               &nbsp;<fmt:message key="link.url"></fmt:message>
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;<s:textfield name="link.url" id="link" cssClass="textInput"></s:textfield>
            </td>
        </tr>
        <tr>
          <td align="right" width="15%" class="leftTdBgColor">
               &nbsp;类别
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;<s:select list="#{1:'成员企业',2:'其他行业'}" name="link.type" cssClass="textInput" cssStyle="width=130"></s:select>
            </td>
        </tr>
    </table>
    <br>
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
            <td align="center" width="30%">
                <input type="button" id="isok" value="<fmt:message key="global.save" />" class="buttonStyle">
                <input type="reset" value="<fmt:message key="global.reset" />" class="buttonStyle">
                <input id="back" type="button" value="<fmt:message key="global.back" />" class="buttonStyle">
            </td>
        </tr>
    </table>
</s:form>
<%@ include file="/common/footer.jsp"%>
