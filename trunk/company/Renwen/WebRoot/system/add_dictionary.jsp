<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/Header.jsp"%>
<s:form action="dictionary_save" method="post" validate="true">
    <s:token />
    <s:hidden name="inputPage" value="/system/add_dictionary.jsp" />
    <s:hidden name="pid" value="3"/>
    <table border="0" cellpadding="1" cellspacing="1" width="100%" class="tableBgColor">
        <tr>
            <td align="right" width="15%" class="leftTdBgColor">
                &nbsp;模块或分类名称
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;<label>
                            <s:textfield name="dictionary.name" cssClass="input_1" cssStyle="width:60%" />
                            <font color="red">*</font>
                        </label>
            </td>
            </tr>
            <tr>
             <td align="right" width="15%" class="leftTdBgColor">
               &nbsp;详细描述
            </td>
            <td align="left" width="35%" class="rightTdBgColor">
                &nbsp;<label>
                            <s:textarea name="dictionary.remark" />
                        </label>
            </td>
        </tr>
    </table>
    <br>
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
            <td align="center" width="30%">
                <input type="submit" value="<fmt:message key="global.save" />" class="buttonStyle">
                <input type="reset" value="<fmt:message key="global.reset" />" class="buttonStyle">
                <input id="back" type="button" value="<fmt:message key="global.back" />" class="buttonStyle">
            </td>
        </tr>
    </table>
</s:form>
<%@ include file="/common/footer.jsp"%>
