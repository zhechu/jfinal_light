package com.ugiant.modules.sys.baseModel;

import com.jfinal.plugin.activerecord.IBean;
import com.ugiant.common.model.BaseModel;

@SuppressWarnings("serial")
public abstract class BaseUser<M extends BaseUser<M>> extends BaseModel<M> implements IBean {

	protected String roleNames;
	
	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public void setName(String name) {
		set("name", name);
	}
	
	public String getName() {
		return get("name");
	}
	
}
