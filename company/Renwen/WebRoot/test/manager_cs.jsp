<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
<s:form action="cs_manager" id="ec">
    <s:hidden name="insert" id="insertAction" value="cs_showAdd.htm" />
   <!-- <s:hidden name="update" id="updateAction" value="cs_showEdit.htm" /> --> 
    <s:hidden name="delete" id="deleteAction" value="cs_delete.htm" />
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
        <!-- &<img src='${pageContext.request.contextPath}/images/edit.gif' id='Update'/> -->
        <ec:table items="css" var="cs" action="${pageContext.request.contextPath}/cs_manager.htm" retrieveRowsCallback="limit" filterRowsCallback="limit"
                  title="<img src='${pageContext.request.contextPath}/images/+.gif' id='Insert'/>
			&<img src='${pageContext.request.contextPath}/images/-.gif' id='Delete'/>
			">
            <%--<ec:exportXls fileName="cs.xls" />
            <ec:exportPdf fileName="cs.pdf" />
            <ec:exportCsv fileName="cs.csv" />--%>
            <ec:row>
                <ec:column alias="checkbox" styleClass="tdCenter" title="<input name='All' type='checkbox' id='All' title='选择所有'/>" width="3%" sortable="false">
                    &nbsp;<input type="checkbox" name="selectedIds" value="${cs.id}" title="${cs.id}" />
                </ec:column>
                <%-- 在此补全要展示的列 --%>
                <ec:column title="备份日期" property="startDay" />
                <ec:column title="登记者" property="csname" />
                <ec:column title="备注" property="content" />
                <ec:column title="状态" property="stort" />
                 <ec:column title="下载" property="id" >
                  <a href="${pageContext.request.contextPath}${cs.filepath}"  style=" text-decoration: none;"><font color="red">下载</font></a>&nbsp;&nbsp;&nbsp;&nbsp;
                  <a href="${pageContext.request.contextPath}/cs_showEdit.htm?id=${cs.id}"  style=" text-decoration: none;"><font color="red">还原</font></a>
                </ec:column>
            </ec:row>
        </ec:table>
    </div>
</s:form>
<%@ include file="/common/footer.jsp"%>