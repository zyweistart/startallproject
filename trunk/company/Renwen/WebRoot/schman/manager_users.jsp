<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
<s:form action="users_manager" id="ec">
   <!-- <s:hidden name="insert" id="insertAction" value="users_showAdd.htm" />
    <s:hidden name="update" id="updateAction" value="users_showEdit.htm" />-->
    <s:hidden name="delete" id="deleteAction" value="users_delete.htm" />
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
        <!-- <img src='${pageContext.request.contextPath}/images/+.gif' id='Insert'/>
			&&<img src='${pageContext.request.contextPath}/images/edit.gif' id='Update'/> -->
        <ec:table items="userss" var="users" action="${pageContext.request.contextPath}/users_manager.htm" retrieveRowsCallback="limit" filterRowsCallback="limit"
                  title="<img src='${pageContext.request.contextPath}/images/-.gif' id='Delete'/>
			">
            <%--<ec:exportXls fileName="users.xls" />
            <ec:exportPdf fileName="users.pdf" />
            <ec:exportCsv fileName="users.csv" />--%>
            <ec:row>
                <ec:column alias="checkbox" styleClass="tdCenter" title="<input name='All' type='checkbox' id='All' title='选择所有'/>" width="3%" sortable="false">
                    &nbsp;<input type="checkbox" name="selectedIds" value="${users.id}" title="${users.id}" />
                </ec:column>
                <%-- 在此补全要展示的列 --%>
                <ec:column title="用户名(点击查看详情)" property="userName">
                <a href="${pageContext.request.contextPath}/visits_viewVisits.htm?userId=${users.id}">${users.userName}</a>
                </ec:column>
                 <ec:column title="姓名(点击查看详情)"  property="uname">
                <a href="${pageContext.request.contextPath}/visits_viewVisits.htm?userId=${users.id}">${users.uname}</a>
                </ec:column>
                <ec:column title="班级" property="userCls"></ec:column>
                <ec:column title="任课教师" property="userTeacther"></ec:column>
                <ec:column title="学期" property="userXq"></ec:column>
                <ec:column title="年份" property="userYear"></ec:column>
                <ec:column title="注册日期" property="startDay"></ec:column>
            </ec:row>
        </ec:table>
    </div>
</s:form>
<%@ include file="/common/footer.jsp"%>