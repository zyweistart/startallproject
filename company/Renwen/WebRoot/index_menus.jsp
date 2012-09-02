<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld"%>
<%@ taglib prefix="fn" uri="/WEB-INF/tld/fn.tld"%>
<%@page
	import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.hightern.consult.util.NewstypeInfo"%>
<%
  NewstypeInfo newstypeInfo=NewstypeInfo.getFromApplicationContext(WebApplicationContextUtils.getWebApplicationContext(getServletContext()));
%>

<c:set var="munes" value="<%=newstypeInfo.Mapcs() %>"></c:set>

<table width="100%" height="35" border="0" cellpadding="0"
	cellspacing="1">
	<tr>
		<td width="100" align="center"><a
			href="${pageContext.request.contextPath}/home.jsp">学院首页</a></td>

		<c:forEach items="${munes}" var="n" varStatus="ssss">
		  <c:if test="${!ssss.first}">
			<td width="100" align="center"><c:if test="${empty n.ljUrl}">
				<a
					href="${pageContext.request.contextPath}/integration_list_home.htm?id=${n.id}&typeId=${n.id}">
				${n.name}</a> 
				<br>
			</c:if> <c:if test="${not empty n.ljUrl}">
				<a href="${pageContext.request.contextPath}${n.ljUrl}">
				${n.name}</a>
				<br>
			</c:if></td>
			</c:if>
		</c:forEach>

   <c:forEach items="${munes}" var="n" varStatus="ssss">
		  <c:if test="${ssss.first}">
			<td width="100" align="center"><c:if test="${empty n.ljUrl}">
				<a
					href="${pageContext.request.contextPath}/integration_list_home.htm?id=${n.id}&typeId=${n.id}">
				${n.name}</a> 
				<br>
			</c:if> <c:if test="${not empty n.ljUrl}">
				<a href="${pageContext.request.contextPath}${n.ljUrl}">
				${n.name}</a>
				<br>
			</c:if></td>
			</c:if>
		</c:forEach>
	</tr>
</table>
