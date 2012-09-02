<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:forEach var="mo" items="${menuByInformations}">
     	<c:choose>
     		<c:when test="${mo.key.pagetype eq '2' }">
	     		<c:forEach items="${mo.value}" var="n" varStatus="status">
	     			<table width="98%" border="0" cellspacing="0" cellpadding="0">
			            <tr>
			              <td height="41" background="${pageContext.request.contextPath}/home/images/t_bg_1.jpg">
				              <table width="100%" height="41" border="0" cellpadding="0" cellspacing="0">
				                  <tr>
				                    <td width="600" class="title_1">${mo.key.name}</td>
				                    <td align="center">
				                    	<a href="${pageContext.request.contextPath}/homePage_detail.htm?id=${n.id}">
				                    		<img src="${pageContext.request.contextPath}/home/images/more_2.jpg" width="78" height="23" border="0"/>
				                    	</a>
				                    </td>
				                  </tr>
				              </table>
			              </td>
			            </tr>
		      			<tr>
			              <td height="100" align="left" valign="top" class="content_1">
					        	<span msg="a${n.id}" style="margin: 0px;padding: 0px;"></span>
					        	<div msg="a${n.id}" style="display:none">
					        		${n.content}
					        	</div>
			              </td>
				        </tr>
		         </table>
         	</c:forEach>
     		</c:when>
     		<c:when test="${mo.key.pagetype eq '3' }">
     			<table width="98%" border="0" cellspacing="0" cellpadding="0">
		          <tr>
		            <td height="41" background="${pageContext.request.contextPath}/home/images/t_bg_1.jpg">
			            <table width="100%" height="41" border="0" cellpadding="0" cellspacing="0">
			                <tr>
			                  <td width="600" class="title_1">${mo.key.name}</td>
			                  <td align="center">
			                  		<a href="${pageContext.request.contextPath}/homePage_list.htm?id=${mo.key.id}">
				                  		<img src="${pageContext.request.contextPath}/home/images/more_1.jpg" width="78" height="23" border="0"/>
			                  		</a>
			                  </td>
			                </tr>
			            </table>
		            </td>
		          </tr>
		          <tr>
		            <td align="center">
			            <table width="95%" border="0" cellspacing="0" cellpadding="0">
			            	<c:forEach items="${mo.value}" var="n">
				         		<tr>
				                  <td width="85%" class="list_2">
				                  		<a href="${pageContext.request.contextPath}/homePage_detail.htm?id=${n.id}" title="${n.title}">
				                  			${fn:substring(n.title, 0, 40)}...
				                  		</a>
				                  </td>
				                  <td width="15%" class="list_data">${n.createTime}</td>
				                </tr>
				         	</c:forEach>
			            </table>
		            </td>
		          </tr>
	    		 </table>
     		</c:when>
     		<c:when test="${mo.key.pagetype eq '4' }">
     			<table width="98%" border="0" cellspacing="0" cellpadding="0">
		          <tr>
		            <td height="41" background="${pageContext.request.contextPath}/home/images/t_bg_1.jpg">
			            <table width="100%" height="41" border="0" cellpadding="0" cellspacing="0">
			                <tr>
			                  <td width="600" class="title_1">${mo.key.name}</td>
			                  <td align="center">
			                  		<a href="${pageContext.request.contextPath}/homePage_list.htm?id=${mo.key.id}">
				                  		<img src="${pageContext.request.contextPath}/home/images/more_1.jpg" width="78" height="23" border="0"/>
			                  		</a>
			                  </td>
			                </tr>
			            </table>
		            </td>
		          </tr>
		          <tr>
		            <td align="center">
			            <table width="95%" border="0" cellspacing="0" cellpadding="0">
		            		<tr>
			            	<c:forEach items="${mo.value}" var="n">
			            			<td width="33%" height="170" class="list_pic">
										<img src="${pageContext.request.contextPath}/imgs/${n.scalepic }" width="215" height="155" />
									</td>
				         	</c:forEach>
							</tr>
		            		<tr>
			            	<c:forEach items="${mo.value}" var="n">
			            			<td style="font-size:12px;">
										<a href="${pageContext.request.contextPath}/homePage_detail.htm?id=${n.id}">${n.title }</a>
									</td>
				         	</c:forEach>
							</tr>
			            </table>
		            </td>
		          </tr>
	    		 </table>
     		</c:when>
     	</c:choose>
  </c:forEach>
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
					if(c.length>150){
						$(this).text(c.substr(0, 150).Trim()+"...");
					}else{
						$(this).text(c.substr(0).Trim());
					}
				}
			});
		}
	});
});
</script>    