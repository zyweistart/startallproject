<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
<s:form action="logs_manager" id="ec">
    <s:hidden name="delete" id="deleteAction" value="logs_delete.htm" />
    <div style="width:100%">
        <table border="0" cellpadding="0" cellspacing="0" width="100%" class="talbe_gray_border">
            <tr>
                <td align="right" width="8%">&nbsp;登陆人员:</td>
                <td align="left" width="20%">
                    &nbsp;<s:textfield name="loginName" value="%{logs.loginName}" cssClass="textInput" cssStyle="width:85%" />
                </td>
                <td align="right" width="8%">&nbsp;登陆IP:</td>
                <td align="left" width="20%">
                    &nbsp;<s:textfield name="loginIp" value="%{logs.loginIp}" cssClass="textInput" cssStyle="width:85%" />
                </td>
                 <td align="right" width="8%">&nbsp;登陆时间:</td>
                <td align="left" width="20%">
                    &nbsp;<s:textfield name="loginDate" value="%{logs.loginDate}" cssClass="Wdate" onfocus="WdatePicker({isShowClear:true,readOnly:true})" cssStyle="width:85%" />
                </td>
                <td width="8%" align="center">
                    <input type="submit" value="<fmt:message key="global.query" />" class="buttonStyle"/>
                </td>
            </tr>
        </table>
        <ec:table items="logss" var="logs" action="${pageContext.request.contextPath}/logs_manager.htm" retrieveRowsCallback="limit" filterRowsCallback="limit"
                  title="<img src='${pageContext.request.contextPath}/images/-.gif' id='Delete'/>">
            <ec:exportXls fileName="logs.xls" />
            <ec:exportPdf fileName="logs.pdf" />
            <ec:exportCsv fileName="logs.csv" />
            <ec:row>
                <ec:column alias="checkbox" styleClass="tdCenter" title="<input name='All' type='checkbox' id='All' title='选择所有'/>" width="3%" sortable="false">
                    &nbsp;<input type="checkbox" name="selectedIds" value="${logs.id}" title="${logs.id}" />
                </ec:column>
               <ec:column styleClass="tdLeft" property="loginName" title="登陆人员" width="15%" />
               <ec:column styleClass="tdLeft" property="loginIp" title="登陆IP" width="15%" />
               <ec:column styleClass="tdLeft" property="loginDate" title="登陆时间" width="15%" />
            </ec:row>
        </ec:table>
    </div>
</s:form>
<%@ include file="/common/footer.jsp"%>