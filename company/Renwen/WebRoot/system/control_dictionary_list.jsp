<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>字典查询!</title>
  </head>
   <frameset id="O_frame" framespacing="0" border="0" frameborder="0" cols="200,5,*">
        <frame id="treeFrame" name="treeFrame" frameborder="0" scrolling="auto" noresize src="${pageContext.request.contextPath}/dictionary_treeList.htm">
        <frame id="spaceFrame" name="spaceFrame" frameborder="0" scrolling="no" noresize src="${pageContext.request.contextPath}/common/arrow.jsp">
        <frame id="operatorFrame" name="operatorFrame" frameborder="0" scrolling="yes" noresize src="${pageContext.request.contextPath}/dictionary_list.htm" target="_self">
         <noframes>
        <body topmargin="0" leftmargin="0">
            <p>
                 此网页使用了框架，但您的浏览器不支持框架。
            </p>
        </body>
     </noframes>
    </frameset>
</html>
