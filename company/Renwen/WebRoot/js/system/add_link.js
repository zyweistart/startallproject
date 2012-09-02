$(document).ready(function(){
    function checkform(){
        var name = $("#name").val()
        var link = $("#link").val()
        
        if(name ==""){
            alert("请输入名称")
            return false;
        }
        if(link==""){
            alert("请输入链接地址")
            return false;
        }
        return true;
    }
    
    $("#isok").click(function(){
        if(checkform()){
            ec.submit();
        }
    })
})