<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.hightern.website.model.Information" %>
<%@include file="/home/include/head.jsp"%>
  
  <tr>
    <td height="200" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="30%" valign="top">
        	<%@include file="/home/include/left.jsp"%>
        </td>
        <td valign="top">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
        	<%
				ArrayList arr = (ArrayList) request.getAttribute("informations");
				int col=3;
				int rem= arr.size() % col;
				int row=arr.size() / col;
				int rows= arr.size()/3+(rem>0?1:0);
				for(int i=0;i<row;i++){
					%><tr><%
					for(int j=0;j<col;j++){
						Information information =(Information)arr.get(col*i+j);
						%><td width="33%" height="170" class="list_pic">
						<img src="${pageContext.request.contextPath}/imgs/<%=information.getScalepic() %>" width="215" height="155" />
					</td><%
					}
					%></tr><tr><%
					for(int j=0;j<col;j++){
						Information information =(Information)arr.get(col*i+j);
						%><td height="20" align="center" class="td_1">
						<a href="${pageContext.request.contextPath}/homePage_detail.htm?id=<%=information.getId() %>"><%=information.getTitle() %></a>
					</td><%
					}
					%></tr><%
				}
				if(rem>0){
				%>
				<tr><%		
				for(int j=0;j<rem;j++){
					Information information =(Information)arr.get(row*col+j);
					if(rem==1){
						%><td height="20" align="center" class="td_1" column="3"><%
					}else if(rem==2){
						if(j==1){
							%><td height="20" align="center" class="td_1"><%
						}else{
							%><td height="20" align="center" class="td_1" column="2"><%
						}
					}
					%>
						<img src="${pageContext.request.contextPath}/imgs/<%=information.getScalepic() %>" width="215" height="155" />
					</td><%
					}%>
				</tr>
				<tr><%		
				for(int j=0;j<rem;j++){
					Information information =(Information)arr.get(row*col+j);
					if(rem==1){
						%><td height="20" align="center" class="td_1" column="3"><%
					}else if(rem==2){
						if(j==1){
							%><td height="20" align="center" class="td_1"><%
						}else{
							%><td height="20" align="center" class="td_1" column="2"><%
						}
					}
					%>
					<a href="${pageContext.request.contextPath}/homePage_detail.htm?id=<%=information.getId() %>"><%=information.getTitle() %></a>
					</td><%
					}}%>
				</tr>
          <tr>
            <td height="30" colspan="3" class="list_page">
				<c:if test="${not empty informations }">
					<form action="${pageContext.request.contextPath}/homePage_list.htm?id=${menu.id}"  method="post" id="adminForm">
						<%@include file="/common/page.jsp" %>
					</form>
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