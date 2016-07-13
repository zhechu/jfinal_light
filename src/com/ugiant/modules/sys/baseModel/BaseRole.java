package com.ugiant.modules.sys.baseModel;


import com.jfinal.plugin.activerecord.IBean;
import com.ugiant.common.model.BaseModel;

@SuppressWarnings("serial")
public abstract class BaseRole<M extends BaseRole<M>> extends BaseModel<M> implements IBean {

    public final static String TABLE_NAME = "sys_role";

    public void setId(Long id) {
        set("id", id);
    }

    public Long getId() {
        return get("id");
    }

    public void setOfficeId(Long officeId) {
        set("office_id", officeId);
    }

    public Long getOfficeId() {
        return get("office_id");
    }

    public void setName(String name) {
        set("name", name);
    }

    public String getName() {
        return get("name");
    }

    public void setEnname(String enname) {
        set("enname", enname);
    }

    public String getEnname() {
        return get("enname");
    }

    public void setRoleType(String roleType) {
        set("role_type", roleType);
    }

    public String getRoleType() {
        return get("role_type");
    }

    public void setDataScope(String dataScope) {
        set("data_scope", dataScope);
    }

    public String getDataScope() {
        return get("data_scope");
    }

    public void setIsSys(String isSys) {
        set("is_sys", isSys);
    }

    public String getIsSys() {
        return get("is_sys");
    }

    public void setUseable(String useable) {
        set("useable", useable);
    }

    public String getUseable() {
        return get("useable");
    }

    public void setCreateBy(Long createBy) {
        set("create_by", createBy);
    }

    public Long getCreateBy() {
        return get("create_by");
    }

    public void setCreateDate(java.util.Date createDate) {
        set("create_date", createDate);
    }

    public java.util.Date getCreateDate() {
        return get("create_date");
    }

    public void setUpdateBy(Long updateBy) {
        set("update_by", updateBy);
    }

    public Long getUpdateBy() {
        return get("update_by");
    }

    public void setUpdateDate(java.util.Date updateDate) {
        set("update_date", updateDate);
    }

    public java.util.Date getUpdateDate() {
        return get("update_date");
    }

    public void setRemarks(String remarks) {
        set("remarks", remarks);
    }

    public String getRemarks() {
        return get("remarks");
    }

}