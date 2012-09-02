<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/common/jquery/jquery-1.3.2.js"></script>
<%
    response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0
    response.setDateHeader("Expires", -1);
%>
<html>
    <head>
        <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <fmt:setBundle basename="ApplicationResources" />
      	<title>人文学院</title>
    </head>
    <script type="text/javascript">
	<!--
		self.moveTo(0,0)
		self.resizeTo(screen.availWidth,screen.availHeight)
	//-->
	</script>
    <frameset framespacing="0" border="0" frameborder="0" rows="105,*">
        <frame name="menuFram" scrolling="no" noresize src="${pageContext.request.contextPath}/common/menu.jsp" target="#">
        <frameset id="oa_frame" cols="120,5,*">
            <frame name="treeFrame" src="${pageContext.request.contextPath}/dispatch_showLeft.htm" scrolling="no">
            <frame name="arrowFrame" src="${pageContext.request.contextPath}/common/arrow.jsp" scrolling="no">
            <frame name="mainFrame" src="${pageContext.request.contextPath}/default.jsp" scrolling="yes">
        </frameset>
        <noframes>
            <body topmargin="0" leftmargin="0">
                <p>
                    此网页使用了框架，但您的浏览器不支持框架。
                </p>
            </body>
        </noframes>
    </frameset>
</html>
