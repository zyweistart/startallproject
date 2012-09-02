$(document).ready(function() {
	$("#b_stu").click(function(){
		window.open("student_stu4cer.htm", "", "dialogWidth=800px;dialogHeight=600px;status=no;help=no;scroll=no");
	});
	$("#isok").click(function(){
		adminForm.submit();
		self.parent.frames["up_iframe"].location.reload();
//		window.open("student_stu4cer.htm", "", "dialogWidth=800px;dialogHeight=600px;status=no;help=no;scroll=no");
//		alert("ok")
	});
});
