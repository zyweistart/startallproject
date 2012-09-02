<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld"%>
<%@ taglib prefix="fn" uri="/WEB-INF/tld/fn.tld"%>
<script type="text/javascript"  src="${pageContext.request.contextPath}/js/index/userLogin.js"></script>
<s:form  action="users_login.htm" method="post" validate="true" id="eec" >
<font color="red"> ${ message }</font>
<table width="80%" border="0" cellpadding="0" cellspacing="0">
 <tbody>
  <c:if test="${empty stuUser}">
    <tr>
     <td width="40%" height="30" align="right">账&nbsp;&nbsp;号：</td>
     <td height="30" align="left"><input name="username" id="username" type="text"  style="width:100px"/></td>
    </tr>
    <tr>
       <td height="30" align="right">密&nbsp;&nbsp;码：</td>
       <td height="30" align="left"><input type="password"  name="password" id="password"   style="width:100px"></td>
    </tr>
    <tr>
     <td height="30" colspan="2" align="center">
     	<a href="#" id="issok">
     		<img src="${pageContext.request.contextPath}/index/images/gif-0554.gif" width="45" height="19" border="0"/>
     	</a> 
     	<a href="${pageContext.request.contextPath}/home/register.jsp">
     		<img src="${pageContext.request.contextPath}/index/images/gif-0555.gif" width="45" height="19" border="0"/>
     	</a>
     </td>
    </tr>
  </c:if>
  <c:if test="${not empty stuUser}">
    <tr>
      <td>
         ${stuUser.uname}
      </td>
      <td>
       <input type="button"  value="退出" id="exitOk">
      </td>
    </tr>
    <tr>
      <td colspan="2">
         <hr>
         <b>详细内容：</b><Br>
           总访问时间：${stuUser.visitsMax}<br>
           总访问次数：${stuUser.visitsSize}<br>
           总测试库访问时间：${stuUser.visitsExtmMax}<Br>
           总测试库访问次数：${stuUser.visitsExtmSize}
      </td>
    </tr>
  </c:if> 
 </tbody>
</table>
</s:form>
<!--<td rowspan="2"><input type="button"  id="isok" name="isok" value="确定" ><br><a href="${pageContext.request.contextPath}/registration.jsp">新注册</a></td>  -->