$(document).ready(function() {
	var options = {
		url : 'authority_save.htm',
		dataType : 'html',
		beforeSubmit : showRequest,
		success : showResponse
	};
    
	$("#authForm").ajaxForm(options);

	$("#typeCode").change(function() {
		var typeCode = $(this).val();
		$.ajax({
			url : 'authority_ajax.htm',
			type : 'post',
			dataType : 'html',
			data : 'typeCode=' + typeCode + '&s='+ Math.round(Math.random() * 10000),
			success : function(xml) {
				if (window.ActiveXObject) {
					var doc = new ActiveXObject("Microsoft.XMLDOM");
					doc.loadXML(xml);
					changTypeList(typeCode, doc);
				} else {
					var parser = new DOMParser();
					var doc = parser.parseFromString(xml, "text/xml");
					changTypeList(typeCode, doc);
				}
			}
		});
	});

	function changTypeList(typeCode, doc) {
		var nodes = doc.getElementsByTagName("node");
		if (nodes != null) {
			var id = "";
			var name = "";
			var code = "";
			var temp = "";
			for (var i = 0; i < nodes.length; i++) {
				id = nodes[i].getAttribute("id");
				name = nodes[i].getAttribute("name");
				temp += "<tr>" 
                        + "<td height=\"25\" style=\"padding-left: 6px\">"
						+ "<input type=\"radio\" name=\"typeName\" value=\""+id+"\"" 
                        + "onclick=\"selectedRadio('"+id+"');\"> "
						+ name + "</td>" + "</tr>"
			}
			$("#typeList").html(temp);
		}
	}
});

function selectedRadio(selectedIds) {
	var type = $("#typeCode").val();
	$.ajax({
		url : 'authority_getModelsToJson.htm',
		type : 'post',
		dataType : 'html',
		data : 'selectedIds=' + selectedIds + '&typeCode=' + type + '&s='+ Math.round(Math.random() * 10000),
		success : function(msg) {
            if(msg != '')
            var ids = msg.split(",");
            $("input[name='selectedIds']").attr("checked", false);
            for (var i = 0; i < ids.length; i++) {
                var id = "box"+ ids[i].replace(new RegExp("(^[\\s]*)|([\\s]*$)","g"), "");
                $("#" + id).attr("checked", true);
            }
		}
	});
}

function showRequest() {
	return true;
}

function showResponse(msg) {
	if (msg == 'success') {
		alert('保存成功!');
	} else {
		alert('保存失败!');
	}
}

function display(id) {
	var traget = document.getElementById("id" + id);
	var image = document.getElementById("image" + id);
	if (traget != null) {
		if (traget.style.display == "none") {
			traget.style.display = "";
			image.src = "./images/ttree_exp1m.gif";
		} else {
			traget.style.display = "none";
			image.src = "./images/ttree_exp1p.gif";
		}
	}
}

function checkAll(obj, id) {
	var chkObjArray = document.getElementById(id).getElementsByTagName("input");
	for (var i = 0; i < chkObjArray.length; i++) {
		chkObjArray[i].checked = obj.checked;
	}
}