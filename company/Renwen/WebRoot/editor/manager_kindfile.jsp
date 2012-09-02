<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
<s:form action="kindfile_manager" id="ec">
    <s:hidden name="insert" id="insertAction" value="kindfile_showAdd.htm" />
    <s:hidden name="update" id="updateAction" value="kindfile_showEdit.htm" />
    <s:hidden name="delete" id="deleteAction" value="kindfile_delete.htm" />
    <div style="width:100%">
    <!-- 
        <table border="0" cellpadding="0" cellspacing="0" width="100%" class="talbe_gray_border">
            <tr>
                <td align="right" width="8%">&nbsp;</td>
                <td align="left" width="20%">
                </td>
                <td align="right" width="8%">&nbsp;</td>
                <td align="left" width="20%">
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
         -->
        <ec:table items="kindfiles" var="kindfile" action="${pageContext.request.contextPath}/kindfile_manager.htm" retrieveRowsCallback="limit" filterRowsCallback="limit"
                  title="
			<img src='${pageContext.request.contextPath}/images/-.gif' id='Delete'/>
			">
            <ec:row>
                <ec:column alias="checkbox" styleClass="tdCenter" title="<input name='All' type='checkbox' id='All' title='选择所有'/>" width="3%" sortable="false">
                    &nbsp;<input type="checkbox" name="selectedIds" value="${kindfile.id}" title="${kindfile.id}" />
                </ec:column>
                <ec:column title="文件名称(点击查看详细)" property="fileName">
                <img src="${pageContext.request.contextPath}${kindfile.filePath}${kindfile.fileName}"  alt="点击可查看详细"  width="50px" height="50px"  onclick="javascript:window.open('${pageContext.request.contextPath}${kindfile.filePath}${kindfile.fileName}', 'newwindow', 'height=400, width=400, top=0, left=0, toolbar=no, menubar=no, resizable=no,location=n o, status=no')">
                </ec:column>
                <ec:column title="文件类型" property="fileType"></ec:column>
                <ec:column title="文件大小" property="fileSize"></ec:column>
                <ec:column title="文件上传日期" property="fileSize"></ec:column>
                <ec:column title="文件上传时间" property="fileTime"></ec:column>
            </ec:row>
        </ec:table>
    </div>
</s:form>
<%@ include file="/common/footer.jsp"%>