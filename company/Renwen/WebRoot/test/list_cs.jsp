<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
<s:form action="cs_list" id="ec">
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
        <ec:table items="css" var="cs" action="${pageContext.request.contextPath}/cs_list.htm" retrieveRowsCallback="limit" filterRowsCallback="limit" title="">
            <%--<ec:exportXls fileName="cs.xls" />
            <ec:exportPdf fileName="cs.pdf" />
            <ec:exportCsv fileName="cs.csv" />--%>
            <ec:row>
                <ec:column alias="num" title="global.serialNumber" cell="rowCount" width="3%" sortable="false" styleClass="tdCenter"/>
                <%-- 在此补全要展示的列 --%>
            </ec:row>
        </ec:table>
    </div>
</s:form>
<%@ include file="/common/footer.jsp"%>