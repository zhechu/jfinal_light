package com.ugiant.modules.sys.web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;

import com.jfinal.aop.Before;
import com.jfinal.kit.PropKit;
import com.ugiant.common.utils.CookieUtils;
import com.ugiant.common.utils.CryptUtil;
import com.ugiant.common.web.BaseController;
import com.ugiant.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.ugiant.modules.sys.utils.UserUtils;
import com.ugiant.modules.sys.validator.SysUserLoginValidator;

/**
 * 系统  控制器
 * @author lingyuwang
 *
 */
public class SystemController extends BaseController {

	/**
	 * 进入系统首页
	 */
	@RequiresUser
	public void index() {
		// 默认页签模式
		String tabmode = CookieUtils.getCookie(this.getRequest(), "tabmode");
		if (tabmode == null){
			tabmode = "1";
			CookieUtils.setCookie(this.getResponse(), "tabmode", tabmode);
		}
		this.setAttr("tabmode", tabmode);
		this.render("sysIndex.jsp");
	}

	/**
	 * 进入登录页
	 * @return
	 */
	public void login() {
		Principal principal = UserUtils.getPrincipal();
		
		// 如果已经登录，则跳转到管理首页
		if(principal != null){
			this.redirect(adminIndexPath);
			return;
		}
		this.setAttrMessage(); // 回显提示信息
		this.createToken("loginToken"); // token
		this.render("sysLogin.jsp");
	}
	
	/**
	 * 登录验证
	 * @throws UnsupportedEncodingException 
	 */
	@Before(SysUserLoginValidator.class)
	public void doLogin() throws UnsupportedEncodingException {
		Principal principal = UserUtils.getPrincipal();
		
		// 如果已经登录，则跳转到管理首页
		if(principal != null){
			this.redirect(adminIndexPath);
			return;
		}
		
		try {
			String username = this.getPara("username");
			String password = this.getPara("password");
			password = CryptUtil.getFromBase64(password);
			Boolean rememberMe = this.getParaToBoolean("rememberMe");
			if (rememberMe == null) {
				rememberMe = false;
			}
			UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
			UserUtils.getSubject().login(token);
		} catch (Exception e) {
			String message = "";
			String className = e.getClass().getName();
			if (IncorrectCredentialsException.class.getName().equals(className)
					|| UnknownAccountException.class.getName().equals(className)){
				message = "用户或密码错误, 请重试.";
			} else if (e.getMessage() != null && StringUtils.startsWith(e.getMessage(), "msg:")){
				message = StringUtils.replace(e.getMessage(), "msg:", "");
			} else {
				message = "系统出现点问题，请稍后再试.";
				e.printStackTrace(); // 输出到控制台
			}
			message = URLEncoder.encode(message, PropKit.get("encoding"));
			this.redirect(adminPath+"/login?message="+message);
			return;
		}
		this.redirect(adminIndexPath);
	}
	
	/**
	 * 登出
	 */
	public void logout() {
		Subject subject = UserUtils.getSubject();
		if (subject != null) {
			//if (subject.isAuthenticated()) {
				subject.logout();
			//}
		}
		this.redirect(adminPath+"/login");
	}
}
