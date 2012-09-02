<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%//超链接 %>
<c:set var="hyperlinkes" value="<%=webSitePageService.getHyperlinks()%>"></c:set>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="60">
								<img src="${pageContext.request.contextPath}/home/images/title_link.jpg" width="258" height="45" />
							</td>
						</tr>
						<tr>
							<td>
								<table width="90%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td align="center">
											<div style="width:209px;height:51px;background-image: url(${pageContext.request.contextPath}/home/images/link_1.jpg);text-align: center;line-height: 51px;font-size:12px;">
												<a href="http://weibo.com/u/2734252020" target="_blank" style="color:#fff">
													人文微博
												</a>
											</div>
										</td>
									</tr>
									<tr>
										<td align="center">
											<div style="width:209px;height:51px;background-image: url(${pageContext.request.contextPath}/home/images/link_2.jpg);text-align: center;line-height: 51px;font-size:12px;">
												<a href="${pageContext.request.contextPath}/feedback_viewmag.htm">
													师生互动
												</a>
											</div>
										</td>
									</tr>
									<tr>
										<td align="center">
											<div style="width:209px;height:51px;background-image: url(${pageContext.request.contextPath}/home/images/link_3.jpg);text-align: center;line-height: 51px;font-size:12px;">
												<a href="${pageContext.request.contextPath}/fbpots_list_home.htm">
													热点问答
												</a>
											</div>
										</td>
									</tr>
									<tr>
										<td align="center">
											<div style="width:209px;height:51px;background-image: url(${pageContext.request.contextPath}/home/images/link_4.jpg);text-align: center;line-height: 51px;font-size:12px;">
												<select name="urls" style="margin-top:17px;width: 150px" onchange="if(this.value!='#')window.open(this.value)">
														<option value="#">友情链接</option>
														<c:forEach items="${hyperlinkes }" var="h">
														<option value="${h.url }">${h.name }</option>
														</c:forEach>
												</select>
											</div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>