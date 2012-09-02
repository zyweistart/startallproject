<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="fn" uri="/WEB-INF/tld/fn.tld"%>
<%@ taglib prefix="ec" uri="/WEB-INF/tld/extremecomponents.tld" %>
<html>
<head>
<link href="${pageContext.request.contextPath}/index/css/default.css"
	rel="stylesheet" type="text/css" />
<title>红帮文化</title>
<!--背景图片透明方法-->
<fmt:setBundle basename="ApplicationResources" />
   <script type="text/javascript" src="${pageContext.request.contextPath}/common/jquery/jquery-1.3.2.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/common/jquery/jquery.form.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/common/jquery/livequery.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/common/jquery/uploadPreview.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/common/global/common.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/common/global/Verifies.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/common/man_xtree/man_xtree.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/common/calendar/WdatePicker.js"></script>
         <link rel="stylesheet" href='${pageContext.request.contextPath}/css/default/css.css' type="text/css">
                <link rel="stylesheet" href='${pageContext.request.contextPath}/css/default/extremecomponents.css' type="text/css">
<script src="${pageContext.request.contextPath}/index/js/iepng.js"
	type="text/javascript"></script>
<!--插入图片透明方法-->
<script type="text/javascript">
	EvPNG.fix('div, ul, img, li, input'); //EvPNG.fix('包含透明PNG图片的标签'); 多个标签之间用英文逗号隔开。
	//-->
</script>
</head>
<body>
<center>
<table width="900" border="2" cellpadding="0" cellspacing="0"
	bordercolor="#FFFFFF"
	background="${pageContext.request.contextPath}/index/images/bg2.gif"
	bgcolor="#FCF0D8">
	<tr>
		<td height="40" colspan="4" valign="bottom" bgcolor="#CFBBA0"><%@include
			file="index_menus.jsp"%></td>
	</tr>
	<tr>
		<td width="180" rowspan="3" align="center" valign="top"
			bgcolor="#CCBEA4"><img
			src="${pageContext.request.contextPath}/index/images/title_1.png"
			width="150" height="235" />
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
           <!-- 二级目录 -->
			
             	<tr>
					<td>
					<div id="list_2">
					<div class="t">
				     <a href="${pageContext.request.contextPath}/extraction_practice.htm">测试库</a>
				    </div>
					</div>
					</td>
				</tr>
					<tr>
					<td>
					<div id="list_2">
					<div class="t">
				      <a href="${pageContext.request.contextPath}/feedback_viewmag.htm">用户留言</a>
				    </div>
					</div>
					</td>
				</tr>
		</table>
		</td>
		<td height="120" valign="bottom"><object
			classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
			codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0"
			width="720" height="120">
			<param name="movie"
				value="${pageContext.request.contextPath}/index/images/banner.swf" />
			<param name="quality" value="high" />
			<param name="wmode" value="transparent" />
			<embed
				src="${pageContext.request.contextPath}/index/images/banner.swf"
				width="720" height="120" quality="high"
				pluginspage="http://www.macromedia.com/go/getflashplayer"
				type="application/x-shockwave-flash" wmode="transparent"></embed> </object></td>
	</tr>

	<tr>
		<td height="650" align="center" valign="top">
		<div id="menu"><a
			href="${pageContext.request.contextPath}/guideIndex.jsp"><img
			src="${pageContext.request.contextPath}/index/images/64.png" alt="首页"
			width="16" height="16" border="0" /></a> <font class="list"><a href="${pageContext.request.contextPath}/integration_list_home.htm?id=21&typeId=21">互动平台</a></div>
	
			<!-- 主内容区 -->
			<table width="95%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left"><s:form action="extraction_startTest" method="post" validate="true" id="adminForm">
    <s:token />
    <s:hidden name="expression" id="exvalue" verifies="true" validation="empty" errormsg="没有添加任何选题类型！"/>
    <table border="0" cellpadding="1" cellspacing="1" width="100%" class="tableBgColor" id="extractionTable">
    	<tr>
    		<td align="center" class="rightTdBgColor" colspan="6"><h1>试题抽取</h1></td>
    	</tr>
         <tr>
            <td align="center" width="18%" class="rightTdBgColor">
                &nbsp;<b>类别</b>
            </td>
            <td align="center" width="18%" class="rightTdBgColor">
                &nbsp;<b>模式</b>
            </td>
            <td align="center" width="18%" class="rightTdBgColor">
                &nbsp;<b>数量</b>
            </td>
            <td align="center" width="18%" class="rightTdBgColor">
                &nbsp;<b>难度指数</b>
            </td>
            <td align="center" width="10%" class="rightTdBgColor">
                &nbsp;<b>操作</b>
            </td>
        </tr>
    </table>
    <br />
    <table border="0" cellpadding="1" cellspacing="1" width="100%" class="tableBgColor">
         <tr>
            <td align="right" width="15%" class="leftTdBgColor">
                &nbsp;试卷库
            </td>
            <td align="left" width="35%" class="rightTdBgColor" colspan="3">
                &nbsp;<s:select list="%{subjectss}" name="subjectsId" id="subjectsId" headerKey="" headerValue="--请选择--" listKey="id" listValue="name"></s:select><fmt:message key="global.redstar" />
            </td>
          
        </tr>
    </table>
    <br />
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
            <td align="right" width="30%">
                <input type="button" value="开始测试" class="buttonStyle" id="formSubmits">
            </td>
        </tr>
    </table>
