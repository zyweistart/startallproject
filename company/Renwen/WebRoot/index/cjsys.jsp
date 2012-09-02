<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld"%>
<%@page import="com.hightern.ckman.util.GjzInfo"%>
<%@page
	import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%
 GjzInfo gjzInfo=GjzInfo.getFromApplicationContext(WebApplicationContextUtils.getWebApplicationContext(getServletContext())); 
%>
<c:set var="gjz" value="<%=gjzInfo.getgjz()%>" ></c:set>
<html>
<head>
<TITLE>${gjz.name}</TITLE>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
<meta id="metaKeywords"  name="keywords"  content="&quot;${gjz.title}.&quot;">
<link href="${pageContext.request.contextPath}/css/index/Style_in.css"
	rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/index/style-min.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/index/jquery.js"></script>

<META content="MSHTML 6.00.2900.3698" name=GENERATOR>
</head>

<body>

<div align="center">

<DIV id=toper>
<DIV class=topimg id=l
	style="BACKGROUND-IMAGE: url(${pageContext.request.contextPath}/images/index/logo.jpg)">
<DIV class=toplxwo id=Div1><A
	href="http://www.sinobpo.com/Contact">联系我们</A></DIV>
</DIV>
</DIV>

<%@include file="../index_menu.jsp"%>



<DIV class=bgImg
	style="BACKGROUND-IMAGE: url(${pageContext.request.contextPath}/images/index/about.jpg)"></DIV>
<DIV class=bgSpare></DIV>
<DIV class=content>
<TABLE style="WIDTH: 100%">
	<TBODY>
		<TR>
			<TD class=TableLeft>
			<DIV class=left><IMG alt=""
				src="${pageContext.request.contextPath}/images/index/jsys.jpg">
			<DIV id=er_muen_zi_tit style="PADDING-TOP: 15px"><IMG alt=""
				src="${pageContext.request.contextPath}/images/index/contact.jpg">
			</DIV>
			</DIV>
			</TD>
			<TD vAlign=top>
			<DIV class=right>
			<DIV class=nav><A
				href="${pageContext.request.contextPath}/index.jsp">首页</A>－<A
				href="#">技术优势</A></DIV>
			<DIV class=con11>
			<UL class=head>
				<LI id=li>C++ 技术优势</LI>
			</UL>
			<DIV class=con15><SPAN id=lblContent> <!-- 内容区 -->
丰富的大型项目开发经验铸就软件的一流品质;以人为本，良好的用户体验更贴近用户需求;精益求精的开发流程使软件运行更加稳定流畅;专业的本地化开发贴合用户需求和使用习惯;良好的软件兼容性使得软件应用更广泛;高度的系统安全性保证数据的安全;模块化开发，在线升级服务提升系统可维护性。

			</SPAN></DIV>
			<DIV style="HEIGHT: 30px"></DIV>

			<!--Leave end--></DIV>
			</DIV>
			</TD>
		</TR>
	</TBODY>
</TABLE>
</DIV>


<DIV class=foot>
<DIV><IMG style="TEXT-ALIGN: center"
	src="${pageContext.request.contextPath}/images/index/bottom.gif"></DIV>
<DIV><IMG style="VERTICAL-ALIGN: text-top"
	src="${pageContext.request.contextPath}/images/index/phone.jpg">
<SPAN>客服电话：0574-55111360 &nbsp;<A href="#">网站地图</A> | <A
	href="${pageContext.request.contextPath}/index/qyContact.jsp">联系方式</A>
| <A href="#">隐私保护</A> | <A href="#">免责声明</A> | <A href="#">友情链接</A> | <A
	href="#">下载中心</A> |<A href="#" target=_blank></A></SPAN> <IMG
	style="VERTICAL-ALIGN: text-top"
	src="${pageContext.request.contextPath}/images/index/police.gif">
</DIV>
</DIV>
<script type="text/javascript">
	var _gaq = _gaq || [];
	_gaq.push( [ '_setAccount', 'UA-16539335-1' ]);
	_gaq.push( [ '_setDomainName', 'none' ]);
	_gaq.push( [ '_setAllowLinker', true ]);
	_gaq.push( [ '_trackPageview' ]);
	(function() {
		var ga = document.createElement('script');
		ga.type = 'text/javascript';
		ga.async = true;
		ga.src = ('https:' == document.location.protocol ? ' https://ssl'
				: ' http://www')
				+ '.google-analytics.com/ga.js';
		var s = document.getElementsByTagName('script')[0];
		s.parentNode.insertBefore(ga, s);
</script> <SCRIPT src="${pageContext.request.contextPath}/js/index/tongji.js"
	type=text/javascript></SCRIPT>
<NOSCRIPT></NOSCRIPT>

</div>
</body>
</html>