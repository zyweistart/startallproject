<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/consult/add_integration.js">
<!--

//-->
</script>
<s:form action="integration_save" method="post" validate="true" id="ec" enctype="multipart/form-data" >
    <s:token />
    <s:hidden name="inputPage" value="/consult/add_integration.jsp" />
    <table border="0" cellpadding="1" cellspacing="1" width="100%" class="tableBgColor">
    <tr>
     <td align="right" width="15%" class="leftTdBgColor">
                &nbsp;菜单目录:
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;<s:hidden name="integration.parentNewsTypeId" id="parentNewsTypeId"></s:hidden>
				<s:hidden name="integration.newsTypeId" id="parentId"></s:hidden>
				<s:textfield readonly="true" name="integration.newsTypeName"
					cssStyle="width:30%" cssClass="textInput" id="parentName"></s:textfield>
				<input type="button" value="请选择" class="buttonStyle"
					onclick="openDialog('newstype_tree.htm',300,650)">
				<font color="red">*</font>
            </td>
    </tr>
        <tr>
            <td align="right" width="15%" class="leftTdBgColor">
                &nbsp;标题名称:
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;<s:textfield name="integration.name" cssClass="textInput"
					cssStyle="width:50%" id="integrationName"/>
					<label><font color="red">*</font></label>
            </td>
            
        </tr>
        <tr>
          	<td align="right" width="15%" class="leftTdBgColor">
				&nbsp;内容来源：
			</td>
			<td align="left" width="35%" class="rightTdBgColor" >
               &nbsp;<s:textfield name="integration.newsfrom" cssClass="textInput"
					cssStyle="width:50%" />
			</td>
			</tr>
			<tr style="display:none">
				<td align="right" width="15%" class="leftTdBgColor" >
				&nbsp;首页宣传：
			</td>
			<td align="left" width="35%" class="rightTdBgColor"  >
               &nbsp;<s:radio list="#{0:'无',1:'1号区域',2:'2号区域',3:'3号区域',4:'4号区域' }" name="integration.xcrj"  value="0"></s:radio>
			</td>
			</tr>
			  <tr >
              <td align="right" width="15%" class="leftTdBgColor">
                &nbsp;上传日期：
            </td>
            <td align="left" width="35%" class="rightTdBgColor" >
                &nbsp;
                <s:textfield name="integration.startDay" cssClass="Wdate" readonly="true" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,readOnly:true})"  />
            </td>
        </tr>
        <tr style="display:none;">
              <td align="right" width="15%" class="leftTdBgColor">
                &nbsp;文件上传
            </td>
            <td align="left" width="35%" class="rightTdBgColor" >
                &nbsp;<input type="file" name="Upload" style="border: 1px solid #000000;" >
                <label>(<font color="red">只限.avi和.wmv格式视频</font>)</label>
            </td>
            
        </tr>
        <tr style="display:none">
         <td align="right" width="15%" class="leftTdBgColor" >
                &nbsp;是否设置为首页公告(动态)
            </td>
            <td align="left" width="35%" class="rightTdBgColor"  >
                &nbsp;<s:radio list="#{0:'否',99:'是'}" name="integration.tzStart"  value="0"></s:radio>
            </td>
        </tr>
        <tr>
          <td class="rightTdBgColor" colspan="4">
            <b>-----下方填写正文-----</b>
          </td>
        </tr>
        <tr>
			<td bgcolor="#FFFFFF" colspan="4">
			<c:set var="editorName" scope="request" value="integration.content" />
               <%@include file="../common/kindEditor.jsp" %>
			</td>
		</tr>
    </table>
    <br>
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
            <td align="center" width="30%">
                <input type="button" value="<fmt:message key="global.save" />"
					class="buttonStyle" id="isok">
                <input type="reset" value="<fmt:message key="global.reset" />" class="buttonStyle">
                <input id="back" type="button" value="<fmt:message key="global.back" />" class="buttonStyle">
            </td>
        </tr>
    </table>
</s:form>
<%@ include file="/common/footer.jsp"%>
