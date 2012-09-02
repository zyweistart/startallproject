<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
<s:form action="feedback_manager" id="ec">
    <s:hidden name="delete" id="deleteAction" value="feedback_delete.htm" />
    <div style="width:100%">
        <table border="0" cellpadding="0" cellspacing="0" width="100%" class="talbe_gray_border">
            <tr>
                <td align="right" width="8%">&nbsp;姓名</td>
                <td align="left" width="20%">
                    &nbsp;<s:textfield name="name" cssClass="textInput" value="%{feedback.name}"></s:textfield>
                </td>
                <td align="right" width="8%">&nbsp;联系方式</td>
                <td align="left" width="20%">
                    &nbsp;<s:textfield name="contact" cssClass="textInput" value="%{feedback.contact}"></s:textfield>
                </td>
                <td align="right" width="8%">&nbsp;E-mail</td>
                <td align="left" width="20%">
                    &nbsp;<s:textfield name="email" cssClass="textInput" value="%{feedback.email}"></s:textfield>
                </td>
                <td width="8%" align="center">
                    <input type="submit" value="<fmt:message key="global.query" />" class="buttonStyle"/>
                </td>
            </tr>
        </table>
        <ec:table items="feedbacks" var="feedback" action="${pageContext.request.contextPath}/feedback_manager.htm" retrieveRowsCallback="limit" filterRowsCallback="limit"
                  title="<img src='${pageContext.request.contextPath}/images/-.gif' id='Delete'/>">
            <ec:row>
                <ec:column alias="checkbox" styleClass="tdCenter" title="<input name='All' type='checkbox' id='All' title='选择所有'/>" width="3%" sortable="false">
                    &nbsp;<input type="checkbox" name="selectedIds" value="${feedback.id}" title="${feedback.id}" />
                </ec:column>
                <ec:column title="姓名" property="name"></ec:column>
                <ec:column title="联系方式" property="contact"></ec:column>
                <ec:column title="E-mail" property="email"></ec:column>
                <ec:column title="留言日期" property="startDay"></ec:column>
                <ec:column title="详细" property="content">
                  <a href="${pageContext.request.contextPath}/feedback_view.htm?id=${feedback.id}" style="text-decoration: none;" target="_blank"><font color="red">详细内容</font></a>
                </ec:column>
            </ec:row>
        </ec:table>
    </div>
</s:form>
<%@ include file="/common/footer.jsp"%>