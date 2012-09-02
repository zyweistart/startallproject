$(document).ready(function() {
	
	
	
	
			function checkform() {
				var integrationName = $("#integrationName").val();
				var parentName = $("#parentName").val();
				var parentId=$("#parentId").val();
				
				if (integrationName == '') {
					alert("请填写标题");
					return false;
				}
                if (parentName == '') {
                    alert("请选择对应菜单");
                    return false;
                }
                if(parentId == 0){
                	alert("不可选择顶级菜单，请选择正确的菜单");
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