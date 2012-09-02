$(document).ready(function(){
	var selectedIds = "";
	var selectedNames = "";
	var max = 0;
	function ckall(){
		$("input[name='selectedIds']").each(function() {
			if ($(this).attr("checked")) {
				if ("" == selectedIds) {
					selectedIds += $(this).val();
					selectedNames += $(this).attr("title");
				} else {
						selectedIds += "," + $(this).val();
						selectedNames += "," + $(this).attr("title");
				}
			}
		});
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
	
	
	$("#b_ass4er").click(function(){
		ckall();
		if(checkEmpty()){
			window.parent.opener.$("#t_eva").val(selectedNames);
			window.parent.opener.$("#h_eva").val(selectedIds);
			window.close();
		}else{
			alert("编号不能为空！");
		}
	});
	
	$("#b_ass4er_more").click(function(){
		ckall();
		if(checkEmpty()){
			window.parent.opener.$("#t_eva").val(window.parent.opener.$("#t_eva").val()+","+selectedNames);
			window.parent.opener.$("#h_eva").val(window.parent.opener.$("#h_eva").val()+","+selectedIds);
			window.close();
		}else{
			alert("编号不能为空！");
		}
	});
	
});