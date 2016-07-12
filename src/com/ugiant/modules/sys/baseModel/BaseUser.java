package com.ugiant.modules.sys.baseModel;


import com.jfinal.plugin.activerecord.IBean;
import com.ugiant.common.model.BaseModel;

@SuppressWarnings("serial")
public abstract class BaseUser<M extends BaseUser<M>> extends BaseModel<M> implements IBean {

    public final static String TABLE_NAME = "sys_user";

    public void setId(String id) {
        set("id", id);
    }

    public Long getId(String id) {
        return get("id");
    }

    public void setCompanyId(String companyId) {
        set("company_id", companyId);
    }

    public Long getCompanyId(String companyId) {
        return get("company_id");
    }

    public void setOfficeId(String officeId) {
        set("office_id", officeId);
    }

    public Long getOfficeId(String officeId) {
        return get("office_id");
    }

    public void setLoginName(String loginName) {
        set("login_name", loginName);
    }

    public String getLoginName(String loginName) {
        return get("login_name");
    }

    public void setPassword(String password) {
        set("password", password);
    }

    public String getPassword(String password) {
        return get("password");
    }

    public void setNo(String no) {
        set("no", no);
    }

    public String getNo(String no) {
        return get("no");
    }

    public void setName(String name) {
        set("name", name);
    }

    public String getName(String name) {
        return get("name");
    }

    public void setEmail(String email) {
        set("email", email);
    }

    public String getEmail(String email) {
        return get("email");
    }

    public void setPhone(String phone) {
        set("phone", phone);
    }

    public String getPhone(String phone) {
        return get("phone");
    }

    public void setMobile(String mobile) {
        set("mobile", mobile);
    }

    public String getMobile(String mobile) {
        return get("mobile");
    }

    public void setUserType(String userType) {
        set("user_type", userType);
    }

    public String getUserType(String userType) {
        return get("user_type");
    }

    public void setPhoto(String photo) {
        set("photo", photo);
    }

    public String getPhoto(String photo) {
        return get("photo");
    }

    public void setLoginIp(String loginIp) {
        set("login_ip", loginIp);
    }

    public String getLoginIp(String loginIp) {
        return get("login_ip");
    }

    public void setLoginDate(String loginDate) {
        set("login_date", loginDate);
    }

    public java.util.Date getLoginDate(String loginDate) {
        return get("login_date");
    }

    public void setLoginFlag(String loginFlag) {
        set("login_flag", loginFlag);
    }

    public String getLoginFlag(String loginFlag) {
        return get("login_flag");
    }

    public void setCreateBy(String createBy) {
        set("create_by", createBy);
    }

    public Long getCreateBy(String createBy) {
        return get("create_by");
    }

    public void setCreateDate(String createDate) {
        set("create_date", createDate);
    }

    public java.util.Date getCreateDate(String createDate) {
        return get("create_date");
    }

    public void setUpdateBy(String updateBy) {
        set("update_by", updateBy);
    }

    public Long getUpdateBy(String updateBy) {
        return get("update_by");
    }

    public void setUpdateDate(String updateDate) {
        set("update_date", updateDate);
    }

    public java.util.Date getUpdateDate(String updateDate) {
        return get("update_date");
    }

    public void setRemarks(String remarks) {
        set("remarks", remarks);
    }

    public String getRemarks(String remarks) {
        return get("remarks");
    }

    public void setOldLoginIp(String oldLoginIp) {
        set("old_login_ip", oldLoginIp);
    }

    public String getOldLoginIp(String oldLoginIp) {
        return get("old_login_ip");
    }

    public void setOldLoginDate(String oldLoginDate) {
        set("old_login_date", oldLoginDate);
    }

    public java.util.Date getOldLoginDate(String oldLoginDate) {
        return get("old_login_date");
    }

}