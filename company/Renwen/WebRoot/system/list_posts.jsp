<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
<s:form action="posts_list" id="ec">
    <div style="width:100%">
         <table border="0" cellpadding="0" cellspacing="0" width="100%" class="talbe_gray_border">
            <tr>
                <td align="right" width="8%">&nbsp;<fmt:message key="posts.name" /><fmt:message key="global.colon" /></td>
                <td align="left" width="15%">
                    &nbsp;<s:textfield name="postName" value="%{posts.postName}" cssClass="textInput" cssStyle="width:85%"/>
                </td>
                <td align="right" width="8%">&nbsp;<fmt:message key="global.createName" /><fmt:message key="global.colon" /></td>
                <td align="left" width="15%">
                    &nbsp;<s:textfield name="createName" value="%{posts.createName}" cssClass="textInput" cssStyle="width:85%"/>
                </td>
                <td align="right" width="8%">&nbsp;<fmt:message key="global.createDate" /><fmt:message key="global.colon" /></td>
                <td align="left" width="15%">
                    &nbsp;<s:textfield name="createDate" value="%{posts.createDate}" cssClass="Wdate" onfocus="WdatePicker({isShowClear:true,readOnly:true})" cssStyle="width:85%"/>
                </td>
                <td align="right" width="8%">&nbsp;<fmt:message key="global.status" /><fmt:message key="global.colon" /></td>
                <td align="left" width="15%">
                    &nbsp;<s:select name="status" value="%{posts.status}" list="#{'1':'启用','2':'停用'}" cssStyle="width:35%"/>
                </td>
                <td width="8%" align="center">
                    <input type="submit" value="<fmt:message key="global.query" />" class="buttonStyle"/>
                </td>
            </tr>
        </table>
        <ec:table items="postss" var="posts" action="${pageContext.request.contextPath}/posts_list.htm" retrieveRowsCallback="limit" filterRowsCallback="limit" title="">
            <%-- <ec:exportXls fileName="posts.xls" />
            <ec:exportPdf fileName="posts.pdf" />
            <ec:exportCsv fileName="posts.csv" />--%>
            <ec:row>
                <ec:column alias="num" title="global.serialNumber" cell="rowCount" width="3%" sortable="false" styleClass="tdCenter"/>
                 <ec:column property="postCode" title="posts.code" styleClass="tdCenter" width="20%"/>
                <ec:column property="postName" title="posts.name" styleClass="tdCenter" width="25%"/>
                <ec:column property="createName" title="global.createName" styleClass="tdCenter" width="15%"/>
                <ec:column property="createDate" title="global.createDate" styleClass="tdCenter" width="15%"/>
                <ec:column styleClass="tdLeft" property="status" title="global.status" width="5%" >
                	<c:choose>
                		<c:when test="${posts.status eq 1}">
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