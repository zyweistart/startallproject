<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
<script  type="text/javascript" src="${pageContext.request.contextPath}/js/schman/add_interactive.js"></script>
<s:form action="interactive_save" method="post"  id="ec" validate="true" enctype="multipart/form-data">
    <s:token />
    <s:hidden name="inputPage" value="/schman/add_interactive.jsp" />
    <table border="0" cellpadding="1" cellspacing="1" width="100%" class="tableBgColor">
        <tr>
            <td align="right" width="15%" class="leftTdBgColor">
                &nbsp;资源描述名:
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;<s:textfield name="interactive.ianame"  id="ianame"  cssClass="textInput" cssStyle="width:30%" ></s:textfield>
            </td>
        </tr>
        <tr>
            <td align="right" width="15%" class="leftTdBgColor">
               &nbsp;是否公开下载:
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;<s:radio list="#{1:'是',2:'否' }"  name="interactive.xzStrtus"  value="2"></s:radio>
            </td>
        </tr>
        <tr>
           <td align="right" width="15%" class="leftTdBgColor">
               &nbsp;上传文件:
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;<input type="file" name="Upload" style="border: 1px solid #000000;" >
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
