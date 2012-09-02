<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/schman/add_forum.js"></script>
<s:form action="forum_save" method="post" validate="true" id="ec">
    <s:token />
    <s:hidden name="forum.id" />
    <s:hidden name="inputPage" value="/schman/edit_forum.jsp" />
    <table border="0" cellpadding="1" cellspacing="1" width="100%" class="tableBgColor">
        <tr>
            <td align="right" width="20%" class="leftTdBgColor">
                &nbsp;类别名称:
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;<s:textfield name="forum.forumName"  id="forumName" cssClass="textInput" cssStyle="width:30%"/>
            </td>
        </tr>
        <tr>
           <td align="right" width="20%" class="leftTdBgColor">
               &nbsp;备注:
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;<s:textarea name="forum.fcontent" cols="60" rows="10"  />
            </td>
        </tr>
    </table>
    <br>
   <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
            <td align="center" width="30%">
                <input type="button" value="<fmt:message key="global.save" />" id="isok" class="buttonStyle">
                <input type="reset" value="<fmt:message key="global.reset" />" class="buttonStyle">
                <input id="back" type="button" value="<fmt:message key="global.back" />" class="buttonStyle">
            </td>
        </tr>
    </table>
</s:form>
<%@ include file="/common/footer.jsp"%>
