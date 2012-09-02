<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/website/information.js"></script>
<s:form action="information_save" method="post" validate="true" enctype="multipart/form-data"  id="ec">
    <s:token />
    <s:hidden name="inputPage" value="/website/add_information.jsp" />
    <table border="0" cellpadding="1" cellspacing="1" width="100%" class="tableBgColor">
    <tr>
     		<td align="right" width="15%" class="leftTdBgColor">
                &nbsp;菜单目录:
            </td>
            <td align="left" width="85%" class="rightTdBgColor" colspan="3">
                &nbsp;<s:textfield readonly="true" name="information.menuname" cssStyle="width:30%" 
                cssClass="textInput" id="pname"></s:textfield>
				<input type="button" value="请选择" class="buttonStyle" onclick="openDialog('information_tree.htm',300,650)">
                <s:hidden name="information.menuId" id="pid"></s:hidden>
                <s:hidden name="information.pagetype" id="pagetype"></s:hidden>
				<font color="red">*</font>
            </td>
    </tr>
        <tr>
            <td align="right" width="15%" class="leftTdBgColor">
                &nbsp;标题名称:
            </td>
            <td align="left" width="85%" class="rightTdBgColor" colspan="3">
                &nbsp;<s:textfield name="information.title" cssClass="textInput" cssStyle="width:50%" id="informationTitle"/>
					<label><font color="red">*</font></label>
            </td>
        </tr>
        <tr>
            <td align="right" width="15%" class="leftTdBgColor">
                &nbsp;创建时间:
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;<s:textfield name="information.createTime" cssClass="textInput" cssStyle="width:50%" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true})"/>
            </td>
            <td align="right" width="15%" class="leftTdBgColor">
                &nbsp;创建者:
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;<s:textfield name="information.createUserName" cssClass="textInput" cssStyle="width:50%"/>
					<s:hidden name="information.createUserCode" id="createUserCode"></s:hidden>
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
            <td align="right" width="15%" class="leftTdBgColor">
                &nbsp;附件上传：
            </td>
            <td align="left" width="85%" class="rightTdBgColor" colspan="3">
            	&nbsp;<input type="file" name="Upload" style="border: 1px solid #000000;"id="Upload"/>
                <label>(<font color="red">多附件请打包上传，请支持RAR、ZIP、7z压缩格式</font>)</label>
            </td>
        </tr>
        <tr>
            <td align="right" width="15%" class="leftTdBgColor">
                &nbsp;是否置顶：
            </td>
            <td align="left" width="85%" class="rightTdBgColor" colspan="3">
            	&nbsp;<s:select list="#{1:'否',2:'是'}" value="%{information.top}" name="information.top" cssStyle="width=130"></s:select>
            </td>
        </tr>
        <tr>
			<td bgcolor="#FFFFFF" colspan="4">
			<c:set var="editorName" scope="request" value="information.content" />
               <%@include file="../common/kindEditor.jsp" %>
			</td>
		</tr>
    </table>
    <br>
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
            <td align="center" width="30%">
                <input type="button" value="<fmt:message key="global.save" />" class="buttonStyle" id="btnsubmit">
                <input type="reset" value="<fmt:message key="global.reset" />" class="buttonStyle">
                <input id="back" type="button" value="<fmt:message key="global.back" />" class="buttonStyle">
            </td>
        </tr>
    </table>
</s:form>
<%@ include file="/common/footer.jsp"%>
