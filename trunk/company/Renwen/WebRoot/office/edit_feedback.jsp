<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
    <table border="0" cellpadding="1" cellspacing="1" width="100%" class="tableBgColor">
        <tr>
            <td align="right" width="15%" class="leftTdBgColor">
                &nbsp;姓名:
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;${feedback.name}
            </td>
        </tr>
        <tr>
           <td align="right" width="15%" class="leftTdBgColor">
               &nbsp;联系方式:
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;${feedback.contact}
            </td>
        </tr>
        <tr>
           <td align="right" width="15%" class="leftTdBgColor">
               &nbsp;E-mail:
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;${feedback.email}
            </td>
        </tr>
        <tr>
           <td align="right" width="15%" class="leftTdBgColor">
               &nbsp;留言日期
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;${feedback.startDay }
            </td>
        </tr>
        <tr>
            <td align="right" width="15%" class="leftTdBgColor">
               &nbsp;留言内容:
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;${feedback.content}
            </td>
        </tr>
    </table>
    <br>
<%@ include file="/common/footer.jsp"%>
