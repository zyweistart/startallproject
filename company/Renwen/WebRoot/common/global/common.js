$(document).ready(function() {
	var selectedIds = "";
	var definitionCode = "";
	$("#All").click(function() {
		if ($(this).attr("checked")) {
			$("input[name='selectedIds']").attr("checked", true);
			selectedIds = "";
			definitionCode = "";
			$("input[name='selectedIds']").each(function() {
				ajustSelectedIds($(this));
			});
		} else {
			$("input[name='selectedIds']").attr("checked", false);
			selectedIds = "";
			definitionCode = "";
		}
	});

	$("input[name='selectedIds']").click(function() {
		ajustAllStatus();
		ajustSelectedIds($(this));
	});

	function ajustSelectedIds(o) {
		if ($(o).attr("checked")) {
			if ("" == selectedIds) {
				selectedIds += $(o).val();
				definitionCode += $(o).attr("title");
			} else {
				if (selectedIds.indexOf($(o).val()) < 0) {
					selectedIds += "," + $(o).val();
					definitionCode += "," + $(o).attr("title");
				}
			}
		} else {
			var index = selectedIds.indexOf($(o).val());
			if (index > 0) {
				selectedIds = selectedIds.substring(0, index - 1) + selectedIds.substring(index + $(o).val().length);
				definitionCode = definitionCode.substring(0, index - 1) + definitionCode.substring(index + $(o).attr("title").length);
			} else if (index == 0) {
				selectedIds = selectedIds.substring(0, index) + selectedIds.substring(index + $(o).val().length);
				definitionCode = definitionCode.substring(0, index) + definitionCode.substring(index + $(o).attr("title").length);
			}
			deleteHeadOrTail();
		}
	}

	function deleteHeadOrTail() {
		if (selectedIds.indexOf(",") == 0) {
			selectedIds = selectedIds.substring(1);
			definitionCode = definitionCode.substring(1);
		}
		if (selectedIds.lastIndexOf(",") == selectedIds.length - 1) {
			selectedIds = selectedIds.substring(0, selectedIds.length - 1);
			definitionCode = definitionCode.substring(0, definitionCode.length - 1);
		}
	}

	/**
	 * 检查至少有一个复选框被选中
	 */
	function checkEmpty() {
		if (null == selectedIds || "undefined" == selectedIds || "" == selectedIds) {
			alert("编号不能为空！");
			return false;
		}
		return true;
	}
	/**
	 * 检查只能有一条被选中
	 */
	function checkSingle() {
		if (selectedIds.indexOf(",") > 0) {
			alert("只能选择一条！");
			return false;
		}
		return true;
	}

	function ajustAllStatus() {
		var count = 0;
		var flag = false;
		$("input[name='selectedIds']").each(function() {
			if (!$(this).attr("checked")) {
				count++;
			}
		});
		if (count == 0) {
			flag = true;
		}
		$("#All").attr("checked", false);
	}

	
	/**
	 * 添加按扭
	 */
	$("#Insert").click(function() {
		locateTo($("#insertAction").val());
		return false;
	});

	/**
	 * 添加按扭
	 */
	$("#addFolder").click(function() {
		locateTo($("#addFolderAction").val());
		return false;
	});

	/**
	 * 添加按扭
	 */
	$("#addFile").click(function() {
		openWindows($("#addFileAction").val(), 720, 500);
		return false;
	});

	/**
	 * 删除按扭
	 */
	$("#Delete").click(function() {
		if (!checkEmpty()) { return false; }
		if (!confirm("确定删除吗？")) return false;
		var a;
		if ($("#deleteAction").val().indexOf("?") < 0) {
			a = $("#deleteAction").val() + "?selectedIds=" + selectedIds;
		} else {
			a = $("#deleteAction").val() + "&selectedIds=" + selectedIds;
		}
		locateTo(a);
		return false;
	});
	
	/**
	 * 撤销按扭
	 */
	   $("#unpass").click(function() {
	        if (!checkEmpty()) { return false; }
	        if (!confirm("确定退回吗？")) return false;
	        var a;
	        if ($("#unpassAction").val().indexOf("?") < 0) {
	            a = $("#unpassAction").val() + "?selectedIds=" + selectedIds;
	        } else {
	            a = $("#unpassAction").val() + "&selectedIds=" + selectedIds;
	        }
	        locateTo(a);
	        return false;
	    });
	   /**
	     * 撤消按钮操作
	     */
	    $("#undo").click(function() {
	        if (!checkEmpty()) {
	            return false;
	        }

	        // 确认删除吗？
	        if (!confirm("确定撤销吗？"))
	            return false;

	        var a;
	        if ($("#undoAction").val().indexOf("?") < 0) {
	            a = $("#undoAction").val() + "?selectedIds=" + selectedIds;
	        } else {
	            a = $("#undoAction").val() + "&selectedIds=" + selectedIds;
	        }
	        locateTo(a);
	        return false;
	    });
	    /**
	     * 发布按钮操作
	     */
	    $("#issued").click(function() {
	        if (!checkEmpty()) {
	            return false;
	        }

	        // 确认操作吗？
	        if (!confirm("确定操作吗？"))
	            return false;

	        var a;
	        if ($("#issuedAction").val().indexOf("?") < 0) {
	            a = $("#issuedAction").val() + "?selectedIds=" + selectedIds;
	        } else {
	            a = $("#issuedAction").val() + "&selectedIds=" + selectedIds;
	        }
	        locateTo(a);
	        return false;
	    });
	
	/**
	 * 移动文件
	 */
    $("#moving").click(function(){
    	if (!checkEmpty()) { return false; }
    	if ($("#movingFilesAction").val().indexOf("?") < 0) {
			a = $("#movingFilesAction").val() + "?selectedIds=" + selectedIds;
		} else {
			a = $("#movingFilesAction").val() + "&selectedIds=" + selectedIds;
		}
    	
    	openWindows(a,250,500);
		return false;
    });
    /**
	 * 复制文件
	 */
    $("#copy").click(function(){
    	if (!checkEmpty()) { return false; }
    	if ($("#copyFilesAction").val().indexOf("?") < 0) {
			a = $("#copyFilesAction").val() + "?selectedIds=" + selectedIds;
		} else {
			a = $("#copyFilesAction").val() + "&selectedIds=" + selectedIds;
		}
    	openWindows(a,250,500);
		return false;
    });
    
	
	
	
	/**
	 * 彻底删除按扭
	 */
	$("#Remove").click(function() {
		if (!checkEmpty()) { return false; }
		if (!confirm("确定彻底删除吗？")) return false;
		var a;
		if ($("#removeAction").val().indexOf("?") < 0) {
			a = $("#removeAction").val() + "?selectedIds=" + selectedIds;
		} else {
			a = $("#removeAction").val() + "&selectedIds=" + selectedIds;
		}
		locateTo(a);
		return false;
	});

	/**
	 * 发布
	 */
	$("#send").click(function() {
		if (!checkEmpty()) { return false; }
		if (!confirm("确定发布吗？")) return false;
		var a;
		if ($("#sendAction").val().indexOf("?") < 0) {
			a = $("#sendAction").val() + "?selectedIds=" + selectedIds;
		} else {
			a = $("#sendAction").val() + "&selectedIds=" + selectedIds;
		}
		locateTo(a);
		return false;
	});

	/**
	 * 撤消按扭
	 */
	$("#unsend").click(function() {
		if (!checkEmpty()) { return false; }
		if (!confirm("确定撤消吗？")) return false;
		var a;
		if ($("#unsendAction").val().indexOf("?") < 0) {
			a = $("#unsendAction").val() + "?selectedIds=" + selectedIds;
		} else {
			a = $("#unsendAction").val() + "&selectedIds=" + selectedIds;
		}
		locateTo(a);
		return false;
	});
	
	/**
	 * 启用
	 */
	$("#use").click(function() {
		if (!checkEmpty()) { return false; }
		if (!confirm("确定启用吗？")) return false;
		var a;
		if ($("#sendAction").val().indexOf("?") < 0) {
			a = $("#sendAction").val() + "?selectedIds=" + selectedIds;
		} else {
			a = $("#sendAction").val() + "&selectedIds=" + selectedIds;
		}
		locateTo(a);
		return false;
	});

	/**
	 * 停用
	 */
	$("#stop").click(function() {
		if (!checkEmpty()) { return false; }
		if (!confirm("确定停用吗？")) return false;
		var a;
		if ($("#unsendAction").val().indexOf("?") < 0) {
			a = $("#unsendAction").val() + "?selectedIds=" + selectedIds;
		} else {
			a = $("#unsendAction").val() + "&selectedIds=" + selectedIds;
		}
		locateTo(a);
		return false;
	});
	/**
	 * 还原按扭
	 */
	$("#Recover").click(function() {
		if (!checkEmpty()) { return false; }
		if (!confirm("确定还原吗？")) return false;
		var a;
		if ($("#recoverAction").val().indexOf("?") < 0) {
			a = $("#recoverAction").val() + "?selectedIds=" + selectedIds;
		} else {
			a = $("#recoverAction").val() + "&selectedIds=" + selectedIds;
		}
		locateTo(a);
		return false;
	});
	/**
	 * 修改按扭
	 */
	$("#Update").click(function() {
		if (!checkEmpty()) { return false; }
		if (!checkSingle()) { return false; }
		var a;
		if ($("#updateAction").val().indexOf("?") < 0) {
			a = $("#updateAction").val() + "?selectedIds=" + selectedIds;
		} else {
			a = $("#updateAction").val() + "&selectedIds=" + selectedIds;
		}
		locateTo(a);
		return false;
	});
	
	/**
	 * 修改按扭
	 */
	$("#Forward").click(function() {
		if (!checkEmpty()) { return false; }
		if (!checkSingle()) { return false; }
		var a;
		if ($("#forwardAction").val().indexOf("?") < 0) {
			a = $("#forwardAction").val() + "?id=" + selectedIds;
		} else {
			a = $("#forwardAction").val() + "&id=" + selectedIds;
		}
		locateTo(a);
		return false;
	});
	$("#buildexam").click(function() {
		if (!checkEmpty()) { return false; }
		if (!confirm("确定要生成试卷吗？")) return false;
		var a;
		if ($("#buildexamAction").val().indexOf("?") < 0) {
			a = $("#buildexamAction").val() + "?selectedIds=" + selectedIds;
		} else {
			a = $("#buildexamAction").val() + "&selectedIds=" + selectedIds;
		}
		locateTo(a);
		return false;
	});
	
	
	/**
	 * 文档分享时用  打开新窗口
	 */
	$("#share").click(function() {
		if (!checkEmpty()) { return false; }
		var a;
		 
		if ($("#shareAction").val().indexOf("?") < 0) {
			a = $("#shareAction").val() + "?selectedIds=" + selectedIds;
		} else {
			a = $("#shareAction").val() + "&selectedIds=" + selectedIds;
		}
		window.showModalDialog(a, window, 'dialogWidth:' + 600 + 'px;dialogHeight:' + 700 + 'px;center:yes;help:no;status:no;scroll:no');
		return false;
	});
	
	/**
	 * 文档分享时用  取消分享
	 */
	$("#unshare").click(function() {
		if (!checkEmpty()) { return false; }
		var scope=$("#unshareAction").val();
		$.ajax( {
			url : 'file_unshare.htm',
			type : 'post',
			dataType : 'html',
			data : 'selectedIds=' + selectedIds + 
			       '&scope='+scope+
			       '&s=' + Math.round(Math.random() * 10000),
			success : function(msg) {
			if(msg!=null&&msg!=''){
				alert(msg);
			}
			location.reload();
			}
		});
		return false;
	});
	/**
	 * 客户关系管理系统  取消分享
	 */
	$("#unsharecustomers").click(function() {
		if (!checkEmpty()) { return false; }
		$.ajax( {
			url : 'customers_unshare.htm',
			type : 'post',
			dataType : 'html',
			data : 'selectedIds=' + selectedIds + '&s=' + Math.round(Math.random() * 10000),
			success : function(msg) {
			if(msg!=null&&msg!=''){
				alert(msg);
			}
			location.reload();
			}
		});
		return false;
	});
	

	/**
	 * 提交按扭
	 */
	$("#Submit").click(function() {
		if (!checkEmpty()) { return false; }
		if (!checkSingle()) { return false; }
		if (!confirm("确认提交申请吗？")) { return false; }
		if (selectFirstOperator(definitionCode)) {
			var a;
			var runIds = $("#executorIds").val();
			var runNames = $("#executorNames").val();
			if ($("#submitAction").val().indexOf("?") < 0) {
				a = $("#submitAction").val() + "?selectedIds=" + selectedIds;
			} else {
				a = $("#submitAction").val() + "&selectedIds=" + selectedIds;
			}
			a += "&executorIds=" + runIds + "&executorNames=" + runNames;
			locateTo(a);
		}
		return false;
	});

	/**
	 * 备份
	 */
	$("#BackUp").click(function() {
		$.ajax( {
			url : 'database_backup.htm',
			type : 'post',
			dataType : 'html',
			data : 'id=' + ids + '&s=' + Math.round(Math.random() * 10000),
			success : function(msg) {
				if (msg != null && msg != '') {
					alert(msg);
				}
			}
		});
	});
	
	$("#Recive").click(function(){
		$.ajax( {
			url : 'inbox_reciveMail.htm',
			type : 'post',
			dataType : 'html',
			data : 's=' + Math.round(Math.random() * 10000),
			success : function(msg) {
				if (msg != null && msg != '') {
					alert(msg);
					window.parent.mainFrame.location.reload();
				}
			}
		});
	});
	/**
	 * 页面跳转
	 */
	function locateTo(h) {
		window.location.href = h;
	}
	/**
	 * 返回
	 */
	$("#back").click(function() {
		history.back(-1);
	});
	$("#daoru").click(function() {
		locateTo($("#daoruAction").val());
		return false;
	});

	$("img[name='img']").click(function(){
		imgClick($(this).attr("id"),$(this).attr("msg"));
	});
	
	
	function imgClick(id,msg){
		if (!checkEmpty()) { return false; }
		if (confirm(msg)){ 
			var a;
			if ($("#"+id+"Action").val().indexOf("?") < 0) {
				a = $("#"+id+"Action").val() + "?selectedIds=" + selectedIds;
			} else {
				a = $("#"+id+"Action").val() + "&selectedIds=" + selectedIds;
			}
			locateTo(a);
		}
	}
	

});



