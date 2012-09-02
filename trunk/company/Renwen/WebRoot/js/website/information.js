$(document).ready(function() {
	$("#btnsubmit").click(function(){
		var pid=$("#pid").val();
		if(pid==''){
			alert("菜单目录不能为空!");
			return;
		}
		var pagetype=$("#pagetype").val();
		if(pagetype==''){
			alert("页类型不能为空!");
			return;
		}
		var informationTitle=$("#informationTitle").val();
		if(informationTitle==''){
			alert("标题不能为空！!");
			return;
		}
		if(pagetype=='4'){
			var UploadScalePic=$("#UploadScalePic").val();
			if($("#id").val()==""){
				if(UploadScalePic==''){
					alert("请选择一张引导图！");
					return;
				}else{
					var ext=UploadScalePic.substring(UploadScalePic.lastIndexOf(".")).toLowerCase();
					if(ext!='.jpg'&&ext!='.jpeg'&&ext!='.png'){
						alert("图片格式只支持JPG、JPEG、PNG格式的图片!");
						return;
					}
				}
			}
		}
		var Upload=$("#Upload").val();
		if(Upload!=''){
			var ext=Upload.substring(Upload.lastIndexOf(".")).toLowerCase();
			if(ext!='.zip'&&ext!='.rar'&&ext!='.7z'){
				alert("附件格式只支持ZIP、RAR、7z格式!");
				return;
			}
		}
		
		$("#ec").submit();
	});
});