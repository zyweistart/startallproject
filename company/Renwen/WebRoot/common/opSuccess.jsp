<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
    <head>
        <script type="text/javascript">
            var i = 4;
            function subSec(){
                i --;
                if(i <= 0){
                    closeWindow();
                }else{
                    setTimeout("subSec()", 1000);
                }
                document.getElementById("sec").innerHTML = i;
            }
            subSec();
                
            function closeWindow(){
                if(window.opener){
                    window.opener.location.reload();
                    window.close();
                }
            }
        </script>

    </head>
    <body onunload="closeWindow();">
        <div style="width: 100%; height: 100%; text-align: center;">
            <div>
                ${message }
            </div>
            <div>
				页面将在
                <span id="sec">3</span>秒内关闭
            </div>
            <div>
				您也可以点击
                <a href="#" onclick="closeWindow();">此处</a> 关闭窗口
            </div>
        </div>
    </body>
</html>