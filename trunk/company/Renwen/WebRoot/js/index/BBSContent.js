$(document).ready(function(){
	
	$("#isok").click(function(){
		var rcontent=$("#rcontent").val();
		if(rcontent==''){
			alert("要回复的内容不能为空");
			$("#rcontent").focus();
			return false;
		};
		
		ec.submit();
		
	});
	
	
});