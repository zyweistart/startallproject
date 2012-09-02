<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0
    response.setDateHeader("Expires", -1);
%>
<html>
<frameset id="folder_Frame" framespacing="0" border="0" frameborder="0" cols="200,5,*">
	<frame id="leftFrame" name="leftFrame" frameborder="0" scrolling="auto" noresize src="${pageContext.request.contextPath}/newstype_xmtree.htm?scope=${scope}">
	<frame id="spaceFrame" name="spaceFrame" frameborder="0" scrolling="no" noresize src="${pageContext.request.contextPath}/index/arrow.jsp">
	<frame id="fileFrame" name="fileFrame" frameborder="0" scrolling="yes" noresize src="${pageContext.request.contextPath}/blank.jsp" target="_self">
	<noframes>
	<body topmargin="0" leftmargin="0">
	<p>此网页使用了框架，但您的浏览器不支持框架。</p>
	</body>
	</noframes>
</frameset>
</html>
