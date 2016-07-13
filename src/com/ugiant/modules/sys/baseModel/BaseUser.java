package com.ugiant.modules.sys.baseModel;


import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.jfinal.plugin.activerecord.IBean;
import com.ugiant.common.model.BaseModel;
import com.ugiant.common.utils.Collections3;
import com.ugiant.common.utils.excel.ExportExcel;
import com.ugiant.common.utils.excel.annotation.ExcelField;
import com.ugiant.common.utils.excel.fieldtype.RoleListType;
import com.ugiant.modules.sys.model.Office;
import com.ugiant.modules.sys.model.Role;

@SuppressWarnings("serial")
public abstract class BaseUser<M extends BaseUser<M>> extends BaseModel<M> implements IBean {

    public final static String TABLE_NAME = "sys_user";
   
    private Office company;	// 归属公司
	private Office office;	// 归属部门

	private List<Role> roleList = Lists.newArrayList(); // 拥有角色列表
	
	private List<Long> roleIdList = Lists.newArrayList(); // 角色 ids
	
	private Long roleId; // 角色 id，用于查询

	@ExcelField(title="归属公司", value="company.name", align=ExportExcel.ALIGN_CENTER, sort=20)
    public Office getCompany() {
    	if (company == null) {
    		company = Office.dao.findById(getCompanyId());
    	}
    	return company;
    }
    
	public void setCompany(Office company) {
		this.company = company;
		if (this.company != null) {
			setCompanyId(this.company.getId());
		}
	}
	
	@ExcelField(title="归属部门", value="office.name", align=ExportExcel.ALIGN_CENTER, sort=25)
    public Office getOffice() {
    	if (office == null) {
    		office = Office.dao.findById(getOfficeId());
    	}
    	return office;
    }

	public void setOffice(Office office) {
		this.office = office;
		if (this.office != null) {
			setOfficeId(this.office.getId());
		}
	}

	/**
	 * 用户拥有的角色名称字符串, 多个角色名称用','分隔.
	 */
	public String getRoleNames() {
		return Collections3.extractToString(getRoleList(), "name", ",");
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	@ExcelField(title="角色", align=ExportExcel.ALIGN_LEFT, sort=800, fieldType=RoleListType.class)
	public List<Role> getRoleList() {
		if (roleList!=null && roleList.isEmpty()) {
			roleList.addAll(Role.dao.findByUserId(getId()));
		}
		return roleList;
	}
	
	public void setRoleIdList(List<Long> roleIdList) {
		this.roleIdList = roleIdList;
	}
	
	public List<Long> getRoleIdList() {
		if (roleList!=null && roleIdList.isEmpty()) {
			for (Role role : getRoleList()) {
				roleIdList.add(role.getId());
			}
		}
		return roleIdList;
	}
	
    public void setId(Long id) {
        set("id", id);
    }

    public Long getId() {
        return get("id");
    }

    public void setCompanyId(Long companyId) {
        set("company_id", companyId);
    }

    public Long getCompanyId() {
        return get("company_id");
    }

    public void setOfficeId(Long officeId) {
        set("office_id", officeId);
    }

    public Long getOfficeId() {
        return get("office_id");
    }

    public void setLoginName(String loginName) {
        set("login_name", loginName);
    }

    @ExcelField(title="登录名", align=ExportExcel.ALIGN_CENTER, sort=30)
    public String getLoginName() {
        return get("login_name");
    }

    public void setPassword(String password) {
        set("password", password);
    }

    public String getPassword() {
        return get("password");
    }

    public void setNo(String no) {
        set("no", no);
    }

    @ExcelField(title="工号", align=ExportExcel.ALIGN_CENTER, sort=45)
    public String getNo() {
        return get("no");
    }

    public void setName(String name) {
        set("name", name);
    }

    @ExcelField(title="姓名", align=ExportExcel.ALIGN_CENTER, sort=40)
    public String getName() {
        return get("name");
    }

    public void setEmail(String email) {
        set("email", email);
    }

    @ExcelField(title="邮箱", align=ExportExcel.ALIGN_LEFT, sort=50)
    public String getEmail() {
        return get("email");
    }

    public void setPhone(String phone) {
        set("phone", phone);
    }

    @ExcelField(title="电话", align=ExportExcel.ALIGN_CENTER, sort=60)
    public String getPhone() {
        return get("phone");
    }

    public void setMobile(String mobile) {
        set("mobile", mobile);
    }

    @ExcelField(title="手机", align=ExportExcel.ALIGN_CENTER, sort=70)
    public String getMobile() {
        return get("mobile");
    }

    public void setUserType(String userType) {
        set("user_type", userType);
    }

    public String getUserType() {
        return get("user_type");
    }

    public void setPhoto(String photo) {
        set("photo", photo);
    }

    public String getPhoto() {
        return get("photo");
    }

    public void setLoginIp(String loginIp) {
        set("login_ip", loginIp);
    }

    @ExcelField(title="最后登录IP", type=1, align=ExportExcel.ALIGN_CENTER, sort=100)
    public String getLoginIp() {
        return get("login_ip");
    }

    public void setLoginDate(Date loginDate) {
        set("login_date", loginDate);
    }

    @ExcelField(title="最后登录日期", type=1, align=ExportExcel.ALIGN_CENTER, sort=110)
    public java.util.Date getLoginDate() {
        return get("login_date");
    }

    public void setLoginFlag(String loginFlag) {
        set("login_flag", loginFlag);
    }

    public String getLoginFlag() {
        return get("login_flag");
    }

    public void setCreateBy(Long createBy) {
        set("create_by", createBy);
    }

    public Long getCreateBy() {
        return get("create_by");
    }

    public void setCreateDate(Date createDate) {
        set("create_date", createDate);
    }

    @ExcelField(title="创建时间", type=0, align=ExportExcel.ALIGN_CENTER, sort=90)
    public java.util.Date getCreateDate() {
        return get("create_date");
    }

    public void setUpdateBy(Long updateBy) {
        set("update_by", updateBy);
    }

    public Long getUpdateBy() {
        return get("update_by");
    }

    public void setUpdateDate(Date updateDate) {
        set("update_date", updateDate);
    }

    public java.util.Date getUpdateDate() {
        return get("update_date");
    }

    public void setRemarks(String remarks) {
        set("remarks", remarks);
    }

    @ExcelField(title="备注", align=ExportExcel.ALIGN_LEFT, sort=900)
    public String getRemarks() {
        return get("remarks");
    }

    public void setOldLoginIp(String oldLoginIp) {
        set("old_login_ip", oldLoginIp);
    }

    public String getOldLoginIp() {
        return get("old_login_ip");
    }

    public void setOldLoginDate(Date oldLoginDate) {
        set("old_login_date", oldLoginDate);
    }

    public java.util.Date getOldLoginDate() {
        return get("old_login_date");
    }

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

}