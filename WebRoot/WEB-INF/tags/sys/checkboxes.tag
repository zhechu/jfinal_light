<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="name" type="java.lang.String" required="true" description="隐藏域名称（ID）"%>
<%@ attribute name="defaultChecked" type="java.util.List" description="默认选中"%>
<%@ attribute name="items" type="java.util.List" description="选项"%>
<%@ attribute name="itemLabel" type="java.lang.String" description="item的text"%>
<%@ attribute name="itemValue" type="java.lang.String" description="item的value"%>
<c:forEach items="${items}" var="item" varStatus="status">
	<span>
		<c:set var="flag" value="false"/>
		<c:forEach items="${defaultChecked}" var="defaultItem">
			<c:if test="${defaultItem[itemValue] eq item[itemValue] }">
				<c:set var="flag" value="true"/>
			</c:if>
		</c:forEach>
		<input type="checkbox" id="${name }${status.index }" name="${name }" value="${item[itemValue] }" ${flag ? 'checked' : '' }>
		<label for="${name }${status.index }">${item[itemLabel] }</label>
	</span>
</c:forEach>