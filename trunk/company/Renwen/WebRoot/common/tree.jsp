<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%
   response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
   response.setHeader("Pragma", "no-cache"); //HTTP 1.0
   response.setDateHeader("Expires", -1);
%>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<fmt:setBundle basename="ApplicationResources" />
<script language="JavaScript" src="${pageContext.request.contextPath}/js/default/outlook.js"></script>
<script language="JavaScript" src="${pageContext.request.contextPath}/common/global/crossbrowser.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/common/jquery/jquery-1.3.2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/common/global/common.js"></script>
<script type="text/javascript">
    <!--
    function resize_op5() {
        if (bt.op5) {
            o.showPanel(o.aktPanel);
            var s = new createPageSize();
            if ((screenSize.width!=s.width) || (screenSize.height!=s.height)) {
                screenSize=new createPageSize();
                setTimeout("o.resize(0,0,screenSize.width,screenSize.height)",100);
            }
            setTimeout("resize_op5()",100);
        }
    }
    function myOnResize() {
        if (bt.ie4 || bt.ie5 || bt.ns5) {
            var s=new createPageSize();
            o.resize(0,0,s.width,s.height);
        }
        else if (bt.ns4) location.reload();
    }
    //-->
</script>
<style>
    <!--
    body {
        margin-left: 0px;
        margin-top: 0px;
        margin-right: 0px;
        margin-bottom: 0px;
        scrollbar-face-color: #F5F5F5;
        scrollbar-highlight-color: #3A556E;
        scrollbar-shadow-color: #3A556E;
        scrollbar-3dlight-color: white;
        scrollbar-arrow-color: #3A556E;
        scrollbar-track-color: white;
        scrollbar-darkshadow-color: white;
    }
    .left_table{
        border-top-width: 1px;
        border-right-width: 1px;
        border-bottom-width: 1px;
        border-top-style: solid;
        border-right-style: solid;
        border-bottom-style: solid;
        border-top-color: #3A556E;
        border-right-color: #3A556E;
        border-bottom-color: #3A556E;
    }
    td {
        font-size: 12px;
        font-family: "宋体";
    }

    div {
        position: absolute;
    }

    a:link,a:visited {
        font-size: 12px;
        font-family: "新宋体";
        color: #333333;
        text-decoration: none;
    }

    a:hover {
        color: #FF6C00;
        text-decoration: underline;
    }

    .selected {
        color: #FF0000;
    }

    .unSelected {

    }
    -->
</style>
<html>
    <body class="left_table" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="resize_op5();" onResize="myOnResize();" oncontextmenu="return false;">
        <script type="text/javascript">
            var o = new createOutlookBar('Bar',0,0,screenSize.width,screenSize.height,'#ffffff','#ffffff');
                <c:forEach items="${models}" var="model" varStatus="st">
                var p${model.id};
                if(${model.grade == 0}){
                    p${model.id} = new createPanel('s${model.id}','${model.name}');
                    o.addPanel(p${model.id});
                }
                if(${model.grade == 1}){
                    if(p${model.parentId} != 'underfined' && p${model.parentId} != null){
                        if(${model.url != "#"}){
                            b${model.id} = new createButton('s${model.id}','${model.name}','${pageContext.request.contextPath}/images/b2nwhite.gif','${pageContext.request.contextPath}/${model.url}','mainFrame');
                            p${model.parentId}.addButton(b${model.id});
                        }else{
                            b${model.id} = new createButton('s${model.id}','${model.name}','${pageContext.request.contextPath}/images/b2owhite.gif','${pageContext.request.contextPath}/${model.url}','mainFrame');
                            p${model.parentId}.addButton(b${model.id});
                        }
                    }
                }
                if(${model.grade == 2}){
                    if(b${model.parentId} != 'underfined' && b${model.parentId} != null){
                        sub${model.id} = new createButton('s${model.id}','${model.name}','${pageContext.request.contextPath}/images/icon.gif','${pageContext.request.contextPath}/${model.url}','mainFrame');
                        b${model.parentId}.addButton(sub${model.id});
                    }
                }
            </c:forEach>
                o.draw();
        </script>
    </body>
</html>
