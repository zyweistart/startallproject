<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld"%>
<%@ taglib prefix="fn" uri="/WEB-INF/tld/fn.tld"%>
<!DOCTYPE unspecified PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
   <title>人文学院-论坛</title>
   <link href="${pageContext.request.contextPath}/index/css/default.css" rel="stylesheet" type="text/css" />
   <link href="${pageContext.request.contextPath}/index/css/bbs.css" rel="stylesheet" type="text/css">
   <script src="${pageContext.request.contextPath}/js/index/jquery.js"
	type=text/javascript></script>
	<!--背景图片透明方法-->
   <script src="${pageContext.request.contextPath}/index/js/iepng.js" type="text/javascript"></script>
  <!--插入图片透明方法-->
  <script type="text/javascript">
  <!--
   EvPNG.fix('div, ul, img, li, input');  //EvPNG.fix('包含透明PNG图片的标签'); 多个标签之间用英文逗号隔开。
   //-->
   </script>
	
 </head>
 <body>
 <center>
   <table width="900" border="2" cellpadding="0" cellspacing="0" bordercolor="#FFFFFF" background="${pageContext.request.contextPath}/index/images/bg2.gif" bgcolor="#FCF0D8">
  <%-- <tr>
     <td height="40" colspan="4" valign="bottom" bgcolor="#CFBBA0" >
     <%@include file="index_menus.jsp"%> 
     </td>
  </tr> --%>

  <tr>
    <td width="180" rowspan="3" align="center" valign="top" bgcolor="#CCBEA4"><img src="${pageContext.request.contextPath}/index/images/title_1.png" width="150" height="235" />
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="center">
           <%@include file="userLogin.jsp" %>
          </td>
        </tr>
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td><img src="${pageContext.request.contextPath}/index/images/bbs_p_2.png" width="175" height="353" /></td>
        </tr>
      </table>
      </td>
    <td height="120" valign="bottom"><object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0" width="720" height="120">
      <param name="movie" value="${pageContext.request.contextPath}/index/images/banner.swf" />
      <param name="quality" value="high" />
      <param name="wmode" value="transparent" />
      <embed src="${pageContext.request.contextPath}/index/images/banner.swf" width="720" height="120" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" wmode="transparent"></embed>
    </object></td>
  </tr>
  <tr>
    <td height="650" align="center" valign="top"><div id="menu"><a href="${pageContext.request.contextPath}/guideIndex.jsp"><img src="${pageContext.request.contextPath}/index/images/64.png" alt="首页" width="16" height="16" border="0" /></a>
    <font class="list"><a href="${pageContext.request.contextPath}/integration_list_home.htm?id=21&typeId=21">互动平台</a></font>
    <font class="list"><a href="${pageContext.request.contextPath}/fbpots_list_home.htm">文化论坛</a></font></div>
      <div style="width:98%;">
      <div id="published"><a href="${pageContext.request.contextPath}/fbpots_view.htm"><img src="${pageContext.request.contextPath}/index/images/published.gif" border="0"/></a></div>
      <div id="page">
      <s:url id="url_pre" value="fbpots_list_home.htm">
					<s:param name="fbpots.pageNo" value="fbpots.pageNo-1"></s:param>
				</s:url> 
				<s:url id="url_next" value="fbpots_list_home.htm">
					<s:param name="fbpots.pageNo" value="fbpots.pageNo+1"></s:param>
				</s:url> 
				<s:url id="url_first" value="fbpots_list_home.htm">
					<s:param name="fbpots.pageNo" value="1"></s:param>
				</s:url> 
                <s:url id="url_last" value="fbpots_list_home.htm">
					<s:param name="fbpots.pageNo" value="fbpots.pageCount"></s:param>
				</s:url>
        <div class="page_1">第${fbpots.pageNo}页/共${fbpots.pageCount}页</div>
        <div class="page_1">${fbpots.pageSize}条每页</div>
        <div class="page_1"><a href="${url_first}">首页</a></div>
        <div class="page_1"><a href="${url_pre}">上一页</a></div>
        <div class="page_1"><a href="${url_next}">下一页</a></div>
        <div class="page_1"><a href="${url_last}">末页</a></div>
      </div>
      <table border="0" cellpadding="0" cellspacing="0" class="datalist" >
        <tr>
          <th width="55%">主题</th>
          <th width="16%">作者</th>
          <th width="13%">浏览量</th>
          <th width="16%">发布日期</th>
        </tr>
          <c:forEach items="${fbpotss}" var="n" varStatus="st">
          <!-- 奇数行 -->
           <c:if test="${st.count%2 eq 1}">
            <tr>
          <td><div class="title"><a href="${pageContext.request.contextPath}/fbreply_viewContent.htm?fbpostsId=${n.id}&f=1">${n.fpTitle}</a></div></td>
          <td>
            ${n.usersName}</td>
          <td>${n.pviews}</td>
          <td>
            <div class="date">${fn:substring(n.startDay,0,10)} </div></td>
           </tr>
           </c:if>
            <!-- 偶数行 -->
            <c:if test="${st.count%2 eq 0}">
          <tr class="altrow">
          <td><div class="title_pic"><a href="${pageContext.request.contextPath}/fbreply_viewContent.htm?fbpostsId=${n.id}&f=1">${n.fpTitle}</a></div></td>
          <td>
            ${n.usersName}</td>
          <td>${n.pviews}</td>
          <td>
            <div class="date">${fn:substring(n.startDay,0,10)}</div></td>
        </tr>
           </c:if>
          </c:forEach>

      </table>

	  <div id="published"><a href="${pageContext.request.contextPath}/fbpots_view.htm"><img src="${pageContext.request.contextPath}/index/images/published.gif" border="0"/></a></div>
      <div id="page">
        <div class="page_1">第${fbpots.pageNo}页/共${fbpots.pageCount}页</div>
        <div class="page_1">${fbpots.pageSize}条每页</div>
        <div class="page_1"><a href="${url_first}">首页</a></div>
        <div class="page_1"><a href="${url_pre}">上一页</a></div>
        <div class="page_1"><a href="${url_next}">下一页</a></div>
        <div class="page_1"><a href="${url_last}">末页</a></div>
      </div>
    </div></td>
  </tr>
  <tr>
    <td height="50" align="center"><%@include file="index_blank.jsp"%></td>
  </tr>
</table>
</center>
 </body>
</html>