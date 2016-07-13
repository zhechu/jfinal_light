package com.ugiant.modules.sys.baseModel;


import com.jfinal.plugin.activerecord.IBean;
import com.ugiant.common.model.BaseModel;

@SuppressWarnings("serial")
public abstract class BaseRoleMenu<M extends BaseRoleMenu<M>> extends BaseModel<M> implements IBean {

    public final static String TABLE_NAME = "sys_role_menu";

    public void setRoleId(Long roleId) {
        set("role_id", roleId);
    }

    public Long getRoleId() {
        return get("role_id");
    }

    public void setMenuId(Long menuId) {
        set("menu_id", menuId);
    }

    public Long getMenuId() {
        return get("menu_id");
    }

}