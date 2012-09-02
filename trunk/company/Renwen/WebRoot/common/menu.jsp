<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Map"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/common/jquery/jquery-1.3.2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/common/global/common.js"></script>
<link rel="stylesheet" href='${pageContext.request.contextPath}/css/default/css.css' type="text/css">
<%
    response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0
    response.setDateHeader("Expires", -1);
    
    Map operators = (Map)application.getAttribute("operatorList");
    int number = 0;
    if(operators != null){
    	number = operators.size();
    }
%>
<html>
    <head>
        <link href="${pageContext.request.contextPath}/css/default/css.css" rel="stylesheet" type="text/css">
        <STYLE>
            a:link,a:visited {
                font-size: 12px;
                font-family: "新宋体";
                color: #333333;
                text-decoration: none;
            }
            a:hover {
                color: #FF6C00;
                text-decoration: none;
            }
            .menuBgA {
                background: url(../images/default/menu-bg.gif) no-repeat left;
                text-align: center;
                width: 70px;
                cursor: hand;
            }
        </STYLE>
    </head>
    <body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0" >
        <form method="post" name="styleForm" action="">
            <div class="top_img">
                <img src="${pageContext.request.contextPath}/images/default/top-img.gif" height="77" width="100%" />
            </div>
            <div align="center">
                <table width="100%" height="28" border="0" cellpadding="0" cellspacing="0" class="menuSpace">
                    <tr>
                        <td width="15%" class="menuSpace" align="center">
                            <img src="${pageContext.request.contextPath}/images/icon/user.gif">欢迎您
                            <font color="red">${currentUser.trueName}</font> 在线：
                            <font color="red"><span id="ol"><%=number %></span></font>
                        </td>
                        <td width="4%">
                            <span style="cursor: hand">
                                <img src="${pageContext.request.contextPath}/images/magnifier_medium_left.png" onclick="openDialog('onLineOperators_getOnlineUser.htm',350,650)" alt="查看在线人员">
                            </span>
                        </td>
                        <!-- 
                        <td width="71" class="menuBg" onmouseover="this.className='menuOver'" onmouseout="this.className='menuBg'">
                           <span style="cursor: hand" onclick="changeTheme();">更改主题</span>
                       </td>
                       -->
                       <td width="71" class="menuBg" onmouseover="this.className='menuOver'" onmouseout="this.className='menuBg'">
                           <span style="cursor: hand" onclick="changePassword();">更改密码</span>
                       </td>
                        <td colspan="3"></td>
                        <td width="20"><img src="${pageContext.request.contextPath}/images/icon/statusHomeOver.gif" style="cursor:hand;" onclick="loadFrames();" alt="首页"></td>
                        <td width="20"><img src="${pageContext.request.contextPath}/images/icon/statusBackOver.gif" style="cursor:hand;" alt="后退"></td>
                        <td width="20"><img src="${pageContext.request.contextPath}/images/icon/statusGoOver.gif" style="cursor:hand;" alt="前进"></td>
                        <td width="20"><img src="${pageContext.request.contextPath}/images/icon/statusRefurbishOver.gif" style="cursor:hand;" onclick="refresh();" alt="刷新"></td>
                        <td width="20"><span style="cursor: hand"><img src="${pageContext.request.contextPath}/images/icon/statusCloseOver.gif" style="cursor:hand;" alt="退出" onclick="exitSystem();"></span></td>
                        <td width="10">&nbsp;</td>
                    </tr>
                </table>
            </div>
        </form>
    </body>
</html>
<script type="text/javascript">
<!--
	setInterval('loadCheck()', 3600 * 2);
	
	window.onunload = onunload_handler;   
	
	function onunload_handler(){   
		var n = window.event.screenX - window.screenLeft; 
		var b = n > document.documentElement.scrollWidth-20; 
		if(b && window.event.clientY < 0 || window.event.altKey) 
		{ 
			$.post("onLineOperators_removeOnLineOperator.htm");
		} 
	} 
	 
	function loadCheck(){
		 $.ajax({
	        url : 'onLineOperators_getNumber.htm',
	        type : 'post',
	        dataType : 'html',
	        data : 's=' + Math.round(Math.random() * 10000),
	        success : function(msg) {
	        	document.getElementById("ol").innerHTML = msg;
	        }
	    });
	}

	function changeTheme(){
		window.open("${pageContext.request.contextPath}/system/change_theme.jsp","mainFrame");
	}
	
	function changePassword(){
		window.open("${pageContext.request.contextPath}/system/change_password.jsp","mainFrame");	
	}
	
	function exitSystem(){
		if(confirm("确定要退出吗?")){
			$.ajax({
		        url : 'onLineOperators_removeOnLineOperator.htm',
		        type : 'post',
		        dataType : 'html',
		        data : 's=' + Math.round(Math.random() * 10000),
		        success : function(json) {}
		    });
			CloseWindow();
		}
	}
	
	function CloseWindow(){
	    top.open('','_self','');
	    top.close();
	}
	
	function refresh()
	{    
	    window.parent.frames.mainFrame.location.reload();
	}
	
	function loadFrames(){
		window.parent.frames.mainFrame.location = '${pageContext.request.contextPath}/default.jsp';
		window.parent.frames.treeFrame.location = '${pageContext.request.contextPath}/dispatch_showLeft.htm';
	}
//-->
</script>
