<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
<s:form action="role_list" id="ec">
    <div style="width:100%">
       <table border="0" cellpadding="0" cellspacing="0" width="100%" class="talbe_gray_border">
            <tr>
                <td align="right" width="8%">&nbsp;<fmt:message key="role.name" /><fmt:message key="global.colon" /></td>
                <td align="left" width="20%">
                    &nbsp;<s:textfield name="roleName" value="%{role.roleName}" cssClass="textInput" cssStyle="width:85%" />
                </td>
                <td align="right" width="8%">&nbsp;<fmt:message key="global.createName" /><fmt:message key="global.colon" /></td>
                <td align="left" width="20%">
                    &nbsp;<s:textfield name="createName" value="%{role.createName}" cssClass="textInput" cssStyle="width:85%" />
                </td>
                <td align="right" width="8%">&nbsp;<fmt:message key="global.createDate" /><fmt:message key="global.colon" /></td>
                <td align="left" width="20%">
                    &nbsp;<s:textfield name="createDate" readonly="true" cssClass="Wdate" value="%{role.createDate}" onfocus="WdatePicker({isShowClear:true,readOnly:true})" cssStyle="width:85%" />
                </td>
                <td width="8%" align="center">
                    <input type="submit" value="<fmt:message key="global.query" />" class="buttonStyle"/>
                </td>
            </tr>
        </table>
        <ec:table items="roles" var="role" action="${pageContext.request.contextPath}/role_list.htm" retrieveRowsCallback="limit" filterRowsCallback="limit" title="">
            <%-- <ec:exportXls fileName="role.xls" />
            <ec:exportPdf fileName="role.pdf" />
            <ec:exportCsv fileName="role.csv" />--%>
            <ec:row>
                <ec:column alias="num" title="global.serialNumber" cell="rowCount" width="3%" sortable="false" styleClass="tdCenter"/>
               <ec:column property="roleCode" title="role.code" styleClass="tdLeft" width="17%"/>
                <ec:column property="roleName" title="role.name" styleClass="tdLeft" width="20%"/>
                <ec:column property="scription" title="global.scription" styleClass="tdLeft" width="30%"/>
                <ec:column property="createName" title="global.createName" styleClass="tdLeft" width="10%"/>
                <ec:column property="createDate" title="global.createDate" styleClass="tdLeft" width="15%"/>
                 <ec:column styleClass="tdLeft" property="status" title="global.status" width="5%" >
                	<c:choose>
                		<c:when test="${role.status eq 1}">
                			启用
                		</c:when>
                		<c:otherwise>
                			<font color="red">停用</font>
                		</c:otherwise>
                	</c:choose>
                </ec:column>
            </ec:row>
        </ec:table>
    </div>
</s:form>
<%@ include file="/common/footer.jsp"%>