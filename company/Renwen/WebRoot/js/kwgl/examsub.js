$(document).ready(function(){
	
	/**
	 * 动态生成年份的下拉框
	 */
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
						$("#d_jn").attr("innerHTML", "&nbsp;<select name='' id='s_jn' disabled='disabled'><option value=''>--请先选择日期--</option></select>");
						claerit();
					}
				}
			});
		}else{
			$("#d_jn").attr("innerHTML", "&nbsp;<select name='' id='s_jn' disabled='disabled'><option value=''>--请先选择日期--</option></select>");
			claerit();
		}
	});
	
	
	$("#t_date").change(function(){//根据日期更新下拉框的考试名称-----------此方法不用，用上面的代替了
		if($(this).val()!=null){
			if(($(this).val()> (ty-5))&&($(this).val() <= (ty+5))){
				$.ajax({
					url:'jnexam_getjnexambyyear.htm',
					type:'post',
					dateType:'html',
					data:'year='+$(this).val()+'&s='+Math.round(Math.random() * 10000),
					success:function(msg){
						if(msg!=null&&msg!=''){
							$("#d_jn").attr("innerHTML", msg);
						}else{
							$("#d_jn").attr("innerHTML", "&nbsp;<select name='' id='s_jn' disabled='disabled'><option value=''>--请先选择日期--</option></select>");
							clearit();
						}
					}
				});
			}else{
				$("#d_jn").attr("innerHTML", "&nbsp;<select name='' id='s_jn' disabled='disabled'><option value=''>--请先选择日期--</option></select>");
				clearit();
			}
		}else{
			$("#d_jn").attr("innerHTML", "&nbsp;<select name='' id='s_jn' disabled='disabled'><option value=''>--请先选择日期--</option></select>");
			clearit();
		}
	});
	
	$("#s_jn").livequery("change",function(){//根据下拉框的考试名称给其他相应的控件赋值
		if($(this).val()!=null&&$(this).val()!=''){
			$.ajax({
				url:'examsub_setvaluebyjnexam.htm',
				type:'post',
				dateType:'html',
				data:'id='+$(this).val()+'&s='+Math.round(Math.random() * 10000),
				success:function(json){
					jsonContent = eval("(" + json + ")");
					if(jsonContent!=null){
						$("#s_jti").attr("value",jsonContent.id);
						$("#s_jtl").attr("value",jsonContent.jtlevel);
						$("#t_ed").val(jsonContent.jdate);
						$("#t_ea").val(jsonContent.jaddress);
						$("#t_re").val(jsonContent.remark);
					}else{
						clearit();
					}
				}
			});
		}else{
			clearit();
		}
	});
	
	function clearit(){//清空
		$("#s_jti").attr("value","");
		$("#s_jtl").attr("value",1);
		$("#t_ed").val("");
		$("#t_et").val("");
		$("#t_eet").val("");
		$("#t_ea").val("");
		$("#t_re").val("");
	}
});