<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
<table border="0" cellpadding="1" cellspacing="1" width="100%"
	class="tableBgColor">
	<tr>
		<td align="center" colspan="2" class="leftTdBgColor">
		<c:forEach items="${onLineOperators}" var="oo" varStatus="ot">
			<c:set var="count" value="${ot.count}"></c:set>
		</c:forEach>
		<font>在线人员列表!</font>&nbsp;&nbsp;&nbsp;&nbsp;总共:${count }人</td>
	</tr>
	<c:forEach items="${onLineOperators}" var="operator" varStatus="st">
		<c:set var="result" value="${(st.index+1) % 2}" />
		<c:if test="${result eq 1}">
			<tr>
		</c:if>
		<td width="50%" class="rightTdBgColor">
			&nbsp;${operator}
		</td>
	</c:forEach>
	</tr>
</table>
<%@ include file="/common/footer.jsp"%>