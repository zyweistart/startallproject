<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
<s:form action="cs_save" method="post" validate="true">
    <s:token />
    <s:hidden name="inputPage" value="/test/add_cs.jsp" />
    <table border="0" cellpadding="1" cellspacing="1" width="100%" class="tableBgColor">
        <tr>
            <td align="center" width="35%" class="rightTdBgColor" colspan="4">
                &nbsp;<b><font size="5">备份数据库</font></b>
            </td>
        </tr>
         <tr>
            <td align="left" width="35%" class="rightTdBgColor" colspan="4">
                &nbsp;
                <font color="red">
                <b>注意事项:</b>
                  <br>
                   &nbsp;&nbsp;&nbsp;&nbsp; 1.远程备份数据库，需要有一个不是root用户的用户权限(即不可使用root用户，需要其他用户,否则备份出错)
                   <br>&nbsp;
                </font>
            </td>
        </tr>
         <tr>
            <td align="left" width="35%" class="rightTdBgColor" colspan="4">
                &nbsp;<b>----- 数据库部分信息  -----</b>
            </td>
        </tr>
        <tr>
            <td align="right" width="15%" class="leftTdBgColor">
                &nbsp;数据库名
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;<s:textfield  name="dbname" cssClass="textInput"  cssStyle="width:80%"/>
            </td>
             <td align="right" width="15%" class="leftTdBgColor">
               &nbsp;IP地址(本地可以不填写)
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;<s:textfield  name="host" cssClass="textInput"  cssStyle="width:80%"/>
            </td>
        </tr>
        <tr>
            <td align="right" width="15%" class="leftTdBgColor">
                &nbsp;用户名
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;<s:textfield  name="username" cssClass="textInput"  cssStyle="width:80%"/>
            </td>
             <td align="right" width="15%" class="leftTdBgColor">
               &nbsp;密码
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;<s:textfield  name="password" cssClass="textInput"  cssStyle="width:80%"/>
            </td>
        </tr>
               <tr>
            <td align="left" width="35%" class="rightTdBgColor" colspan="4">
                &nbsp;<b>----- 备注部分信息  -----</b>
            </td>
        </tr>
          <tr>
            <td align="right" width="15%" class="leftTdBgColor">
                &nbsp;备份者登记
            </td>
            <td align="left" width="35%" class="rightTdBgColor" colspan="3">
                &nbsp;<s:textfield  name="cs.csname" cssClass="textInput"  cssStyle="width:10%"/>
            </td>
        </tr>
        <tr>
            <td align="right" width="15%" class="leftTdBgColor">
               &nbsp;备注
            </td>
            <td align="left" width="35%" class="rightTdBgColor" colspan="3">
                &nbsp;<s:textarea name="cs.content" cssClass="textInput"  cols="10" rows="10" cssStyle="width:80%"/>
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
