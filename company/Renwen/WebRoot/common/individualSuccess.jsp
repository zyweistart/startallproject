<%@ page language="java" pageEncoding="UTF-8"%>
<html>
    <body>
       
        <div style="width: 100%; height: 100%; text-align: center;">
            <div>
                <script type="text/javascript">
                  alert("${message}")
                	  window.parent.leftFrame.location.reload();
                	  window.location.href = ("${url}" == "null" || "${url}" == "") ? "#" : "${url}";
                	  
                </script>
            </div>
            
        </div>
    </body>
</html>