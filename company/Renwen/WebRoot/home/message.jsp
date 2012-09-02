<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/home/include/head.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/office/mboard.js"></script>
<!--背景图片透明方法-->
<script src="${pageContext.request.contextPath}/index/js/iepng.js"
	type="text/javascript"></script>
<!--插入图片透明方法-->
<script type="text/javascript">
	EvPNG.fix('div, ul, img, li, input'); //EvPNG.fix('包含透明PNG图片的标签'); 多个标签之间用英文逗号隔开。
	//-->
</script>
<tr>
	<td height="200" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="30%" valign="top">
					<%@include file="/home/include/left.jsp"%>
				</td>
				<td valign="top">
					<table width="98%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="41" background="${pageContext.request.contextPath}/home/images/t_bg_1.jpg">
								<table width="100%" height="41" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td width="600" class="title_1">
											${menu.pname } <font class="title_menu"> <a href="${pageContext.request.contextPath}/homePage_list.htm?id=${menu.id}">${menu.name}</a> </font>
										</td>
										<td align="center">&nbsp;</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td align="center">
							
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
							                      <%@include file="/common/qtkindEditor.jsp" %>
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
					</table>
				</td>
			</tr>
		</table>
	</td>
</tr>
<%@include file="/home/include/foot.jsp"%>