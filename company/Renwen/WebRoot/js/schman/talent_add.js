$(document).ready(function() {
	
	$("#isok").click(function() {
		var talentPost=$("#talentPost").val();
		var talentQuantity=$("#talentQuantity").val();
		var talentWorkplace=$("#talentWorkplace").val();
		var talentDepartment=$("#talentDepartment").val();
		if(talentPost==''){
			alert("请填写招聘职位！");
			$("#talentPost").focus();
			return false;
		}
		if(talentQuantity==''){
			alert("请填写需求数！");
			$("#talentQuantity").focus();
			return false;
		}
		if(talentWorkplace==''){
			alert("请填写工作地点！");
			$("#talentWorkplace").focus();
			return false;
		}
		if(talentDepartment==''){
			alert("请填写所属部门！");
			$("#talentDepartment").focus();
			return false;
		}
		
		ec.submit();
		
	});
	
});