<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/home/include/head.jsp"%>
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
											人文学院<font class="title_menu">登陆</font>
										</td>
										<td align="center">&nbsp;</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td align="center">
								<br/><br/><br/><br/>
								<%@include file="/home/include/userlogin.jsp"%>
							
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</td>
</tr>
<%@include file="/home/include/foot.jsp"%>