/**
 * 根据流程编号选择审批人
 * 
 * @param defCode
 *            流程编号
 */
function selectFirstOperator(defCode) {
	var parArray = window.showModalDialog('monitoring_getFirstOperator.htm?selectedIds=' + defCode, window, 'dialogWidth:' + 550 + 'px;dialogHeight:' + 400
			+ 'px;center:yes;help:no;status:no;scroll:no');
	if (parArray != undefined) {
		document.getElementById("executorIds").value = parArray[0];
		document.getElementById("executorNames").value = parArray[1];
		return true;
	}
	return false;
}

/**
 * 取出下一步的执行人
 * 
 * @param {}
 *            instCode
 * @param {}
 *            nodeCode
 * @return {Boolean}
 */
function selectNextOperator(instCode, nodeCode) {
	var url = 'monitoring_getNextOperator.htm?instancescode=' + instCode + '&nodeCode=' + nodeCode;
	var parArray = window.showModalDialog(encodeURI(url), window, 'dialogWidth:' + 550 + 'px;dialogHeight:' + 400 + 'px;center:yes;help:no;status:no;scroll:no');
	if (parArray != undefined) {
		document.getElementById("executorIds").value = parArray[0];
		document.getElementById("executorNames").value = parArray[1];
		return true;
	}
	return false;
}

