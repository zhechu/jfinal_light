<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="name" type="java.lang.String" required="true" description="隐藏域名称（ID）"%>
<%@ attribute name="defaultSelected" type="java.lang.String" description="默认选中"%>
<%@ attribute name="items" type="java.util.List" description="选项"%>
<%@ attribute name="cssClass" type="java.lang.String" description="样式"%>
<%@ attribute name="itemLabel" type="java.lang.String" description="item的text"%>
<%@ attribute name="itemValue" type="java.lang.String" description="item的value"%>
<select name="${name}" class="${cssClass }">
	<option value="">=== 请选择 ===</option>
	<c:forEach items="${items}" var="dict">
		<c:choose>
			<c:when test="${defaultSelected eq dict[itemValue] }">
				<option value="${dict[itemValue] }" selected="selected">${dict[itemLabel] }</option>
			</c:when>   
			<c:otherwise>
				<option value="${dict[itemValue] }">${dict[itemLabel] }</option>
			</c:otherwise>  
		</c:choose>
	</c:forEach>
</select>