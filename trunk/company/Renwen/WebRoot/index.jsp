<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<html>
<head>
 <title>人文学院</title>

</head>
<frameset rows="0,*" frameborder="NO" border="0" framespacing="0">
<frame src="${pageContext.request.contextPath}/music.jsp" name="topFrame" scrolling="NO" noresize >
<frame src="${pageContext.request.contextPath}/integration_list_home.htm?id=4&typeId=4" name="mainFrame">
</frameset>
<noframes>
<body>

</body>
</noframes>
</html>
