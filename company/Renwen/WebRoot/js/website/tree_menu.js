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
		url : 'menu_ajax.htm',
		type : 'post',
		dataType : 'html',
		data : 'id=' + ids + '&s=' + Math.round(Math.random() * 10000),
		success : function(json) {
			jsonContent = eval("(" + json + ")");
			if (null == jsonContent) {
				window.dialogArguments.$("#pname").val('顶级');
				window.dialogArguments.$("#pid").val("0");
				window.dialogArguments.$("#level").val("0");
				window.close();
			} else {
				window.dialogArguments.$("#pname").val(jsonContent.name);
				window.dialogArguments.$("#pid").val(jsonContent.id);
				window.dialogArguments.$("#level").val(parseInt(jsonContent.level)+1);
				window.close();
			}
		}
	});
}