<%@ page language="java" pageEncoding="UTF-8"%>
<html>
    <body>
        <div style="width: 100%; height: 100%; text-align: center;">
            <div>
                ${message }
            </div>
            <div>
				页面将在
                <span id="sec">5</span>秒内返回
            </div>
            <div>
				您也可以点击
                <a href="${(url == null) or ("" eq url) ? "#" : url }">此处</a> 返回
            </div>
        </div>
        <script type="text/javascript">
            var i = 6;
            function subSec(){
                i --;
                if(i <= 0){
                    window.location.href = ("${url}" == "null" || "${url}" == "") ? "#" : "${url}";
                }else{
                    setTimeout("subSec()", 1000);
                }
                document.getElementById("sec").innerHTML = i;
            }
            subSec();
        </script>
    </body>
</html>