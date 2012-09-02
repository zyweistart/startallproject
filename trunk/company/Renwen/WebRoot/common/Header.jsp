<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="fn" uri="/WEB-INF/tld/fn.tld"%>
<%@ taglib prefix="FCK" uri="/WEB-INF/tld/FCKeditor.tld"%>
<%@ taglib prefix="ec" uri="/WEB-INF/tld/extremecomponents.tld" %>
<!--<link href="${pageContext.request.contextPath}/css/default.css" rel="stylesheet" type="text/css">  -->
<%
    response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0
    response.setDateHeader("Expires", -1);
%>
<html>
    <head>
        <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <fmt:setBundle basename="ApplicationResources" />
        <script type="text/javascript" src="${pageContext.request.contextPath}/common/jquery/jquery-1.3.2.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/common/jquery/jquery.form.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/common/jquery/livequery.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/common/jquery/uploadPreview.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/common/global/common.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/common/global/Verifies.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/common/man_xtree/man_xtree.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/common/calendar/WdatePicker.js"></script>
        <c:choose>
            <c:when test="${empty currentUser.cssStyle}">
                <link rel="stylesheet" href='${pageContext.request.contextPath}/css/default/css.css' type="text/css">
                <link rel="stylesheet" href='${pageContext.request.contextPath}/css/default/extremecomponents.css' type="text/css">
            </c:when>
            <c:otherwise>
                <link rel="stylesheet" href='${pageContext.request.contextPath}/css/default/css.css' type="text/css">
                <link rel="stylesheet" href='${pageContext.request.contextPath}/css/default/extremecomponents.css' type="text/css">
            </c:otherwise>
        </c:choose>
        <script type="text/javascript">
			<!--
			window.status = "宁波海腾计算机有限公司 © 2008-2010版权所有 Mail:hightern@hightern.com Tel:0574-55111360  Fax:0574-56704211 地址:宁波市兴宁路47号宁波大学商务中心3楼";
			//-->
		</script>
    </head>
    <body class="fileTableBg1" leftmargin="0" rightmargin="0" topmargin="0" bottommargin="0" style="width: 100%;overflow: auto;">
        <table width='100%' height="100%" cellspacing="0" cellpadding="0" border="0">
            <tr valign='top'>
                <td align="left" style="padding-left:3px;padding-right:3px;padding-top:3px;padding-bottom:0px;">