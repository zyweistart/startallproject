<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
<script type="text/javascript">

//
$(document).ready(function() {
    function checkform() {
            var pid = $("#pid").val();
          if(pid!=""){
            if(isNaN(pid)){
                //alert("几级类型必须是数字");
                return 'Yes';
            }
          }
        return "YES";
    }
        $("#isok").click(function() {
                if (checkform() != '') {
                    ec.submit();
                }
            });
});
</script>
<s:form action="newstype_manager" id="ec">
	<s:hidden name="insert" id="insertAction" value="newstype_showAdd.htm" />
	<s:hidden name="update" id="updateAction" value="newstype_showEdit.htm" />
	<s:hidden name="delete" id="deleteAction" value="newstype_delete.htm" />
	<div style="width: 100%">
		<table border="0" cellpadding="0" cellspacing="0" width="100%"
			class="talbe_gray_border">
			<tr>
				<td align="right" width="8%">
					&nbsp;名称
				</td>
				<td align="left" width="20%">
					&nbsp;
					<s:textfield name="name" cssClass="textInput" value="%{newstype.name}"></s:textfield>
				</td>
				<td align="right" width="8%">
					&nbsp;上级名称
				</td>
				<td align="left" width="20%">
					<s:textfield name="pname" cssClass="textInput" value="%{newstype.pname}"></s:textfield>
					&nbsp;
				</td>
				<!-- 
				<td align="right" width="8%">
					&nbsp; 几级类型
				</td>
				<td align="left" width="20%">
					&nbsp;
					<s:textfield name="length" value="%{newstype.length}" id="pid"></s:textfield>
				</td> -->
				<td width="8%" align="center">
					<input type="button" id="isok"
						value="<fmt:message key="global.query" />" class="buttonStyle" />
				</td>
			</tr>
		</table>
		<ec:table items="newstypes" var="newstype"
			action="${pageContext.request.contextPath}/newstype_manager.htm"
			retrieveRowsCallback="limit" filterRowsCallback="limit"
			title="<img src='${pageContext.request.contextPath}/images/+.gif' id='Insert'/>
			&<img src='${pageContext.request.contextPath}/images/-.gif' id='Delete'/>
			&<img src='${pageContext.request.contextPath}/images/edit.gif' id='Update'/>">
			<%--<ec:exportXls fileName="newstype.xls" />
            <ec:exportPdf fileName="newstype.pdf" />
            <ec:exportCsv fileName="newstype.csv" />--%>
			<ec:row>
				<ec:column alias="checkbox" styleClass="tdCenter"
					title="<input name='All' type='checkbox' id='All' title='选择所有'/>"
					width="3%" sortable="false">
                    &nbsp;<input type="checkbox" name="selectedIds"
						value="${newstype.id}" title="${newstype.id}" />
				</ec:column>
				<%-- 在此补全要展示的列 --%>
				<ec:column property="id" title="ID"></ec:column>
				<ec:column property="name" title="菜单名称"></ec:column>
				<ec:column property="pid" title="上级ID"></ec:column>
				<ec:column property="pname" title="上级菜单名称"></ec:column>
				<ec:column property="category" title="显示形式">
				  <font color="red">${newstype.templateTitle}</font>
				</ec:column>
				<%-- 
				<ec:column property="length" title="几级类型"></ec:column>
				 --%>
			</ec:row>
		</ec:table>
	</div>
</s:form>
<%@ include file="/common/footer.jsp"%>