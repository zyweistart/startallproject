$(document).ready(function() {

   $("#isok").click(function(){
	   var ianame=$("#ianame").val();
	   if(ianame==''){
		   alert("不能为空");
		   $("#ianame").focus();
		   return false;
	   }
	   ec.submit();
   });

});