<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
<s:form action="operator_manager" id="ec">
    <s:hidden name="insert" id="insertAction" value="operator_showAdd.htm" />
    <s:hidden name="update" id="updateAction" value="operator_showEdit.htm" />
    <s:hidden name="delete" id="deleteAction" value="operator_delete.htm" />
    <div style="width:100%">
        <table border="0" cellpadding="0" cellspacing="0" width="100%" class="talbe_gray_border">
            <tr>
                <td align="right" width="8%">&nbsp;<fmt:message key="operator.loginName" /><fmt:message key="global.colon" /></td>
                <td align="left" width="20%">
                    &nbsp;<s:textfield name="loginName" value="%{operator.loginName}" cssClass="textInput" cssStyle="width:85%" />
                </td>
                <td align="right" width="8%">&nbsp;<fmt:message key="operator.trueName" /><fmt:message key="global.colon" /></td>
                <td align="left" width="20%">
                    &nbsp;<s:textfield name="trueName" value="%{operator.trueName}" cssClass="textInput" cssStyle="width:85%" />
                </td>
                <td align="right" width="8%">&nbsp;<fmt:message key="operator.idCard" /><fmt:message key="global.colon" /></td>
                <td align="left" width="20%">
                    &nbsp;<s:textfield name="idCard" value="%{operator.idCard}" cssClass="textInput" cssStyle="width:85%" />
                </td>
                <td width="8%" align="center">
                    <input type="submit" value="<fmt:message key="global.query" />" class="buttonStyle"/>
                </td>
            </tr>
        </table>
        <ec:table items="operators" var="operator" action="${pageContext.request.contextPath}/operator_manager.htm" retrieveRowsCallback="limit" filterRowsCallback="limit"
                  title="<img src='${pageContext.request.contextPath}/images/+.gif' id='Insert'/>
			&<img src='${pageContext.request.contextPath}/images/-.gif' id='Delete'/>
			&<img src='${pageContext.request.contextPath}/images/edit.gif' id='Update'/>">
            <%-- <ec:exportXls fileName="operator.xls" />
            <ec:exportPdf fileName="operator.pdf" />
            <ec:exportCsv fileName="operator.csv" /> --%>
            <ec:row>
                <ec:column alias="checkbox" styleClass="tdCenter" title="<input name='All' type='checkbox' id='All' title='选择所有'/>" width="3%" sortable="false">
                    &nbsp;<input type="checkbox" name="selectedIds" value="${operator.id}" title="${operator.id}" />
                </ec:column>
                <ec:column property="trueName" title="operator.trueName" styleClass="tdLeft" width="10%" />
                <ec:column property="loginName" title="operator.loginName" styleClass="tdLeft" width="10%" />
                <ec:column property="idCard" title="operator.idCard" styleClass="tdLeft" width="20%" />
                <ec:column property="phone" title="global.phone" styleClass="tdLeft" width="15%" />
                <ec:column property="address" title="operator.address" styleClass="tdLeft" width="20%" />
                <ec:column property="createDate" title="global.createDate" styleClass="tdLeft" width="15%" />
                 <ec:column styleClass="tdLeft" property="status" title="global.status" width="5%" >
                	<c:choose>
                		<c:when test="${operator.status eq 1}">
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