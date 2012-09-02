<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/consult/add_integration.js">
<!--

//-->
</script>
<s:form action="integration_save" method="post" validate="true" id="ec" enctype="multipart/form-data" >
    <s:token />
    <s:hidden name="pdid"  value="%{pdid}" />
    <s:hidden name="integration.newsTypeId" />
    <s:hidden name="integration.parentNewsTypeId" />
    <s:hidden name="integration.newsTypeName" />
    <s:hidden name="inputPage" value="/consult/individualAdd_integration.jsp" />
    <table border="0" cellpadding="1" cellspacing="1" width="100%" class="tableBgColor">
        <tr>
            <td align="right" width="8%" class="leftTdBgColor">
                &nbsp;菜单目录:
            </td>
            <td align="left" width="35%" class="rightTdBgColor" colspan="3">
                &nbsp;<b>${integration.newsTypeName}</b>
            </td>
        </tr>
        <c:if test="${newstype.upStatus==2}">	
         <tr>
            <td align="right" width="8%" class="leftTdBgColor">
                &nbsp;内容信息状态:
            </td>
            <td align="left" width="35%" class="rightTdBgColor" colspan="3">
                &nbsp;<b>${newstype.templateTitle}</b>
            </td>
        </tr>
        </c:if>
        <tr>
            <td align="right" width="8%" class="leftTdBgColor">
                &nbsp;标题名称:
            </td>
            <td align="left" width="35%" class="rightTdBgColor" colspan="3">
                &nbsp;<s:textfield name="integration.name" cssClass="textInput"
					cssStyle="width:50%" id="integrationName"/>
					<label><font color="red">*</font></label>
            </td>
        </tr>
        <tr>
          	<td align="right" width="8%" class="leftTdBgColor">
				&nbsp;内容来源：
			</td>
			<td align="left" width="35%" class="rightTdBgColor" colspan="3">
               &nbsp;<s:textfield name="integration.newsfrom" cssClass="textInput"
					cssStyle="width:50%" />
			</td>
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
		<c:if test="${newstype.upStatus==1}">	
        <tr style="display: none">
              <td align="right" width="8%" class="leftTdBgColor">
                &nbsp;文件上传
            </td>
            <td align="left" width="35%" class="rightTdBgColor" colspan="3">
                &nbsp;<input type="file" name="upload" style="border: 1px solid #000000;" >
                <label>(<font color="red">只限.avi和.wmv格式视频</font>)</label>
            </td>
        </tr>
        </c:if>
        <tr style="display: none;">
         <td>
         <s:radio list="#{0:'否',99:'是'}" name="integration.tzStart"  value="0" ></s:radio>
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
