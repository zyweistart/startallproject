function doPrint() { 

/// 隐藏不需要打印的内容 
        try 
    { 
        PageSetup_Null(); 
    } 
    catch(e) 
    { 
       // var errorMsg = e.message+"\r"+"请设置:IE选项->安全->Internet->"+"ActiveX控件和插件"+"\r"+"对未标记为可安全执行脚本的ActiveX的控件初始化并执行脚本->允许/提示"; 
       // alert(errorMsg); 
       // return; 
    } 
    window.print(); 
} 


    
var HKEY_Root,HKEY_Path,HKEY_Key; 
HKEY_Root="HKEY_CURRENT_USER"; 
HKEY_Path="\\Software\\Microsoft\\Internet Explorer\\PageSetup\\"; 
//设置网页打印的页眉页脚为空 
function PageSetup_Null() 
{ 
      var Wsh=new ActiveXObject("WScript.Shell"); 
      HKEY_Key="header"; 
      Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,""); 
      HKEY_Key="footer"; 
      Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,""); 
      HKEY_Key="margin_left" 
      Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"0"); //键值设定--左边边界 

      HKEY_Key="margin_top" 
      Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"0"); //键值设定--上边边界 

      HKEY_Key="margin_right" 
      Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"0"); //键值设定--右边边界 

      HKEY_Key="margin_bottom" 
      Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"0"); //键值设定--下边边界 
} 
