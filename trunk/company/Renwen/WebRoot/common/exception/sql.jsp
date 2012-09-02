<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
<html>
    <head>
        <title>sql异常页面</title>
    </head>
    <body>
        <center>
            <s:property value="exception.message" />
            <s:actionerror/>
        </center>
    </body>
</html>
