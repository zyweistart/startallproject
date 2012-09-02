<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/common/jquery/jquery-1.3.2.js"></script>
<html>
    <head>
        <META http-equiv="Content-Type" content="text/html; charset=UTF-8" >
        <link rel="stylesheet" href='${pageContext.request.contextPath}/css/reddish/css.css' type="text/css">
    </head>
    <script language=javascript type="text/javascript">
        var iniCols, noCols, o_mf, o_ms, s;
        function ini() {
            o_mf = parent.document.getElementById("O_frame");
            o_ms = document.getElementById("treeFrame");
            noCols = iniCols = o_mf.cols;
            noCols = "0,5,*";
            s = false;
        }
        function changeLeft(){
            s = !s;
            o_mf.cols = s ? noCols : iniCols;
            o_ms.src = s ? "${pageContext.request.contextPath}/images/arrow-right.gif" : "${pageContext.request.contextPath}/images/arrow-left.gif";
        }
    </script>
    <body onload="ini()" >
        <table width="5" border="0" align="center" cellspacing="0" cellpadding="0">
            <tr>
                <td height="500" valign="middle"><img src="${pageContext.request.contextPath}/images/arrow-left.gif" style="cursor:hand;" id="menuSwitch" onclick="changeLeft();" border="0"></td>
            </tr>
        </table>
    </body>
</html>