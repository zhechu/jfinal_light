package com.ugiant.common.validator;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.validate.Validator;

/**
 * 验证器 基类
 * @author lingyuwang
 *
 */
public abstract class BaseValidator extends Validator {
	
	protected static final String mobilephonePattern = "^(((13[0-9]{1})|(15[0-9]{1}))+\\d{8})$"; // 手机
	
	protected static final String telephonePattern = "^(0[0-9]{2,3}\\-)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?$"; // 电话
	
	public BaseValidator() {
		super();
		this.setShortCircuit(true); // 短路
	}

	/**
	 * 管理基础路径
	 */
	protected String adminPath = PropKit.get("adminPath");

	/**
	 * 验证手机号
	 * @param field
	 * @param errorKey
	 * @param errorMessage
	 */
	protected void validateMobilephone(String field, String errorKey, String errorMessage) {
		validateRegex(field, mobilephonePattern, false, errorKey, errorMessage);
	}
	
	/**
	 * 验证电话号码
	 * @param field
	 * @param errorKey
	 * @param errorMessage
	 */
	protected void validateTelephone(String field, String errorKey, String errorMessage){
		validateRegex(field, telephonePattern, false, errorKey, errorMessage);
	}
	
	/**
	 * 验证字符串
	 * @param value 值
	 * @param regExpression 正则表达式
	 * @param isCaseSensitive 是否大小写敏感
	 * @param errorKey 错误信息键
	 * @param errorMessage 错误信息值
	 */
	protected void validateRegexString(String value, String regExpression, boolean isCaseSensitive, String errorKey, String errorMessage) {
		if (value == null) {
        	addError(errorKey, errorMessage);
        	return ;
        }
        Pattern pattern = isCaseSensitive ? Pattern.compile(regExpression) : Pattern.compile(regExpression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(value);
        if (!matcher.matches())
        	addError(errorKey, errorMessage);
	}
	
	/**
	 * 返回带提示信息参数的需重定向的 url
	 * @param c
	 * @return
	 */
	protected String getRedirectUrl(Controller c) {
		String message = c.getAttr("message");
		try {
			message = URLEncoder.encode(message, PropKit.get("encoding"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String actionKey = c.getAttr("actionKey");
		return actionKey + "?message="+message+"&type=error";
	}
	
}
