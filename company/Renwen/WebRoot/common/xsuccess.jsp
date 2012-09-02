<%@ page language="java" pageEncoding="UTF-8"%>
<html>
    <body>
        <script type="text/javascript">
          alert("${message}");
          window.location.href = ("${url}" == "null" || "${url}" == "") ? "#" : "${url}";
        </script>
    </body>
</html>