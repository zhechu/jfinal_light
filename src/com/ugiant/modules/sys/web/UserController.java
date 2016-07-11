package com.ugiant.modules.sys.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresUser;

import com.jfinal.aop.Before;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import com.ugiant.common.model.TempFileRender;
import com.ugiant.common.utils.CodeUtils;
import com.ugiant.common.utils.CryptUtil;
import com.ugiant.common.utils.ExcelUtils;
import com.ugiant.common.utils.FileUtils;
import com.ugiant.common.utils.PageUtils;
import com.ugiant.common.web.BaseController;
import com.ugiant.modules.sys.service.SystemService;
import com.ugiant.modules.sys.utils.UserUtils;
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
		systemService.updatePasswordById(1L, "654321");
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
		
		// 封装用户
		Record user = encapsulateUser();
		
		Page<Record> page = systemService.findPageByUser(pageNo, pageSize, user);
		if (repage!=null && repage && page.getList().isEmpty() && pageNo>1) { // 若需恢复页码，则检查其恢复页是否有记录，若没有，则页码减1
			page = systemService.findPageByUser(pageNo-1, pageSize, user);
		}
		
		this.setAttr("user", user);
		this.setAttr("page", page);
		this.setAttrMessage();
		this.render("userList.jsp");
	}
	
	/**
	 * 用户添加页
	 * @return
	 */
	public void form() {
		Record user = null;
		Long id = this.getParaToLong("id");
		if (id != null) { // 编辑
			user = systemService.getUserById(id);
		} else { // 添加
			user = new Record();
			user.set("company_id", UserUtils.getUser().getLong("company_id"));
			user.set("company_name", UserUtils.getUser().getStr("company_name"));
			user.set("office_id", UserUtils.getUser().getLong("office_id"));
			user.set("office_name", UserUtils.getUser().getStr("office_name"));
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
		Record currentUser = UserUtils.getUser();
		Boolean is_save = this.getParaToBoolean("is_save");
		if (is_save!=null && is_save) { // 提交表单时，再保存
			Record user = new Record();
			user.set("id", currentUser.getLong("id"));
			user.set("email", this.getPara("email"));
			user.set("phone", this.getPara("phone"));
			user.set("mobile", this.getPara("mobile"));
			user.set("remarks", this.getPara("remarks"));
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
	 * 添加或编译用户信息
	 * @throws UnsupportedEncodingException 
	 */
	@Before({SysUserSaveValidator.class})
	public void save() throws UnsupportedEncodingException {
		String message = "";
		boolean repage = false; // 若是更新，则需恢复页码，否则，不需
		Long id = this.getParaToLong("id");
		Long company_id = this.getParaToLong("company_id");
		Long office_id = this.getParaToLong("office_id");
		String no = this.getPara("no");
		String name = this.getPara("name");
		String oldLoginName = this.getPara("oldLoginName");
		String loginName = this.getPara("loginName");
		String newPassword = this.getPara("newPassword");
		String email = this.getPara("email");
		String phone = this.getPara("phone");
		String mobile = this.getPara("mobile");
		String loginFlag = this.getPara("loginFlag");
		Long[] roleIdList = this.getParaValuesToLong("roleIdList");
		String remarks = this.getPara("remarks");
		if (id != null) { // 编辑
			repage = true;
			Record sourceUser = systemService.getUserById(id);
			if (sourceUser != null) {
				if (!systemService.isNotExistLoginName(oldLoginName, loginName)){ // 用户登录名去重
					message = "保存用户 " + sourceUser.getStr("login_name") + " 失败，登录名已存在";
				} else {
					Record user = new Record();
					user.set("id", id);
					// 封装用户
					encapsulateUser(company_id, office_id, no, name, loginName, newPassword, email, phone, mobile,
							loginFlag, roleIdList, remarks, user);
					if (systemService.updateUser(user)) {
						message = "保存用户 " + loginName + " 成功";
					} else {
						message = "保存用户失败，系统出现点问题，请稍后再试.";
					}
				}
			} else {
				message = "保存用户失败，参数有误";
			}
		} else { // 添加
			Record user = new Record();
			// 封装用户
			encapsulateUser(company_id, office_id, no, name, loginName, newPassword, email, phone, mobile,
					loginFlag, roleIdList, remarks, user);
			if (systemService.saveUser(user)) {
				message = "保存用户'" + loginName + "成功";
			} else {
				message = "保存用户失败，系统出现点问题，请稍后再试.";
			}
		}
		this.redirect(adminPath+"/sys/user/list?repage="+repage+"&message="+URLEncoder.encode(message, PropKit.get("encoding")));
	}

	/**
	 * 封装用户
	 * @param company_id
	 * @param office_id
	 * @param no
	 * @param name
	 * @param loginName
	 * @param newPassword
	 * @param email
	 * @param phone
	 * @param mobile
	 * @param loginFlag
	 * @param roleIdList
	 * @param remarks
	 * @param user
	 */
	private void encapsulateUser(Long company_id, Long office_id, String no, String name, String loginName,
			String newPassword, String email, String phone, String mobile, String loginFlag, Long[] roleIdList,
			String remarks, Record user) {
		user.set("company_id", company_id);
		user.set("office_id", office_id);
		user.set("no", no);
		user.set("name", name);
		user.set("login_name", loginName);
		if (StrKit.notBlank(newPassword)) {
			newPassword = CryptUtil.getFromBase64(newPassword);
			newPassword = systemService.entryptPassword(newPassword);
			user.set("password", newPassword);
		}
		user.set("email", email);
		user.set("phone", phone);
		user.set("mobile", mobile);
		user.set("login_flag", loginFlag);
		user.set("remarks", remarks);
		user.set("role_id_list", roleIdList);
	}
	
	/**
	 * 判断用户登录名是否重复
	 */
	public void checkLoginName() {
		String oldLoginName = this.getPara("oldLoginName");
		String loginName = this.getPara("loginName");
		boolean flag = systemService.isNotExistLoginName(oldLoginName, loginName);
		this.renderJson(flag);
	}

	/**
	 * 修改密码页
	 * @return
	 */
	@Before({SysUserModifyPwdValidator.class})
	public void modifyPwd() {
		Record currentUser = UserUtils.getUser();
		Boolean is_save = this.getParaToBoolean("is_save");
		if (is_save!=null && is_save) { // 提交表单时，再保存
			String oldPassword = this.getPara("oldPassword");
			oldPassword = CryptUtil.getFromBase64(oldPassword);
			String newPassword = this.getPara("newPassword");
			newPassword = CryptUtil.getFromBase64(newPassword);
			if (systemService.validatePassword(oldPassword, currentUser.getStr("password"))){
				boolean flag = systemService.updatePasswordById(currentUser.getLong("id"), newPassword);
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
			Record user = systemService.getUserById(id);
			if (user != null) {
				String loginName = user.getStr("login_name");
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
	 */
	public void export() {
		Record user = encapsulateUser();
		List<Record> userList = systemService.findByUser(user);
		File excelFile = getExcelFile(userList);
		this.render(new TempFileRender(excelFile));
	}

	/**
	 * 获取写入导出数据的文件
	 * @param list
	 * @return
	 */
	private File getExcelFile(List<Record> list) {
		String templateName = PathKit.getRootClassPath() + PropKit.get("excel_template") + File.separator + "sys_user_export.xlsx";
		XSSFWorkbook wb = null;
		InputStream is = null;
		OutputStream os = null;
		String excelFileName = "用户数据" + CodeUtils.generateCode("yyyyMMddHHmmss") + ".xlsx";
		File excelFile = new File(PathKit.getWebRootPath()+ File.separator + "temp"+ File.separator + excelFileName);
		FileUtils.safeMkdir(excelFile); // 检查文件夹是否存在，不存在则先创建
		try {
			is = new FileInputStream(templateName);
			wb = new XSSFWorkbook(is);
			Map<String, CellStyle> styles = ExcelUtils.createStyles(wb);
			XSSFSheet sheet = wb.getSheetAt(0);
			Row row = null;
			Record user = null;
			for (int i = 0; i < list.size(); i++) { // 从第3行开始
				row = sheet.createRow(i+2);
				user = list.get(i);
				// 归属公司
				ExcelUtils.addCell(wb, row, 0, user.getStr("company_name"), ExcelUtils.ALIGN_CENTER, styles, String.class);
				// 归属部门
				ExcelUtils.addCell(wb, row, 1, user.getStr("office_name"), ExcelUtils.ALIGN_CENTER, styles, String.class);
				// 登录名
				ExcelUtils.addCell(wb, row, 2, user.getStr("login_name"), ExcelUtils.ALIGN_CENTER, styles, String.class);
				// 姓名
				ExcelUtils.addCell(wb, row, 3, user.getStr("name"), ExcelUtils.ALIGN_CENTER, styles, String.class);
				// 工号
				ExcelUtils.addCell(wb, row, 4, user.getStr("no"), ExcelUtils.ALIGN_CENTER, styles, String.class);
				// 邮箱
				ExcelUtils.addCell(wb, row, 5, user.getStr("email"), ExcelUtils.ALIGN_LEFT, styles, String.class);
				// 电话
				ExcelUtils.addCell(wb, row, 6, user.getStr("phone"), ExcelUtils.ALIGN_LEFT, styles, String.class);
				// 手机
				ExcelUtils.addCell(wb, row, 7, user.getStr("mobile"), ExcelUtils.ALIGN_LEFT, styles, String.class);
				// 创建时间
				ExcelUtils.addCell(wb, row, 8, user.getDate("create_date"), ExcelUtils.ALIGN_CENTER, styles, Date.class);
				// 最后登录IP
				ExcelUtils.addCell(wb, row, 9, user.getStr("login_ip"), ExcelUtils.ALIGN_CENTER, styles, String.class);
				// 最后登录日期
				ExcelUtils.addCell(wb, row, 10, user.getDate("login_date"), ExcelUtils.ALIGN_CENTER, styles, Date.class);
				// 角色
				ExcelUtils.addCell(wb, row, 11, user.getStr("role_names"), ExcelUtils.ALIGN_LEFT, styles, String.class);
				// 备注
				ExcelUtils.addCell(wb, row, 12, user.getStr("remarks"), ExcelUtils.ALIGN_LEFT, styles, String.class);
			}
			os = new FileOutputStream(excelFile);
			wb.write(os);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return excelFile;
	}
	
	/**
	 * 下载导入用户数据模板
	 */
	public void importTemplate() {
		String templateName = PathKit.getRootClassPath() + PropKit.get("excel_template") + File.separator + "sys_user_import.xlsx";
		File templateFile = new File(templateName);
		File excelFile = new File(PathKit.getWebRootPath()+ File.separator + "temp" + File.separator + "用户数据导入模板.xlsx");
		FileUtils.safeMkdir(excelFile); // 检查文件夹是否存在，不存在则先创建
		FileUtils.copyFile(templateFile, excelFile);
		this.render(new TempFileRender(excelFile));
	}
	
	/**
	 * 导入
	 */
	public void importExcel() {
		UploadFile uploadFile = this.getFile("file");
		File file = null;
		if (uploadFile != null) {
			List<Record> list = new ArrayList<Record>();
			InputStream is = null;
			XSSFWorkbook wb = null;
			try {
				file = uploadFile.getFile();
				is = new FileInputStream(file);
				wb = new XSSFWorkbook(is);
				XSSFSheet sheet = wb.getSheetAt(0);
				Row row = null;
				Record record = null;
				for (int i = 0; i < sheet.getLastRowNum(); i++) { // 从第3行开始
					row = sheet.getRow(i+2);
					if (row==null || row.getCell(0)==null || StrKit.isBlank(row.getCell(0).toString())) {
						continue;
					}
					record = new Record();
					
					list.add(record);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			// 删除导入文件
			file.delete();
			
			// 数据验证与完善
		} else {
			
		}
	}
	
	/**
	 * 封装用户参数
	 * @return
	 */
	private Record encapsulateUser() {
		Long companyId = this.getParaToLong("company_id");
		String companyName = this.getPara("company_name");
		Long officeId = this.getParaToLong("office_id");
		String officeName = this.getPara("office_name");
		String loginName = this.getPara("login_name");
		String name = this.getPara("name");
		String loginFlag = this.getPara("login_flag");
		Long roleId = this.getParaToLong("role_id");
		
		Record user = new Record(); // 用户
		user.set("company_id", companyId);
		user.set("company_name", companyName);
		user.set("office_id", officeId);
		user.set("office_name", officeName);
		user.set("login_name", loginName);
		user.set("name", name);
		user.set("login_flag", loginFlag);
		user.set("role_id", roleId);
		return user;
	}
	
}
