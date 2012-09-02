<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld"%>
<%@ taglib prefix="fn" uri="/WEB-INF/tld/fn.tld"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>人文学院</title>
<link href="${pageContext.request.contextPath}/index/css/default.css" rel="stylesheet" type="text/css" />
 
</head>
<body>
<center>
<table width="1002" height="600" border="0" cellpadding="0" cellspacing="0" background="${pageContext.request.contextPath}/index/images/bg.jpg">
  <tr>
    <td>
	<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0" width="1002" height="600">
    <param name="movie" value="${pageContext.request.contextPath}/index/images/index_jsp.swf" />
    <param name="quality" value="high" />
    <param name="wmode" value="transparent" />
    <embed src="${pageContext.request.contextPath}/index/images/index_jsp.swf" width="1002" height="600" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" wmode="transparent"></embed>
    </object>
	</td>
  </tr>
</table>
</center>
</body>
</html>