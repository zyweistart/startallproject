<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld"%>


<!-- 以下为  播放视频部分 -->
 <embed src="${pageContext.request.contextPath}${integration.filePath}"
              quality="high" align="middle" autosize="true" 
              allowScriptAccess="sameDomain" type="application/x-oleobject" height="500px" width="700px" />
<!-- 结束 -->                  

