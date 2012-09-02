<%@ page language="java" pageEncoding="UTF-8"%>
<html>
    <body>
       
        <div style="width: 100%; height: 100%; text-align: center;">
            <div>
                <script type="text/javascript">
                  alert("留言成功！")
                	  window.location.href = ("${url}" == "null" || "${url}" == "") ? "#" : "${url}"+"?id="+${users.id};
                </script>
            </div>
            
        </div>
    </body>
</html>