$(document).ready(function(){
	var selc = $("#s_ss");
	selc.empty();
	var ty = new Date().getYear();
	for(var i = (ty-5);i<=(ty+1);i++){
		var option = $("<option>").text(i+"年").val(i);
	    selc.append(option);
	}
	$("#s_ss").attr("value",ty-1);
	
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
						clearit();
					}
				}
			});
		}else{
			$("#d_jn").attr("innerHTML", "&nbsp;<select name='' id='s_jn' disabled='disabled'><option value=''>--请先选择年份--</option></select>");
			$("#d_sub").attr("innerHTML","<select id='s_sub' name='examsubid' disabled='disabled'><option value=''>--请先选择考试名称--</option></select>");
			clearit();
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
						clearit();
					}
				}
			});
		}else{
			$("#d_sub").attr("innerHTML","<select id='s_sub' name='examsubid' disabled='disabled'><option value=''>--请先选择考试名称--</option></select>");
			clearit();
		}
	});
	
	$("#s_sub").livequery("change",function(){//根据下拉框的考试科目给其他相应的控件赋值
		if($(this).val()!=null&&$(this).val()!=''){
			$.ajax({
				url:'examsub_showexamsub.htm',
				type:'post',
				dateType:'html',
				data:'id='+$(this).val()+'&s='+Math.round(Math.random() * 10000),
				success:function(json){
					jsonContent = eval("("+json+")");
					$("#d_jtn").attr("innerHTML",jsonContent.jtname);//工种
					if(jsonContent.jtlevel==1){$("#d_jtl").attr("innerHTML","一级");}
					if(jsonContent.jtlevel==2){$("#d_jtl").attr("innerHTML","二级");}
					if(jsonContent.jtlevel==3){$("#d_jtl").attr("innerHTML","三级");}
					if(jsonContent.jtlevel==4){$("#d_jtl").attr("innerHTML","四级");}
					if(jsonContent.jtlevel==5){$("#d_jtl").attr("innerHTML","五级");}
					if(jsonContent.jtlevel==6){$("#d_jtl").attr("innerHTML","无级别");}
					$("#d_et").attr("innerHTML",jsonContent.edate+"   "+jsonContent.etime+"-"+jsonContent.eetime);//考试日期
					$("#d_ea").attr("innerHTML",jsonContent.eaddress);//考试时间
					$("#d_pc").attr("innerHTML",jsonContent.id);//报名人数
				}
			});
		}else{
			clearit();
		}
	});
	
	$("#cr").livequery("change",function(){
		if($(this).val()!=null&&$(this).val()!=''){
			$.ajax({
				url:'classroom_ajaxbyid.htm',
				type:'post',
				dateType:'html',
				data:'id='+$(this).val()+'&s='+Math.round(Math.random()*10000),
				success:function(json){
					jsonContent=eval("("+json+")");
					if(jsonContent != null){
						$("#h_seatcount").val(jsonContent.i);
						$("#h_clsname").val(jsonContent.roomname);
						$("#h_clsid").val(jsonContent.id);
					}
				}
			});
		}
	});
	
	
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
	

	
	function clearit(){
		$("#d_jtn").attr("innerHTML","");//工种
		$("#d_jtl").attr("innerHTML","");//等级
		$("#d_et").attr("innerHTML","");//考试日期
		$("#d_ea").attr("innerHTML","");//考试时间
		$("#d_pc").attr("innerHTML","");//报名人数
		$("#d_cls").attr("innerHTML","");//教室
	}
	$("#isok").click(function(){
		var ids = new Array();
		$("input:hidden[name='clsids']").each(function(){
			ids.push( "["+$(this).val()+"]");
		});
		if(ids==null||ids == ""){
			alert("请选择教室！");
			return ;
		}
		var n = $("#d_pc").attr("innerHTML");
		if(n==null||n==""||n<=0){
			alert("请确定本场考试已选择且有报考的考生！");
			return ;
		}
		ec.submit();
	});
});