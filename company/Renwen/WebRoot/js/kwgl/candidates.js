$(document).ready(function(){
	
	var selc = $("#s_ss");
	selc.empty();
	var ty = new Date().getYear();
	for(var i = (ty-5);i<=(ty+1);i++){
		var option = $("<option>").text(i+"年").val(i);
	    selc.append(option);
	}
	$("#s_ss").attr("value",ty-1);
	
	
	$("#r1").click(function() {
		$("#dddd").show();
	});
	$("#r2").click(function() {
		$("#dddd").hide();
	});
	
	
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
						$("#d_jn").attr("innerHTML", "&nbsp;<select name='jnid' id='s_jn' disabled='disabled'><option value=''>--请先选择年份--</option></select>");
					}
				}
			});
		}else{
			$("#d_jn").attr("innerHTML", "&nbsp;<select name='jnid' id='s_jn' disabled='disabled'><option value=''>--请先选择年份--</option></select>");
		}
	});
	
	
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
			$("#d_pro").attr("innerHTML", "&nbsp;<select name=\"s_pro\" id=\"s_pro\"	disabled=\"disabled\"><option value=\"\">--请先选择学院--</option></select>");
			$("#d_cls").attr("innerHTML", "&nbsp;<select name=\"s_cls\" id=\"s_cls\"	disabled=\"disabled\"><option value=\"\">--请先选择专业--</option></select>");
			$("#d_stu").attr("innerHTML", "&nbsp;<select name=\"s_stu\"	id=\"s_stu\" disabled=\"disabled\"><option value=\"\">--请先选择班级--</option></select>");
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
			$("#d_cls").attr("innerHTML", "&nbsp;<select name=\"s_cls\" id=\"s_cls\" disabled=\"disabled\"><option value=\"\">--请先选择专业--</option></select>");
			$("#d_stu").attr("innerHTML", "&nbsp;<select name=\"s_stu\"	id=\"s_stu\" disabled=\"disabled\"><option value=\"\">--请先选择班级--</option></select>");
		}
	});
	
	
	$("#s_cls").livequery("change",function(){
		if($(this).val()!=''){
			$.ajax({
				url:'student_getS.htm',
				type:'post',
				dateType:'html',
				data:'clsid='+$(this).val()+'&s='+Math.round(Math.random() * 10000),
				success:function(msg){
					if(msg!=null&&msg!=''){
						$("#d_stu").attr("innerHTML", msg);
					}
				}
			});
		}else{
			$("#d_stu").attr("innerHTML", "&nbsp;<select name=\"s_stu\"	disabled=\"disabled\"><option value=\"\">--请先选择班级--</option></select>");
		}
	});
	
	
	$("#s_stu").livequery("change",function(){
		if($(this).val()!=''){
			$.ajax({
				url:'student_getD.htm',
				type:'post',
				dateType:'html',
				data:'id='+$(this).val()+'&s='+Math.round(Math.random() * 10000),
				success:function(json){
					jsonContent = eval("(" + json + ")");
					if(jsonContent!=null){
						$("#t_cname").val(jsonContent.stuname);
						$("#t_idcode").val(jsonContent.idcode);
						$("#t_cbirth").val(jsonContent.birth);
						if(jsonContent.sex == '1'){
							$("#s_csex1").attr("checked",true);
						}
						if(jsonContent.sex == '2'){
							$("#s_csex2").attr("checked",true);
						}
					}
				}
			});
		}
	});
});