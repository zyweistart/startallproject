<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
<script type="text/javascript">
<!--
	$().ready(function() {
		var options = {
			url : 'authority_changePassword.htm',
			dataType : 'html',
			beforeSubmit : showRequest,
			success : showResponse
		};
		
		$("#authForm").ajaxForm(options);
	});
	
	function showRequest() {
		var oldPass = $("#oldPassword").val();
		var newPass = $("#newPassword").val();
		var rePass = $("#rePassword").val();
		if(oldPass == ''){
			alert('请输入旧密码!');
			return false;
		}
		if(newPass == ''){
			alert('请输入新密码!');
			return false;
		}
		if(rePass == ''){
			alert('请输入确认密码!');
			return false;
		}
		if(newPass != rePass){
			alert("新密码和确认码输入不一致,请核查!");
			return false;
		}
		
		return true;
	}
	
	function showResponse(msg) {
		if (msg == 'success') {
			alert('密码修改成功!');
		} else {
			alert('密码修改失败,原因: '+ msg);
		}
	}
//-->
</script>
<s:form action="authority_changePassword" id="authForm">
	<table border="0" cellpadding="1" cellspacing="1" width="100%"
		class="tableBgColor">
		<tr>
			<td align="center" width="100%" colspan="2" height="25" class="leftTdBgColor">
			        <div style="rightTdBgColor"><b>外协工系统用户密码修改</b></div>
			</td>
		</tr>
		<tr>
            <td align="right" width="40%" height="25" class="leftTdBgColor">
               	用户名<fmt:message key="global.colon"/>
            </td>
            <td align="left" width="60%" class="rightTdBgColor">
                <input type="text" name="code" class="textInput" readonly="readonly" value="${currentUser.code}" style="width:35%;" />
                <fmt:message key="global.redstar"/>
            </td>
        </tr>
		<tr>
			<td align="right" height="25" class="leftTdBgColor">
				真实姓名<fmt:message key="global.colon"/>
			</td>
			<td align="left" class="rightTdBgColor">
				<input type="text" name="name" class="textInput" readonly="readonly" value="${currentUser.trueName}" style="width:35%;" />
			</td>
		</tr>
		<tr>
			<td align="right" height="25" class="leftTdBgColor">
				旧密码<fmt:message key="global.colon" />
			</td>
			<td align="left" class="rightTdBgColor">
				<input type="text" name="oldPassword" id="oldPassword" class="textInput" style="width:35%;" />
			</td>
		</tr>
		<tr>
			<td align="right" height="25" class="leftTdBgColor">
				新密码<fmt:message key="global.colon" />
			</td>
			<td align="left" class="rightTdBgColor">
			    <input type="text" name="newPassword" id="newPassword" class="textInput" style="width:35%;" />
			</td>
		</tr>
		<tr>
			<td align="right" height="25" class="leftTdBgColor">
				密码确认<fmt:message key="global.colon" />
			</td>
			<td align="left" class="rightTdBgColor">
				<input type="text" name="rePassword" id="rePassword" class="textInput" style="width:35%;" />
			</td>
		</tr>
	</table>
	<br>
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
            <td align="center" width="30%">
                <input type="submit" value="<fmt:message key="global.save" />" class="buttonStyle">
                &nbsp;
                <input type="reset" value="<fmt:message key="global.reset" />" class="buttonStyle">
            </td>
        </tr>
    </table>
</s:form>
<%@ include file="/common/footer.jsp"%>