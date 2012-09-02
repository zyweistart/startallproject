$(document).ready(function() {
		$("#okbutton").click(function() {
			var flag = false;
			var max = 0;
			var ids;
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
		url : 'newstype_ajax.htm',
		type : 'post',
		dataType : 'html',
		data : 'id=' + ids + '&s=' + Math.round(Math.random() * 10000),
		success : function(json) {
			jsonContent = eval("(" + json + ")");
			if (null == jsonContent) {
				window.dialogArguments.$("#parentName").val('顶级');
				window.dialogArguments.$("#parentId").val("0");
				window.dialogArguments.$("#parentNewsTypeId").val("0");
				window.close();
			} else {
				window.dialogArguments.$("#parentName").val(jsonContent.name);
				window.dialogArguments.$("#parentId").val(jsonContent.id);
				window.dialogArguments.$("#parentNewsTypeId").val(jsonContent.pid);
				window.close();
			}
		}
	});
}