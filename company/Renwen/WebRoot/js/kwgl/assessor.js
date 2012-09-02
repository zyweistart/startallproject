$(document).ready(
		function() {
			$("#s_sch").change(
					function() {
						if ($(this).val != null && $(this).val() != '') {
							$.ajax({
								url : 'teacher_gettchbysch.htm',
								type : 'post',
								datetype : 'html',
								data : 'id=' + $(this).val() + '&s='
										+ Math.round(Math.random() * 10000),
								success : function(msg) {
									if (msg != null && msg != '') {
										$("#d_tch").attr("innerHTML", msg);
									}else{
										$("#d_tch").attr("innerHTML", "<select id=\"s_tch\" name=\"tchid\"><option value=\"\">请选择</option></select>");
									}
								}
							});
						}else{
							$("#d_tch").attr("innerHTML", "<select id=\"s_tch\" name=\"tchid\"><option value=\"\">请选择</option></select>");
						}
					});

			$("#s_tch").livequery(
					"change",
					function() {
						if ($(this).val() != null && $(this).val != '') {
							$.ajax({
								url : 'teacher_ajaxtch.htm',
								type : 'post',
								datetype : 'html',
								data : 'id=' + $(this).val() + '&s='
										+ Math.round(Math.random() * 10000),
								success : function(json) {
									jsonContent = eval("(" + json + ")");
									if(jsonContent!=null){
										$("#t_tchname").val(jsonContent.tname);
										if(jsonContent.tsex == '1'){
											$("#r_asex1").attr("checked",true);
										}
										if(jsonContent.tsex == '2'){
											$("#r_asex2").attr("checked",true);
										}
									}
								}
							});
						}
					});
		});