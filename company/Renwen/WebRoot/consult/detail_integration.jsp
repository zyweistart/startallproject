<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>

<table border="0" cellpadding="1" align="center" cellspacing="1" width="70%" class="tableBgColor">
    <tr>
       <td  width="100%" class="rightTdBgColor">
       <br>
       <h3 align="center"> ${integration.name}</h3>
          <p align="center">来源:<b>${integration.newsfrom}</b>&nbsp;&nbsp;&nbsp;点击率:<b>${integration.hits}</b>&nbsp;&nbsp;&nbsp;更新时间:<b>${integration.startDay}</b> </p>
           <br>
           <p align="left">&nbsp;&nbsp;&nbsp;<b>-------以下为正文--------</b></p>
             <br>
             &nbsp;&nbsp;&nbsp;&nbsp;${integration.content }
           <br>
           <br>
           <br>
        </td>
    </tr>
</table>
<br>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
        <td align="center" width="30%">
            <input id="back" type="button" value="<fmt:message key="global.back" />" class="buttonStyle">
        </td>
    </tr>
</table>

<%@ include file="/common/footer.jsp"%>
