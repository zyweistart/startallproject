<%@page contentType="text/html" pageEncoding="UTF-8"%>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/common/kindEditor/themes/default/default.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/common/kindEditor/plugins/code/prettify.css" />
	<script charset="utf-8" src="${pageContext.request.contextPath}/common/kindEditor/kindeditor.js"></script>
	<script charset="utf-8" src="${pageContext.request.contextPath}/common/kindEditor/lang/zh_CN.js"></script>
	<script charset="utf-8" src="${pageContext.request.contextPath}/common/kindEditor/plugins/code/prettify.js"></script>
	<script>
		KindEditor.ready(function(K) {
			var editor1 = K.create('textarea[name="${editorName}"]', {
				cssPath : '${pageContext.request.contextPath}/plugins/code/prettify.css',
				uploadJson : '${pageContext.request.contextPath}/fileEditor',
				fileManagerJson : '${pageContext.request.contextPath}/kindfile_uploadfile_manager.htm',
				allowFileManager : true,
				width:"100%",
				height:"350px",
				afterBlur:function() {
					var self = this;
					self.sync();
			   }
			});
			prettyPrint();
		});
	</script>
		<textarea name="${editorName}" cols="100" rows="8" style="width:700px;height:200px;visibility:hidden;">${editorValue}</textarea>

