<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
<s:form action="integration_save" method="post" validate="true"
	enctype="multipart/form-data">
	<s:token />
	<s:hidden name="integration.id" />
	<s:hidden name="integration.filePath" />
    <s:hidden name="integration.videoPath" />
	<s:hidden name="integration.hits" />
	<s:hidden name="pdid" value="%{pdid}" />
	<s:hidden name="integration.newsTypeId" />
	<s:hidden name="integration.parentNewsTypeId" />
	<s:hidden name="integration.newsTypeName" />
	<s:hidden name="inputPage" value="/consult/individualEdit_integration.jsp" />
	<table border="0" cellpadding="1" cellspacing="1" width="100%"
		class="tableBgColor">
		<tr>
			<td align="right" width="8%" class="leftTdBgColor">&nbsp;菜单目录:</td>
			<td align="left" width="35%" class="rightTdBgColor" colspan="3">
			&nbsp;<b>${integration.newsTypeName}</b></td>
		</tr>
		<c:if test="${newstype.upStatus==2}">
			<tr>
				<td align="right" width="8%" class="leftTdBgColor">
				&nbsp;内容信息状态:</td>
				<td align="left" width="35%" class="rightTdBgColor" colspan="3">
				&nbsp;<b>${newstype.templateTitle}</b></td>
			</tr>
		</c:if>
		<tr>
			<td align="right" width="8%" class="leftTdBgColor">&nbsp;标题名称:</td>
			<td align="left" width="35%" class="rightTdBgColor" colspan="3">
			&nbsp;<s:textfield name="integration.name" cssClass="textInput"
				cssStyle="width:50%" id="integrationName" /></td>
		</tr>
		<tr>
			<td align="right" width="8%" class="leftTdBgColor">&nbsp;内容来源：</td>
			<td align="left" width="35%" class="rightTdBgColor" colspan="3">
			<s:textfield name="integration.newsfrom" cssClass="textInput"
				cssStyle="width:50%" /></td>
		</tr>
			<tr>
          	<td align="right" width="8%" class="leftTdBgColor">
				&nbsp;上传日期：
			</td>
			<td align="left" width="35%" class="rightTdBgColor" colspan="3">
               &nbsp; 
               <s:textfield name="integration.startDay" cssClass="Wdate" readonly="true" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,readOnly:true})"  />
			</td>
			</tr>
		<tr style="display: none;">
			<td align="right" width="15%" class="leftTdBgColor">&nbsp;首页宣传：
			</td>
			<td align="left" width="35%" class="rightTdBgColor">&nbsp;<s:radio
				list="#{0:'无',1:'1号区域',2:'2号区域',3:'3号区域',4:'4号区域' }"
				name="integration.xcrj" value="%{integration.xcrj}"></s:radio></td>
		</tr>
		<c:if test="${newstype.upStatus==1}">
			<tr>
				<td align="right" width="15%" class="leftTdBgColor">&nbsp;文件上传
				</td>
				<td align="left" width="35%" class="rightTdBgColor" colspan="3">
				&nbsp;<input type="file" name="Upload"
					style="border: 1px solid #000000;"> <label>(<font
					color="red">只限.avi和.wmv格式视频</font>)</label><c:if
					test="${not empty integration.filePath}">存在文件,可在上传文件替换</c:if></td>
			</tr>
			<c:if test="${not empty  integration.videoPath}">
			  <tr>
			    <td align="left" width="15%" class="rightTdBgColor" colspan="4">&nbsp;<b>视频缩略图</b>
			     <br>
			     <img src="${pageContext.request.contextPath}${integration.videoPath}" >
			    </td>
			  </tr>
			</c:if>
		</c:if>
		<tr style="display: none;">
			<td align="right" width="15%" class="leftTdBgColor">
			&nbsp;是否设置为首页公告(动态)</td>
			<td align="left" width="35%" class="rightTdBgColor">&nbsp;<s:radio
				list="#{0:'否',99:'是'}" name="integration.tzStart"
				value="%{integration.tzStart}"></s:radio></td>
		</tr>
		<tr>
			<td class="rightTdBgColor" colspan="4"><b>-----下方填写正文-----</b></td>
		</tr>
		<tr >
			<td bgcolor="#FFFFFF" colspan="6"><c:set var="editorName"
				scope="request" value="integration.content" /> <c:set
				value="${integration.content}" var="editorValue"></c:set> <%@include
				file="../common/kindEditor.jsp"%></td>
		</tr>
	</table>
	<br>
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td align="center" width="30%"><input type="submit"
				value="<fmt:message key="global.save" />" class="buttonStyle">
			<input type="reset" value="<fmt:message key="global.reset" />"
				class="buttonStyle"> <input id="back" type="button"
				value="<fmt:message key="global.back" />" class="buttonStyle">
			</td>
		</tr>
	</table>
</s:form>
<%@ include file="/common/footer.jsp"%>
