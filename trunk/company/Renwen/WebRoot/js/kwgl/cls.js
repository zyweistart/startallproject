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
		}
	});
	
});