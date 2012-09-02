$(document)
		.ready(
				function() {

					var strpath = $("#strpath").val();

					$("#isok")
							.click(
									function() {
										var zwname = $("#uname").val();
										var userCls = $("#userCls").val();
										var userTeacther = $("#userTeacther").val();
										var userYear = $("#userYear").val();
										var userXq = document.getElementsByName('userXq')[0].checked; 
										if(userXq==false){
											userXq=2;
										}else{
											userXq=1;
										}
										var identity = $("#identity").val();
										var username = $("#username").val();
										var password = $("#password").val();
										var rpassword = $("#rpassword").val();
										var usernamexx = $("#usernamexx").val();
										var passwordxx = $("#passwordxx").val();
										var rpasswordxx = $("#rpasswordxx").val();
										
										
										if (usernamexx == '') {
											$("#username").focus();
											return false;
										}
										if (passwordxx == '') {
											$("#password").focus();
											return false;
										}
										if (rpasswordxx == '') {
											$("#rpassword").focus();
											return false;
										}
										if (username == '') {
											$("#username").focus();
											return false;
										}
										if (password == '') {
											$("#password").focus();
											return false;
										}
										if (rpassword == '') {
											$("#rpassword").focus();
											return false;
										}

										$("#usernamexx").val("");
										
										$
												.ajax( {
													url : 'users_zcUsers.htm',
													type : 'post',
													dataType : 'html',
													data : 'username='
															+ username
															+ '&password='
															+ password
															+ '&zwname='
															+ zwname
															+ '&userCls='
															+ userCls
															+ '&userTeacther='
															+ userTeacther
															+ '&userYear='
															+ userYear
															+ '&userXq='
															+ userXq
															+ '&identity='
															+ identity
															+ '&s='
															+ Math
																	.round(Math
																			.random() * 10000),
													success : function(msg) {
														if (msg != null
																&& msg != '') {
															if (msg == '1') {
																alert("错误注册");
															} else {
																// alert("注册成功");
																window.location.href = msg;
															}
														} else {
															$("#usernamexx")
																	.val("");
														}
													}
												});

									});

					$("#username")
							.change(
									function() {
										var username = $("#username").val();
										if (username == '') {
											$("#usernamexx")
													.attr("innerHTML",
															"<font color=\"red\" size=\"2\">注意:不能为空</font>");
											$("#usernamexx").val("");
											return false;
										} else {
											$("#usernamexx").attr("innerHTML",
													"");
											$("#usernamexx").val("1");
										}

										$
												.ajax( {
													url : 'users_checkname.htm',
													type : 'post',
													dataType : 'html',
													data : 'username='
															+ $("#username")
																	.val()
															+ '&s='
															+ Math
																	.round(Math
																			.random() * 10000),
													success : function(msg) {
														if (msg != null
																&& msg != '') {
															if (msg == '1') {
																$("#usernamexx")
																		.attr(
																				"innerHTML",
																				"<font color=\"red\" size=\"2\">注意:已使用</font>");
																$("#usernamexx")
																		.val("");
															} else {
																$("#usernamexx")
																		.attr(
																				"innerHTML",
																				"");
																$("#usernamexx")
																		.val(
																				"1");
															}
														} else {
															$("#usernamexx")
																	.val("");
														}
													}
												});

									});

					// $("#username")
					// .focus(
					// function() {
					// var username = $("#username").val();
					// if (username == '') {
					// $("#usernamexx")
					// .attr("innerHTML",
					// "<font color=\"red\">不能为空</font>");
					// $("#usernamexx").val("");
					// return false;
					// } else {
					// $("#usernamexx").attr("innerHTML",
					// "");
					// $("#usernamexx").val("1");
					// }
					//
					// $
					// .ajax( {
					// url : 'users_checkname.htm',
					// type : 'post',
					// dataType : 'html',
					// data : 'username='
					// + $("#username")
					// .val()
					// + '&s='
					// + Math
					// .round(Math
					// .random() * 10000),
					// success : function(msg) {
					// if (msg != null
					// && msg != '') {
					// if (msg == '1') {
					// $("#usernamexx")
					// .attr(
					// "innerHTML",
					// "<font color=\"red\">已使用</font>");
					// $("#usernamexx")
					// .val("");
					// } else {
					// $("#usernamexx")
					// .attr(
					// "innerHTML",
					// "");
					// $("#usernamexx")
					// .val(
					// "1");
					// }
					// } else {
					// $("#usernamexx")
					// .val("");
					// }
					// }
					// });
					//
					// });

					$("#password")
							.change(
									function() {
										var password = $("#password").val();
										var rpassword = $("#rpassword").val();

										if (password == '') {
											$("#passwordxx")
													.attr("innerHTML",
															"<font color=\"red\" size=\"2\">注意:不能为空</font>");

											if (rpassword != '') {
												$("#rpasswordxx").val("1");
											}

											$("#passwordxx").val("");
											return false;
										} else {
											$("#passwordxx").attr("innerHTML",
													"");
											$("#passwordxx").val("1");
										}

										if (rpassword == '') {
											return false;
										}

										if (password != rpassword) {
											$("#passwordxx")
													.attr("innerHTML",
															"<font color=\"red\" size=\"2\">注意:密码不一致</font>");
											$("#passwordxx").val("");
										} else {
											$("#passwordxx").attr("innerHTML",
													"");
											$("#passwordxx").val("1");
											$("#rpasswordxx").val("1");
										}

									});

					$("#rpassword")
							.change(
									function() {
										var password = $("#password").val();
										var rpassword = $("#rpassword").val();

										if (rpassword == '') {
											$("#passwordxx").attr("innerHTML",
													"");
											$("#rpasswordxx")
													.attr("innerHTML",
															"<font color=\"red\" size=\"2\">注意:不能为空</font>");
											$("#rpasswordxx").val("");

											if (password != '') {
												$("#passwordxx").val("1");
											}

											return false;
										} else {
											$("#rpasswordxx").attr("innerHTML",
													"");
											$("#rpasswordxx").val("1");
										}

										if (password == '') {
											return false;
										}

										if (password != rpassword) {
											$("#passwordxx")
													.attr("innerHTML",
															"<font color=\"red\" size=\"2\">注意:密码不一致</font>");
											$("#rpasswordxx").val("");
										} else {
											$("#passwordxx").attr("innerHTML",
													"");
											$("#passwordxx").val("1");
											$("#rpasswordxx").val("1");
										}

									});

				});