<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
<s:form action="link_manager" id="ec">
<s:hidden name="issued" id="issuedAction" value="link_isSued.htm" />
<s:hidden name="undo" id="undoAction" value="link_undo.htm" />
    <s:hidden name="insert" id="insertAction" value="link_showAdd.htm" />
    <s:hidden name="update" id="updateAction" value="link_showEdit.htm" />
    <s:hidden name="delete" id="deleteAction" value="link_delete.htm" />
    <div style="width:100%">
        <table border="0" cellpadding="0" cellspacing="0" width="100%" class="talbe_gray_border">
            <tr>
                <td align="right" width="8%">&nbsp;<fmt:message key="link.name"/></td>
                <td align="left" width="20%">
                    &nbsp;<s:textfield name="name" cssClass="textInput" value="%{link.name}"></s:textfield>
                </td>
                <td align="right" width="8%">&nbsp;<fmt:message key="link.url"/></td>
                <td align="left" width="20%">
                    &nbsp;<s:textfield name="url" cssClass="textInput" value="%{link.url}"></s:textfield>
                </td>
                <td align="right" width="8%">&nbsp;<fmt:message key="link.isShow"/></td>
                <td align="left" width="20%">
                    &nbsp;<s:select list="#{'':'全部',1:'不显示',99:'显示'}" value="%{link.isShow}" name="isShow"></s:select>
                </td>
                <td width="8%" align="center">
                    <input type="submit" value="<fmt:message key="global.query" />" class="buttonStyle"/>
                </td>
            </tr>
        </table>
        <ec:table items="links" var="link" action="${pageContext.request.contextPath}/link_manager.htm" retrieveRowsCallback="limit" filterRowsCallback="limit"
                  title="<img src='${pageContext.request.contextPath}/images/+.gif' id='Insert'/>
			&<img src='${pageContext.request.contextPath}/images/-.gif' id='Delete'/>
			&<img src='${pageContext.request.contextPath}/images/edit.gif' id='Update'/>
			&<img src='${pageContext.request.contextPath}/images/send.gif' id='issued'/>
        &<img src='${pageContext.request.contextPath}/images/unSend.gif' id='undo'/>">
            <%--<ec:exportXls fileName="link.xls" />
            <ec:exportPdf fileName="link.pdf" />
            <ec:exportCsv fileName="link.csv" />--%>
            <ec:row>
                <ec:column alias="checkbox" styleClass="tdCenter" title="<input name='All' type='checkbox' id='All' title='选择所有'/>" width="3%" sortable="false">
                    &nbsp;<input type="checkbox" name="selectedIds" value="${link.id}" title="${link.id}" />
                </ec:column>
                <%-- 在此补全要展示的列 --%>
                <ec:column title="连接名称" property="name"></ec:column>
                <ec:column title="URL地址" property="url"></ec:column>
                <ec:column title="是否已显示" property="isShow">
                    <c:choose>
                    <c:when test="${link.isShow eq 1}">
                    <span style="color: red">不显示</span>
                    </c:when>
                    <c:when test="${link.isShow eq 99}">
                    显示
                    </c:when>
                    </c:choose>
                </ec:column>
            </ec:row>
        </ec:table>
    </div>
</s:form>
<%@ include file="/common/footer.jsp"%>