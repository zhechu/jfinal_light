package com.ugiant.modules.sys.web;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresUser;

import com.google.common.collect.Lists;
import com.jfinal.aop.Before;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;
import com.ugiant.common.utils.CodeUtils;
import com.ugiant.common.utils.CryptUtil;
import com.ugiant.common.utils.PageUtils;
import com.ugiant.common.utils.excel.ExportExcel;
import com.ugiant.common.utils.excel.ImportExcel;
import com.ugiant.common.web.BaseController;
import com.ugiant.modules.sys.model.User;
import com.ugiant.modules.sys.service.SystemService;
import com.ugiant.modules.sys.utils.UserUtils;
import com.ugiant.modules.sys.validator.SysUserImportValidator;
import com.ugiant.modules.sys.validator.SysUserInfoValidator;
import com.ugiant.modules.sys.validator.SysUserModifyPwdValidator;
import com.ugiant.modules.sys.validator.SysUserSaveValidator;

/**
 * 用户 控制器
 * @author lingyuwang
 *
 */
@RequiresUser
public class UserController extends BaseController {

	private SystemService systemService = SystemService.service;
	
	/**
	 * 用户管理页
	 * @return
	 */
	public void index() {
		this.render("userIndex.jsp");
	}
	
	/**
	 * 用户管理子页
	 * @return
	 */
	public void list() {
		Boolean repage = this.getParaToBoolean("repage");
		int pageNo = PageUtils.getPageNo(this.getRequest(), this.getResponse(), this.getParaToInt("pageNo"), repage);
		int pageSize = PageUtils.getPageSize(this.getParaToInt("pageSize"));
		
		User user = this.getBean(User.class);
		
		Page<User> page = systemService.findPageByUser(pageNo, pageSize, user);
		if (repage!=null && repage && page.getList().isEmpty() && pageNo>1) { // 若需恢复页码，则检查其恢复页是否有记录，若没有，则页码减1
			page = systemService.findPageByUser(pageNo-1, pageSize, user);
		}
		
		this.setAttr("companyName", this.getPara("companyName")); // 回显所属公司名称
		this.setAttr("officeName", this.getPara("officeName")); // 回显所属部门名称
		this.setAttr("user", user); // 回显查询信息
		this.setAttr("page", page);
		this.setAttrMessage();
		this.render("userList.jsp");
	}
	
	/**
	 * 用户添加页
	 * @return
	 */
	public void form() {
		User user = null;
		Long id = this.getParaToLong("id");
		if (id != null) { // 编辑
			user = systemService.getUserById(id);
		} else { // 添加
			user = new User();
		}
		this.setAttr("user", user);
		this.setAttr("allRoles", systemService.findAllRole());
		this.setAttrMessage();
		this.createToken("userFormToken"); // token
		this.render("userForm.jsp");
	}
	
	/**
	 * 用户个人信息页
	 * @return
	 */
	@Before({SysUserInfoValidator.class})
	public void info() {
		User currentUser = UserUtils.getUser();
		Boolean is_save = this.getParaToBoolean("is_save");
		if (is_save!=null && is_save) { // 提交表单时，再保存
			User user = this.getModel(User.class);
			user.setId(currentUser.getId());
			boolean flag = systemService.updateUser(user);
			if (flag) {
				currentUser = UserUtils.getUser(); // 刷新
				this.setAttr("message", "保存用户信息成功");
			} else {
				this.setAttr("message", "保存用户信息失败");
			}
		} else {
			this.setAttrMessage();
		}
		this.setAttr("user", currentUser);
		this.createToken("userInfoToken"); // token
		this.render("userInfo.jsp");
	}
	
	/**
	 * 添加或编辑用户信息
	 * @throws UnsupportedEncodingException 
	 */
	@Before({SysUserSaveValidator.class})
	public void save() throws UnsupportedEncodingException {
		String message = "";
		boolean repage = false; // 若是更新，则需恢复页码，否则，不需
		
		Long[] roleIdList = this.getParaValuesToLong("roleIdList");
		User user = this.getBean(User.class);
		user.setRoleIdList(Arrays.asList(roleIdList)); // 用户角色
		
		Long id = user.getId();
		String oldLoginName = this.getPara("oldLoginName");
		String newPassword = this.getPara("newPassword");
		if (id != null) { // 编辑
			repage = true;
			User sourceUser = systemService.getUserById(id);
			if (sourceUser != null) {
				if (!systemService.isNotExistLoginName(oldLoginName, user.getLoginName())){ // 用户登录名去重
					message = "保存用户 " + sourceUser.getLoginName() + " 失败，登录名已存在";
				} else {
					if (StrKit.notBlank(newPassword)) {
						newPassword = CryptUtil.getFromBase64(newPassword);
						newPassword = systemService.entryptPassword(newPassword);
						user.set("password", newPassword);
					}
					if (systemService.updateUser(user)) {
						message = "保存用户 " + user.getLoginName() + " 成功";
					} else {
						message = "保存用户失败，系统出现点问题，请稍后再试.";
					}
				}
			} else {
				message = "保存用户失败，参数有误";
			}
		} else { // 添加
			if (StrKit.notBlank(newPassword)) {
				newPassword = CryptUtil.getFromBase64(newPassword);
				newPassword = systemService.entryptPassword(newPassword);
				user.set("password", newPassword);
			}
			if (systemService.saveUser(user)) {
				message = "保存用户'" + user.getLoginName() + "成功";
			} else {
				message = "保存用户失败，系统出现点问题，请稍后再试.";
			}
		}
		this.redirect(adminPath+"/sys/user/list?repage="+repage+"&message="+URLEncoder.encode(message, PropKit.get("encoding")));
	}

