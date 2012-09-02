<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/consult/add_newstype.js">
<!--

//-->
</script>
<s:form action="newstype_save" method="post" validate="true" id="ec" enctype="multipart/form-data">
    <s:token />
    <s:hidden name="newstype.id" />
    <s:hidden name="newstype.xcImg" />
    <s:hidden name="inputPage" value="/consult/edit_newstype.jsp" />
    <table border="0" cellpadding="1" cellspacing="1" width="100%" class="tableBgColor">
        <tr>
            <td align="right" width="15%" class="leftTdBgColor" >
                &nbsp;菜单名称:
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;<s:textfield name="newstype.name" cssClass="textInput" id="newstype"></s:textfield>
            </td><span style="color: red"></span>
             <td align="right" width="15%" class="leftTdBgColor">
               &nbsp;上级名称:
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
            <s:hidden name="newstype.pid" id="parentId"></s:hidden>
                &nbsp;<s:textfield cssClass="textInput" name="newstype.pname" id="parentName"></s:textfield>
                <input type="button" value="请选择" class="button"
                    onclick="openDialog('newstype_tree.htm',300,650)">
            </td>
        </tr>
         <tr>
         <td align="right" width="15%" class="leftTdBgColor">
               &nbsp;显示形式:
            </td>
            <td align="left" width="35%" class="rightTdBgColor" >
               &nbsp; <s:select  list="%{templatetypes}"   listKey="id"  listValue="title"  id="category" name="newstype.category"  ></s:select>
               <label><font color="red">(修改后 以前添加内容排放容易出错 请谨慎处理)</font></label>
            </td>
             <td align="right" width="15%" class="leftTdBgColor">
               &nbsp;首页菜单:
            </td>
            <td align="left" width="35%" class="rightTdBgColor" >
               &nbsp;<s:radio name="newstype.tstart" list="#{99:'是',1:'否'}" value="%{newstype.tstart}"></s:radio>
            </td>
        </tr>
         <tr>
           <td colspan="6" class="rightTdBgColor" >
             <b>模板内容效果图片</b><br> <span id="showeffect" ></span>
           </td>
        </tr>
           <tr>
         <td colspan="6" class="rightTdBgColor" >
           <b>------以下可不填写--------</b>
         </td>
        </tr>
        <tr>
            <td align="right" width="15%" class="leftTdBgColor">
               &nbsp;用户是否可以创建子级目录(该级目录下)：
            </td>
            <td align="left" width="35%" class="rightTdBgColor" >
               &nbsp;<s:radio name="newstype.operatStatus" list="#{1:'不可以',2:'可以'}" ></s:radio>
            </td>
             <td align="right" width="15%" class="leftTdBgColor">
               &nbsp;该目录，用户是否可以删除：
            </td>
            <td align="left" width="35%" class="rightTdBgColor" >
               &nbsp;<s:radio name="newstype.removeStatus" list="#{1:'不可以',2:'可以'}" ></s:radio>
            </td>
        </tr>
        <tr>
           <td align="right" width="15%" class="leftTdBgColor">
               &nbsp;菜单URL:
            </td>
            <td align="left" width="35%" class="rightTdBgColor"  colspan="3">
               &nbsp;<s:textfield name="newstype.ljUrl" cssStyle="width:40%" cssClass="textInput" id="ljurl"/>
             <label>(<font color="red">比如：newstype_manager.htm 或者/consult/manager_newstype.jsp</font>)</label>
            </td>
        </tr>
        <tr>
           <td align="right" width="15%" class="leftTdBgColor">
               &nbsp;宣传图:
            </td>
            <td align="left" width="35%" class="rightTdBgColor"  >
               &nbsp;<input type="file" name="Upload" style="border: 1px solid #000000;" >
            </td>
             <td align="right" width="15%" class="leftTdBgColor">
               &nbsp;菜单URL:
            </td>
            <td align="left" width="35%" class="rightTdBgColor" >
               &nbsp;<s:textfield name="newstype.imgUrl" cssStyle="width:70%" cssClass="textInput" id="ljurl"/>
            </td>
        </tr>
         <c:if test="${!empty newstype.xcImg }">
          <tr>
           <td align="right" width="15%" class="leftTdBgColor">
               &nbsp;是否不需要宣传图:
            </td>
            <td align="left" width="35%" class="rightTdBgColor" colspan="3" >
               &nbsp;<input type="checkbox" name="pdxc"  value="1">不需要
            </td>
         </tr>
        </c:if>
         <tr>
         <td colspan="6" class="rightTdBgColor" >
           <b>宣传图(浏览)</b>
         </td>
        </tr>
        <tr>
         <td colspan="6" class="rightTdBgColor" >
           <c:if test="${!empty newstype.xcImg }">
            <img  src="${pageContext.request.contextPath}${newstype.xcImg}">
           </c:if>
           <c:if test="${empty newstype.xcImg }">
            <font color="red" size="5"><b>该菜单无宣传图片</b></font>
           </c:if>
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
