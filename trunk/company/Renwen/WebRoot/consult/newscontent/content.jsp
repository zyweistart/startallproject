<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld"%>




<!--以下为   显示内容部分 -->
<div id="content_news">
<div class="title1">${integration.name }</div>
<div class="date">来源：${integration.newsfrom }
点击率：${integration.hits} 更新时间：${integration.startDay }</div>

<div class="main">${integration.content }</div>
</div>
<div class="page"><a href="#" onclick="history.go(-1);">[返回列表]</a>&nbsp;&nbsp;&nbsp;<a
	href="#" onclick="window.close();">[关闭]</a></div>
<!--结束 -->	
