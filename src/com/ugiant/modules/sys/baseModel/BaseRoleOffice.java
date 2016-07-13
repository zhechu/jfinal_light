package com.ugiant.modules.sys.baseModel;


import com.jfinal.plugin.activerecord.IBean;
import com.ugiant.common.model.BaseModel;

@SuppressWarnings("serial")
public abstract class BaseRoleOffice<M extends BaseRoleOffice<M>> extends BaseModel<M> implements IBean {

    public final static String TABLE_NAME = "sys_role_office";

    public void setRoleId(Long roleId) {
        set("role_id", roleId);
    }

    public Long getRoleId() {
        return get("role_id");
    }

    public void setOfficeId(Long officeId) {
        set("office_id", officeId);
    }

    public Long getOfficeId() {
        return get("office_id");
    }

}