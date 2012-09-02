<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld"%>
<%@ taglib prefix="fn" uri="/WEB-INF/tld/fn.tld"%>

<html>
<head>
  <title></title>
</head>
<body>
   <s:form action="fdbmag_save.htm" method="post" validate="true" id="ec">
     <s:hidden name="fdbmag.replyStatus"></s:hidden>
      <table  border="0" cellpadding="0" cellspacing="0">
        <tbody>
            <tr>
              <td><b>疑问、建议</b></td>
            </tr>
            <tr>
             <td><s:textarea name="fdbmag.fproblem" cols="10" rows="10"></s:textarea> </td>
            </tr>
            <tr>
               <td>
                 <input type="button" name="isok" id="isok"  value="提交" >
               </td>
            </tr>
        </tbody>
      </table>
   </s:form>
</body>
</html>