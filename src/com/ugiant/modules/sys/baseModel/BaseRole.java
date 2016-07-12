package com.ugiant.modules.sys.baseModel;


import com.jfinal.plugin.activerecord.IBean;
import com.ugiant.common.model.BaseModel;

@SuppressWarnings("serial")
public abstract class BaseRole<M extends BaseRole<M>> extends BaseModel<M> implements IBean {

    public final static String TABLE_NAME = "sys_role";

    public void setId(String id) {
        set("id", id);
    }

    public Long getId(String id) {
        return get("id");
    }

    public void setOfficeId(String officeId) {
        set("office_id", officeId);
    }

    public Long getOfficeId(String officeId) {
        return get("office_id");
    }

    public void setName(String name) {
        set("name", name);
    }

    public String getName(String name) {
        return get("name");
    }

    public void setEnname(String enname) {
        set("enname", enname);
    }

    public String getEnname(String enname) {
        return get("enname");
    }

    public void setRoleType(String roleType) {
        set("role_type", roleType);
    }

    public String getRoleType(String roleType) {
        return get("role_type");
    }

    public void setDataScope(String dataScope) {
        set("data_scope", dataScope);
    }

    public String getDataScope(String dataScope) {
        return get("data_scope");
    }

    public void setIsSys(String isSys) {
        set("is_sys", isSys);
    }

    public String getIsSys(String isSys) {
        return get("is_sys");
    }

    public void setUseable(String useable) {
        set("useable", useable);
    }

    public String getUseable(String useable) {
        return get("useable");
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

}