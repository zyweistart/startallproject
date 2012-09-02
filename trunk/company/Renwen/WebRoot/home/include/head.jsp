<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.hightern.website.action.WebSitePageService"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld"%>
<%@ taglib prefix="fn" uri="/WEB-INF/tld/fn.tld"%>
<%
	WebSitePageService webSitePageService=WebSitePageService.getFromApplicationContext(WebApplicationContextUtils.getWebApplicationContext(getServletContext()));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>人文学院——浙江纺织服装职业技术学院</title>
<link href="${pageContext.request.contextPath}/home/css/css.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/home/css/bbs.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/common/jquery/jquery-1.3.2.js"></script>
</head>
<body>
<table width="1003" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="172" style="background-image:url(${pageContext.request.contextPath}/home/images/top_bg.jpg); background-repeat:no-repeat;"><table width="100%" height="172" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="50" align="right" class="td_1">
        	<c:if test="${empty stuUser}">
        		<a href="${pageContext.request.contextPath}/home/login.jsp">登陆</a>
        	</c:if>
        	<c:if test="${not empty stuUser}">
        		${stuUser.uname}
        		<a href="${pageContext.request.contextPath}/users_exit.htm">退出</a>
        	</c:if>
       		&nbsp;|&nbsp;
        	<a href="#">设为首页</a>&nbsp;|&nbsp;<a href="#">加入收藏</a>&nbsp;&nbsp;
        </td>
      </tr>
      <tr>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td height="120" align="center" valign="top">
        	<table width="90%" border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td width="11%"><a href="${pageContext.request.contextPath}/home.jsp"><img src="${pageContext.request.contextPath}/home/images/menu_1.bmp" border="0" /></a></td>
	            <td width="11%"><a href="${pageContext.request.contextPath}/homePage.htm?id=1"><img src="${pageContext.request.contextPath}/home/images/menu_2.bmp" border="0" /></a></td>
	            <td width="11%"><a href="${pageContext.request.contextPath}/homePage.htm?id=2"><img src="${pageContext.request.contextPath}/home/images/menu_3.bmp" border="0" /></a></td>
	            <td width="11%"><a href="${pageContext.request.contextPath}/homePage.htm?id=3"><img src="${pageContext.request.contextPath}/home/images/menu_4.bmp" border="0" /></a></td>
	            <td width="11%"><a href="${pageContext.request.contextPath}/homePage.htm?id=4"><img src="${pageContext.request.contextPath}/home/images/menu_5.bmp" border="0" /></a></td>
	            <td width="11%"><a href="${pageContext.request.contextPath}/homePage.htm?id=5"><img src="${pageContext.request.contextPath}/home/images/menu_6.bmp" border="0" /></a></td>
	            <td width="11%"><a href="${pageContext.request.contextPath}/homePage.htm?id=6"><img src="${pageContext.request.contextPath}/home/images/menu_7.bmp" border="0" /></a></td>
	            <td width="11%"><a href="${pageContext.request.contextPath}/homePage.htm?id=7"><img src="${pageContext.request.contextPath}/home/images/menu_8.bmp" border="0" /></a></td>
	            <td width="12%"><a href="${pageContext.request.contextPath}/homePage.htm?id=8"><img src="${pageContext.request.contextPath}/home/images/menu_9.bmp" border="0" /></a></td>
	          </tr>
	        </table>
        </td>
      </tr>
    </table></td>
  </tr>