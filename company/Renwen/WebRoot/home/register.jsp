<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/home/include/head.jsp"%>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/schman/registration.js"></script>
<!--背景图片透明方法-->
<script src="${pageContext.request.contextPath}/index/js/iepng.js"
	type="text/javascript"></script>
<!--插入图片透明方法-->
<script type="text/javascript">
	EvPNG.fix('div, ul, img, li, input'); //EvPNG.fix('包含透明PNG图片的标签'); 多个标签之间用英文逗号隔开。
	//-->
</script>
<style type="text/css">
<!--
form td{
height:30px;
}
-->
</style>

<tr>
	<td height="200" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="30%" valign="top">
					<%@include file="/home/include/links.jsp"%>
				</td>
				<td valign="top">
					<table width="98%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="41" background="${pageContext.request.contextPath}/home/images/t_bg_1.jpg">
								<table width="100%" height="41" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td width="600" class="title_1">
											人文学院<font class="title_menu">用户注册</font>
										</td>
										<td align="center">&nbsp;</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td align="center">
								<br/><br/>
								  <input type="hidden" name="strpath" id="strpath" value="${pageContext.request.contextPath}">
								  <s:form method="post" action="#" id="ec">
						          	<s:token />
									<!-- 主内容区 -->
									<table width="95%" border="0" cellspacing="0" cellpadding="0">
										<tr align="center">
											<td align="right" valign="top">
											 用户名:
											</td>
											<td align="left">
											   <s:textfield name="username"  id="username" cssStyle="width:150px" /><br>
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
					</table>
				</td>
			</tr>
		</table>
	</td>
</tr>
<%@include file="/home/include/foot.jsp"%>