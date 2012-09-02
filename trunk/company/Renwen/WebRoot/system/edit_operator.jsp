<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/common/global/swap.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/operator.js"></script>
<s:form action="operator_save" method="post" validate="true">
    <s:token />
    <s:hidden name="operator.id" />
    <s:hidden name="operator.code" />
    <s:hidden name="operator.models" />
    <s:hidden name="operator.password" />
    <s:hidden name="operator.roles" id="roles" />
    <s:hidden name="inputPage" value="/system/edit_operator.jsp" />
     <table border="0" cellpadding="1" cellspacing="1" width="100%" class="tableBgColor">
        <tr>
           <td align="right" width="15%" class="leftTdBgColor">
                &nbsp;<fmt:message key="operator.loginName" /><fmt:message key="global.colon" />
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;<s:textfield name="operator.loginName" cssClass="textInput" cssStyle="width:75%" />
            </td>
            <td align="right" width="15%" class="leftTdBgColor">
               &nbsp;<fmt:message key="global.password" /><fmt:message key="global.colon" />
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;<s:textfield name="operator.repassword" value="%{operator.password}" cssClass="textInput" cssStyle="width:75%" />
            </td>
        </tr>
         <tr>
            <td align="right" width="15%" class="leftTdBgColor">
               &nbsp;<fmt:message key="operator.trueName" /><fmt:message key="global.colon" />
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;<s:textfield name="operator.trueName" cssClass="textInput" cssStyle="width:75%" />
            </td>
            <td align="right" width="15%" class="leftTdBgColor">
                &nbsp;<fmt:message key="operator.postName"/><fmt:message key="global.colon" />
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;<s:select list="postss" listKey="postCode" listValue="postName" name="operator.postCode" cssStyle="width:75%"></s:select>
            </td>
        </tr>
         <tr>
          <td align="right" width="15%" class="leftTdBgColor">
                &nbsp;<fmt:message key="operator.idCard" /><fmt:message key="global.colon" />
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;<s:textfield name="operator.idCard" cssClass="textInput" cssStyle="width:75%" />
            </td>
         	<td align="right" width="15%" class="leftTdBgColor">
               &nbsp;<fmt:message key="global.sex" /><fmt:message key="global.colon" />
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;<s:select name="operator.sex" list="#{'男':'男','女':'女'}" cssStyle="width:25%" />
            </td>
        </tr>
        <tr>
         	<td align="right" width="15%" class="leftTdBgColor">
                &nbsp;<fmt:message key="operator.brithDay" /><fmt:message key="global.colon" />
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;<s:textfield name="operator.brithday" cssClass="Wdate" onfocus="WdatePicker({isShowClear:true,readOnly:true})" cssStyle="width:35%" />
            </td>
            <td align="right" width="15%" class="leftTdBgColor">
                &nbsp;<fmt:message key="global.phone" /><fmt:message key="global.colon" />
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;<s:textfield name="operator.phone"  cssClass="textInput" cssStyle="width:75%" />
            </td>
        </tr>
        <tr>
            <td align="center" class="leftTdBgColor" colspan="4">
             <table border="1" bordercolor="#FFFFFF" bordercolorlight="#000000"
					bgcolor="#6699CC" width="80%" class="swap">
					<tr>
						<td align="center" height="30">
							备选角色
						</td>
						<td align="center">
							&nbsp;
						</td>
						<td align="center">
							已选角色
						</td>
					</tr>
					<tr>
						<td align="center">
							<select name="waitIds" size="20" multiple="multiple"
								style="width: 100%;">
								<c:forEach items="${roles}" var="role">
									<option value="${role.roleCode }">
										${role.roleName }
									</option>
								</c:forEach>
							</select>
						</td>
						<td align="center" width="15%" s>
							<input type="button" class="buttonStyle" value=" 添加 "
								onclick="move(this.form.waitIds,this.form.selectedIds)"
								name="B1">
							<br>
							<input type="button" class="buttonStyle" value=" 移出 "
								onclick="move(this.form.selectedIds,this.form.waitIds)"
								name="B2">
						</td>
						<td align="center">
							<select multiple="multiple" name="selectedIds" size="20"
								style="width: 100%;">
								<c:forEach items="${selectedRoles}" var="temp">
									<option value="${temp.roleCode }">
										${temp.roleName }
									</option>
								</c:forEach>
							</select>
						</td>
					</tr>
				</table>
            </td>
        </tr>
         <tr>
            <td align="right" width="15%" class="leftTdBgColor">
                &nbsp;<fmt:message key="global.scription" /><fmt:message key="global.colon" />
            </td>
            <td align="left" width="35%" class="rightTdBgColor" colspan="3">
                &nbsp;<s:textarea name="operator.scription"  cssStyle="width:90%;height:80px;" />
            </td>
        </tr>
        <tr>
            <td align="right" width="15%" class="leftTdBgColor">
                &nbsp;<fmt:message key="operator.address" /><fmt:message key="global.colon" />
            </td>
            <td align="left" width="35%" class="rightTdBgColor" colspan="3">
                &nbsp;<s:textfield name="operator.address"  cssClass="textInput" cssStyle="width:75%" />
            </td>
        </tr>
        <tr>
            <td align="right" width="15%" class="leftTdBgColor">
                &nbsp;<fmt:message key="global.createName" /><fmt:message key="global.colon" />
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;<s:textfield name="operator.createName" value="管理员" readonly="true" cssClass="textInput" cssStyle="width:75%" />
            </td>
             <td align="right" width="15%" class="leftTdBgColor">
               &nbsp;<fmt:message key="global.createDate" /><fmt:message key="global.colon" />
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;<s:textfield name="operator.createDate" cssClass="Wdate" readonly="true" onfocus="WdatePicker({isShowClear:true,readOnly:true})" cssStyle="width:75%" />
            </td>
        </tr>
          <tr>
           <td align="right" width="15%" class="leftTdBgColor">
                &nbsp;<fmt:message key="global.status" /><fmt:message key="global.colon" />
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;<s:select name="operator.status" list="#{'1':'启用','2':'停用'}" cssStyle="width:25%"/>
            </td>
            <td align="right" width="15%" class="leftTdBgColor">
                &nbsp;<fmt:message key="global.sort" /><fmt:message key="global.colon" />
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;<s:textfield name="operator.sort" cssClass="textInput" cssStyle="width:25%"/>
            </td>
        </tr>
    </table>
    <br>
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
            <td align="center" width="30%">
            <input type="submit" name="提交">
                <input type="button" onclick="preSubmit(this.form);" value="<fmt:message key="global.save" />" class="buttonStyle">
                <input type="reset" value="<fmt:message key="global.reset" />" class="buttonStyle">
                <input id="back" type="button" value="<fmt:message key="global.back" />" class="buttonStyle">
            </td>
        </tr>
    </table>
</s:form>
<%@ include file="/common/footer.jsp"%>