/**
 * 打开办理工作的界面
 */
function openProcessing() {
	var width = screen.width - 10;
	var height = screen.height - 75;
	window.open('monitoring_personalDotask.htm', 'newwindow', 'left=0,top=0,toolbar=no,location=no,status=yes,menubar=no,scrollbars=yes,width==' + width + ',height=' + height + '');
}

function processWork(processCode, nodeCode) {
  window.location = "monitoring_running.htm?processcode=" + encodeURIComponent(encodeURIComponent(processCode)) + "&nodeCode=" + encodeURIComponent(encodeURIComponent(nodeCode));
}
function detail_personalProtask(processCode, nodeCode){
	window.open("monitoring_detail_personalProtask.htm?processcode=" + encodeURIComponent(encodeURIComponent(processCode)) + "&nodeCode=" + encodeURIComponent(encodeURIComponent(nodeCode)));
}

/**
 * 取得当前的执行状况
 * 
 * @param {}
 *            instCode
 * @param {}
 *            nodeCode
 */
function getActiveTasksView(processCode, nodeCode) {
	var url = 'monitoring_activeTasksView.htm?processcode=' + processCode + '&nodeCode=' + nodeCode;
	window.showModalDialog(encodeURI(url), window, 'dialogWidth:1024px;dialogHeight:600px;center:yes;help:no;status:no;scroll:no');
}

