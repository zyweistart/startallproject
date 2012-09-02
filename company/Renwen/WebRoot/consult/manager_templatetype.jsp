<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
<s:form action="templatetype_manager" id="ec">
    <s:hidden name="insert" id="insertAction" value="templatetype_showAdd.htm" />
    <s:hidden name="update" id="updateAction" value="templatetype_showEdit.htm" />
    <s:hidden name="delete" id="deleteAction" value="templatetype_delete.htm" />
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
        <ec:table items="templatetypes" var="templatetype" action="${pageContext.request.contextPath}/templatetype_manager.htm" retrieveRowsCallback="limit" filterRowsCallback="limit"
                  title="<img src='${pageContext.request.contextPath}/images/+.gif' id='Insert'/>
			&<img src='${pageContext.request.contextPath}/images/-.gif' id='Delete'/>
			&<img src='${pageContext.request.contextPath}/images/edit.gif' id='Update'/>">
            <%--<ec:exportXls fileName="templatetype.xls" />
            <ec:exportPdf fileName="templatetype.pdf" />
            <ec:exportCsv fileName="templatetype.csv" />--%>
            <ec:row>
                <ec:column alias="checkbox" styleClass="tdCenter" title="<input name='All' type='checkbox' id='All' title='选择所有'/>" width="3%" sortable="false">
                    &nbsp;<input type="checkbox" name="selectedIds" value="${templatetype.id}" title="${templatetype.id}" />
                </ec:column>
                <%-- 在此补全要展示的列 --%>
                <ec:column title="模板描述名" property="title" />
                <ec:column title="模板文件路径" property="mbPath" />
                <ec:column title="展示几条数据" property="dataSize" />
            </ec:row>
        </ec:table>
    </div>
</s:form>
<%@ include file="/common/footer.jsp"%>