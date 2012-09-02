<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld"%>

<!-- 以下为 列表形式内容显示 包括分页 -->
	<div id="content_news">

		    <c:forEach items="${integrations}" var="integration" varStatus="st">
		        <div class="list">
		           <div class="list_content"><a href="integration_content.htm?id=${integration.id }">${fn:substring(integration.name,0,40)}</a></div>
		           <div class="list_date">[${fn:substring(integration.startDay,5,10)}]</div>
		        </div>
		    </c:forEach>
		    
		</div>
		
		<div class="page">
		<s:url id="url_pre"
					value="integration_list_home.htm">
					<s:param name="id" value="newstype.id"></s:param>	
					 <c:if test="${typeId!=null }">
					 <s:param name="typeId" value="%{typeId}"></s:param>
					 </c:if>
					 <s:param name="typeA" value="newstype.category"></s:param>
					<s:param name="integration.pageNo" value="integration.pageNo-1"></s:param>
				</s:url> <s:url id="url_next" value="integration_list_home.htm">
					<s:param name="id" value="newstype.id"></s:param>
					 <c:if test="${typeId!=null }">
					 <s:param name="typeId" value="%{typeId}"></s:param>
					 </c:if>
					<s:param name="typeA" value="newstype.category"></s:param>
					<s:param name="integration.pageNo" value="integration.pageNo+1"></s:param>
				</s:url> <s:url id="url_first" value="integration_list_home.htm">
					<s:param name="id" value="newstype.id"></s:param>
					 <c:if test="${typeId!=null }">
					 <s:param name="typeId" value="%{typeId}"></s:param>
					 </c:if>
					<s:param name="typeA" value="newstype.category"></s:param>
					<s:param name="integration.pageNo" value="1"></s:param>
				</s:url> <s:url id="url_last" value="integration_list_home.htm">
					<s:param name="id" value="newstype.id"></s:param>
					 <c:if test="${typeId!=null}">
					 <s:param name="typeId" value="%{typeId}"></s:param>
					 </c:if>
					<s:param name="typeA" value="newstype.category"></s:param>
					<s:param name="integration.pageNo" value="integration.pageCount"></s:param>
				</s:url>
				第${integration.pageNo}页 [15条每页] <a href="${url_first }">[首 页]</a> <a
					href="${url_pre }">[上 页]</a> <a href="${url_next }">[下 页]</a> <a
					href="${url_last }">[尾 页]</a>
		</div>
<!-- 结束 -->		
	