</s:form></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td height="50" align="center"><%@include file="index_blank.jsp"%>
		</td>
	</tr>
</table>
</center>









</body>
</html>
<script type="text/javascript">
function addextraction(){
	var extractionstr=$("#exvalue").val();
	var subjectschildId=$("#subjectschildId").val();
	var mode=$("#mode").val();
	var num=$("#num").val();
	var level=$("#level").val();
	if(subjectschildId==""){
		alert("请选择科目类型！");
		return;
	}
	if(mode==""){
		alert("请选择考试的模式！");
		return;
	}
	if(num==""){
		alert("请输入试题的数量！");
		return;
	}else{
		if(isNaN(num)){
			alert("试题数量必须为数字!");
			return;
		}
	}
	var tableObj = document.getElementById("extractionTable");
    var rowIndex = getNextRowIndex(tableObj);
    var trObj = tableObj.insertRow(rowIndex);
	var str=subjectschildId+","+mode+","+num+",0,"+level+"|";
    var tdObj = trObj.insertCell(0);
    tdObj.align = "center";
    tdObj.innerHTML = getSelectedText("subjectschildId");
    tdObj = trObj.insertCell(1);
    tdObj.align = "center";
    tdObj.innerHTML =getSelectedText("mode");
    tdObj = trObj.insertCell(2);
    tdObj.align = "center";
    tdObj.innerHTML =num;
    tdObj = trObj.insertCell(3);
    tdObj.align = "center";
    tdObj.innerHTML =getSelectedText("level");
    tdObj = trObj.insertCell(4);
    tdObj.align = "center";
    tdObj.innerHTML ="<a href=\"javascript:deleteextr('"+str+"',"+rowIndex+")\" style=\"color:red;\">删除</a>";
	$("#extractionTable td").addClass("rightTdBgColor");

	alert(extractionstr+str);
	$("#exvalue").val(extractionstr+str);
}
function deleteextr(str,row){
	var extractionstr=$("#exvalue").val();
	var ex=extractionstr.split('|');
	var extrstr="";
	for(i=0;i<ex.length;i++){
		var val=ex[i];
		if(val!=""){
			if(str!=(val+"|")){
				extrstr+=val+"|";
			}
		}
	}
	var tableObj = document.getElementById('extractionTable');
    tableObj.deleteRow(row);
	$("#exvalue").val(extrstr);
}
$(function(){
	$("#subjectsId").change(function(){
		var value=$(this).val()
		if(value!=""){
			
       for(var i=document.getElementById("extractionTable").rows.length ;i>2;i--){
    	   document.getElementById("extractionTable").deleteRow(document.getElementById("extractionTable").rows.length-1); 
    	}
			<%//单选题%>
			$.ajax( {
				url : 'questions_ajaxSize.htm',
				type : 'post',
				dataType : 'html',
				data : 'subjectsID='+value+'&typeID=2&s=' + Math.round(Math.random() * 10000),
				success : function(content){
				 if(content!=null && content!=''){
				  var tableObj = document.getElementById("extractionTable");
			      var rowIndex = getNextRowIndex(tableObj);
			      var trObj = tableObj.insertRow(rowIndex);
			      var tdObj = trObj.insertCell(0);
			      tdObj.align = "center";
			      tdObj.innerHTML = getSelectedText("subjectsId");
			      tdObj = trObj.insertCell(1);
			      tdObj.align = "center";
			      tdObj.innerHTML ="单选";
			      tdObj = trObj.insertCell(2);
			      tdObj.align = "center";
			      tdObj.innerHTML =content;
			      tdObj = trObj.insertCell(3);
			      tdObj.align = "center";
			      tdObj.innerHTML ="简单";
			      tdObj = trObj.insertCell(4);
			      tdObj.align = "center";
			      tdObj.innerHTML ="";
			  	  $("#extractionTable td").addClass("rightTdBgColor");
				 }
			  	  
				}
			});
			
	    
			<%//多选%>
			$.ajax( {
				url : 'questions_ajaxSize.htm',
				type : 'post',
				dataType : 'html',
				data : 'subjectsID='+value+'&typeID=3&s=' + Math.round(Math.random() * 10000),
				success : function(content){
				 if(content!=null && content!=''){
				  var tableObj = document.getElementById("extractionTable");
			      var rowIndex = getNextRowIndex(tableObj);
			      var trObj = tableObj.insertRow(rowIndex);
			      var tdObj = trObj.insertCell(0);
			      tdObj.align = "center";
			      tdObj.innerHTML = getSelectedText("subjectsId");
			      tdObj = trObj.insertCell(1);
			      tdObj.align = "center";
			      tdObj.innerHTML ="多选";
			      tdObj = trObj.insertCell(2);
			      tdObj.align = "center";
			      tdObj.innerHTML =content;
			      tdObj = trObj.insertCell(3);
			      tdObj.align = "center";
			      tdObj.innerHTML ="简单";
			      tdObj = trObj.insertCell(4);
			      tdObj.align = "center";
			      tdObj.innerHTML ="";
			  	  $("#extractionTable td").addClass("rightTdBgColor");
				 }
				}
			});

			<%//判断题%>
			$.ajax( {
				url : 'questions_ajaxSize.htm',
				type : 'post',
				dataType : 'html',
				data : 'subjectsID='+value+'&typeID=1&s=' + Math.round(Math.random() * 10000),
				success : function(content){
				 if(content!=null && content!=''){
				  var tableObj = document.getElementById("extractionTable");
			      var rowIndex = getNextRowIndex(tableObj);
			      var trObj = tableObj.insertRow(rowIndex);
			      var tdObj = trObj.insertCell(0);
			      tdObj.align = "center";
			      tdObj.innerHTML = getSelectedText("subjectsId");
			      tdObj = trObj.insertCell(1);
			      tdObj.align = "center";
			      tdObj.innerHTML ="判断题";
			      tdObj = trObj.insertCell(2);
			      tdObj.align = "center";
			      tdObj.innerHTML =content;
			      tdObj = trObj.insertCell(3);
			      tdObj.align = "center";
			      tdObj.innerHTML ="简单";
			      tdObj = trObj.insertCell(4);
			      tdObj.align = "center";
			      tdObj.innerHTML ="";
			  	  $("#extractionTable td").addClass("rightTdBgColor");
				 }
				}
			});
			
			<%//问答题%>
			$.ajax( {
				url : 'questions_ajaxSize.htm',
				type : 'post',
				dataType : 'html',
				data : 'subjectsID='+value+'&typeID=4&s=' + Math.round(Math.random() * 10000),
				success : function(content){
				 if(content!=null && content!=''){
				  var tableObj = document.getElementById("extractionTable");
			      var rowIndex = getNextRowIndex(tableObj);
			      var trObj = tableObj.insertRow(rowIndex);
			      var tdObj = trObj.insertCell(0);
			      tdObj.align = "center";
			      tdObj.innerHTML = getSelectedText("subjectsId");
			      tdObj = trObj.insertCell(1);
			      tdObj.align = "center";
			      tdObj.innerHTML ="问答题";
			      tdObj = trObj.insertCell(2);
			      tdObj.align = "center";
			      tdObj.innerHTML =content;
			      tdObj = trObj.insertCell(3);
			      tdObj.align = "center";
			      tdObj.innerHTML ="简单";
			      tdObj = trObj.insertCell(4);
			      tdObj.align = "center";
			      tdObj.innerHTML ="";
			  	  $("#extractionTable td").addClass("rightTdBgColor");
				 }
				}
			});
			
			$.ajax( {
				url : 'questions_ajax.htm',
				type : 'post',
				dataType : 'html',
				data : 'subjectsID='+value+'&s=' + Math.round(Math.random() * 10000),
				success : function(content){
				$("#exvalue").val(content);
				}
			});
		}else{
			 for(var i=document.getElementById("extractionTable").rows.length ;i>2;i--){
		    	   document.getElementById("extractionTable").deleteRow(document.getElementById("extractionTable").rows.length-1); 
		    	}
				$("#exvalue").val("");
			}
	});




	
	
	<%//加载初始化数据%>
	$("#formSubmits").click(function(){
		var ver=new Verifies();
		if(ver.validation()){
			adminForm.submit();
		}
	});
});
</script>