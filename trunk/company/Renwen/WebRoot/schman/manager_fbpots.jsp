<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
<s:form action="fbpots_manager" id="ec">
    <s:hidden name="insert" id="insertAction" value="fbpots_showAdd.htm" />
    <s:hidden name="update" id="updateAction" value="fbpots_showEdit.htm" />
    <s:hidden name="delete" id="deleteAction" value="fbpots_delete.htm" />
    <div style="width:100%">
        <table border="0" cellpadding="0" cellspacing="0" width="100%" class="talbe_gray_border">
            <tr>
                <td align="right" width="8%">&nbsp;帖子标题</td>
                <td align="left" width="20%">
                    &nbsp;<s:textfield name="fpTitle" cssClass="textInput" value="%{fbpots.fpTitle}"></s:textfield>
                </td>
                <td align="right" width="8%">&nbsp;发布者</td>
                <td align="left" width="20%">
                    &nbsp;<s:textfield name="usersName" cssClass="textInput" value="%{fbpots.usersName}"></s:textfield>
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
        <ec:table items="fbpotss" var="fbpots" action="${pageContext.request.contextPath}/fbpots_manager.htm" retrieveRowsCallback="limit" filterRowsCallback="limit"
                  title="<img src='${pageContext.request.contextPath}/images/-.gif' id='Delete'/>">
            <ec:row>
                <ec:column alias="checkbox" styleClass="tdCenter" title="<input name='All' type='checkbox' id='All' title='选择所有'/>" width="3%" sortable="false">
                    &nbsp;<input type="checkbox" name="selectedIds" value="${fbpots.id}" title="${fbpots.id}" />
                </ec:column>
                <ec:column title="帖子标题" property="fpTitle"></ec:column>
                <ec:column title="发布者" property="usersName"></ec:column>
                <ec:column title="浏览量" property="pviews"></ec:column>
                <ec:column title="发布日期" property="startDay"></ec:column>
            </ec:row>
        </ec:table>
    </div>
</s:form>
<%@ include file="/common/footer.jsp"%>