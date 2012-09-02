<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.hightern.consult.util.NewstypeInfo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld"%>
<%@ taglib prefix="fn" uri="/WEB-INF/tld/fn.tld"%>
<html>
<head>

 <title>红帮文化</title>
<script src="${pageContext.request.contextPath}/js/index/jquery.js"type=text/javascript></script>
</head>
<%
  NewstypeInfo newstypeInfo=NewstypeInfo.getFromApplicationContext(WebApplicationContextUtils.getWebApplicationContext(getServletContext()));
%>
<body>
<c:set var="munes" value="<%=newstypeInfo.Mapcs() %>"></c:set>
  

 <c:forEach items="${munes}" var="n">
      <c:if test="${empty n.ljUrl}">
       <a href="${pageContext.request.contextPath}/integration_list_home.htm?id=${n.id}&typeId=${n.id}"> ${n.name}</a><br>
      </c:if>
      <c:if test="${not empty n.ljUrl}">
       <a href="${pageContext.request.contextPath}${n.ljUrl}"> ${n.name}</a><br>
      </c:if>
        <c:forEach items="${n.children}"  var="n1">
          &nbsp;&nbsp;&nbsp;&nbsp;
          <c:if test="${empty n1.ljUrl}">
          *<a href="${pageContext.request.contextPath}/integration_list_home.htm?id=${n1.id}&typeId=${n.id}">${n1.name}</a><br>
          </c:if>
          <c:if test="${not empty n1.ljUrl}">
          *<a href="${pageContext.request.contextPath}${n1.ljUrl}">${n1.name}</a><br>
          </c:if>
        </c:forEach>
 </c:forEach>

<br>


<%@include file="/userLogin.jsp" %>


</body>
</html>
