$(document).ready(function() {
	$("#isok").click(function(){
		var fptitle=$("#fptitle").val();
		var fpContent=$("#fpContent").val();
		
		if(fptitle==''){
			alert("内容不能为空");
		  $("#fptitle").focus();
		   return false;
		}
		
		
		if(fpContent==''){
			alert("内容不能为空");
			$("#fpContent").focus();
			return false;
		}
		ec.submit();
	});
	
	

});