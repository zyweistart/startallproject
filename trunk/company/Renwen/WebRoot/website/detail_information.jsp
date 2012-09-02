<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
<table border="0" cellpadding="1" cellspacing="1" width="100%" class="tableBgColor">

	<tr>
     		<td align="right" width="15%" class="leftTdBgColor">
                &nbsp;菜单目录:
            </td>
            <td align="left" width="85%" class="rightTdBgColor" colspan="3">
            	${information.menuname }
            </td>
    </tr>
        <tr>
            <td align="right" width="15%" class="leftTdBgColor">
                &nbsp;标题名称:
            </td>
            <td align="left" width="85%" class="rightTdBgColor" colspan="3">
            	${information.title }
            </td>
        </tr>
        <tr>
            <td align="right" width="15%" class="leftTdBgColor">
                &nbsp;创建时间:
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
            	${information.createTime }
            </td>
            <td align="right" width="15%" class="leftTdBgColor">
                &nbsp;创建者:
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
            	${information.createUserName }
            </td>
        </tr>
        <tr style="display:none" id="scalepic">
            <td align="right" width="15%" class="leftTdBgColor">
                &nbsp;导引图：
            </td>
            <td align="left" width="85%" class="rightTdBgColor" colspan="3">
            	&nbsp;<input type="file" name="UploadScalePic" style="border: 1px solid #000000;" id="UploadScalePic"/>
                <label>(<font color="red">支持JPG、JPEG、PNG</font>)</label>
            </td>
        </tr>
        <tr>
			<td bgcolor="#FFFFFF" colspan="4">
				${information.content}
			</td>
		</tr>
</table>
<br>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
        <td align="center" width="30%">
            <input id="back" type="button" value="<fmt:message key="global.back" />" class="buttonStyle">
        </td>
    </tr>
</table>
<%@ include file="/common/footer.jsp"%>
