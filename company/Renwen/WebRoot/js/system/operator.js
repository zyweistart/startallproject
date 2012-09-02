function preSubmit(formObj) {
	getSelectIds(formObj);
	document.forms(this.form).submit();
}

function getSelectIds(formObj) {
	var selectObj = formObj.selectedIds;
	var selectIds = "";
	for (var i = 0; i < selectObj.options.length; i++) {
		selectIds = i == 0 ? selectObj.options[i].value : selectIds + ","
				+ selectObj.options[i].value;
	}
	if (selectIds != "") {
		document.getElementById("roles").value = selectIds;
	}
}
