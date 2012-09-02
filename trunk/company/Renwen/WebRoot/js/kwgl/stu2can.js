$(function(){
	var selectedIds ="";
	$("#s_ss").change(function(){//根据日期更新下拉框的考试名称
		if($(this).val()!=null){
			$.ajax({
				url:'jnexam_getjnexambyyear.htm',
				type:'post',
				dateType:'html',
				data:'year='+$(this).val()+'&s='+Math.round(Math.random() * 10000),
				success:function(msg){
					if(msg!=null&&msg!=''){
						$("#d_jn").attr("innerHTML", msg);
					}else{
						$("#d_jn").attr("innerHTML", "&nbsp;<select name='' id='s_jn' disabled='disabled'><option value=''>--请先选择年份--</option></select>");
					}
				}
			});
		}else{
			$("#d_jn").attr("innerHTML", "&nbsp;<select name='' id='s_jn' disabled='disabled'><option value=''>--请先选择年份--</option></select>");
		}
	});
	
	$("#s_cls").livequery("change",function(){
		if($(this).val()!=''){
			$.ajax({
				url:'candidates_getSBC.htm',
				type:'post',
				dateType:'html',
				data:'clsid='+$(this).val()+'&s='+Math.round(Math.random()*10000),
				success:function(msg){
					if(msg!=null&&msg!=''){
						$("#d_stu").attr("innerHTML",msg);
					}else{
						$("#d_stu").attr("innerHTML","<font style=\"color: red\">该班还没有学生!</font>");
					}
				}
			});
		}
	});

	
	$("#All").click(function() {
		if ($(this).attr("checked")) {
			$("input[name='selectedIds']").attr("checked", true);
			selectedIds = "";
			definitionCode = "";
			checkmeo();
		} else {
			$("input[name='selectedIds']").attr("checked", false);
			selectedIds = "";
			definitionCode = "";
		}
	});

	$("input[name='selectedIds']").livequery("click",function() {
		selectedIds="";
		ajustAllStatus();
		
		$("input[name='selectedIds']").each(function() {
			if ($(this).attr("checked")) {
				if ("" == selectedIds) {
					selectedIds += $(this).val();
				} else {
						selectedIds += "," + $(this).val();
				}
				
			}
		});
		
	});
	
	function ajustAllStatus() {
		var count = 0;
		var flag = false;
		$("input[name='selectedIds']").each(function() {
			if (!$(this).attr("checked")) {
				count++;
			}
		});
		if (count == 0) {
			flag = true;
		}
		$("#All").attr("checked", false);
	}
		
	function checkmeo(){
		$("input[name='selectedIds']").each(function() {
			if ($(this).attr("checked")) {
				if ("" == selectedIds) {
					selectedIds += $(this).val();
				} else {
						selectedIds += "," + $(this).val();
				}
				
			}
		});
	}
	
	$("#isok").click(function() {
		var jnid = $("#s_jn").attr("value");

		if (jnid == null || jnid == "") {
			alert("请选择一场技能考试");
			return;
		}
		if(selectedIds == ""){
			alert("请选择至少一名学生");
			return false;
		}
		 adminForm.submit();
	});
});