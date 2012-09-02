$(document).ready(function() {
	
	 $.ajax({
         url : 'fwl_zajax.htm',
         type : 'post',
         dataType : 'html',
         data : 's='
         + Math.round(Math.random() * 10000),
         success : function(msg) {
//		 alert(msg);
//		   $("#wzll").val(msg);
            return null;
         }
     });
	
});