<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="fn" uri="/WEB-INF/tld/fn.tld"%>
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
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/system/authority.js"></script>
        <link rel="stylesheet" href='${pageContext.request.contextPath}/css/default/css.css' type="text/css">
	</head>
    <body class="fileTableBg1" leftmargin="0" rightmargin="0" topmargin="0" bottommargin="0" style="width: 100%;overflow: auto;">
        <table width='1200px' height="100%" cellspacing="0" cellpadding="0" border="0">
            <tr valign='top'>
                <td align="left" style="padding-left:3px;padding-right:3px;padding-top:3px;padding-bottom:0px;">
					<s:form action="authority_save" id="authForm">
						<table border="0" width="100%" class="wbs_table" cellpadding="0" cellspacing="0">
							<tr>
								<td class="wbs_td" colspan="12" align="left">
									<table border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td class="gantt_tdSeparate"></td>
											<td>
												<input type="submit" class="buttonStyle" value="修改权限" />
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td width="15%" style="padding-left:10px" class="wbs_header">
									<SELECT name="typeCode" id="typeCode">
										<c:choose>
											<c:when test="${not empty postss}">
												<option value="posts" selected="selected">按职务</option>
												<option value="roles">按角色</option>
												<option value="operator">按个人</option>
											</c:when>
											<c:when test="${not empty roles}">
												<option value="posts">按职务</option>
												<option value="roles" selected="selected">按角色</option>
												<option value="operator">按个人</option>
											</c:when>
											<c:otherwise>
												<option value="posts">按职务</option>
												<option value="roles">按角色</option>
												<option value="operator" selected="selected">按个人</option>
											</c:otherwise>
										</c:choose>
									</SELECT>
								</td>
								<c:set var="count" value="${fn:length(models)}"></c:set>
								<c:forEach var="top" items="${models}">
									<td align="center" class="wbs_header"> 
										${top.name} <input type="checkbox" name="selectedIds" id="box${top.id }" value="${top.id }" onclick="checkAll(this,'id${top.id}')" title="全选"/>
									</td>
								</c:forEach>
							</tr>
							<tr>
								<td valign="top"  width="15%" class="wbs_td" style="padding-left:5px">
									<div style='height:600px;overflow-x:no;overflow-y:auto;word-break: break-all;'>
										<table border="0" cellpadding="0" cellspacing="0" id="typeList">
											<c:forEach items="${postss}" var="posts">
												<tr>
													<td height="25" style="padding-left: 6px">
														<input type="radio" name="typeName" value="${posts.postCode }" onclick="selectedRadio('${posts.postCode }');"> ${posts.postName }
													</td>
												</tr>
											</c:forEach>
										</table>
										<div>
								</td>
								<td id="id_module" valign="top" colspan="${count }">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
								<c:forEach var="top" items="${models}">
									<td id="id${top.id}" valign="top" width="${100 / count }%" class="wbs_td" style="padding-left:5px">
										<div style='height:600px;overflow-x:no;overflow-y:auto;word-break: break-all;'>
											<table border="0" cellpadding="0" cellspacing="0">
												<c:forEach var="second" items="${top.children}">
													<tr>
														<td height="20">
															<a onclick="display('${second.id}')"><img src="${pageContext.request.contextPath}/images/ttree_exp1m.gif"
																align="absmiddle" class="hand" border="0" id="image${second.id}" /></a>
																<input type="checkbox" name="selectedIds" id="box${second.id }" value="${second.id }" onclick="checkAll(this,'id${second.id}')"/>
															${second.name}
														</td>
													</tr>
													<tr>
													<td id="id${second.id}" valign="top">
														<table border="0" cellpadding="0" cellspacing="0">
															<c:forEach var="third" items="${second.children}">
																<tr>
																	<td width="10" height="20">
																		&nbsp;
																	</td>
																	<td width="300">
																		<a onclick="display('${third.id}')">
																			<img src="${pageContext.request.contextPath}/images/ttree_exp1m.gif" align="absmiddle" class="hand" border="0" id="image${third.id}"/>
																		</a>
																		<c:set var="thirdNumber" value="0"/>
																		<c:forEach var="thirdName" items="${third.children}" varStatus="status">
																			<c:set var="thirdNumber" value="${status.count }"/>
																		</c:forEach>
																		<c:choose>
																			<c:when test="${thirdNumber>1}">
																				<input type="checkbox" id="box${third.id }" name="selectedIds" onclick="checkAll(this,'id${third.id}')"/>
																			</c:when>
																			<c:otherwise>
																				<input type="checkbox" id="box${third.id }" name="selectedIds" value="${third.id }">
																			</c:otherwise>
																		</c:choose>
																		${third.name}
																	</td>
																</tr>
																<tr>
																	<td width="300" id="id${third.id}" valign="top" colspan="2">
																		<table border="0" cellpadding="0" cellspacing="0">
																			<c:forEach var="fourth" items="${third.children}">
																				<tr>
																					<td width="40" height="20">
																						&nbsp;
																					</td>
																					<td width="260">
																						<input type="checkbox" id="box${fourth.id }" name="selectedIds" value="${fourth.id }">
																						${fourth.name}
																					</td>
																				</tr>
																			</c:forEach>
																		</table>
																	</td>
																</tr>
															</c:forEach>
														</table>
													</td>
												</tr>
												</c:forEach>
											</table>
										</div>
									</td>
								</c:forEach>
								</tr>
								</table>
							   </td>
							</tr>
						</table>
					</s:form>
					<%@ include file="/common/footer.jsp"%>