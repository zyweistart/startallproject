$(document).ready(function(){
	$("#b_stu4cer").click(function(){
		var selectedIds = "";
		var selectedNames = "";
		var max = 0;
		$("input[name='selectedIds']").each(function() {
			if ($(this).attr("checked")) {
				if ("" == selectedIds) {
					selectedIds += $(this).val();
					selectedNames += $(this).attr("title");
				} else {
						selectedIds += "," + $(this).val();
						selectedNames += "," + $(this).attr("title");
				}
				max++;
			}
		});
		
		if (max > 1) {
			alert('只能选择一个对象!');
			return false;
		}
		/**
		 * 检查至少有一个复选框被选中
		 */
		function checkEmpty() {
			if (null == selectedIds || "undefined" == selectedIds || "" == selectedIds) {
				return false;
			}
			return true;
		}
		
		if(checkEmpty()){
			window.parent.opener.$("#t_cer").val(selectedNames);
			window.parent.opener.$("#h_cer").val(selectedIds);
			window.close();
		}else{
			alert("编号不能为空！");
		}
		
	});
});