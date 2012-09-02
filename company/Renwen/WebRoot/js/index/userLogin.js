$(document).ready(
		function() {

			$("#issok").click(function() {
				var username = $("#username").val();
				var password = $("#password").val();
				if (username == '') {
					alert("不能为空");
					$("#username").focus();
					return false;
				}

				if (password == '') {
					alert("不能为空");
					$("#password").focus();
					return false;
				}

				eec.submit();

			});

			$("#exitOk").click(
					function() {
						// 获取当前网址
						var curWwwPath = window.document.location.href;
						// 获取主机地址之后的目录
						var pathName = window.document.location.pathname;
						var pos = curWwwPath.indexOf(pathName);
						// 获取主机地址
						var localhostPaht = curWwwPath.substring(0, pos);
						// 获取带"/"的项目名
						var projectName = pathName.substring(0, pathName
								.substr(1).indexOf('/') + 1);
						window.location.href = projectName + "/users_exit.htm";
					});

		});