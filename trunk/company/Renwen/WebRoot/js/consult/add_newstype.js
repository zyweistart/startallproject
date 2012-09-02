$(document).ready(function() {
	var category=$("#category").val();
	
	 $.ajax({
		 url : 'templatetype_showEffect.htm',
			type : 'post',
			dataType : 'html',
			data : 'category='+ category+ '&s='+ Math.round(Math.random() * 10000),
			success : function(msg) {
				if (msg != null&& msg != '') {
					 $("#showeffect").attr("innerHTML",msg);
				} else {
					
				}
			}
	 });
	
	
    $("#category").change(function(){
    	var category=$("#category").val();
    	
    	 $.ajax({
    		 url : 'templatetype_showEffect.htm',
				type : 'post',
				dataType : 'html',
				data : 'category='+ category+ '&s='+ Math.round(Math.random() * 10000),
				success : function(msg) {
					if (msg != null&& msg != '') {
						 $("#showeffect").attr("innerHTML",msg);
					} else {
						
					}
				}
    	 });
    });
	
    
			function checkform() {
				var type = $("#parentName").val();
				var newstype = $("#newstype").val();
                var category=$("#category").val();
                
				if (newstype == '') {
					alert("请填写菜单名称");
					return false;
				}
				if (type == '') {
					alert("请选择菜单类型");
					return false;
				}
				if(category==''){
					alert("请选择内容现在模板")
					return false;
				}
				return true;
			}
			$("#isok").click(function() {
						if (checkform()) {
							ec.submit();
						}
					});
			

			
			
			
			
});