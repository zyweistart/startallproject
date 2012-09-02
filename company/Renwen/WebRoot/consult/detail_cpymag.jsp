<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
<table border="0" cellpadding="1" cellspacing="1" width="100%" class="tableBgColor">
     <tr>
          <td align="center" colspan="4" class="leftTdBgColor">
            <font size="4"><b>公司联系方式</b></font>(<font color="red"><b><a href="cpymag_showEdit.htm" style="text-decoration:none;color: red">修改信息</a></b></font>)
          </td>
        </tr>
        <tr>
          <td align="left" colspan="4" class="rightTdBgColor">
            <b>-------基本信息----------</b> 
          </td>
        </tr>
        <tr>
            <td align="right" width="15%" class="leftTdBgColor">
                &nbsp;公司地址:
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;${cpymag.address}
            </td>
             <td align="right" width="15%" class="leftTdBgColor">
               &nbsp;电话:
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;${cpymag.phone}
            </td>
        </tr>
        <tr>
          <td align="right" width="15%" class="leftTdBgColor">
                &nbsp;传真:
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;${cpymag.fax}
            </td>
             <td align="right" width="15%" class="leftTdBgColor">
               &nbsp;E-mail:
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;${cpymag.email}
            </td>
        </tr>
        <tr>
          <td colspan="4" class="leftTdBgColor">
             详细备注:
          </td>
        </tr>
        <tr>
          <td colspan="4" class="rightTdBgColor">
            ${cpymag.content}
          </td>
        </tr>
</table>
<br>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
        <td align="center" width="30%">
            <input id="back" type="button" value="<fmt:message key="global.back" />" class="buttonStyle">
        </td>
    </tr>
</table>
<%@ include file="/common/footer.jsp"%>
