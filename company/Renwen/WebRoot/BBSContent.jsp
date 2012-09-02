<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld"%>
<%@ taglib prefix="fn" uri="/WEB-INF/tld/fn.tld"%>
<!DOCTYPE unspecified PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
   <title>人文学院-论坛</title>
   <script src="${pageContext.request.contextPath}/js/index/jquery.js"
	type=text/javascript></script>
	<link href="${pageContext.request.contextPath}/index/css/default.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/index/css/bbs.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/index/BBSContent.js" ></script>
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

 <%--  <tr>

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

          <td><img src="${pageContext.request.contextPath}/index/images/bbs_p_3.png" width="175" height="353" /></td>

        </tr>

      </table></td>

    <td height="120" valign="bottom"><object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0" width="720" height="120">

      <param name="movie" value="${pageContext.request.contextPath}/index/images/banner.swf" />

      <param name="quality" value="high" />

      <param name="wmode" value="transparent" />

      <embed src="${pageContext.request.contextPath}/index/images/banner.swf" width="720" height="120" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" wmode="transparent"></embed>

    </object></td>

  </tr>

  

  <tr>

    <td height="650" align="center" valign="top"><div id="menu">
    <a href="${pageContext.request.contextPath}/guideIndex.jsp"><img src="${pageContext.request.contextPath}/index/images/64.png" alt="首页" width="16" height="16" border="0" /></a>
    <font class="list"><a href="${pageContext.request.contextPath}/integration_list_home.htm?id=21&typeId=21">互动平台</a></font>
    <font class="list"><a href="${pageContext.request.contextPath}/fbpots_list_home.htm">文化论坛</a></font></div>

      <div id="bbs_content">

	  <div id="published"><a href="${pageContext.request.contextPath}/fbpots_view.htm"><img src="${pageContext.request.contextPath}/index/images/published.gif" border="0"></a></div>

  <div id="published"><img src="${pageContext.request.contextPath}/index/images/reply.gif" width="55" height="30"></div>

  <div id="page">
 <s:url id="url_pre" value="fbreply_viewContent.htm">
                    <s:param name="fbpostsId" value="%{fbpostsId}"></s:param>
					<s:param name="fbreply.pageNo" value="fbreply.pageNo-1"></s:param>
				</s:url> 
				<s:url id="url_next" value="fbreply_viewContent.htm">
				    <s:param name="fbpostsId" value="%{fbpostsId}"></s:param>
					<s:param name="fbreply.pageNo" value="fbreply.pageNo+1"></s:param>
				</s:url> 
				<s:url id="url_first" value="fbreply_viewContent.htm">
				    <s:param name="fbpostsId" value="%{fbpostsId}"></s:param>
					<s:param name="fbreply.pageNo" value="1"></s:param>
				</s:url> 
                <s:url id="url_last" value="fbreply_viewContent.htm">
                    <s:param name="fbpostsId" value="%{fbpostsId}"></s:param>
					<s:param name="fbreply.pageNo" value="fbreply.pageCount"></s:param>
				</s:url>
        <div class="page_1">第${fbreply.pageNo}页/共${fbreply.pageCount}页</div>
        <div class="page_1">${fbreply.pageSize}条每页</div>
        <div class="page_1"><a href="${url_first}">首页</a></div>
        <div class="page_1"><a href="${url_pre}">上一页</a></div>
        <div class="page_1"><a href="${url_next}">下一页</a></div>
        <div class="page_1"><a href="${url_last}">末页</a></div>

        </div>

        <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF" >

          <tr>

            <td colspan="3" class="td_title">足不出户也能欣赏哥们滴旅游照片</td>

          </tr>

          <tr>

            <td width="15%" rowspan="2" align="left" valign="top" class="name">${fbpots.usersName}</td>

            <td width="80%" class="date">发表于 ${fbpots.startDay}</td>

            <td class="date">楼主</td>

          </tr>

          <tr>

            <td> ${fbpots.fpContent}&nbsp;</td>

            <td>&nbsp;</td>

          </tr>

        </table>

<c:forEach var="n"  items="${fbreplys}" varStatus="stt">
        <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF" >

          <tr>

            <td width="15%" rowspan="2" align="left" valign="top" class="name">${n.userName}</td>

            <td width="80%" class="date">发表于 ${n.startDay}</td>

            <td class="date">${(fbreply.pageNo-1)*(fbreply.pageSize)+stt.count}楼</td>

          </tr>

          <tr>

            <td>${n.rcontent} &nbsp;</td>

            <td>&nbsp;</td>

          </tr>

        </table>
</c:forEach>

        <div id="page">

           <div class="page_1">第${fbreply.pageNo}页/共${fbreply.pageCount}页</div>
        <div class="page_1">${fbreply.pageSize}条每页</div>
        <div class="page_1"><a href="${url_first}">首页</a></div>
        <div class="page_1"><a href="${url_pre}">上一页</a></div>
        <div class="page_1"><a href="${url_next}">下一页</a></div>
        <div class="page_1"><a href="${url_last}">末页</a></div>

        </div>
        
  <s:form action="fbreply_viewSave.htm" method="post" validate="true" id="ec">
  <s:hidden name="fbpostsId" value="%{fbpostsId}"></s:hidden>
  <s:hidden name="fbreply.pageNo" value="%{fbreply.pageNo}"></s:hidden>
        <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF">

          <tr>

            <td colspan="2" align="left" valign="top" class="td_1">快速回复主题</td>

          </tr>

          <tr>

            <td width="15%" rowspan="2" align="left" valign="top" class="name"><c:if test="${empty stuUser}">访客</c:if><c:if test="${not empty stuUser}">${stuUser.userName}</c:if>：</td>

            <td align="left"><textarea name="rcontent" id="rcontent"  cols="80" rows="6"></textarea></td>

          </tr>

          <tr>

            <td align="left"><input id="isok" name="isok" type="button" class="buttom" value="发表" />

                <input name="Submit2" type="reset" class="buttom" value="清空" /></td>

          </tr>

        </table>
</s:form>
      </div></td>

  </tr>

  <tr>

    <td height="50" align="center"><%@include file="index_blank.jsp"%></td>

  </tr>

</table>
</center>
 </body>
</html>