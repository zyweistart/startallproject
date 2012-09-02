$(document).ready(function() {
	
	$("#isok").click(function(){
		var forumName=$("#forumName").val();
		
		if(forumName==''){
			alert("标题不能为空,请输入!");
			return false;
		}
		ec.submit();
	});
	
	
});