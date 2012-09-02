<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>播放音乐</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/common/jquery/jquery-1.3.2.js"></script>

<script type="text/javascript">
	function run() {
		var s = document.getElementById("dd");
		if (s.innerHTML == 0) {
			$.ajax( {
				url : 'visits_ajax.htm',
				type : 'post',
				dataType : 'html',
				data : 's=' + Math.round(Math.random() * 10000),
				success : function(msg) {
				s.innerHTML = 54000;       
				}
			});
			return false;
		}
		s.innerHTML = s.innerHTML * 1 - 1;
	}
	window.setInterval("run();", 1000);

	function aa() {
		var s = document.getElementById("dd");
		s.innerHTML = 1;
	}


	  window.onunload = function() {     
		  $.ajax( {
				url : 'visits_ajax.htm',
				type : 'post',
				dataType : 'html',
				data : 's=' + Math.round(Math.random() * 10000),
				success : function(msg) {
				s.innerHTML = 54000;       
				}
			});
	     
	    } 
</script>


</head>
<body>
${stuUser.visitsId}
<input type="button" value="测试" onclick="aa()">
<span id="dd">54000</span>
<audio controls="controls" autoplay="autoplay" loop="loop" hiddeh="true">
<source
	src="${pageContext.request.contextPath}/music/huayangnianhua.wma"
	type="audio/mpeg">

<OBJECT classid=clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA>
	<param name="_ExtentX" value="9313">
	<param name="_ExtentY" value="1588">
	<param name="AUTOSTART" value="true">
	<param name="hidden" value="true">
	<param name="SHUFFLE" value="0">
	<param name="PREFETCH" value="0">
	<param name="NOLABELS" value="0">
	<param name="SRC"
		value="${pageContext.request.contextPath}/music/huayangnianhua.wma";>
	<param name="CONTROLS" value="ControlPanel,StatusBar">
	<param name="CONSOLE" value="Clip1">
	<param name="LOOP" value="true">
	<param name="NUMLOOP" value="0">
	<param name="CENTER" value="0">
	<param name="MAINTAINASPECT" value="0">
	<param name="BACKGROUNDCOLOR" value="#000000">
</OBJECT>

<object align=middle classid=CLSID:22d6f312-b0f6-11d0-94ab-0080c74c7e95
	class=OBJECT id=MediaPlayer width=400 height=50>
	<param name=ShowStatusBar value=-1>
	<param name=Filename
		value=${pageContext.request.contextPath}/music/huayangnianhua.wma>
</object>
</audio>

</body>
</html>