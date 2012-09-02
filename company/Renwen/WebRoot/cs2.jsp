<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld"%>
<%@ taglib prefix="fn" uri="/WEB-INF/tld/fn.tld"%>
<html>
 <head>
<link href="${pageContext.request.contextPath}/index/css/default.css" rel="stylesheet" type="text/css" />
<title>人文学院</title>
<!--背景图片透明方法-->
<script src="${pageContext.request.contextPath}/index/js/iepng.js" type="text/javascript"></script>
<!--插入图片透明方法-->
<script type="text/javascript">
<!--
EvPNG.fix('div, ul, img, li, input');  //EvPNG.fix('包含透明PNG图片的标签'); 多个标签之间用英文逗号隔开。
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}
function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}
function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
//-->
</script>
 </head>
<body onLoad="MM_preloadImages('${pageContext.request.contextPath}/index/images/fh1.gif')">
  <center>
    <table width="1002" height="600" border="0" cellpadding="0" cellspacing="0" style="background-image: url(${pageContext.request.contextPath}/index/images/bg_1.jpg);background-repeat: no-repeat;">
  <tr>
    <td width="180" height="115">&nbsp;</td>
    <td colspan="2" valign="top">
      <%@include file="cs2_menus.jsp" %>
    </td>
  </tr>
  <tr>
    <td height="485" rowspan="3">&nbsp;</td>
    <td width="700" height="430" rowspan="2"><table width="100%" height="430" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="256" valign="top"><img src="${pageContext.request.contextPath}/index/images/p_1.png" width="256" height="423"></td>
        <td width="370" align="left" valign="top">
		 <c:forEach var="n" items="${integrations}">
		  <div id="list_1">
		   <div class="t"><a href="${pageContext.request.contextPath}/integration_content.htm?id=${n.id}&typeId=${typeId}" target="_blank">${fn:substring(n.name,0,15)}</a></div>
		   <div class="d">${fn:substring(n.startDay,0,10)}</div>
		   </div>
		 </c:forEach>
	
	<c:if test="${not empty integrations}">
		<div class="page">
		      <s:url id="url_pre" value="integration_list_home.htm">
		            <s:param name="id" value="%{id}"></s:param>
		            <s:param name="typeId" value="%{typeId}"></s:param>
					<s:param name="integration.pageNo" value="integration.pageNo-1"></s:param>
				</s:url> 
				<s:url id="url_next" value="integration_list_home.htm">
				    <s:param name="id" value="%{id}"></s:param>
		            <s:param name="typeId" value="%{typeId}"></s:param>
					<s:param name="integration.pageNo" value="integration.pageNo+1"></s:param>
				</s:url> 
				<s:url id="url_first" value="integration_list_home.htm">
				    <s:param name="id" value="%{id}"></s:param>
		            <s:param name="typeId" value="%{typeId}"></s:param>
					<s:param name="integration.pageNo" value="1"></s:param>
				</s:url> 
                <s:url id="url_last" value="integration_list_home.htm">
                    <s:param name="id" value="%{id}"></s:param>
		            <s:param name="typeId" value="%{typeId}"></s:param>
					<s:param name="integration.pageNo" value="integration.pageCount"></s:param>
				</s:url>
				
		当前第${integration.pageNo}页/共${integration.pageCount}页 [<a href="${url_first}">首页</a>]    [<a href="${url_pre}">上页</a>]    [<a href="${url_next}">下页</a>]   [<a href="${url_last}">末页</a>] 
		 </div>
		 </c:if>
		</td>
        <td align="center" valign="top"><font class="t_1">${newstype.name}</font></td>
      </tr>
    </table></td>
    <td height="400" align="center" valign="top"> 
    <c:forEach items="${newstypes}" var="n">
	<div class="btn_1" >
	<c:if test="${empty n.ljUrl}">
						<a
							href="${pageContext.request.contextPath}/integration_list_home.htm?id=${n.id}&typeId=${typeId}">
						${n.name}</a>
					</c:if> <c:if test="${not empty n.ljUrl}">
						<a href="${pageContext.request.contextPath}${n.ljUrl}">
						${n.name}</a>
					</c:if></div>
	</c:forEach>
	</td>
  </tr>
  <tr>
    <td height="30" align="right" valign="middle"><a href="${pageContext.request.contextPath}/guideIndex.jsp"><img src="${pageContext.request.contextPath}/index/images/fh.gif" width="25" height="27" border="0" id="Image1" onMouseOver="MM_swapImage('Image1','','${pageContext.request.contextPath}/index/images/fh1.gif',1)" onMouseOut="MM_swapImgRestore()"></a>&nbsp;</td>
  </tr>
  <tr>
    <td height="55" align="center" valign="middle"><%@include file="index_blank.jsp"%></td>
    <td>&nbsp;</td>
  </tr>
</table>
  </center>


</body>
</html> 