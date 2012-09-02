<%@page contentType="text/html" pageEncoding="UTF-8"%>
<table width="90%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td height="41"
			background="${pageContext.request.contextPath}/home/images/t_bg_1.jpg"><table
				width="100%" height="41" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="190" class="title_1">分类</td>
					<td align="right">&nbsp;</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td align="right"><table width="90%" border="0" cellspacing="0"
				cellpadding="0">
				<c:forEach var="mo" items="${menus}">
					<tr>
						<td class="list_1"><a href="${pageContext.request.contextPath}/homePage_list.htm?id=${mo.id}">${mo.name}</a></td>
					</tr>
				</c:forEach>
			</table></td>
	</tr>
</table>
<%@include file="/home/include/links.jsp" %>