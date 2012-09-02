<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/home/include/head.jsp"%>
<%//通知公告 %>
<c:set var="announcements" value="<%=webSitePageService.findInformationByMenuId(24l)%>"></c:set>
<%//竞赛获取 %>
<c:set var="js" value="<%=webSitePageService.findInformationByMenuId(23l)%>"></c:set>
<%//人文乐园 %>
<c:set var="paradises" value="<%=webSitePageService.findInformationBySingleMenuId(28l,false)%>"></c:set>
<%//特色培训 %>
<c:set var="characteristics" value="<%=webSitePageService.findInformationBySingleMenuId(66l,false)%>"></c:set>
<%//主讲教师 %>
<c:set var="teacher" value="<%=webSitePageService.findInformationBySingleMenuId(29l,true)%>"></c:set>
<%//图文 %>
<c:set var="photos" value="<%=webSitePageService.findPhotos()%>"></c:set>
<tr>
	<td height="430" style=" background:url(${pageContext.request.contextPath}/home/images/banner.jpg); background-repeat:no-repeat;">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="150">&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td width="100" height="280" align="left" valign="top">
					<img src="${pageContext.request.contextPath}/home/images/t_1.png" width="63" height="261" />
				</td>
				<td width="400" align="center" valign="top">
					<table width="90%" border="0" cellspacing="0" cellpadding="0">
						<c:forEach var="information" items="${announcements}">
							<tr>
								<td class="list_4">
									<a href="${pageContext.request.contextPath}/homePage_detail.htm?id=${information.id}" title="${information.title}">
										${fn:substring(information.title, 0, 15)}...
									</a>
								</td>
							</tr>
						</c:forEach>
					</table>
				</td>
				<td>&nbsp;</td>
			</tr>
		</table>
	</td>
