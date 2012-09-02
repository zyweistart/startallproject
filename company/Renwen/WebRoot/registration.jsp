<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld"%>
<html>
<head>
<script src="${pageContext.request.contextPath}/js/index/jquery.js"type=text/javascript></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/schman/registration.js"></script>
<link href="${pageContext.request.contextPath}/index/css/default.css"
	rel="stylesheet" type="text/css" />
<title>红帮文化</title>
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
			
             	<tr>
					<td>
					<div id="list_2">
					<div class="t">
				     <a href="${pageContext.request.contextPath}/extraction_practice.htm">测试库</a>
				    </div>
					</div>
					</td>
				</tr>
			
					<tr>
					<td>
					<div id="list_2">
					<div class="t">
				      <a href="${pageContext.request.contextPath}/feedback_viewmag.htm">用户留言</a>
				    </div>
					</div>
					</td>
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
			width="16" height="16" border="0" /></a>用户注册</div>
		
		<input type="hidden" name="strpath" id="strpath" value="${pageContext.request.contextPath}">
		  <s:form method="post" action="#" id="ec">
          <s:token />
			<!-- 主内容区 -->
			<table width="95%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" colspan="2" ><b><font color="red" size="5">新用户注册</font></b><br><hr color="red"></td>
				</tr>
				<tr align="center">
					<td align="right" valign="top">
					 用户名:
					</td>
					<td align="left">
					   <s:textfield name="username"  id="username" cssStyle="width:150px" /><Br>
                       <label id="usernamexx"></label>
					</td>
				</tr>
				<tr align="center">
				   <td align="right" valign="top">
				    密码:
				   </td> 
				   <td align="left">
				    <s:password name="password" id="password" cssStyle="width:150px" /><br>
                    <label id="passwordxx"></label>
				   </td>
				</tr>
				<tr align="center">
				   <td align="right" valign="top">
				    重置密码:
				   </td> 
				   <td align="left">
				    <s:password name="rpassword" id="rpassword" cssStyle="width:150px" /><br>
                    <label id="rpasswordxx"></label>
				   </td>
				</tr>
					<tr align="center">
				   <td align="right" valign="top">
				    姓名:
				   </td> 
				   <td align="left">
				   <s:textfield name="uname"  id="uname" cssStyle="width:150px" /><Br>
				   </td>
				</tr>
					<tr align="center">
				   <td align="right" valign="top">
				    班级:
				   </td> 
				   <td align="left">
				   <s:textfield name="userCls"  id="userCls" cssStyle="width:150px" /><Br>
				   </td>
				</tr>
					<tr align="center">
				   <td align="right" valign="top">
				    任课老师:
				   </td> 
				   <td align="left">
				   <s:textfield name="userTeacther"  id="userTeacther" cssStyle="width:150px" /><Br>
				   </td>
				</tr>
			    <tr align="center">
				   <td align="right" valign="top">
				    年份:
				   </td> 
				   <td align="left">
				   <s:textfield name="userYear"  id="userYear" cssStyle="width:150px" /><Br>
				   </td>
				</tr>
				  <tr align="center">
				   <td align="right" valign="top">
				    学期:
				   </td> 
				   <td align="left">
				   <s:radio id="userXq" name="userXq" list="#{1:'第一学期',2:'第二学期'}" value="1"></s:radio>
				   </td>
				</tr>
				  <tr align="center">
				   <td align="right" valign="top">
				    身份证:
				   </td> 
				   <td align="left">
				   <s:textfield name="identity"  id="identity" cssStyle="width:150px" /><Br>
				   </td>
				</tr>
				<tr align="center">
				   <td align="right">
				   &nbsp;
				   </td> 
				   <td align="left">
				    <input type="button" id="isok" value="提交" >
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
