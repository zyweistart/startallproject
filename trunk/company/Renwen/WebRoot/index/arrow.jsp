<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href='${pageContext.request.contextPath}/css/default/css.css' type="text/css">
<script language=javascript type="text/javascript">
        var iniCols, noCols, o_mf, o_ms, s;
        function ini() {
            o_mf = parent.document.getElementById("folder_Frame");
            o_ms = document.getElementById("leftFrame");
            noCols = iniCols = o_mf.cols;
            noCols = "0,5,*";
            s = false;
        }
        function changeLeft(){
            s = !s;
            o_mf.cols = s ? noCols : iniCols;
        }
    </script>
<body onload="ini()" oncontextmenu="return false;">
<table width="5" border="0" align="center" cellspacing="0" cellpadding="0">
	<tr>
		<td height="500" valign="middle"><img src="${pageContext.request.contextPath}/images/resize.gif" style="cursor: hand;" id="menuSwitch" onclick="changeLeft();" border="0"></td>
	</tr>
</table>
</body>
</html>