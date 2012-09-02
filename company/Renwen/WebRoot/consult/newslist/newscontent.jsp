<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld"%>

<c:forEach items="${newstypes}" var="n">
  <a href="integration_list_home.htm?id=${n.id}&typeA=${n.category}&typeId=${typeId}">${n.name}</a><br>
     <c:forEach items="${n.children}" var="n1"> 
        &nbsp;&nbsp;&nbsp;&nbsp;<a href="integration_list_home.htm?id=${n1.id}&typeA=${n1.category}&typeId=${typeId}">${n1.name}</a><br>
     </c:forEach>
</c:forEach>



<c:if test="${not empty integration.id }">
<div align="center">
  <p><b>${integration.name}</b></p>
   <br>
   <p>${integration.content }</p>
</div>
</c:if>