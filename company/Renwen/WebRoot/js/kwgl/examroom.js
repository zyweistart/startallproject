$(document).ready(function() {
	$("#floor").change(function(){
		if($(this).val()!=''){
			$.ajax({
				url:'classroom_getClassRoom.htm',
				type:'post',
				dateType:'html',
				data:'floorid='+$(this).val()+'&s='+Math.round(Math.random() * 10000),
				success:function(msg){
					if(msg!=null&&msg!=''){
						$("#classroom").attr("innerHTML", msg);
					}
				}
			});
		}else{
			$("#classroom").attr("innerHTML", "&nbsp;<select name=\"classroomid\"	disabled=\"disabled\"><option value=\"\">--请选择--</option></select>");
		}
	});
	$("#b_cer2er").click(function(){
		window.open("certificate_cer2er.htm","","dialogWidth=800px;dialogHeight=600px;status=no;help=no;scroll=no");
	});
	$("#b_tch2er").click(function(){
		window.open("teacher_tch2er.htm","","dialogWidth=800px;dialogHeight=600px;status=no;help=no;scroll=no");
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
						$("#d_jn").attr("innerHTML", "&nbsp;<select name='' id='s_jn' disabled='disabled'><option value=''>--请先选择年份--</option></select>");
						$("#d_sub").attr("innerHTML","<select id='s_sub' name='examsubid' disabled='disabled'><option value=''>--请先选择考试名称--</option></select>");
					}
				}
			});
		}else{
			$("#d_jn").attr("innerHTML", "&nbsp;<select name='' id='s_jn' disabled='disabled'><option value=''>--请先选择年份--</option></select>");
			$("#d_sub").attr("innerHTML","<select id='s_sub' name='examsubid' disabled='disabled'><option value=''>--请先选择考试名称--</option></select>");
		}
	});
	
	$("#s_jn").livequery("change",function(){//根据下拉框的考试名称给其他相应的控件赋值
		if($(this).val()!=null&&$(this).val()!=''){
			$.ajax({
				url:'examsub_getebjn.htm',
				type:'post',
				dateType:'html',
				data:'id='+$(this).val()+'&s='+Math.round(Math.random() * 10000),
				success:function(msg){
					if(msg!=null&&msg!=''){
						$("#d_sub").attr("innerHTML",msg);
					}else{
						$("#d_sub").attr("innerHTML","<select id='s_sub' name='examsubid' disabled='disabled'><option value=''>--请先选择考试名称--</option></select>");
					}
				}
			});
		}else{
			$("#d_sub").attr("innerHTML","<select id='s_sub' name='examsubid' disabled='disabled'><option value=''>--请先选择考试名称--</option></select>");
		}
	});
});