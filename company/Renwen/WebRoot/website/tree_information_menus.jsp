<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/website/tree_information_menus.js"></script>
<link rel="stylesheet" href='${pageContext.request.contextPath}/common/man_xtree/xtree.css' type="text/css">
<div>
    <div align="center">
        <input name="save" id="okbutton" type="button" value="<fmt:message key="global.confirm" />" class="buttonStyle">
        <input type="button" value="<fmt:message key="global.cancel" />" onclick="window.close()" class="buttonStyle">
    </div>
    <div style="overflow: auto">
        <script language="JavaScript">
            var treeitem = new Array();
            if(document.getElementById) {
                webFXTreeConfig.rootIcon = "${pageContext.request.contextPath}/common/man_xtree/images/foldericon.png";
                webFXTreeConfig.openRootIcon = "${pageContext.request.contextPath}/common/man_xtree/images/openfoldericon.png";
                webFXTreeConfig.folderIcon = "${pageContext.request.contextPath}/common/man_xtree/images/foldericon.png";
                webFXTreeConfig.openFolderIcon = "${pageContext.request.contextPath}/common/man_xtree/images/openfoldericon.png";
                webFXTreeConfig.fileIcon = "${pageContext.request.contextPath}/common/man_xtree/images/file.png";
                webFXTreeConfig.iIcon = "${pageContext.request.contextPath}/common/man_xtree/images/I.png";
                webFXTreeConfig.lIcon = "${pageContext.request.contextPath}/common/man_xtree/images/L.png";
                webFXTreeConfig.lMinusIcon = "${pageContext.request.contextPath}/common/man_xtree/images/Lminus.png";
                webFXTreeConfig.lPlusIcon = "${pageContext.request.contextPath}/common/man_xtree/images/Lplus.png";
                webFXTreeConfig.tIcon = "${pageContext.request.contextPath}/common/man_xtree/images/T.png";
                webFXTreeConfig.tMinusIcon = "${pageContext.request.contextPath}/common/man_xtree/images/Tminus.png";
                webFXTreeConfig.tPlusIcon = "${pageContext.request.contextPath}/common/man_xtree/images/Tplus.png";
                webFXTreeConfig.blankIcon = "${pageContext.request.contextPath}/common/man_xtree/images/blank.png";
                var i = 0;
                var j = i+1;
                var root = new WebFXTree('<fmt:message key="dictionary.treeMenu" />');
                treeitem[0] =  new WebFXTreeItem(0,-1,'<fmt:message key="dictionary.topMenu" />');
                <c:forEach items="${menus}" var="menu">
                
				treeitem[j++] =  new WebFXTreeItem('${menu.id}','${menu.pid}',"${menu.name}");

                </c:forEach>
                root.add(treeitem[0]);
                getTree_new();
                document.write(root);}
        </script>
    </div>
</div>
<%@ include file="/common/footer.jsp"%>