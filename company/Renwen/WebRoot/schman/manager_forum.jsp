<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
<s:form action="forum_manager" id="ec">
    <s:hidden name="insert" id="insertAction" value="forum_showAdd.htm" />
    <s:hidden name="update" id="updateAction" value="forum_showEdit.htm" />
    <s:hidden name="delete" id="deleteAction" value="forum_delete.htm" />
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
        <ec:table items="forums" var="forum" action="${pageContext.request.contextPath}/forum_manager.htm" retrieveRowsCallback="limit" filterRowsCallback="limit"
                  title="<img src='${pageContext.request.contextPath}/images/+.gif' id='Insert'/>
			&<img src='${pageContext.request.contextPath}/images/-.gif' id='Delete'/>
			&<img src='${pageContext.request.contextPath}/images/edit.gif' id='Update'/>">
            <%--<ec:exportXls fileName="forum.xls" />
            <ec:exportPdf fileName="forum.pdf" />
            <ec:exportCsv fileName="forum.csv" />--%>
            <ec:row>
                <ec:column alias="checkbox" styleClass="tdCenter" title="<input name='All' type='checkbox' id='All' title='选择所有'/>" width="3%" sortable="false">
                    &nbsp;<input type="checkbox" name="selectedIds" value="${forum.id}" title="${forum.id}" />
                </ec:column>
                <%-- 在此补全要展示的列 --%>
                <ec:column title="类别名称"  property="forumName" />
            </ec:row>
        </ec:table>
    </div>
</s:form>
<%@ include file="/common/footer.jsp"%>