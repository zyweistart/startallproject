<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
<%
            String path = request.getContextPath();
            String basePath = request.getScheme() + "://"
                    + request.getServerName() + ":" + request.getServerPort()
                    + path + "/";
%>
<html>
    <head>
        <base href="<%=basePath%>">
        <title>异常页面</title>
    </head>
    <body>
        <center>
            <s:property value="exception.message" />
            <s:actionerror/>
        </center>
    </body>
</html>
