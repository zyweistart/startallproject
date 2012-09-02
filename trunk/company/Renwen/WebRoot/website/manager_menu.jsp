<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
<s:form action="menu_manager" id="ec">
    <s:hidden name="insert" id="insertAction" value="menu_showAdd.htm" />
    <s:hidden name="update" id="updateAction" value="menu_showEdit.htm" />
    <s:hidden name="delete" id="deleteAction" value="menu_delete.htm" />
    <div style="width:100%">
        <table border="0" cellpadding="0" cellspacing="0" width="100%" class="talbe_gray_border">
            <tr>
                <td align="right" width="8%">&nbsp;名称</td>
                <td align="left" width="20%">
                    &nbsp;<s:textfield name="name" cssClass="textInput" value="%{menu.name}"></s:textfield>
                </td>
                <td align="right" width="8%">&nbsp;上级</td>
                <td align="left" width="20%">
                    &nbsp;<s:textfield name="pname" cssClass="textInput" value="%{menu.pname}"></s:textfield>
                </td>
                <td align="right" width="8%">&nbsp;页类型</td>
                <td align="left" width="20%">
                 	 &nbsp;<s:select list="#{'':'全部',1:'父级类型',2:'单页类型',3:'列表类型',4:'图文类型'}" value="%{menu.pagetype}" name="pagetype" cssStyle="width=130"></s:select>
                </td>
                <td width="8%" align="center">
                    <input type="submit" value="<fmt:message key="global.query" />" class="buttonStyle"/>
                </td>
            </tr>
        </table>
        <ec:table items="menus" var="menu" action="${pageContext.request.contextPath}/menu_manager.htm" retrieveRowsCallback="limit" filterRowsCallback="limit"
                  title="<img src='${pageContext.request.contextPath}/images/+.gif' id='Insert'/>
			&<img src='${pageContext.request.contextPath}/images/-.gif' id='Delete'/>
			&<img src='${pageContext.request.contextPath}/images/edit.gif' id='Update'/>">
            <%--<ec:exportXls fileName="menu.xls" />
            <ec:exportPdf fileName="menu.pdf" />
            <ec:exportCsv fileName="menu.csv" />--%>
            <ec:row>
                <ec:column alias="checkbox" styleClass="tdCenter" title="<input name='All' type='checkbox' id='All' title='选择所有'/>" width="3%" sortable="false">
                    &nbsp;<input type="checkbox" name="selectedIds" value="${menu.id}" title="${menu.id}" />
                </ec:column>
                <ec:column title="名称" property="name"></ec:column>
                <ec:column title="上级菜单" property="pname"></ec:column>
                 <ec:column title="页面类型" property="pagetype">
                    <c:choose>
                    <c:when test="${menu.pagetype eq 1}">
                    	父级类型
                    </c:when>
                    <c:when test="${menu.pagetype eq 2}">
                    	单页类型
                    </c:when>
                    <c:when test="${menu.pagetype eq 3}">
                    	列表类型
                    </c:when>
                    <c:when test="${menu.pagetype eq 4}">
                    	图文类型
                    </c:when>
                    </c:choose>
                </ec:column>
                
            </ec:row>
        </ec:table>
    </div>
</s:form>
<%@ include file="/common/footer.jsp"%>