<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/home/include/head.jsp"%>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/index/BBSContent.js" ></script>
	<!--背景图片透明方法-->

<script src="${pageContext.request.contextPath}/index/js/iepng.js" type="text/javascript"></script>

<!--插入图片透明方法-->

<script type="text/javascript">

<!--

EvPNG.fix('div, ul, img, li, input');  //EvPNG.fix('包含透明PNG图片的标签'); 多个标签之间用英文逗号隔开。

//-->

</script>
  <tr>
    <td height="200" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
       <td width="30%" valign="top">
					<%@include file="/home/include/links.jsp"%>
				</td>
        <td valign="top">
		<div id="bbs_content">
	  <div id="published"><a href="${pageContext.request.contextPath}/fbpots_view.htm"><img src="${pageContext.request.contextPath}/index/images/published.gif" border="0"></a></div>
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

            <td colspan="3" class="td_title">${fbpots.fpTitle}</td>

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

            <td align="left"><textarea name="rcontent" id="rcontent"  cols="70" rows="6"></textarea></td>

          </tr>

          <tr>

            <td align="left"><input id="isok" name="isok" type="button" class="buttom" value="发表" />

                <input name="Submit2" type="reset" class="buttom" value="清空" /></td>

          </tr>

        </table>
</s:form>
      </div>
		
		</td>
      </tr>
      
    </table></td>
  </tr>
<%@include file="/home/include/foot.jsp"%>
