<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="fn" uri="/WEB-INF/tld/fn.tld"%>
<html>
<head>
  <title></title>
</head>
<body>
<!--列表-->
<div class="list_menu">首页-〉在线考试列表</div>
<div id="content_news">
<c:forEach items="${extractions }" var="extraction">
  <div class="list">
    <div class="list_content">[<a href="${pageContext.request.contextPath}/extraction_list.htm?subjectid=${extraction.subjectsTypeId}">${extraction.subjects.name}</a>]&nbsp;&nbsp;
    <a href="javascript:;" onclick="alert('在线测试只允许在校学生进行测试！');">
    	<c:if test="${fn:length(extraction.title)>30}">
			${fn:substring(extraction.title,0,30)}...
		</c:if>
		<c:if test="${fn:length(extraction.title)<=30 }">
			${extraction.title}
		</c:if>
    </a>
   </div>
    <div class="list_date">${fn:substring(extraction.buildtime,0,10)}</div>
  </div>
</c:forEach>

</div>

</body>
</html>