/**
 * 取得执行情度
 * 
 * @param {}
 *            instCode
 * @param {}
 *            nodeCode
 */
function getScheduleView(appId, objectType) {
	var url = 'monitoring_scheduleView.htm?id=' + appId + '&typeCode=' + objectType;
	window.showModalDialog(encodeURI(url), window, 'dialogWidth:1024px;dialogHeight:600px;center:yes;help:no;status:no;scroll:no');
}

/**
 * 打开新窗口
 */
function openWindows(url, w, h) {
	var iTop = (screen.height - 30 - h) / 2;
	var iLeft = (screen.width - 10 - w) / 2;
	window.open(url, 'window', 'height=' + h + ',width=' + w + ',top=' + iTop + ',left=' + iLeft + ',toolbar=no,menubar=no,scrollbars=yes,resizeable=no,location=no,status=no');
}

/**
 * 打开新窗口(模态)
 */
function openDialog(l, w, h) {
	if (w == null) w = 600;
	if (h == null) h = 400;
	var parArray = window.showModalDialog(l, window, 'dialogWidth:' + w + 'px;dialogHeight:' + h + 'px;center:yes;help:no;status:yes;scroll:auto');
	return parArray;
}
function updateSelectedElement(selectElementID, selectedValue) {
	var selectElement = document.getElementById(selectElementID);
	for ( var i = 0; i < selectElement.options.length; i++) {
		if (selectElement.options[i].value == selectedValue) {
			selectElement.options[i].selected = true;
		} else {
			selectElement.options[i].selected = false;
		}
	}
}

