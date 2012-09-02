$(document).ready(function() {
	var types=$("#types").val();
	if(types!='' && types=='99'){
		$("td[id='xzbn']").show();
		$("td[id='xzdc']").hide();
		$("#prodfee").val("");
		$("#loadfee").val("");
	}else if(types!='' && types=='1'){
		$("td[id='xzdc']").show();
		$("td[id='xzbn']").hide();
		$("#packfee").val("");
		$("#modifycs").val("");
	}
	
	
//	$("#tjfixed").click(function(){
//		var fixed=$("#fixed").val();   //添加内容
//		var fixednr=$("#fixednr").text(); //文本框
//		var fixednl=$("#fixednl").text(); //div
//		if(fixed==''){
//			alert("没有可添加的号码清单!");
//			$("#fixed").focus();
//			return false;
//		}
//		if(fixednl==''){
//			$("#fixednl").attr("innerHTML",fixed);
//			$("#fixednr").text(fixed);
//		}else{
//	     $("#fixednl").attr("innerHTML",""+fixednl+","+fixed);
//		 $("#fixednr").text(fixednr+","+fixed);
//		}
//		$("#fixed").val("");
//	});
//	
//	
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
//	
	
	
	
});