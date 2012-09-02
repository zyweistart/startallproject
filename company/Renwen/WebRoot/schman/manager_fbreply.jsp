<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
<s:form action="fbreply_manager" id="ec">
    <s:hidden name="insert" id="insertAction" value="fbreply_showAdd.htm" />
    <s:hidden name="update" id="updateAction" value="fbreply_showEdit.htm" />
    <s:hidden name="delete" id="deleteAction" value="fbreply_delete.htm" />
    <div style="width:100%">
        <table border="0" cellpadding="0" cellspacing="0" width="100%" class="talbe_gray_border">
            <tr>
                <td align="right" width="8%">&nbsp;帖子</td>
                <td align="left" width="20%">
                    &nbsp;<s:textfield name="potsTitle" cssClass="textInput" value="%{fbreply.potsTitle}"></s:textfield>
                </td>
                <td align="right" width="8%">&nbsp;回复者</td>
                <td align="left" width="20%">
                    &nbsp;<s:textfield name="userName" cssClass="textInput" value="%{fbreply.userName}"></s:textfield>
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
        <ec:table items="fbreplys" var="fbreply" action="${pageContext.request.contextPath}/fbreply_manager.htm" retrieveRowsCallback="limit" filterRowsCallback="limit"
                  title="
			<img src='${pageContext.request.contextPath}/images/-.gif' id='Delete'/>">
            <ec:row>
                <ec:column alias="checkbox" styleClass="tdCenter" title="<input name='All' type='checkbox' id='All' title='选择所有'/>" width="3%" sortable="false">
                    &nbsp;<input type="checkbox" name="selectedIds" value="${fbreply.id}" title="${fbreply.id}" />
                </ec:column>
                <ec:column title="帖子" property="potsTitle"></ec:column>
                <ec:column title="回复者" property="userName"></ec:column>
                <ec:column title="回复内容" property="rcontent"></ec:column>
                <ec:column title="回复日期" property="startDay"></ec:column>
            </ec:row>
        </ec:table>
    </div>
</s:form>
<%@ include file="/common/footer.jsp"%>