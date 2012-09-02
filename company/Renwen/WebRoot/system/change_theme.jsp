<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
<script type="text/javascript">
<!--
	window.onload=function(){
		var theme = 'reddish';
		updateRadioElement('cssStyle', theme);
	}
	
	$().ready(function() {
		var options = {
			url : 'authority_changeTheme.htm',
			dataType : 'html',
			beforeSubmit : showRequest,
			success : showResponse
		};
		
		$("#changeForm").ajaxForm(options);
	});
	
	function showRequest() {
		return true;
	}
	
	function showResponse(msg) {
		if (msg == 'success') {
			alert('更改成功!');
			window.parent.frames.mainFrame.location.reload();
		    window.parent.frames.treeFrame.location.reload();
		    window.parent.frames.arrowFrame.location.reload();
		    window.parent.frames.menuFram.location.reload();
		} else {
			alert('保存失败!');
		}
	}
//-->
</script>
<s:form action="authority_changeTheme" id="changeForm">
<table border="0" cellpadding="1" cellspacing="1" width="100%" class="arrange_table">
	<tr>
		<td align="center" width="15%" class="arrange_header" height="22">
			主题名称
		</td>
		<td align="center" width="85%" class="arrange_header">
			主题样式
		</td>
	</tr>
	<tr>
		<td align="left" width="15%" class="arrange_td">
			<input type="radio" name="cssStyle" value="default" checked="checked"> 默认主题
		</td>
		<td align="left" width="85%" class="arrange_td1">
			<img src="${pageContext.request.contextPath}/images/theme/default.jpg" width="100%">
		</td>
	</tr>
	<tr>
		<td align="left" width="15%" class="arrange_td">
			<input type="radio" name="cssStyle" value="reddish"> 粉红回忆
		</td>
		<td align="left" width="85%" class="arrange_td1">
			<img src="${pageContext.request.contextPath}/images/theme/reddish.jpg" width="100%">
		</td>
	</tr>
	<tr>
		<td align="left" width="15%" class="arrange_td">
			<input type="radio" name="cssStyle" value="azure"> 碧海蓝天
		</td>
		<td align="left" width="85%" class="arrange_td1">
			<img src="${pageContext.request.contextPath}/images/theme/azure.jpg" width="100%">
		</td>
	</tr>
	<tr>
		<td align="left" width="15%" class="arrange_td">
			<input type="radio" name="cssStyle" value="classcial"> 古色古香
		</td>
		<td align="left" width="85%" class="arrange_td1">
			<img src="${pageContext.request.contextPath}/images/theme/classcial.jpg" width="100%">
		</td>
	</tr>
</table>
<br>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
            <td align="center" width="30%">
                <input type="submit" value="更改确认" class="buttonStyle">
                <input type="reset" value="取消" class="buttonStyle">
            </td>
        </tr>
    </table>
</s:form>
<%@ include file="/common/footer.jsp"%>