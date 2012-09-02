<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/common/man_xtree/man_xtree.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/common/xtree/xtree.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/common/global/webtree.js"></script>
<link rel="stylesheet" href='${pageContext.request.contextPath}/css/default/css.css' type="text/css">
<link rel="stylesheet" href='${pageContext.request.contextPath}/common/xtree/xtree.css' type="text/css">
<style>
.webfx-tree-item a.selected-inactive {
	color: #FFFFFF;
	background: highlight;
	text-decoration: none;
}
</style>
<body class="left_table" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" oncontextmenu="return false;">
<div style='height: 600px; overflow-x: auto; overflow-y: auto; word-break: break-all;' ><script type="text/javascript">
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
         var topName = '前台菜单内容管理';
         var xTree = null;
         xTree = new WebFXTree(topId, topName, 'javascript:getChild(0);');
         <c:forEach items="${newstypes}" var="newstype">
             var xTreeNode = new WebFXTreeItem('${newstype.id}', '${newstype.name}', 'javascript:getChild(\'${newstype.id}\');');
             xTreeNode.folder = true;
             xTreeNode._last = true;
             xTree.add(xTreeNode, false);
         </c:forEach>
             document.write(xTree);
             //-->
     </script></div>
<script type="text/javascript">
<!--

function getChild(ids){
	if(ids==null){var url = "${pageContext.request.contextPath}/Login.jsp";}
	else if(ids==0){var url="${pageContext.request.contextPath}/blank.jsp";}
	else{
    var url = "${pageContext.request.contextPath}/integration_individualMag.htm?scope=${scope}&id="+ids;
	}
    if(getSelectedNode().childNodes.length==0){
        $.ajax({
           url : 'newstype_getChild.htm',
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
   parent.frames.fileFrame.location.href=url;
}

//-->
</script>
<%@ include file="/common/footer.jsp"%>
</body>