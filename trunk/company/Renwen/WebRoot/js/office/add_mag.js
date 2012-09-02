$(document).ready(function() {
	
	$("#type1").click(function(){
//		alert($(this).select().val());
		$("#xzts").attr("innerHTML","---- <b>单次</b> ----");
		$("td[id='xzdc']").show();
		$("td[id='xzbn']").hide();
		$("#packfee").val("");
		$("#modifycs").val("");
	});
	
	$("#type2").click(function(){
//		alert($(this).select().val());
		$("#xzts").attr("innerHTML","---- <b>包年</b> ----");
		$("td[id='xzbn']").show();
		$("td[id='xzdc']").hide();
		$("#prodfee").val("");
		$("#loadfee").val("");
	});
	
//	$("#tjfixed").click(function(){
//		var fixed=$("#fixed").val();
//		var fixednr=$("#fixednr").text();
//		var fixednl=$("#fixednl").text();
//		if(fixed==''){
//			alert("没有可添加的固话清单!");
//			$("#fixed").focus();
//			return false;
//		}
//		if(fixednl==''){
//			$("#fixednl").text(fixed);
//			$("#fixednr").text(fixed);
//		}else{
//	     $("#fixednl").text(fixednl+","+fixed);
//		 $("#fixednr").text(fixednr+","+fixed);
//		}
//		$("#fixed").val("");
//	});
//	
//	//固话
//	$("#xgfixed").click(function(){
//		$("#fixednl").hide();
//		$("#fixednr").show();
//	});
//	
//	$("#fixednr").blur(function(){
//	 var fixednr=$("#fixednr").text();
//	  $("#fixednl").text(fixednr);
//	  $("#fixednr").hide();
//	  $("#fixednl").show();	
//	});
//	
//	
//	//手机号码
//	$("#xgfixedsj").click(function(){
//		$("#fixednlsj").hide();
//		$("#fixednrsj").show();
//	});
//	
//	$("#fixednrsj").blur(function(){
//		 var fixednrsj=$("#fixednrsj").text();
//		  $("#fixednlsj").text(fixednrsj);
//		  $("#fixednrsj").hide();
//		  $("#fixednlsj").show();	
//		});
//	
//	$("#tjfixedsj").click(function(){
//		var fixedsj=$("#fixedsj").val();
//		var fixednrsj=$("#fixednrsj").text();
//		var fixednlsj=$("#fixednlsj").text();
//		if(fixedsj==''){
//			alert("没有可添加的手机号码清单!");
//			$("#fixedsj").focus();
//			return false;
//		}
//		if(fixednlsj==''){
//			$("#fixednlsj").text(fixedsj);
//			$("#fixednrsj").text(fixedsj);
//		}else{
//	     $("#fixednlsj").text(fixednlsj+","+fixedsj);
//		 $("#fixednrsj").text(fixednrsj+","+fixedsj);
//		}
//		$("#fixedsj").val("");
//	});
});