function updateRadioElement(radioElementName, radioValue) {
	var radioElement = document.getElementsByName(radioElementName);
	for ( var i = 0; i < radioElement.length; i++) {
		if (radioElement[i].value == radioValue) {
			radioElement[i].checked = true;
		}
	}
}

function updateCheckBoxElement(checkBoxElementName, checkValue) {
	var checkBoxElement = document.getElementsByName(checkBoxElementName);
	var checkValueArray = new Array();
	checkValueArray = checkValue.split(",");
	for ( var i = 0; i < checkBoxElement.length; i++) {
		for ( var j = 0; j < checkValueArray.length; j++) {
			if (checkBoxElement[i].value == checkValueArray[j]) {
				checkBoxElement[i].checked = true;
			}
		}
	}
}

/**
 * 取选择框的值
 * 
 * @param selectElementID
 * @return String
 */
function getSelectedValue(selectElementID) {
	var selectElement = document.getElementById(selectElementID);
	return selectElement.options[selectElement.selectedIndex].value;
}
function getSelectedText(selectElementID) {
	var selectElement = document.getElementById(selectElementID);
	return selectElement.options[selectElement.selectedIndex].innerText;
}
function getSelectedTexts(selectElementID,selectElementIndex) {
	var selectElement = document.getElementById(selectElementID);
	colls = selectElement.options;
	for(i=0;i<colls.length;i++){
		if(colls[i].value==selectElementIndex){
			return selectElement.options[i].innerText;
		}
	}
}
/**
 * 取得单选框的值
 * 
 * @param selectElementName
 * @return String
 */
function getRadioValue(selectElementName) {
	var radioElement = document.getElementsByName(selectElementName);
	var readioValue = '';
	for ( var i = 0; i < radioElement.length; i++) {
		if (radioElement[i].checked == true) readioValue = radioElement[i].value;
		break;
	}
	return readioValue;
}

/**
 * 取得复选框的值
 * 
 * @param checkBoxElementName
 * @return String
 */
function getCheckBoxValue(checkBoxElementName) {
	var checkBoxElement = document.getElementsByName(checkBoxElementName);
	var result = "";
	for ( var i = 0; i < checkBoxElement.length; i++) {
		if (checkBoxElement[i].checked) {
			if (i == 0) result += checkBoxElement[i].value;
			else result += "," + checkBoxElement[i].value;
		}
	}
	return result;
}
/**
 * 删除附件
 * 
 * @param {}
 *            annexId
 */
function deleteAnnex(annexId) {
	if (confirm("确定要删除此附件吗?")) {
		$.ajax( {
			url : 'annex_delete.htm',
			type : 'post',
			dataType : 'html',
			data : 'id=' + annexId + '&s=' + Math.round(Math.random() * 10000),
			success : function(txt) {
				if (txt == "success") {
					alert("删除成功!");
					window.location.reload();
				}
			}
		});
	}
}

//增加一行
function addRow(table) {
    var tableObj = document.getElementById(table);
    var rowIndex = getNextRowIndex(tableObj);
    var trObj = tableObj.insertRow(rowIndex);
    var tdObj = trObj.insertCell(0);
    tdObj.align = "left";
    tdObj.innerHTML = '<input name="Filedata" type="file" style="width:100%" class="textInput">';
    tdObj = trObj.insertCell(1);
    tdObj.align = "center";
    tdObj.innerHTML = '<img src="/images/-.gif" border="0" style="cursor:hand;" onclick="delRow(\'AnnexTable\',this.parentNode)">';
}
//得到当前行号
function getRowIndex(tdObj) {
    var trObj = tdObj.parentNode;
    return trObj.rowIndex;
}
//得到下一行的序号  
function getNextRowIndex(tableObj) {
    return tableObj.rows.length;
}
//删除一行
function delRow(table, tdObj) {
    var tableObj = document.getElementById(table);
    var currentRow = getRowIndex(tdObj);
    tableObj.deleteRow(currentRow);
}
