package com.ugiant.modules.sys.validator;

import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.ugiant.common.validator.BaseValidator;
/**
 * 用户个人信息  验证器
 * @author lingyuwang
 *
 */
public class SysUserInfoValidator extends BaseValidator {

	@Override
	protected void validate(Controller c) {
		Boolean is_save = c.getParaToBoolean("is_save");
		if (is_save!=null && is_save) { // 提交表单时，再验证
			this.validateToken("userInfoToken", "message", "上次已保存，请不要重复提交");
			this.validateRequiredString("user.name", "message", "请填写姓名");
			this.validateString("user.name", 1, 50, "message", "姓名不能超过50个字符");
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
			String remarks = c.getPara("user.remarks");
			if (StrKit.notBlank(remarks)) {
				this.validateString("user.remarks", 0, 200, "message", "备注不能超过200个字符");
			}
		}
	}

	@Override
	protected void handleError(Controller c) {
		c.redirect(this.getRedirectUrl(c));
	}

}