</tr>
<tr>
	<td height="200" valign="top">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="240" align="center" valign="top">
					<table width="95%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="41">
							<script type="text/javascript">
								var pics="";
								var links="";
								var texts="";
								<c:forEach items="${photos}" var="p" varStatus="status">
									var imgUrl${status.index+1} = "${pageContext.request.contextPath}${p.thumbnail}";
									var imgtext${status.index+1} = "${p.title}"
									var imgLink${status.index+1} = escape("${pageContext.request.contextPath}/homePage_detail.htm?id=${p.id}");
									pics+=imgUrl${status.index+1}+"|";
									texts+=imgtext${status.index+1}+"|";
									links+=imgLink${status.index+1} +"|";
								</c:forEach>
								pics=pics.substring(0,pics.length-1);
								links=links.substring(0,links.length-1);
								texts=texts.substring(0,texts.length-1);
								var focus_width = 240
								var focus_height = 160
								var text_height = 18
								var swf_height = focus_height + text_height
								document.write('<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0" width="'+ focus_width +'" height="'+ swf_height +'">');
								document.write('<param name="allowScriptAccess" value="sameDomain"><param name="movie" value="${pageContext.request.contextPath}/home/images/focus2.swf"><param name="quality" value="high"><param name="bgcolor" value="#F0F0F0">');
								document.write('<param name="menu" value="false"><param name=wmode value="opaque">');
								document.write('<param name="FlashVars" value="pics='+pics+'&links='+links+'&texts='+texts+'&borderwidth='+focus_width+'&borderheight='+focus_height+'&textheight='+text_height+'">');
								document.write('</object>');
							</script>
							</td>
						</tr>
					</table>
					<%@include file="/home/include/links.jsp" %>
				</td>
				<td align="center" valign="top">
					<table width="95%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="41" background="${pageContext.request.contextPath}/home/images/t_bg_1.jpg">
								<table width="100%" height="41" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td width="190">
											<img src="${pageContext.request.contextPath}/home/images/t_2.png" width="223" height="41" />
										</td>
										<td align="right">
											<a href="${pageContext.request.contextPath}/homePage_list.htm?id=23">
												<img src="${pageContext.request.contextPath}/home/images/more_1.jpg" width="78" height="23"  border="0" />
											</a>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td align="right">
								<table width="95%" border="0" cellspacing="0" cellpadding="0">
									<c:forEach var="information" items="${js}">
										<tr>
											<td width="80%" class="list_2">
												<a href="${pageContext.request.contextPath}/homePage_detail.htm?id=${information.id}" title="${information.title}">
													${fn:substring(information.title, 0, 20)}...
												</a>
											</td>
											<td width="20%" class="list_data">${information.createTime}</td>
										</tr>
									</c:forEach>
								</table>
							</td>
						</tr>
					</table>
					<table width="95%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="41" background="${pageContext.request.contextPath}/home/images/t_bg_1.jpg">
								<table width="100%" height="41" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td width="190">
											<img src="${pageContext.request.contextPath}/home/images/t_5.png" width="223" height="41" />
										</td>
										<td align="right">
											<a href="${pageContext.request.contextPath}/homePage_list.htm?id=29">
												<img src="${pageContext.request.contextPath}/home/images/more_1.jpg" width="78" height="23"  border="0"/>
											</a>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td height="193" align="right" style="background:url(${pageContext.request.contextPath}/home/images/bg_1.jpg); background-repeat:no-repeat;">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="270" align="right">
											<c:if test="${not empty teacher }">
											<a href="${pageContext.request.contextPath}/homePage_detail.htm?id=${teacher.id }">
												<img src="${pageContext.request.contextPath}/imgs/${teacher.scalepic }" width="215" height="155"  border="0"/>
											</a>
											</c:if>
										</td>
										<td>&nbsp;</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
				<td width="260" valign="top">
					<table width="98%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="41" background="${pageContext.request.contextPath}/home/images/t_bg_1.jpg">
								<table width="100%" height="41" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td width="190">
											<img src="${pageContext.request.contextPath}/home/images/t_3.png" width="223" height="41" />
										</td>
										<td align="right">&nbsp;</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr align="center" valign="middle">
							<td height="200" style="background:url(${pageContext.request.contextPath}/home/images/bg_2.jpg); background-repeat:no-repeat;">
								<table width="90%" border="0" cellspacing="0" cellpadding="0">
					                <tr>
					                  <td class="content_2">
						                  	<a href="${pageContext.request.contextPath}/homePage_detail.htm?id=${paradises.id }">
						                  		<span msg="a1" style="margin: 0px;padding: 0px;"></span></a>
								        	<div msg="a1" style="display:none">
								        		${paradises.content}
								        	</div>
						                  	[<a href="${pageContext.request.contextPath}/homePage_list.htm?id=28">更多</a>]
					                  </td>
					                </tr>
					            </table>
							</td>
						</tr>
					</table>
					<table width="98%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="41" background="${pageContext.request.contextPath}/home/images/t_bg_1.jpg">
								<table width="100%" height="41" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td width="190">
											<img src="${pageContext.request.contextPath}/home/images/t_4.png" width="223" height="41" />
										</td>
										<td align="right">&nbsp;</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td height="235" alignt="center" style="background:url(${pageContext.request.contextPath}/home/images/bg_3.jpg); background-repeat:no-repeat">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
					                  <td class="content_2" align="center">
					                  		<a href="${pageContext.request.contextPath}/homePage_detail.htm?id=${characteristics.id }">
					                  		<span msg="b1" style="margin: 0px;padding: 0px;"></span></a>
								        	<div msg="b1" style="display:none">
								        		${characteristics.content}
								        	</div>
					                  		[<a href="${pageContext.request.contextPath}/homePage_list.htm?id=66">更多</a>]
					                  </td>
					                </tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</td>
</tr>
<%@include file="/home/include/foot.jsp"%>
<script type="text/javascript">
String.prototype.Trim = function(){
	return this.replace(/(^\s*)|(\s*$)/g, ""); 
}
$(function(){
	$("div").each(function(){
		var v=$(this);
		var msg=v.attr("msg");
		if(msg){
			$("span").each(function(){
				var m=$(this).attr("msg");
				if(m===msg){
					var s=v.text();
					var c="";
					for(var i=0;i<s.length;i++){
						b=s.charCodeAt(i);
						if(b>255){
							c+=s.charAt(i);
						}
					}
					if(c.length>65){
						$(this).text(c.substr(0, 65).Trim()+"...");
					}else{
						$(this).text(c.substr(0).Trim());
					}
				}
			});
		}
	});
});
</script>
  