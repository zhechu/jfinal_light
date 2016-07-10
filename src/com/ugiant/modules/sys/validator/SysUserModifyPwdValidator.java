package com.ugiant.modules.sys.validator;

import com.jfinal.core.Controller;
import com.ugiant.common.validator.BaseValidator;
/**
 * 用户修改密码  验证器
 * @author lingyuwang
 *
 */
public class SysUserModifyPwdValidator extends BaseValidator {

	@Override
	protected void validate(Controller c) {
		Boolean is_save = c.getParaToBoolean("is_save");
		if (is_save!=null && is_save) { // 提交表单时，再验证
			this.validateToken("userModifyPwdToken", "message", "上次已保存，请不要重复提交");
			this.validateString("oldPassword", 3, 50, "message", "旧密码长度最少是 3 最大是 50 的字符串");
			this.validateString("newPassword", 3, 50, "message", "新密码长度最少是 3 最大是 50 的字符串");
			this.validateEqualField("confirmNewPassword", "newPassword", "message", "输入确认新密码有误");
		}
	}

	@Override
	protected void handleError(Controller c) {
		c.redirect(this.getRedirectUrl(c));
	}

}
