package com.ugiant.modules.sys.baseModel;


import com.jfinal.plugin.activerecord.IBean;
import com.ugiant.common.model.BaseModel;

@SuppressWarnings("serial")
public abstract class BaseUserRole<M extends BaseUserRole<M>> extends BaseModel<M> implements IBean {

    public final static String TABLE_NAME = "sys_user_role";

    public void setUserId(Long userId) {
        set("user_id", userId);
    }

    public Long getUserId() {
        return get("user_id");
    }

    public void setRoleId(Long roleId) {
        set("role_id", roleId);
    }

    public Long getRoleId() {
        return get("role_id");
    }

}