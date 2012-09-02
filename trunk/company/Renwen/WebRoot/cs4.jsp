<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld"%>
<%@ taglib prefix="fn" uri="/WEB-INF/tld/fn.tld"%>

<html>
<head>
<link href="${pageContext.request.contextPath}/index/css/default.css"
	rel="stylesheet" type="text/css" />
<title>人文学院</title>
<!--背景图片透明方法-->
<script src="${pageContext.request.contextPath}/index/js/iepng.js"
	type="text/javascript"></script>
<!--插入图片透明方法-->
<script type="text/javascript">
	EvPNG.fix('div, ul, img, li, input'); //EvPNG.fix('包含透明PNG图片的标签'); 多个标签之间用英文逗号隔开。
	//-->
</script>
</head>
<body>

<center>
<table width="900" border="2" cellpadding="0" cellspacing="0"
	bordercolor="#FFFFFF"
	background="${pageContext.request.contextPath}/index/images/bg2.gif"
	bgcolor="#FCF0D8">
	<tr>
		<td height="40" colspan="4" valign="bottom" bgcolor="#CFBBA0"><%@include
			file="index_menus.jsp"%></td>
	</tr>
	<tr>
		<td width="180" rowspan="3" align="center" valign="top"
			bgcolor="#CCBEA4"><img
			src="${pageContext.request.contextPath}/index/images/title_1.png"
			width="150" height="235" />
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<!-- 二级目录 -->
			<c:forEach items="${newstypes}" var="n">
				<tr>
					<td>
					<div id="list_2">
					<div class="t"><c:if test="${empty n.ljUrl}">
						<a
							href="${pageContext.request.contextPath}/integration_list_home.htm?id=${n.id}&typeId=${typeId}">
						${n.name}</a>
					</c:if> <c:if test="${not empty n.ljUrl}">
						<a href="${pageContext.request.contextPath}${n.ljUrl}">
						${n.name}</a>
					</c:if></div>
					</div>
					</td>
				</tr>
			</c:forEach>

		</table>
		</td>
		<td height="120" valign="bottom"><object
			classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
			codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0"
			width="720" height="120">
			<param name="movie"
				value="${pageContext.request.contextPath}/index/images/banner.swf" />
			<param name="quality" value="high" />
			<param name="wmode" value="transparent" />
			<embed
				src="${pageContext.request.contextPath}/index/images/banner.swf"
				width="720" height="120" quality="high"
				pluginspage="http://www.macromedia.com/go/getflashplayer"
				type="application/x-shockwave-flash" wmode="transparent"></embed> </object></td>
	</tr>

	<tr>
		<td height="650" align="center" valign="top">
		<div id="menu"><a
			href="${pageContext.request.contextPath}/guideIndex.jsp"><img
			src="${pageContext.request.contextPath}/index/images/64.png" alt="首页"
			width="16" height="16" border="0" /></a> <c:if
			test="${newstype.length != 1}">
			<font class="list"><a href="${pageContext.request.contextPath}/integration_list_home.htm?id=${typeId}&typeId=${typeId}">${newstype.pname}</a></font>
		</c:if> <font class="list"><a href="${pageContext.request.contextPath}/integration_list_home.htm?id=${newstype.id}&typeId=${typeId}">${newstype.name}</a></font></div>
		<c:if test="${not empty integration.name}">
			<!-- 主内容区 -->
			<table width="95%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" class="title_1">${integration.name}</td>
				</tr>
				<tr>
					<td align="left" class="date">上传：${integration.newsfrom}
					时间：${integration.startDay}</td>
				</tr>
				<tr>
					<td align="left"><embed
						src="${pageContext.request.contextPath}${integration.filePath}"
						quality="high" align="middle" autosize="true"
						allowScriptAccess="sameDomain" type="application/x-oleobject"
						height="600px" width="700px" /></td>
				</tr>
				<tr>
					<td class="date">[<a
						href="${pageContext.request.contextPath}/index.jsp">返回列表</a>][<a
						href="#" onclick="javascript:window.close();">关闭</a>]</td>
				</tr>
			</table>
		</c:if></td>
	</tr>
	<tr>
		<td height="50" align="center"><%@include file="index_blank.jsp"%>
		</td>
	</tr>
</table>
</center>

</body>
</html>