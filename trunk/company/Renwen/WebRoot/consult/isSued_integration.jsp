<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
<s:form action="integration_isSued" id="ec">
<s:hidden name="issued" id="issuedAction" value="integration_rerouted.htm" />
<s:hidden name="undo" id="undoAction" value="integration_undo.htm" />
    <div style="width:100%">
        <table border="0" cellpadding="0" cellspacing="0" width="100%" class="talbe_gray_border">
            <tr>
                <td align="right" width="8%">
                    &nbsp;信息标题
                </td>
                <td align="left" width="20%">
                    &nbsp;<s:textfield name="name" value="%{integration.name}"/>
                </td>
                <td align="right" width="8%">
                    &nbsp;信息类别
                </td>
                <td align="left" width="20%">
                    &nbsp;<s:textfield name="newsTypeName" value="%{integration.newsTypeName}"/>
                </td>
                <td align="right" width="8%">
                    &nbsp;是否发布
                </td>
                <td align="left" width="20%">
                    &nbsp;<s:select list="#{'':'全部',99:'已发布',1:'未发布'}" name="isSued" value="%{integration.isSued}"></s:select>
                </td>
                <td width="8%" align="center">
                    <input type="submit" value="<fmt:message key="global.query" />" class="buttonStyle"/>
                </td>
            </tr>
        </table>
        <ec:table items="integrations" var="integration" action="${pageContext.request.contextPath}/integration_isSued.htm" retrieveRowsCallback="limit" filterRowsCallback="limit" 
        title="<img src='${pageContext.request.contextPath}/images/send.gif' id='issued'/>
        &<img src='${pageContext.request.contextPath}/images/unSend.gif' id='undo'/>">
            <%-- <ec:exportXls fileName="news.xls" />
            <ec:exportPdf fileName="news.pdf" />
            <ec:exportCsv fileName="news.csv" />--%>
            <ec:row>
            <ec:column alias="checkbox" styleClass="tdCenter" title="<input name='All' type='checkbox' id='All' title='选择所有'/>" width="3%" sortable="false">
                    &nbsp;<input type="checkbox" name="selectedIds" value="${integration.id}" title="${integration.id}" />
                </ec:column>
                
                <%-- 在此补全要展示的列 --%>
                <ec:column title="标题" property="name" width="40%"  ></ec:column>
                <ec:column title="类型" property="newsTypeName" width="25%" ></ec:column>
                <ec:column title="状态" property="isSued" width="10%">
                  <c:if test="${integration.isSued==99}">
                   已发布
                  </c:if>
                  <c:if test="${integration.isSued!=99}">
                  <span style="color: red">未发布</span>
                  </c:if>
                </ec:column>
            </ec:row>
        </ec:table>
    </div>
</s:form>
<%@ include file="/common/footer.jsp"%>