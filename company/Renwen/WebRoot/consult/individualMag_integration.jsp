<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
<script type="text/javascript"  src="${pageContext.request.contextPath}/js/consult/individualMag_integration.js"></script>
<s:form action="integration_manager" id="ec">
    <s:hidden name="Insert" id="insertAction" value="integration_showAdd.htm?pdid=%{id}" />
    <s:hidden name="update" id="updateAction" value="integration_showEdit.htm?pdid=%{id}" />
    <s:hidden name="delete" id="deleteAction" value="integration_delete.htm?pdid=%{id}" />
    <s:hidden name="issued" id="issuedAction" value="integration_rerouted.htm?pdid=%{id}" />
    <s:hidden name="undo" id="undoAction" value="integration_undo.htm?pdid=%{id}" />
    <div style="width:100%">
        <%-- 如果有查询条件，请在此补全 
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
       --%>
        <table border="0" cellpadding="0" cellspacing="0" width="100%" class="talbe_gray_border">
          <tr>
              <td align="left" width="8%">&nbsp;&nbsp;
              <c:if test="${not empty newstype.id}"><font size="3"><b>当前选中目录:</b></font>${newstype.name}</font>&nbsp;&nbsp;&nbsp;&nbsp;<font size="3"><b>使用模板:</b></font><font size="2">${newstype.templateTitle}</font></c:if>
              &nbsp;
               <c:if test="${newstype.removeStatus==1 &&  newstype.operatStatus==2}">
                <b>操作:</b><a href="${pageContext.request.contextPath}/newstype_showAdd.htm?pdId=${newstype.id}" style="TEXT-DECORATION:none;">添加子级</a>
               </c:if>
               <c:if test="${newstype.operatStatus==1 && newstype.removeStatus==2}">
                <b>操作:</b><a href="${pageContext.request.contextPath}/newstype_individualDelete.htm?pdId=${newstype.id}" style="TEXT-DECORATION:none;" id="clremove">删除</a> 
               </c:if>
               <c:if test="${newstype.operatStatus==2 && newstype.removeStatus==2}">
                <b>操作:</b><a href="${pageContext.request.contextPath}/newstype_showAdd.htm?pdId=${newstype.id}" style="TEXT-DECORATION:none;">添加子级</a>
                |<a href="${pageContext.request.contextPath}/newstype_individualDelete.htm?pdId=${newstype.id}" style="TEXT-DECORATION:none;" id="clremove">删除</a> 
               </c:if>
               </td>
          </tr>
        </table>
        <ec:table items="integrations" var="integration" action="${pageContext.request.contextPath}/integration_individualMag.htm?id=${id}" retrieveRowsCallback="limit" filterRowsCallback="limit"
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
                <ec:column title="首页通知公告(状态)" property="tzStart" >
                  <c:if test="${integration.tzStart eq 0 }">否</c:if>
                  <c:if test="${integration.tzStart eq 99 }"><font color="red">是</font></c:if>
                </ec:column>
                <ec:column title="更新时间" property="startDay" width="23%"/>
            </ec:row>
        </ec:table>
    </div>
</s:form>
<%@ include file="/common/footer.jsp"%>