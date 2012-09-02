// 新增node
function addNode(id, templateName) {
	var node = new WebFXTreeItem(id,templateName,'javascript:void(0);getChild(\'' + id + '\');','','${pageContext.request.contextPath}/js/xtree/images/foldericon.png','${pageContext.request.contextPath}/js/xtree/images/openfoldericon.png');
	node.folder = false;
	node._last = true;
	var nodeObj = getSelectedNode();
	nodeObj.add(node, false);
}
// 得到当前节点
function getSelectedNode() {
	return xTree.getSelected();
}
// 展开节点
function openNode(id) {
	var currentNode = getSelectedNode();
	var childs = currentNode.childNodes;
	var nodeId = webFXTreeHandler.idPrefix + id;
	for (var i = 0; i < childs.length; i++) {
		if (childs[i].id == nodeId) {
			childs[i].focus();
			getChild_1(id);
			break;
		}
	}
}

// 取得ajax返回的XML记录
function showXMLResponse(xml) {
	// 用于 IE 的代码：
	if (window.ActiveXObject) {
		var doc = new ActiveXObject("Microsoft.XMLDOM");
		doc.loadXML(xml);
		updateFolders(doc);
		// 用于 Mozilla, Firefox, Opera, 等浏览器的代码：
	} else {
		var parser = new DOMParser();
		var doc = parser.parseFromString(xml, "text/xml");
		updateFolders(doc);
	}
}

// 在树形菜单中加载文件夹
function updateFolders(doc) {
	var nodes = doc.getElementsByTagName("node");
	if (nodes != null) {
		var id = "";
		var name = "";
		var code = "";
		var nodeObj = getSelectedNode();
		var node = null;
		for (var i = 0; i < nodes.length; i++) {
			id = nodes[i].getAttribute("id");
			name = nodes[i].getAttribute("name");
			code = nodes[i].getAttribute("code");
			var node = new WebFXTreeItem(id, name,
					'javascript:void(0);getChild(\'' + id + '\');');
			node.folder = true;
			node._last = true;
			nodeObj.add(node, false);
		}
		nodeObj.expand();
	}
}