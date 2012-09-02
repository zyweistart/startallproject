$(document).ready(function() {
		$("#okbutton").click(function() {
			var flag = false;
			var max = 0;
			var ids=0;
			$("input[type='checkbox']").each(function() {
						if ($(this).attr("checked")) {
							max++;
							ids = $(this).val();
							flag = true;
						}
					});
			if (max > 1) {
				alert('只能选择一个对象!');
				return false;
			}
			if (!flag) {
				alert("请选择上级类型!");
				return false;
			}
			getjson(ids);
		});
	});

function getjson(ids) {
	$.ajax({
		url : 'information_ajax.htm',
		type : 'post',
		dataType : 'html',
		data : 'id=' + ids + '&s=' + Math.round(Math.random() * 10000),
		success : function(json) {
			if(json=='1'){
				alert("当前类别为父级类型！ ");
			}else if(json=='2'){
				alert("当前类别为单页面形式，已创建相应信息页面！ ");
			}else{
				jsonContent = eval("(" + json + ")");
				if (null == jsonContent) {
					alert("当前类别不可用！ ");
				} else {
					window.dialogArguments.$("#pname").val(jsonContent.name);
					window.dialogArguments.$("#pid").val(jsonContent.id);
					window.dialogArguments.$("#pagetype").val(jsonContent.pagetype);
					if(jsonContent.pagetype=='4'){
						window.dialogArguments.$("#scalepic").show();
					}else{
						window.dialogArguments.$("#scalepic").hide();
					}
					window.close();
				}
			}
		}
	});
}