<html>
	<%@ page contentType="text/html;charset=UTF-8" language="java"%>
	<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
	<fmt:setBundle basename="ApplicationResources" />
	<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", -1);
%>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/common/js/jquery.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/common/xtree/xtree.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/common/js/webtree.js"></script>
	<link rel="stylesheet"
		href='${pageContext.request.contextPath}/common/xtree/xtree.css'
		type="text/css">
	<style>
<!--
 @CHARSET "UTF-8";

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
-->
</style>
	<body>
		<div
			style='height: 600px; overflow-x: auto; overflow-y: auto; word-break: break-all;'>
			<script type="text/javascript">
               <!--
               webFXTreeConfig.rootIcon = "${pageContext.request.contextPath}/common/xtree/images/foldericon.png";
               webFXTreeConfig.openRootIcon = "${pageContext.request.contextPath}/common/xtree/images/openfoldericon.png";
               webFXTreeConfig.folderIcon = "${pageContext.request.contextPath}/common/xtree/images/foldericon.png";
               webFXTreeConfig.openFolderIcon = "${pageContext.request.contextPath}/common/xtree/images/openfoldericon.png";
               webFXTreeConfig.fileIcon = "${pageContext.request.contextPath}/common/xtree/images/file.png";
               webFXTreeConfig.iIcon = "${pageContext.request.contextPath}/common/xtree/images/I.png";
               webFXTreeConfig.lIcon = "${pageContext.request.contextPath}/common/xtree/images/L.png";
               webFXTreeConfig.lMinusIcon = "${pageContext.request.contextPath}/common/xtree/images/Lminus.png";
               webFXTreeConfig.lPlusIcon = "${pageContext.request.contextPath}/common/xtree/images/Lplus.png";
               webFXTreeConfig.tIcon = "${pageContext.request.contextPath}/common/xtree/images/T.png";
               webFXTreeConfig.tMinusIcon = "${pageContext.request.contextPath}/common/xtree/images/Tminus.png";
               webFXTreeConfig.tPlusIcon = "${pageContext.request.contextPath}/common/xtree/images/Tplus.png";
               webFXTreeConfig.blankIcon = "${pageContext.request.contextPath}/common/xtree/images/blank.png";
               var topId = "0";
               var topName = '数据字典管理';
               var xTree = null;
               xTree = new WebFXTree(topId, topName, 'javascript:void(0);');
               <c:forEach items="${dictionarys}" var="dictionary">
                   var xTreeNode = new WebFXTreeItem('${dictionary.id}', '${dictionary.name}', 'javascript:getChild(\'${dictionary.id}\');');
                   xTreeNode.folder = true;
                   xTreeNode._last = true;
                   xTree.add(xTreeNode, false);
               </c:forEach>
                   document.write(xTree);
                   //-->
           </script>
		</div>
	</body>
	<script type="text/javascript">
<!--
function getChild(ids){
    var url = "${pageContext.request.contextPath}/dictionary_manager.htm?dictionary.parentId="+ids;
    var url2 = "${pageContext.request.contextPath}/dictionary_manager.htm?pid="+ids;
    if(getSelectedNode().childNodes.length==0){
        $.ajax({
           url : 'dictionary_getChild.htm',
           type : 'post',
           dataType : 'html',
           data : 'id=' + ids + '&s=' + Math.round(Math.random() * 10000),
           success : function(xml) {
               showXMLResponse(xml);
           }
       });
   }else{
       getSelectedNode().expand();
   }
   parent.frames.operatorFrame.location.href=url2;
}
//-->
</script>
</html>
