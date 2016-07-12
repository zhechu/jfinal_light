package com.ugiant.modules.sys.baseModel;


import com.jfinal.plugin.activerecord.IBean;
import com.ugiant.common.model.BaseModel;

@SuppressWarnings("serial")
public abstract class BaseUserRole<M extends BaseUserRole<M>> extends BaseModel<M> implements IBean {

    public final static String TABLE_NAME = "sys_user_role";

    public void setUserId(String userId) {
        set("user_id", userId);
    }

    public Long getUserId(String userId) {
        return get("user_id");
    }

    public void setRoleId(String roleId) {
        set("role_id", roleId);
    }

    public Long getRoleId(String roleId) {
        return get("role_id");
    }

}