<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
<s:form action="information_release" id="ec">
    <s:hidden name="issued" id="issuedAction" value="information_rerouted.htm" />
    <s:hidden name="undo" id="undoAction" value="information_undo.htm" />
    <div style="width:100%">
        <table border="0" cellpadding="0" cellspacing="0" width="100%" class="talbe_gray_border">
            <tr>
                <td align="right" width="5%">&nbsp;标题</td>
                <td align="left" width="15%">
                    &nbsp;<s:textfield name="title" cssClass="textInput" value="%{information.title}"></s:textfield>
                </td>
                <td align="right" width="5%">&nbsp;创建者</td>
                <td align="left" width="15%">
                    &nbsp;<s:textfield name="createUserName" cssClass="textInput" value="%{information.createUserName}"></s:textfield>
                </td>
                <td align="right" width="5%">&nbsp;发布状态</td>
                <td align="left" width="15%">
                    &nbsp;<s:select list="#{'':'全部',1:'未发布',2:'已发布'}" value="%{information.releases}" name="releases" cssStyle="width=130"></s:select>
                </td>
                <td align="right" width="5%">&nbsp;栏目分类</td>
                <td align="left" width="20%">
                    &nbsp;
                    <s:textfield readonly="true" name="menuname"  value="%{information.menuname}" cssStyle="width:30%"  cssClass="textInput" id="pname"></s:textfield>
					<input type="button" value="请选择" class="buttonStyle" onclick="openDialog('information_trees.htm',300,650)">
                	<s:hidden name="menuId"  value="%{information.menuId}" id="pid"></s:hidden>
                </td>
                <td width="8%" align="center">
                    <input type="submit" value="<fmt:message key="global.query" />" class="buttonStyle"/>
                </td>
            </tr>
        </table>
        <ec:table items="informations" var="information" action="${pageContext.request.contextPath}/information_release.htm" retrieveRowsCallback="limit" filterRowsCallback="limit"
                  title="<img src='${pageContext.request.contextPath}/images/send.gif' id='issued'/>
			&<img src='${pageContext.request.contextPath}/images/unSend.gif' id='undo'/>">
            <ec:row>
                <ec:column alias="checkbox" styleClass="tdCenter" title="<input name='All' type='checkbox' id='All' title='选择所有'/>" width="3%" sortable="false">
                    &nbsp;<input type="checkbox" name="selectedIds" value="${information.id}" title="${information.id}" />
                </ec:column>
                
                <ec:column title="标题" property="title">
                	<a href="${pageContext.request.contextPath}/information_detail.htm?id=${information.id}">${information.title}</a>
                </ec:column>
                <ec:column title="创建时间" property="createTime"></ec:column>
                <ec:column title="创建者" property="createUserName"></ec:column>
                <ec:column title="点击率" property="hits"></ec:column>
                <ec:column title="发布状态" property="releases">
                    <c:choose>
                    <c:when test="${information.releases eq 1}">
                    	<span style="color: red">未发布</span>
                    </c:when>
                    <c:when test="${information.releases eq 2}">
                   	 	<span style="color: green">已发布</span>
                    </c:when>
                    </c:choose>
                </ec:column>
                
            </ec:row>
        </ec:table>
    </div>
</s:form>
<%@ include file="/common/footer.jsp"%>