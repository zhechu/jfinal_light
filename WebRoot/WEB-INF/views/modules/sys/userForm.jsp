<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#no").focus();
			$("#inputForm").validate({
				rules: {
					"company_id" : {required:true},
					"office_id" : {required:true},
					"no" : {required:true, maxlength:50},
					"name" : {required:true, maxlength:50},
					"loginName" : {required:true, maxlength:50, remote: "${ctx}/sys/user/checkLoginName?oldLoginName=" + encodeURIComponent('${user.login_name}')},
					"newPassword" : {minlength:3, maxlength:50},
					"confirmNewPassword" : {equalTo:"#newPassword"},
					"email" : {required:true, email:true, maxlength:50},
					"phone" : {maxlength:50, telephone:true},
					"mobile" : {required:true, maxlength:50, mobilephone:true},
					"loginFlag" : {required:true},
					"roleIdList" : {required:true},
					"remarks" : {maxlength:200}
				},
				messages: {
					"loginName": {remote: "用户登录名已存在"},
					"confirmNewPassword": {equalTo: "输入与上面相同的密码"}
				},
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					if ($("#newPassword").val()) { // 若密码不为空才加密
						$("#newPassword").val(base64encode($("#newPassword").val()));
					}
					if ($("#confirmNewPassword").val()) {
						$("#confirmNewPassword").val(base64encode($("#confirmNewPassword").val()));
					}
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/user/list">用户列表</a></li>
		<li class="active"><a href="${ctx}/sys/user/form?id=${user.id}">用户<shiro:hasPermission name="sys:user:edit">${not empty user.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:user:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form id="inputForm" action="${ctx}/sys/user/save" method="post" class="form-horizontal">
		<sys:message content="${message}"/>
		<input type="hidden" name="id" value="${user.id }">
		<input type="hidden" name="userFormToken" value="${userFormToken}" />
		<div class="control-group">
			<label class="control-label">头像:</label>
			<div class="controls">
				<input type="hidden" id="nameImage" name="photo" maxlength="255" class="input-xlarge"/>
				<sys:ckfinder input="nameImage" type="images" uploadPath="/photo" selectMultiple="false" maxWidth="100" maxHeight="100"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">归属公司:</label>
			<div class="controls">
                <sys:treeselect id="company" name="company_id" value="${user.company_id}" labelName="company_name" labelValue="${user.company_name}"
					title="公司" url="/sys/office/treeData?type=1"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">归属部门:</label>
			<div class="controls">
                <sys:treeselect id="office" name="office_id" value="${user.office_id}" labelName="office_name" labelValue="${user.office_name}"
					title="部门" url="/sys/office/treeData?type=2" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">工号:</label>
			<div class="controls">
				<input type="text" name="no" value="${user.no}"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">姓名:</label>
			<div class="controls">
				<input type="text" name="name" value="${user.name}"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">登录名:</label>
			<div class="controls">
				<input id="oldLoginName" name="oldLoginName" type="hidden" value="${user.login_name}">
				<input type="text" name="loginName" class="userName" value="${user.login_name}"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">密码:</label>
			<div class="controls">
				<input id="newPassword" name="newPassword" type="password" value="" class="${empty user.id?'required':''}"/>
				<c:if test="${empty user.id}"><span class="help-inline"><font color="red">*</font> </span></c:if>
				<c:if test="${not empty user.id}"><span class="help-inline">若不修改密码，请留空。</span></c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">确认密码:</label>
			<div class="controls">
				<input id="confirmNewPassword" name="confirmNewPassword" type="password" value=""/>
				<c:if test="${empty user.id}"><span class="help-inline"><font color="red">*</font> </span></c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮箱:</label>
			<div class="controls">
				<input type="email" name="email" value="${user.email}"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电话:</label>
			<div class="controls">
				<input type="text" name="phone" value="${user.phone}"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机:</label>
			<div class="controls">
				<input type="text" name="mobile" value="${user.mobile}"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">允许登录:</label>
			<div class="controls">
				<sys:select name="loginFlag" items="${fns:getDictList('yes_no')}" cssClass="input-medium" defaultSelected="${user.login_flag }" itemLabel="label" itemValue="value"/>
				<span class="help-inline"><font color="red">*</font> “是”代表此账号允许登录，“否”则表示此账号不允许登录</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">用户角色:</label>
			<div class="controls">
				<sys:checkboxes name="roleIdList" items="${allRoles}" itemLabel="name" itemValue="id" defaultChecked="${user.role_list }"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				<textarea name="remarks" rows="3" maxlength="200" class="input-xlarge"></textarea>
			</div>
		</div>
		<c:if test="${not empty user.id}">
			<div class="control-group">
				<label class="control-label">创建时间:</label>
				<div class="controls">
					<label class="lbl"><fmt:formatDate value="${user.create_date}" type="both" dateStyle="full"/></label>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">最后登陆:</label>
				<div class="controls">
					<label class="lbl">IP: ${user.login_ip}&nbsp;&nbsp;&nbsp;&nbsp;时间：<fmt:formatDate value="${user.login_date}" type="both" dateStyle="full"/></label>
				</div>
			</div>
		</c:if>
		<div class="form-actions">
			<shiro:hasPermission name="sys:user:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form>
</body>
</html>