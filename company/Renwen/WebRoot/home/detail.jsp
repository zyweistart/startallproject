<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/home/include/head.jsp"%>
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
								<div class="content_title">${information.title}</div>
								<br />
								<div class="content">${information.content}</div>
								<br />
								<c:if test="${not empty information.fileName}">
								<div class="list_page">附件下载:<a href="/information_download.htm?id=${information.id}"><font color="red" size="2">${information.fileName }</font></a></div>
								</c:if>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</td>
</tr>
<%@include file="/home/include/foot.jsp"%>