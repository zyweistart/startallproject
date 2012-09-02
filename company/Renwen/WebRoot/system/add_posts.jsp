<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
<s:form action="posts_save" method="post" validate="true">
    <s:token />
    <s:hidden name="inputPage" value="/system/add_posts.jsp" />
    <table border="0" cellpadding="1" cellspacing="1" width="100%" class="tableBgColor">
        <tr>
            <td align="right" width="15%" class="leftTdBgColor">
                &nbsp;<fmt:message key="posts.code" /><fmt:message key="global.colon" />
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;<s:textfield name="posts.postCode" readonly="true" cssClass="textInput" cssStyle="width:75%"/>
            </td>
             <td align="right" width="15%" class="leftTdBgColor">
               &nbsp;<fmt:message key="posts.name" /><fmt:message key="global.colon" />
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;<s:textfield name="posts.postName"  cssClass="textInput" cssStyle="width:75%"/>
            </td>
        </tr>
        <tr>
            <td align="right" width="15%" class="leftTdBgColor">
                &nbsp;<fmt:message key="global.scription" /><fmt:message key="global.colon" />
            </td>
            <td align="left" width="35%" class="rightTdBgColor" colspan="3">
                &nbsp;<s:textarea name="posts.scription" cssStyle="width:90%;height:80px;"/>
            </td>
        </tr>
         <tr>
            <td align="right" width="15%" class="leftTdBgColor">
                &nbsp;<fmt:message key="global.createName" /><fmt:message key="global.colon" />
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;<s:textfield name="posts.createName" readonly="true" value="管理员" cssClass="textInput" cssStyle="width:35%"/>
            </td>
             <td align="right" width="15%" class="leftTdBgColor">
               &nbsp;<fmt:message key="global.createDate" /><fmt:message key="global.colon" />
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;<s:textfield name="posts.createDate" onfocus="WdatePicker({isShowClear:true,readOnly:true})"  cssClass="Wdate" cssStyle="width:35%"/>
            </td>
        </tr>
         <tr>
           <td align="right" width="15%" class="leftTdBgColor">
                &nbsp;<fmt:message key="global.status" /><fmt:message key="global.colon" />
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;<s:select name="posts.status" list="#{'1':'启用','2':'停用'}" cssStyle="width:25%"/>
            </td>
            <td align="right" width="15%" class="leftTdBgColor">
                &nbsp;<fmt:message key="global.sort" /><fmt:message key="global.colon" />
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;<s:textfield name="posts.sort" cssClass="textInput" cssStyle="width:25%"/>
            </td>
        </tr>
    </table>
    <br>
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
            <td align="center" width="30%">
                <input type="submit" value="<fmt:message key="global.save" />" class="buttonStyle">
                <input type="reset" value="<fmt:message key="global.reset" />" class="buttonStyle">
                <input id="back" type="button" value="<fmt:message key="global.back" />" class="buttonStyle">
            </td>
        </tr>
    </table>
</s:form>
<%@ include file="/common/footer.jsp"%>
