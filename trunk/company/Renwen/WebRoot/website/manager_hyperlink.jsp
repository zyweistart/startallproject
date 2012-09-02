<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
<s:form action="hyperlink_manager" id="ec">
    <s:hidden name="insert" id="insertAction" value="hyperlink_showAdd.htm" />
    <s:hidden name="update" id="updateAction" value="hyperlink_showEdit.htm" />
    <s:hidden name="delete" id="deleteAction" value="hyperlink_delete.htm" />
    <div style="width:100%">
        <ec:table items="hyperlinks" var="hyperlink" action="${pageContext.request.contextPath}/hyperlink_manager.htm" retrieveRowsCallback="limit" filterRowsCallback="limit"
                  title="<img src='${pageContext.request.contextPath}/images/+.gif' id='Insert'/>
			&<img src='${pageContext.request.contextPath}/images/-.gif' id='Delete'/>
			&<img src='${pageContext.request.contextPath}/images/edit.gif' id='Update'/>">
            <%--<ec:exportXls fileName="hyperlink.xls" />
            <ec:exportPdf fileName="hyperlink.pdf" />
            <ec:exportCsv fileName="hyperlink.csv" />--%>
            <ec:row>
                <ec:column alias="checkbox" styleClass="tdCenter" title="<input name='All' type='checkbox' id='All' title='选择所有'/>" width="3%" sortable="false">
                    &nbsp;<input type="checkbox" name="selectedIds" value="${hyperlink.id}" title="${hyperlink.id}" />
                </ec:column>
               	<ec:column title="名称" property="name"></ec:column>
                <ec:column title="链接地址" property="url"></ec:column>
                <ec:column title="分类" property="category">
                    <c:choose>
                    <c:when test="${hyperlink.category eq 1}">
                    	否
                    </c:when>
                    <c:when test="${hyperlink.category eq 2}">
                    	是
                    </c:when>
                    </c:choose>
                </ec:column>
                
                
            </ec:row>
        </ec:table>
    </div>
</s:form>
<%@ include file="/common/footer.jsp"%>