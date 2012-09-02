<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld"%>
<script type="text/javascript">
    function changeValidateCode(obj) {
        var timenow = new Date().getTime();
        obj.src="${pageContext.request.contextPath}/random.htm?d="+timenow;
    }
    <c:if test="${not empty message}">
        alert("用户、密码或验证码输入有误,请重试!");
    </c:if>
</script>

<html>
    <head>
        <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/login.css" type="text/css"></link>
        <fmt:setBundle basename="ApplicationResources" />
        <title>人文学院</title>
    </head>
    <body bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
        <table width="100%" height="600" border="0" cellpadding="0" cellspacing="0" background="images/login/login_01.gif">
            <tr>
                <td height="163" colspan="3">
                    <!--<div id="logo"></div>-->
                </td>
            </tr>
            <tr>
                <td>
                    &nbsp;
                </td>
                <td width="607" height="236" background="images/login/login_05.png">
                    <table width="100%" height="100%" border="0" cellpadding="0"
                        cellspacing="0">
                        <tr>
                            <td width="53%">
                                &nbsp;
                            </td>
                            <th width="30%" height="80">
                                &nbsp;
                            </th>
                            <td>
                                &nbsp;
                            </td>
                        </tr>
                        <tr>
                            <td>
                                &nbsp;
                            </td>
                            <td height="115">
                                <s:form action="dispatch">
                                  <s:hidden name="inputPage" value="/Login.jsp" />
                                    <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr>
                                            <td height="33%">
                                                用户名:
                                            </td>
                                            <td height="33%">
                                                <input type="text" name="username" class="input"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td height="33%">
                                                密&nbsp;&nbsp;码:
                                            </td>
                                            <td height="33%">
                                                <input type="password" name="password" class="input"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td height="33%">
                                                验证码:
                                            </td>
                                            <td height="33%">
                                                <input type="text" style="width: 45px;" maxlength="5" name="randcode" class="input_1">
                                                <img src="${pageContext.request.contextPath}/random.htm" onclick="changeValidateCode(this)" />
                                            </td>
                                        </tr>
                                        <tr>
                                            <td height="34%" align="center" colspan="2">
                                                <label>
                                                    <input name="Submit" type="submit" class="buttom" value="登录">
                                                </label>
                                                <label>
                                                    &nbsp;<input name="reset" type="reset" class="buttom" value="清除">
                                                </label>
                                            </td>
                                        </tr>
                                    </table>
                                </s:form>
                                <br>
                            </td>
                            <td>
                                &nbsp;
                            </td>
                        </tr>
                        <tr>
                            <td>
                                &nbsp;
                            </td>
                            <td>
                                &nbsp;
                            </td>
                            <td>
                                &nbsp;
                            </td>
                        </tr>
                    </table>
                </td>
                <td>
                    &nbsp;
                </td>
            </tr>
            <tr>
                <td>
                    &nbsp;
                </td>
                <td>
                    &nbsp;
                </td>
                <td>
                    &nbsp;
                </td>
            </tr>
        </table>
    </body>
</html>