	/**
	 * 判断用户登录名是否重复
	 */
	public void checkLoginName() {
		String oldLoginName = this.getPara("oldLoginName");
		String loginName = this.getPara("user.loginName");
		boolean flag = systemService.isNotExistLoginName(oldLoginName, loginName);
		this.renderJson(flag);
	}

	/**
	 * 修改密码页
	 * @return
	 */
	@Before({SysUserModifyPwdValidator.class})
	public void modifyPwd() {
		User currentUser = UserUtils.getUser();
		Boolean is_save = this.getParaToBoolean("is_save");
		if (is_save!=null && is_save) { // 提交表单时，再保存
			String oldPassword = this.getPara("oldPassword");
			oldPassword = CryptUtil.getFromBase64(oldPassword);
			String newPassword = this.getPara("newPassword");
			newPassword = CryptUtil.getFromBase64(newPassword);
			if (systemService.validatePassword(oldPassword, currentUser.getPassword())){
				boolean flag = systemService.updatePasswordById(currentUser.getId(), newPassword);
				if (flag) {
					this.setAttr("message", "修改密码成功");
				} else {
					this.setAttr("message", "修改密码失败，系统出现点问题，请稍后再试.");
				}
			}else{
				this.setAttr("message", "修改密码失败，旧密码错误");
			}
		} else {
			this.setAttrMessage();
		}
		this.createToken("userModifyPwdToken"); // token
		this.render("userModifyPwd.jsp");
	}
	
	/**
	 * 删除
	 * @throws UnsupportedEncodingException 
	 */
	public void delete() throws UnsupportedEncodingException {
		String message = "";
		Long id = this.getParaToLong("id");
		if (id != null) {
			User user = systemService.getUserById(id);
			if (user != null) {
				String loginName = user.getLoginName();
				if (systemService.deleteUser(user)) {
					message = "删除用户 "+loginName+" 成功";
				} else {
					message = "保存用户失败，系统出现点问题，请稍后再试.";
				}
			} else {
				message = "删除用户失败，参数有误";
			}
		} else {
			message = "删除用户失败，参数有误";
		}
		this.redirect(adminPath+"/sys/user/list?repage=true&message="+URLEncoder.encode(message, PropKit.get("encoding")));
	}
	
	/**
	 * 导出
	 * @throws IOException 
	 */
	public void export() throws IOException {
		User user = this.getBean(User.class);
		List<User> userList = systemService.findByUser(user);
		String fileName = "用户数据" + CodeUtils.generateCode("yyyyMMddHHmmss") + ".xlsx";
		new ExportExcel("用户数据", User.class).setDataList(userList).write(this.getResponse(), fileName).dispose();
		this.renderNull();
	}

	/**
	 * 下载导入用户数据模板
	 * @throws IOException 
	 */
	public void importTemplate() throws IOException {
		List<User> userList = Lists.newArrayList();
		userList.add(UserUtils.getUser());
		String fileName = "用户数据导入模板.xlsx";
		new ExportExcel("用户数据", User.class, 2).setDataList(userList).write(this.getResponse(), fileName).dispose();
		this.renderNull();
	}
	
	/**
	 * 导入
	 * @throws UnsupportedEncodingException 
	 */
	@Before(SysUserImportValidator.class)
	public void importExcel() throws UnsupportedEncodingException {
		String message;
		UploadFile uploadFile = this.getFile("file");
		File file = uploadFile.getFile();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<User> list = ei.getDataList(User.class);
			for (User user : list){
				try{
					if (systemService.isNotExistLoginName("", user.getLoginName())){
						user.setPassword(systemService.entryptPassword("123456"));
						validateUser(user); // 验证数据
						systemService.saveUser(user);
						successNum++;
					} else {
						throw new RuntimeException("登录名 "+user.getLoginName()+" 已存在");
					}
				} catch (RuntimeException ex){
					failureMsg.append("<br/>用户 "+user.getLoginName()+" 导入失败：");
					failureMsg.append(ex.getMessage()+"; ");
					failureNum++;
				} catch (Exception ex) {
					failureMsg.append("<br/>用户 "+user.getLoginName()+" 导入失败："+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条用户，导入信息如下：");
			}
			message = "已成功导入 "+successNum+" 条用户"+failureMsg;
		} catch (Exception e) {
			message = "导入用户失败！失败信息："+e.getMessage();
		}
		file.delete(); // 删除导入的文件
		this.redirect(adminPath+"/sys/user/list?message="+URLEncoder.encode(message, PropKit.get("encoding")));
	}

	/**
	 * 导入验证
	 * @param user
	 */
	private void validateUser(User user) {
		if (StrKit.isBlank(user.getLoginName())) {
			throw new RuntimeException("登录名不能为空");
		}
		if (user.getCompanyId() == null) {
			throw new RuntimeException("归属公司有误");
		}
		if (user.getOfficeId() == null) {
			throw new RuntimeException("归属部门有误");
		}
		if (StrKit.isBlank(user.getName())) {
			throw new RuntimeException("姓名不能为空");
		}
		if (StrKit.isBlank(user.getEmail())) {
			throw new RuntimeException("邮箱不能为空");
		}
		if (StrKit.isBlank(user.getMobile())) {
			throw new RuntimeException("手机不能为空");
		}
		if (user.getRoleList()==null || user.getRoleList().isEmpty()) {
			throw new RuntimeException("角色有误");
		}
	}
	
}
