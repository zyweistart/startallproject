<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
<s:form action="integration_manager" id="ec">
    <s:hidden name="insert" id="insertAction" value="integration_showAdd.htm" />
    <s:hidden name="update" id="updateAction" value="integration_showEdit.htm" />
    <s:hidden name="delete" id="deleteAction" value="integration_delete.htm" />
    <s:hidden name="issued" id="issuedAction" value="integration_rerouted.htm" />
    <s:hidden name="undo" id="undoAction" value="integration_undo.htm" />
    <div style="width:100%">
        <%-- 如果有查询条件，请在此补全 --%>
        <table border="0" cellpadding="0" cellspacing="0" width="100%" class="talbe_gray_border">
            <tr>
                <td align="right" width="8%">&nbsp;标题名称</td>
                <td align="left" width="20%">
                    &nbsp;<s:textfield name="name" cssClass="textInput" value="%{integration.name}"/>
                </td>
                <td align="right" width="8%">&nbsp;菜单目录</td>
                <td align="left" width="20%">
                    &nbsp;<s:textfield name="newsTypeName" cssClass="textInput" value="%{integration.newsTypeName}"/>
                </td>
                <td align="right" width="8%">&nbsp;是否发布</td>
                <td align="left" width="20%">
                    &nbsp;<s:select list="#{'':'全部',99:'已发布',1:'未发布'}" name="isSued" value="%{integration.isSued}"></s:select>
                </td>
                <td width="8%" align="center">
                    <input type="submit" value="<fmt:message key="global.query" />" class="buttonStyle"/>
                </td>
            </tr>
        </table>
       
        <ec:table items="integrations" var="integration" action="${pageContext.request.contextPath}/integration_manager.htm" retrieveRowsCallback="limit" filterRowsCallback="limit"
                  title="<img src='${pageContext.request.contextPath}/images/send.gif' id='issued'/>
            &<img src='${pageContext.request.contextPath}/images/unSend.gif' id='undo'/>
            &<img src='${pageContext.request.contextPath}/images/+.gif' id='Insert'/>
			&<img src='${pageContext.request.contextPath}/images/-.gif' id='Delete'/>
			&<img src='${pageContext.request.contextPath}/images/edit.gif' id='Update'/>">
            <%--<ec:exportXls fileName="integration.xls" />
            <ec:exportPdf fileName="integration.pdf" />
            <ec:exportCsv fileName="integration.csv" />--%>
            <ec:row>
                <ec:column alias="checkbox" styleClass="tdCenter" title="<input name='All' type='checkbox' id='All' title='选择所有'/>" width="3%" sortable="false">
                    &nbsp;<input type="checkbox" name="selectedIds" value="${integration.id}" title="${integration.id}" />
                </ec:column>
                <%-- 在此补全要展示的列 --%>
                <ec:column title="标题名称" property="name" width="25%">
                <c:if test="${integration.category  !=11 }">
                 <a href="integration_detail.htm?id=${integration.id}" target="_blank" style="text-decoration:none" title="点击查看详细信息">${integration.name}</a>
                </c:if>
                 <c:if test="${integration.category eq 11 }">
                 ${integration.name}
                </c:if>
                </ec:column>
                <ec:column title="菜单目录" property="newsTypeName" width="15%"></ec:column>
                <ec:column title="状态" property="isSued" width="10%">
                  <c:if test="${integration.isSued==99}">
                   已发布
                  </c:if>
                  <c:if test="${integration.isSued!=99}">
                  <span style="color: red">未发布</span>
                  </c:if>
                </ec:column>
                <ec:column title="更新时间" property="startDay" width="23%">
                </ec:column>
            </ec:row>
        </ec:table>
    </div>
</s:form>
<%@ include file="/common/footer.jsp"%>