<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/home/include/head.jsp"%>
  
  <tr>
    <td height="200" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
      <td width="30%" valign="top">
					<%@include file="/home/include/links.jsp"%>
				</td>
        <td valign="top">
		<div style="width:98%;">
      <div id="published"><a href="${pageContext.request.contextPath}/fbpots_view.htm"><img src="${pageContext.request.contextPath}/home/images/published.gif" border="0"/></a></div>
      <div id="page">
		<s:url id="url_pre" value="fbpots_list_home.htm">
			<s:param name="fbpots.pageNo" value="fbpots.pageNo-1"></s:param>
		</s:url> 
		<s:url id="url_next" value="fbpots_list_home.htm">
			<s:param name="fbpots.pageNo" value="fbpots.pageNo+1"></s:param>
		</s:url> 
		<s:url id="url_first" value="fbpots_list_home.htm">
			<s:param name="fbpots.pageNo" value="1"></s:param>
		</s:url> 
              <s:url id="url_last" value="fbpots_list_home.htm">
			<s:param name="fbpots.pageNo" value="fbpots.pageCount"></s:param>
		</s:url>
        <div class="page_1">第${fbpots.pageNo}页/共${fbpots.pageCount}页</div>
        <div class="page_1">${fbpots.pageSize}条每页</div>
        <div class="page_1"><a href="${url_first}">首页</a></div>
        <div class="page_1"><a href="${url_pre}">上一页</a></div>
        <div class="page_1"><a href="${url_next}">下一页</a></div>
        <div class="page_1"><a href="${url_last}">末页</a></div>
      </div>
      <table border="0" cellpadding="0" cellspacing="0" class="datalist" >
        <tr>
          <th width="55%">主题</th>
          <th width="16%">作者/时间</th>
          <th width="13%">回复/查看</th>
          <th width="16%">最后发表</th>
        </tr>
        <c:forEach items="${fbpotss}" var="n" varStatus="st">
          <!-- 奇数行 -->
           <c:if test="${st.count%2 eq 1}">
            <tr>
          <td><div class="title"><a href="${pageContext.request.contextPath}/fbreply_viewContent.htm?fbpostsId=${n.id}&f=1">${n.fpTitle}</a></div></td>
          <td>
            ${n.usersName}</td>
          <td>${n.pviews}</td>
          <td>
            <div class="date">${fn:substring(n.startDay,0,10)} </div></td>
           </tr>
           </c:if>
            <!-- 偶数行 -->
            <c:if test="${st.count%2 eq 0}">
          <tr class="altrow">
          <td><div class="title_pic"><a href="${pageContext.request.contextPath}/fbreply_viewContent.htm?fbpostsId=${n.id}&f=1">${n.fpTitle}</a></div></td>
          <td>
            ${n.usersName}</td>
          <td>${n.pviews}</td>
          <td>
            <div class="date">${fn:substring(n.startDay,0,10)}</div></td>
        </tr>
           </c:if>
          </c:forEach>
      </table>
	  <div id="published"><a href="${pageContext.request.contextPath}/fbpots_view.htm"><img src="${pageContext.request.contextPath}/home/images/published.gif" border="0"/></a></div>
      <div id="page">
        <div class="page_1">第${fbpots.pageNo}页/共${fbpots.pageCount}页</div>
        <div class="page_1">${fbpots.pageSize}条每页</div>
        <div class="page_1"><a href="${url_first}">首页</a></div>
        <div class="page_1"><a href="${url_pre}">上一页</a></div>
        <div class="page_1"><a href="${url_next}">下一页</a></div>
        <div class="page_1"><a href="${url_last}">末页</a></div>
      </div>
    </div>
		
		</td>
      </tr>
      
    </table></td>
  </tr>
<%@include file="/home/include/foot.jsp"%>
