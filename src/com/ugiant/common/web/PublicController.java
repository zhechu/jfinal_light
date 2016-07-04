package com.ugiant.common.web;


import org.apache.commons.lang3.StringUtils;

import com.jfinal.render.CaptchaRender;
import com.ugiant.common.utils.CookieUtils;

/**
 * 公共 控制器
 * @author lingyuwang
 *
 */
public class PublicController extends BaseController {

	/**
	 * 切换主题
	 */
	public void theme(){
		String theme = this.getPara("theme");
		if (StringUtils.isNotBlank(theme)){
			CookieUtils.setCookie(this.getResponse(), "theme", theme);
		}else{
			theme = CookieUtils.getCookie(this.getRequest(), "theme");
		}
		this.redirect(this.getPara("url"));
	}

	/**
	 * 生成验证码
	 */
	public void captchaImg() {
		CaptchaRender img = new CaptchaRender();  
        this.render(img);  
	}
	
}
