<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld"%>
<%@ taglib prefix="fn" uri="/WEB-INF/tld/fn.tld"%>
<!DOCTYPE unspecified PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
   <script src="${pageContext.request.contextPath}/js/index/jquery.js"
	type=text/javascript></script>
 <script src="${pageContext.request.contextPath}/js/index/bbspost.js"  type=text/javascript></script>	
   <link rel="stylesheet" href='${pageContext.request.contextPath}/css/default/css.css' type="text/css">
                <link rel="stylesheet" href='${pageContext.request.contextPath}/css/default/extremecomponents.css' type="text/css">
<link href="${pageContext.request.contextPath}/index/css/default.css"
	rel="stylesheet" type="text/css" />
<title>人文学院-论坛</title>
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
	<%-- <tr>
		<td height="40" colspan="4" valign="bottom" bgcolor="#CFBBA0"><%@include
			file="index_menus.jsp"%></td>
	</tr> --%>
	<tr>
		<td width="180" rowspan="3" align="center" valign="top"
			bgcolor="#CCBEA4"><img
			src="${pageContext.request.contextPath}/index/images/title_1.png"
			width="150" height="235" />
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
           <!-- 二级目录 -->
					<tr>
					<td>
					<div id="list_2">
					<div class="t">
				    <a href="${pageContext.request.contextPath}/fbpots_list_home.htm">文化论坛</a>
				    </div>
					</div>
					</td>
				</tr>
					<%-- <tr>
					<td>
					<div id="list_2">
					<div class="t">
				      <a href="${pageContext.request.contextPath}/feedback_viewmag.htm">用户留言</a>
				    </div>
					</div>
					</td>
				</tr> --%>
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
			width="16" height="16" border="0" /></a><font class="list"><a href="${pageContext.request.contextPath}/integration_list_home.htm?id=21&typeId=21">互动平台</a></font></div>
		
		<div align="left"><b><font color="red" size="5">&nbsp;新帖子</font></b><br><hr color="red"></div>
		  
		  <s:form method="post" action="fbpots_viewSave.htm" id="ec">
          <s:token />
			<!-- 主内容区 -->
			<table width="95%" border="0" cellpadding="1" cellspacing="1" class="tableBgColor">
			
				<tr align="center">
					<td align="right" valign="top" class="leftTdBgColor">
					 标题:<font color="red" size="2">*</font>
					</td>
					<td align="left" class="rightTdBgColor">
					  <s:textfield   id="fptitle"  name="fbpots.fpTitle"   cssStyle="width:150px" ></s:textfield><Br>
                      <label id="fptitlexx"></label>
                      
					</td>
				</tr>
				<tr align="center" >
				   <td align="right" valign="top" class="leftTdBgColor">
				    类别选项:
				   </td> 
				   <td align="left" class="rightTdBgColor">
				    <s:select name="fbpots.forumId"   list="%{forums}" listKey="id"  listValue="forumName"  ></s:select><br>
				   </td>
				</tr>
				<tr align="center">
				  <td align="left" class="rightTdBgColor" colspan="2">
				  	<c:set var="editorName" scope="request" value="fbpots.fpContent" />
				  	<c:set var="editorHeight" scope="request" value="400px" />
                      <%@include file="../common/qtkindEditor.jsp" %>
				  </td>
				</tr>
				<tr align="center">
				   <td align="center" class="rightTdBgColor" colspan="2">
				    <input type="button" id="isok" value="提交" class="buttonStyle">
				   </td>
				</tr>
			</table>
		</s:form>
		</td>
	</tr>
	<tr>
		<td height="50" align="center"><%@include file="index_blank.jsp"%>
		</td>
	</tr>
</table>
</center>  
 </body>
</html>