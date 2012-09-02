<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
<s:form action="dictionary_manager" id="ec">
    <s:hidden name="insert" id="insertAction" value="dictionary_showAdd.htm" />
    <s:hidden name="update" id="updateAction" value="dictionary_showEdit.htm" />
    <s:hidden name="delete" id="deleteAction" value="dictionary_delete.htm" />
    <s:hidden name="pid"/>
    <div style="width:100%">
        <%-- 如果有查询条件，请在此补全
        <table border="0" cellpadding="0" cellspacing="0" width="100%" class="talbe_gray_border">
            <tr>
                <td align="right" width="8%">&nbsp;</td>
                <td align="left" width="20%">
                    &nbsp;
                </td>
                <td align="right" width="8%">&nbsp;</td>
                <td align="left" width="20%">
                    &nbsp;
                </td>
                <td align="right" width="8%">&nbsp;</td>
                <td align="left" width="20%">
                    &nbsp;
                </td>
                <td width="8%" align="center">
                    <input type="submit" value="<fmt:message key="global.query" />" class="buttonStyle"/>
                </td>
            </tr>
        </table>
        --%>
        <ec:table items="dictionarys" var="dictionary" action="${pageContext.request.contextPath}/dictionary_manager.htm" retrieveRowsCallback="limit" filterRowsCallback="limit"
                  title="<img src='${pageContext.request.contextPath}/images/+.gif' id='Insert'/>
			&<img src='${pageContext.request.contextPath}/images/-.gif' id='Delete'/>
			&<img src='${pageContext.request.contextPath}/images/edit.gif' id='Update'/>">
            <%--<ec:exportXls fileName="dictionary.xls" />
            <ec:exportPdf fileName="dictionary.pdf" />
            <ec:exportCsv fileName="dictionary.csv" />--%>
            <ec:row>
                <ec:column alias="checkbox" styleClass="tdCenter" title="<input name='All' type='checkbox' id='All' title='选择所有'/>" width="3%" sortable="false">
                    &nbsp;<input type="checkbox" name="selectedIds" value="${dictionary.id}" title="${dictionary.id}" />
                </ec:column>
                <%-- 在此补全要展示的列 --%>
            </ec:row>
            <ec:column property="id" title="编号ID" styleClass="tdCenter"/>
            <ec:column property="name" title="名称" styleClass="tdCenter"/>
            <ec:column property="remark" title="描述" styleClass="tdCenter"/>
        </ec:table>
    </div>
</s:form>
<%@ include file="/common/footer.jsp"%>