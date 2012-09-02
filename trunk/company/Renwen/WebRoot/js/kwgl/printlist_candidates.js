$(document).ready(function(){
	var pv = $("#pn").attr("value");
	var pi  = parseInt(pv);
	$("#s").click(function(){
		$("#pn").attr("value",1);
		printlist.submit();
	});
	$("#q").click(function(){
		$("#pn").attr("value",(pi-1));
		printlist.submit();
	});
	$("#h").click(function(){
		$("#pn").attr("value",pi+1);
		printlist.submit();
	});
	$("#w").click(function(){
		$("#pn").attr("value",pi);
		printlist.submit();
	});
	$("#s_s").change(function(){
		$("#pn").attr(1);
		printlist.submit();
	});
	
	$("#b_print").click(function(){
		$("#sh").hide();
		window.print();
		$("#sh").show();
//		setTimeout($("#sh").show(),10000);
	});
});