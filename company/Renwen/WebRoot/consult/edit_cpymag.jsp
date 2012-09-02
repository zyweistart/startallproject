<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
<s:form action="cpymag_save" method="post" validate="true">
    <s:token />
    <s:hidden name="cpymag.id" />
    <s:hidden name="inputPage" value="/consult/edit_cpymag.jsp" />
    <table border="0" cellpadding="1" cellspacing="1" width="100%" class="tableBgColor">
       
        <tr>
          <td align="center" colspan="4" class="leftTdBgColor">
            <font size="4"><b>公司联系方式</b></font>
          </td>
        </tr>
        
       <!-- 
        <tr>
          <td align="left" colspan="4" class="rightTdBgColor">
            <b>-------基本信息----------</b> 
          </td>
        </tr>
        <tr>
            <td align="right" width="15%" class="leftTdBgColor">
                &nbsp;公司地址:
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;<s:textfield name="cpymag.address" cssClass="textInput"
					cssStyle="width:80%" />
            </td>
             <td align="right" width="15%" class="leftTdBgColor">
               &nbsp;电话:
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;<s:textfield name="cpymag.phone" cssClass="textInput"
					cssStyle="width:80%"  />
            </td>
        </tr>
        <tr>
          <td align="right" width="15%" class="leftTdBgColor">
                &nbsp;传真:
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;<s:textfield name="cpymag.fax" cssClass="textInput"
					cssStyle="width:80%" />
            </td>
             <td align="right" width="15%" class="leftTdBgColor">
               &nbsp;E-mail:
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;<s:textfield name="cpymag.email" cssClass="textInput"
					cssStyle="width:80%"  />
            </td>
        </tr>
   -->      
         <tr style="height: 400px;">
            <td bgcolor="#FFFFFF" colspan="4">
            <c:set var="editorName" scope="request" value="cpymag.content" />
            <c:set value="${cpymag.content}" var="editorValue"></c:set>
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
