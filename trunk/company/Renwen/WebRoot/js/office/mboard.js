$(document).ready(function() {

   $("#isok").click(function(){
	  var name=$("#name").val();
	  var content=$("#content").val();
	  if(name==''){
		  alert("请正确留言!");
		  $("#name").focus();
		  return false;
	  }
	   if(content==''){
		   alert("请正确留言!");
		   $("#content").focus();
		   return false; 
	   }
	  
	  ec.submit();
	   
   });




});