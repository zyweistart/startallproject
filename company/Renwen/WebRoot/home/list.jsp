<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/home/include/head.jsp"%>
<tr>
	<td height="200" valign="top">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="30%" valign="top">
					<%@include file="/home/include/left.jsp"%>
				</td>
				<td valign="top">
				<c:choose>
					<c:when test="${not empty informations }">
						<table width="98%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td height="41" background="${pageContext.request.contextPath}/home/images/t_bg_1.jpg">
										<table width="100%" height="41" border="0" cellpadding="0" cellspacing="0">
											<tr>
												<td width="600" class="title_1">
													${menu.pname}<font class="title_menu"><a href="${pageContext.request.contextPath}/homePage_list.htm?id=${menu.id}">${menu.name }</a></font>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td align="center">
										<table width="95%" border="0" cellspacing="0" cellpadding="0">
											<c:forEach var="information" items="${informations}">
												<tr>
													<td width="85%" class="list_3">
														<a href="${pageContext.request.contextPath}/homePage_detail.htm?id=${information.id}" title="${information.title}">
															${fn:substring(information.title, 0, 40)}...
														</a>
													</td>
													<td width="15%" align="center" class="list_data">${information.createTime}</td>
												</tr>
											</c:forEach>
										</table>
									</td>
								</tr>
								<tr>
									<td align="center" class="list_page">
										<form action="${pageContext.request.contextPath}/homePage_list.htm?id=${menu.id}"  method="post" id="adminForm">
											<%@include file="/common/page.jsp" %>
										</form>
									</td>
								</tr>
							</table>
						</c:when>
						<c:otherwise>
						
							<table width="98%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td height="41" background="${pageContext.request.contextPath}/home/images/t_bg_1.jpg">
										<table width="100%" height="41" border="0" cellpadding="0" cellspacing="0">
											<tr>
												<td width="600" class="title_1">
													${menu.pname}<font class="title_menu"><a href="${pageContext.request.contextPath}/homePage_list.htm?id=${menu.id}">${menu.name }</a></font>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<a href="${pageContext.request.contextPath}/homePage_detail.htm?id=${information.id}"><span msg="a${information.id}" style="margin: 2px;padding: 2px;"></span></a>
							        	<div msg="a${information.id}" style="display:none">
							        		${information.content}
							        	</div>
									</td>
								</tr>
							</table>
						</c:otherwise>
					</c:choose>
					<%@include file="/home/include/body.jsp" %>
				</td>
			</tr>
		</table>
	</td>
</tr>
<%@include file="/home/include/foot.jsp"%>