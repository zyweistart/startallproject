<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
<table border="0" cellpadding="1" cellspacing="1" width="100%" class="tableBgColor">
    <tr>
       <td align="center" class="leftTdBgColor" colspan="4">
          <h1>用户详细使用情况分析</h1>
       </td>
    </tr>
    <tr>
        <td align="right" width="15%" class="leftTdBgColor">
            &nbsp;姓名:
        </td>
        <td align="left" width="35%" class="rightTdBgColor" colspan="3">
            &nbsp;${Visitsmag.userName}
        </td>
    </tr>
     <tr>
        <td align="right" width="15%" class="leftTdBgColor">
            &nbsp;总访问时间:
        </td>
        <td align="left" width="35%" class="rightTdBgColor">
            &nbsp;${Visitsmag.visitsMax}
        </td>
         <td align="right" width="15%" class="leftTdBgColor">
           &nbsp;总访问次数:
        </td>
        <td align="left" width="35%" class="rightTdBgColor">
            &nbsp;${Visitsmag.visitsSize}
        </td>
    </tr>
      <tr>
        <td align="right" width="15%" class="leftTdBgColor">
            &nbsp;总测试库访问时间:
        </td>
        <td align="left" width="35%" class="rightTdBgColor">
            &nbsp;${Visitsmag.visitsExtmMax}
        </td>
         <td align="right" width="15%" class="leftTdBgColor">
           &nbsp;总测试库访问次数:
        </td>
        <td align="left" width="35%" class="rightTdBgColor">
            &nbsp;${Visitsmag.visitsExtmSize}
        </td>
    </tr>
    <tr>
     <td  class="leftTdBgColor" colspan="4">
        <B>详细时间分析：</B>
     </td>
    </tr>
    <tr>
       <td class="rightTdBgColor"  colspan="4" >
           <c:forEach var="n" items="${visitss}">
               &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;开始访问时间：${n.startDay}  总停留时间：${n.recordSize} <Br>
           </c:forEach>
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
