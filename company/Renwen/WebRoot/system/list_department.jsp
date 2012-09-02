<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
<s:form action="department_list" id="ec">
    <div style="width:100%">
            <table border="0" cellpadding="0" cellspacing="0" width="100%" class="talbe_gray_border">
            <tr>
                <td align="right" width="8%">&nbsp;<fmt:message key="department.name" /><fmt:message key="global.colon" /></td>
                <td align="left" width="20%">
                    &nbsp;<s:textfield name="depName" value="%{department.depName}" cssClass="textInput" cssStyle="width:85%" />
                </td>
                <td align="right" width="8%">&nbsp;<fmt:message key="global.createName" /><fmt:message key="global.colon" /></td>
                <td align="left" width="20%">
                    &nbsp;<s:textfield name="createName" value="%{department.createName}" cssClass="textInput" cssStyle="width:85%" />
                </td>
                <td align="right" width="8%">&nbsp;<fmt:message key="global.createDate" /><fmt:message key="global.colon" /></td>
                <td align="left" width="20%">
                    &nbsp;<s:textfield name="createDate" readonly="true" cssClass="Wdate" value="%{department.createDate}" onfocus="WdatePicker({isShowClear:true,readOnly:true})" cssStyle="width:85%" />
                </td>
                <td width="8%" align="center">
                    <input type="submit" value="<fmt:message key="global.query" />" class="buttonStyle"/>
                </td>
            </tr>
        </table>
        <ec:table items="departments" var="department" action="${pageContext.request.contextPath}/department_list.htm" retrieveRowsCallback="limit" filterRowsCallback="limit" title="">
            <ec:exportXls fileName="department.xls" />
            <ec:exportPdf fileName="department.pdf" />
            <ec:exportCsv fileName="department.csv" />
            <ec:row>
                <ec:column alias="num" title="global.serialNumber" cell="rowCount" width="3%" sortable="false" styleClass="tdCenter"/>
                <ec:column styleClass="tdLeft" property="depCode" title="department.code" width="15%" />
                <ec:column styleClass="tdLeft" property="depName" title="department.name" width="20%" />
                <ec:column styleClass="tdLeft" property="scription" title="department.scription" width="30%" />
                <ec:column styleClass="tdLeft" property="createName" title="global.createName" width="10%" />
                <ec:column styleClass="tdLeft" property="createDate" title="global.createDate" width="10%" />
                 <ec:column styleClass="tdLeft" property="status" title="global.status" width="5%" >
                	<c:choose>
                		<c:when test="${department.status eq 1}">
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