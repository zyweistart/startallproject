<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/consult/individualAdd_newstype.js">
<!--
//-->
</script>
<s:form action="newstype_save" method="post" validate="true" id="ec"  enctype="multipart/form-data" >
    <s:token />
    <s:hidden name="pdId" value="%{pdId}" />
    <s:hidden name="newstype.pname" value="%{pdName}" />
    <s:hidden name="newstype.removeStatus" />
    <s:hidden name="inputPage" value="/consult/individualAdd_newstype.jsp" />
    <table border="0" cellpadding="1" cellspacing="1" width="100%" class="tableBgColor">
        <tr>
            <td align="right" width="15%" class="leftTdBgColor">
                &nbsp;目录名称<font color="red">*</font>：
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;<s:textfield name="newstype.name" cssClass="textInput" id="newstype"></s:textfield>
            </td>
             <td align="right" width="15%" class="leftTdBgColor">
               &nbsp;上级目录名称<font color="red">*</font>：
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
            <s:hidden name="newstype.pid" id="parentId"></s:hidden>
                &nbsp;${pdName}
            </td>
        </tr>
        <tr>
            <td align="right" width="15%" class="leftTdBgColor">
               &nbsp;选择模板效果<font color="red">*</font>：
            </td>
            <td align="left" width="35%" class="rightTdBgColor"  >
               &nbsp;
               <c:if test="${empty templatetypes}">
                  无模板
               </c:if>
               <c:if test="${not empty  templatetypes}">
               <s:select  list="%{templatetypes}"   listKey="id"  listValue="title"  id="category" name="newstype.category"  ></s:select>
               </c:if>
            </td>
              <td align="right" width="15%" class="leftTdBgColor">
               &nbsp;宣传图片<font color="red">*</font>：
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;<input type="file" name="Upload" style="border: 1px solid #000000;" >
            </td>
        </tr>
        <tr style="display: none;">
          <td align="right" width="15%" class="leftTdBgColor">
               &nbsp;目录是否可见<font color="red">*</font>：
            </td>
            <td align="left" width="35%" class="rightTdBgColor" >
               &nbsp;<s:radio name="newstype.tstart" list="#{99:'是',1:'否'}" value="99"></s:radio>
            </td>
        </tr>
        <tr>
           <td colspan="6" class="rightTdBgColor" >
             <b>模板内容效果图片</b><br> <span id="showeffect" ></span>
           </td>
        </tr>
        <tr style="display: none;">
            <td align="right" width="15%" class="leftTdBgColor">
               &nbsp;用户是否可以创建子级目录(该级目录下)：
            </td>
            <td align="left" width="35%" class="rightTdBgColor"  colspan="3">
               &nbsp;<s:radio name="newstype.operatStatus" list="#{1:'不可以',2:'可以'}" value="1"></s:radio>
            </td>
        </tr>
      
    </table>
    <br>
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
            <td align="center" width="30%">
                <input type="button" id="isok" value="<fmt:message key="global.save" />" class="buttonStyle">
                <input type="reset" value="<fmt:message key="global.reset" />" class="buttonStyle">
                <input id="back" type="button" value="<fmt:message key="global.back" />" class="buttonStyle">
            </td>
        </tr>
    </table>
</s:form>
<%@ include file="/common/footer.jsp"%>
