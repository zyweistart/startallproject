$(document).ready(function() {
	$("#s_sch").change(function(){
		if($(this).val()!=''){
			$.ajax({
				url:'profession_getPro.htm',
				type:'post',
				dateType:'html',
				data:'schid='+$(this).val()+'&s='+Math.round(Math.random() * 10000),
				success:function(msg){
					if(msg!=null&&msg!=''){
						$("#d_pro").attr("innerHTML", msg);
					}
				}
			});
		}else{
			$("#d_pro").attr("innerHTML", "&nbsp;<select name=\"s_pro\"	disabled=\"disabled\"><option value=\"\">--请先选择学院--</option></select>");
			$("#d_cls").attr("innerHTML", "&nbsp;<select name=\"s_cls\"	disabled=\"disabled\"><option value=\"\">--请先选择专业--</option></select>");
		}
	});
	
	$("#s_pro").livequery("change",function(){
		if($(this).val()!=''){
			$.ajax({
				url:'cls_getC.htm',
				type:'post',
				dateType:'html',
				data:'proid='+$(this).val()+'&s='+Math.round(Math.random() * 10000),
				success:function(msg){
					if(msg!=null&&msg!=''){
						$("#d_cls").attr("innerHTML", msg);
					}
				}
			});
		}else{
			$("#d_cls").attr("innerHTML", "&nbsp;<select name=\"s_cls\"	disabled=\"disabled\"><option value=\"\">--请先选择专业--</option></select>");
		}
	});
	
	$("#instu").click(
			function() {
				window.open('student_showexcelimport.htm', '',
						'height=200px, width=450px');
			});
});