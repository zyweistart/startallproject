<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld"%>
<html>
<head>
<script src="${pageContext.request.contextPath}/js/index/jquery.js"type=text/javascript></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/office/mboard.js"></script>
  <link rel="stylesheet" href='${pageContext.request.contextPath}/css/default/css.css' type="text/css">
                <link rel="stylesheet" href='${pageContext.request.contextPath}/css/default/extremecomponents.css' type="text/css">
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
        <tr>
          <td align="center">
          <%@include file="userLogin.jsp" %>
          </td>
        </tr>
		<tr>
          <td>&nbsp;</td>
        </tr>
		<tr>
		  <td><img src="${pageContext.request.contextPath}/index/images/bbs_p_1.png" width="170" height="350" /></td>
	    </tr>
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
			
			<div align="left"><b><font color="red" size="5">&nbsp;用户留言</font></b><br><hr color="red"></div>
		  <s:form method="post" action="feedback_viewsave.htm" id="ec">
          <s:token />
			<!-- 主内容区 -->
			<table width="95%" border="0" cellpadding="1" cellspacing="1" class="tableBgColor">
				<tr align="center">
					<td align="right" valign="top" class="leftTdBgColor">
					 姓名:<font color="red" size="2">*</font>
					</td>
					<td align="left" class="rightTdBgColor">
					   <s:textfield name="name"  id="name" cssStyle="width:150px" /><Br>
                      
					</td>
				</tr>
				<tr align="center">
				   <td align="right" valign="top" class="leftTdBgColor">
				    联系方式:
				   </td> 
				   <td align="left" class="rightTdBgColor">
				    <s:textfield name="contact" id="contact" cssStyle="width:150px" /><br>
                    
				   </td>
				</tr>
				<tr align="center">
				   <td align="right" valign="top" class="leftTdBgColor">
				    电子邮件:
				   </td> 
				   <td align="left" class="rightTdBgColor">
				    <s:textfield name="email" id="email" cssStyle="width:150px" /><br>
				   </td>
				</tr>
				<tr align="center">
				  <td align="left" colspan="2" class="rightTdBgColor">
				  	<c:set var="editorName" scope="request" value="content" />
				  	<c:set var="editorHeight" scope="request" value="400px" />
                      <%@include file="../common/qtkindEditor.jsp" %>
				  </td>
				</tr>
				<tr align="center">
				   <td align="left" colspan="2" class="rightTdBgColor">
				   <hr>
				    <input type="button" id="isok" value="提交" class="buttonStyle" style="width:100">
				    <hr>
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
