<%@page contentType="text/html" pageEncoding="UTF-8"%>
<br />
<!-- 
<input type="hidden" value="0" name="$SORTNAME" id="$SORTNAME"/>
<input type="hidden" value="0" name="$SORTTYPE" id="$SORTTYPE"/>
 -->
<table width="100%" border="0" align="right" cellpadding="0" cellspacing="0" style="font-size:12px;">
	<tr>
		<td id="page" align="center">
			<input type="hidden" name="start" id="start" value="${$STARTPAGE}"/>
			【页数<font color="red"><B>${$TOTALPAGE}</B></font>】
			【记录<font color="red"><B>${$TOTAL}</B></font>】
			【<a href="javascript:;" class="idpages"start="1">首页</a>】
			<c:choose>
				<c:when test="${$PREPAGE ge 1 && $STARTPAGE gt $PREPAGE}">
					【<a href="javascript:;" class="idpages"start="${$PREPAGE}">上一页</a>】
				</c:when>
				<c:otherwise>
					【<span>上一页</span>】
				</c:otherwise>
			</c:choose>
			<%
				Integer total=Integer.parseInt(request.getAttribute("$TOTALPAGE").toString());
				Integer curPage=Integer.parseInt(request.getAttribute("$STARTPAGE").toString());
				if(curPage<=0){
					curPage=1;
				}
				int pagenum=5;
				int cout=pagenum;
				int pre=-1;
				while(pre<=0&&cout>=0){
					pre=curPage-cout;
					cout--;
				}
				if(pre>0){
					for(int i=pre;i<curPage;i++){
						out.print("<a href=\"javascript:;\" class=\"idpages\"start=\""+i+"\">"+i+"</a>&nbsp;");
					}
				}
				int next=curPage+pagenum;
				while(next>total){
					next--;
				}
				out.print("<b><a href=\"javascript:;\" class=\"idpages\"start=\""+curPage+"\">["+curPage+"]</a></b>");
				if(next>0){
					for(int i=curPage+1;i<=next;i++){
						out.print("&nbsp;<a href=\"javascript:;\" class=\"idpages\"start=\""+i+"\">"+i+"</a>");
					}
				}
			%>
			<c:choose>
				<c:when test="${$NEXTPAGE le $TOTALPAGE && $STARTPAGE lt $TOTALPAGE}">
					【<a href="javascript:;" class="idpages"start="${$NEXTPAGE}">下一页</a>】
				</c:when>
				<c:otherwise>
					【<span>下一页</span> 】
				</c:otherwise>
			</c:choose> 
			【<a href="javascript:;" class="idpages" start="${$TOTALPAGE}">尾页</a>】
			【每页
			<select name="limit" id="limit">
				<option value="15"<c:if test="${$PAGELIMIT eq 15 }"> selected=selected</c:if>>15</option>
				<option value="35"<c:if test="${$PAGELIMIT eq 35 }"> selected=selected</c:if>>35</option>
				<option value="45"<c:if test="${$PAGELIMIT eq 45 }"> selected=selected</c:if>>45</option>
			</select>】
			<input name="forwardnum" size="1" id="forwardnum"/>
			<a href="javascript:;" id="btnforward">跳转</a>
		</td>
	</tr>
</table>
<script type="text/javascript">
$(function(){
	$(".idpages").click(function(){
		var start=$(this).attr("start");
		if(start==""){
			start="1";
		}
		$("#start").val(start);
		adminForm.submit();
	});
	$("#limit").change(function(){
		$("#start").val("1");
		adminForm.submit();
	});
	$("#btnforward").click(function(){
		var i=$("#forwardnum").val();
		$("#forwardnum").val("");
		if(!isNaN(i)){
			var total=${$TOTALPAGE};
			if(i<0){
				alert("页码不能小于零!");
			}else if(total<i){
				alert("页码最大值为${$TOTALPAGE}");
			}else{
				$("#start").val(i);
				adminForm.submit();
			}
		}else{
			alert("页码为数字!");
		}
	});
});
</script>