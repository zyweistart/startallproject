/**
 * 表单验证jQuery版
 * @author Start
 * @Date 2011-01-25
 */
function Verifies(){
	
	var verifiesvar=this;
	
	function isEmail( text ){
		var pattern = "^[\\w-_\.]*[\\w-_\.]\@[\\w]\.+[\\w]+[\\w]$";
		var regex = new RegExp( pattern );
		return regex.test( text );
	}
	function ver(index,item){
		/*是否验证*/
		var tar=$(item);
		if(tar.attr("verifies")){
			tar.removeClass(verifiesvar.VerifiesConfig.VerifiesClass);
			var value=$.trim(tar.val().toString());
			/*验证类型*/
			var valiArr=tar.attr('validation');
			var errormsg=tar.attr('errormsg');
			if(valiArr){
				var arr=valiArr.toString().split(",");
				for(i=0;i<arr.length;i++){
					vali=arr[i];
					if(vali=="empty"){
						if(value==""){
							if(errormsg){
								alert(errormsg);
							}else{
								alert("该字段不能为空！");
							}
							tar.addClass(verifiesvar.VerifiesConfig.VerifiesClass);
							return false;
						}
					}else if(vali=="number"){
						if(isNaN(value)){
							if(errormsg){
								alert(errormsg);
							}else{
								alert("该字段只能为数字");
							}
							tar.addClass(verifiesvar.VerifiesConfig.VerifiesClass);
							return false;
						}
					}else if(vali=="email"){
						if(value!=""&&!isEmail(value)){
							if(errormsg){
								alert(errormsg);
							}else{
								alert("请输入正确的邮箱格式！");
							}
							tar.addClass(verifiesvar.VerifiesConfig.VerifiesClass);
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	return{
		validation:function(){
			var returnVal;
			$.each($(":input"),function(index,item){
				returnVal=ver(index,item);
				return returnVal;
			});
			if(returnVal){
				return true;
			}else{
				return false;
			}
		}
	};
};
Verifies.prototype.VerifiesConfig={
	VerifiesClass:'verifiesInput'
};
$(function(){
	$("#formSubmit").click(function(){
		var ver=new Verifies();
		if(ver.validation()){
			adminForm.submit();
		}
	});
});