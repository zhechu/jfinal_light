package com.ugiant.modules.sys.validator;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.render.CaptchaRender;
import com.ugiant.common.validator.BaseValidator;
/**
 * 管理用户登录 验证器
 * @author lingyuwang
 *
 */
public class SysUserLoginValidator extends BaseValidator {

	@Override
	protected void validate(Controller c) {
		String validateCode = c.getPara("validateCode");
		if (StrKit.notBlank(validateCode)) {
			if (!CaptchaRender.validate(c, validateCode)) {
				this.addError("message", "验证码不正确.");
			}
		} else {
			this.addError("message", "请填写验证码.");
		}
		this.validateRequired("password", "message", "请填写密码.");
		this.validateRequired("username", "message", "请填写用户名.");
	}

	@Override
	protected void handleError(Controller c) {
		String message = c.getAttr("message");
		try {
			message = URLEncoder.encode(message, PropKit.get("encoding"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		c.redirect(adminPath+"/login?message="+message);
	}

}
