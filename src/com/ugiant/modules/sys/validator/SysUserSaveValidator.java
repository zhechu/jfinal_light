package com.ugiant.modules.sys.validator;

import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.ugiant.common.validator.BaseValidator;
/**
 * 保存用户信息  验证器
 * @author lingyuwang
 *
 */
public class SysUserSaveValidator extends BaseValidator {

	@Override
	protected void validate(Controller c) {
		this.validateToken("userFormToken", "message", "上次已保存，请不要重复提交");
		this.validateRequired("user.companyId", "message", "请选择归属公司");
		this.validateRequired("user.officeId", "message", "请选择归属部门");
		this.validateRequiredString("user.no", "message", "请填写工号");
		this.validateString("user.no", 1, 50, "message", "工号不能超过50个字符");
		this.validateRequiredString("user.name", "message", "请填写姓名");
		this.validateString("user.name", 1, 50, "message", "姓名不能超过50个字符");
		this.validateRequiredString("user.loginName", "message", "请填写登录名");
		this.validateString("user.loginName", 1, 50, "message", "登录名不能超过50个字符");
		// 判断是否为更新
		Integer id = c.getParaToInt("user.id");
		if (id != null) { // 编辑
			// 若密码不为空，则验证
			String newPassword = c.getPara("newPassword");
			if (StrKit.notBlank(newPassword)) {
				this.validateString("newPassword", 3, 50, "message", "密码长度最少是 3 最大是 50 的字符串");
				this.validateEqualField("confirmNewPassword", "newPassword", "message", "输入确认密码有误");
			}
		} else { // 添加
			this.validateRequiredString("newPassword", "message", "请填写密码");
			this.validateString("newPassword", 3, 50, "message", "密码长度最少是 3 最大是 50 的字符串");
			this.validateEqualField("confirmNewPassword", "newPassword", "message", "输入确认密码有误");
		}
		this.validateRequiredString("user.email", "message", "请填写邮箱");
		this.validateString("user.email", 0, 50, "message", "邮箱不能超过50个字符");
		this.validateEmail("user.email", "message", "请输入正确格式的电子邮件");
		String phone = c.getPara("user.phone");
		if (StrKit.notBlank(phone)) {
			this.validateString("user.phone", 0, 50, "message", "电话不能超过50个字符");
			this.validateTelephone("user.phone", "message", "请输入正确格式的电话");
		}
		this.validateRequiredString("user.mobile", "message", "请填写手机");
		this.validateString("user.mobile", 0, 50, "message", "手机不能超过50个字符");
		this.validateMobilephone("user.mobile", "message", "请输入正确格式的手机");
		this.validateRequiredString("user.loginFlag", "message", "请选择是否允许登录");
		this.validateRequired("roleIdList", "message", "请选择用户角色");
		String remarks = c.getPara("user.remarks");
		if (StrKit.notBlank(remarks)) {
			this.validateString("user.remarks", 0, 200, "message", "备注不能超过200个字符");
		}
	}

	@Override
	protected void handleError(Controller c) {
		c.setAttr("type", "error");
		c.forwardAction(adminPath+"/sys/user/form");
	}

}
