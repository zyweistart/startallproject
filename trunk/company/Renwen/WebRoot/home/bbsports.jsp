<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/home/include/head.jsp"%>
 <script src="${pageContext.request.contextPath}/js/index/bbspost.js"  type=text/javascript></script>	
<!--背景图片透明方法-->
<script src="${pageContext.request.contextPath}/index/js/iepng.js" type="text/javascript"></script>
<!--插入图片透明方法-->
<script type="text/javascript">
	EvPNG.fix('div, ul, img, li, input'); //EvPNG.fix('包含透明PNG图片的标签'); 多个标签之间用英文逗号隔开。
	//-->
</script>
<tr>
	<td height="200" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td valign="top">
					<table width="98%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="41" background="${pageContext.request.contextPath}/home/images/t_bg_1.jpg">
								<table width="100%" height="41" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td width="600" class="title_1">
											人文学院<font class="title_menu">发表帖子</font>
										</td>
										<td align="center">&nbsp;</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td align="center">
							
									<s:form method="post" action="fbpots_viewSave.htm" id="ec">
          <s:token />
			<!-- 主内容区 -->
			<table width="95%" border="0" cellpadding="1" cellspacing="1" class="tableBgColor">
			
				<tr align="center">
					<td align="left" valign="top" class="leftTdBgColor">
					 标题:<font color="red" size="2">*</font>
					</td>
					<td align="left" class="rightTdBgColor">
					  <s:textfield   id="fptitle"  name="fbpots.fpTitle"   cssStyle="width:150px" ></s:textfield><Br>
                      <label id="fptitlexx"></label>
                      
					</td>
				</tr>
				<tr align="center" >
				   <td align="left" valign="top" class="leftTdBgColor">
				    类别选项:
				   </td> 
				   <td align="left" class="rightTdBgColor">
				    <s:select name="fbpots.forumId"   list="%{forums}" listKey="id"  listValue="forumName"  ></s:select><br>
				   </td>
				</tr>
				<tr align="center">
				  <td align="left" class="rightTdBgColor" colspan="2">
				  	<c:set var="editorName" scope="request" value="fbpots.fpContent" />
				  	<c:set var="editorHeight" scope="request" value="400px" />
                      <%@include file="../common/qtkindEditor.jsp" %>
				  </td>
				</tr>
				<tr align="center">
				   <td align="center" class="rightTdBgColor" colspan="2">
				    <input type="button" id="isok" value="提交" class="buttonStyle">
				   </td>
				</tr>
			</table>
		</s:form>
							
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</td>
</tr>
<%@include file="/home/include/foot.jsp"%>