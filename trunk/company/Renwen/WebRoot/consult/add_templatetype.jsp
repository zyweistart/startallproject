<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/consult/add_templatetype.js"></script>
<s:form action="templatetype_save" method="post" validate="true" id="ec"  enctype="multipart/form-data" >
    <s:token />
    <s:hidden name="inputPage" value="/consult/add_templatetype.jsp" />
    <table border="0" cellpadding="1" cellspacing="1" width="100%" class="tableBgColor">
        <tr>
            <td align="right" width="8%" class="leftTdBgColor">
                &nbsp;模板名:
            </td>
            <td align="left" width="30%" class="rightTdBgColor">
                &nbsp;<s:textfield  name="templatetype.title"  id="title" cssClass="textInput"
					cssStyle="width:60%"/>
					<label id="titlexx"></label>
            </td>
             <td align="right" width="30%" class="leftTdBgColor">
               &nbsp;<br>模板页面地址<br>(<font color="red">从WebRoot下面开发写起例如：/consult/add_templatetype.jsp</font>)
            </td>
            <td align="left" width="30%" class="rightTdBgColor">
                &nbsp;<s:textfield  name="templatetype.mbPath"  id="mbPath"  cssClass="textInput"
					cssStyle="width:60%"/>
					<label id="mbPathxx"></label>
            </td>
        </tr>
        <tr>
            <td align="right" width="8%" class="leftTdBgColor">
                &nbsp;该模板用户是否可以使用:
            </td>
            <td align="left" width="35%" class="rightTdBgColor" >
                &nbsp;<s:radio name="templatetype.operatStatus"  list="#{1:'可以',2:'不可以'}" value="1"></s:radio>
            </td>
            <td align="right" width="8%" class="leftTdBgColor">
                &nbsp;展示几条数据：
            </td>
            <td align="left" width="35%" class="rightTdBgColor" >
                &nbsp;<s:textfield name="templatetype.dataSize"  value="1" cssClass="textInput"
					cssStyle="width:60%"/>
					<br><label><font color="red">*请最小填写 1   数字即可，不可存在中文字</font> </label>
            </td>
        </tr>
        <tr>
            <td align="right" width="8%" class="leftTdBgColor">
                &nbsp;模板预览图片:
            </td>
            <td align="left" width="35%" class="rightTdBgColor" >
                &nbsp;<input type="file" name="Upload" style="border: 1px solid #000000;">
            </td>
            <td align="right" width="8%" class="leftTdBgColor">
                &nbsp;是否该模板需要上传:
            </td>
            <td align="left" width="35%" class="rightTdBgColor" >
                &nbsp;<s:radio list="#{1:'是',2:'否'}" name="templatetype.upStatus"  value="1" ></s:radio>
            </td>
        </tr>
    </table>
    <br>
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
            <td align="center" width="30%">
                <input type="button" value="<fmt:message key="global.save" />"  id="isok" class="buttonStyle">
                <input type="reset" value="<fmt:message key="global.reset" />" class="buttonStyle">
                <input id="back" type="button" value="<fmt:message key="global.back" />" class="buttonStyle">
            </td>
        </tr>
    </table>
</s:form>
<%@ include file="/common/footer.jsp"%>
