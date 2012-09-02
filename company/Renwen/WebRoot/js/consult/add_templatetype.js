$(document).ready(function() {

					$("#isok").click(function() {
										var title = $("#title").val();
										var mbPath = $("#mbPath").val();
										if (title == '') {
											$("#title").focus();
											$("#titlexx").attr("innerHTML","<font color=\"red\">模板描述名 不为空</font>");
											return false;
										} else {
											$("#titlexx").attr("innerHTML", "");
										}
										if (mbPath == '') {
											$("#mbPath").focus();
											$("#mbPathxx").attr("innerHTML","<font color=\"red\">模板路径不可为空</font>");
											if(!confirm("模板页面为空，是否确定提交?")){
												return false;	
											}
											
										} else {
											$("#mbPathxx").attr("innerHTML", "");
										}
										ec.submit();
									});

					$("#title").change(function() {
										var title = $("#title").val();
										if (title == '') {
											$("#title").focus();
											$("#titlexx").attr("innerHTML","<font color=\"red\">模板描述名 不为空</font>");
											return false;
										} else {
											$("#titlexx").attr("innerHTML", "");
										}
									});

					$("#mbPath").change(function() {
										var mbPath = $("#mbPath").val();

										if (mbPath == '') {
											
											$("#mbPathxx").attr("innerHTML","<font color=\"red\">模板路径不可为空</font>");
											return false;
										} else {
											$("#mbPathxx").attr("innerHTML", "");
										}

										$.ajax( {
													url : 'templatetype_judgePath.htm',
													type : 'post',
													dataType : 'html',
													data : 'mbPath='
															+ mbPath
															+ '&s='
															+ Math.round(Math.random() * 10000),
													success : function(msg) {

														if (msg != null
																&& msg != '') {
															if (msg == '1') {
																$("#mbPathxx").attr("innerHTML","<font color=\"green\">可用</font>");
																$("#mbPathxx").val("1");
															} else {
																$("#mbPathxx").attr("innerHTML","<font color=\"red\">模板文件,不可用</font>");
																$("#mbPathxx").val("");
															}
														} else {
															$("#mbPathxx").attr("innerHTML","<font color=\"red\">出错</font>");
															$("#mbPathxx").val("");
														}
													}
												});

									});
					
					$("#mbPath").blur(function() {
								var mbPath = $("#mbPath").val();
								if (mbPath == '') {									
									$("#mbPathxx").attr("innerHTML","<font color=\"red\">模板路径不可为空</font>");
									return false;
								} else {
									$("#mbPathxx").attr("innerHTML", "");
								}
								$.ajax( {
											url : 'templatetype_judgePath.htm',
											type : 'post',
											dataType : 'html',
											data : 'mbPath='
													+ mbPath
													+ '&s='
													+ Math.round(Math.random() * 10000),
											success : function(msg) {
												if (msg != null && msg != '') {
													if (msg == '1') {
														$("#mbPathxx").attr("innerHTML","<font color=\"green\">可用</font>");
														$("#mbPathxx").val("1");
													} else {
														$("#mbPathxx").attr("innerHTML","<font color=\"red\">模板文件,不可用</font>");
														$("#mbPathxx").val("");
													}
												} else {
													$("#mbPathxx").attr("innerHTML","<font color=\"red\">出错</font>");
													$("#mbPathxx").val("");
												}
											}
										});

							});

				});