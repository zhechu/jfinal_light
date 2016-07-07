package com.ugiant.modules.sys.validator;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
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
			this.validateRequiredString("name", "message", "请填写姓名");
			this.validateString("name", 1, 50, "message", "姓名不能超过50个字符");
			String email = c.getPara("email");
			if (StrKit.notBlank(email)) {
				this.validateString("email", 0, 50, "message", "邮箱不能超过50个字符");
				this.validateEmail("email", "message", "请输入正确格式的电子邮件");
			}
			String phone = c.getPara("phone");
			if (StrKit.notBlank(phone)) {
				this.validateString("phone", 0, 50, "message", "电话不能超过50个字符");
				this.validateTelephone("phone", "message", "请输入正确格式的电话");
			}
			String mobile = c.getPara("mobile");
			if (StrKit.notBlank(mobile)) {
				this.validateString("mobile", 0, 50, "message", "手机不能超过50个字符");
				this.validateMobilephone("mobile", "message", "请输入正确格式的手机");
			}
			String remarks = c.getPara("remarks");
			if (StrKit.notBlank(remarks)) {
				this.validateString("remarks", 0, 200, "message", "备注不能超过200个字符");
			}
		}
	}

	@Override
	protected void handleError(Controller c) {
		String message = c.getAttr("message");
		try {
			message = URLEncoder.encode(message, PropKit.get("encoding"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		c.redirect(adminPath+"/sys/user/info?message="+message);
	}

}
