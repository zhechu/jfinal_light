package com.ugiant.modules.sys.baseModel;


import com.jfinal.plugin.activerecord.IBean;
import com.ugiant.common.model.BaseModel;

@SuppressWarnings("serial")
public abstract class BaseMenu<M extends BaseMenu<M>> extends BaseModel<M> implements IBean {

    public final static String TABLE_NAME = "sys_menu";

    public void setId(String id) {
        set("id", id);
    }

    public Long getId(String id) {
        return get("id");
    }

    public void setParentId(String parentId) {
        set("parent_id", parentId);
    }

    public Long getParentId(String parentId) {
        return get("parent_id");
    }

    public void setParentIds(String parentIds) {
        set("parent_ids", parentIds);
    }

    public String getParentIds(String parentIds) {
        return get("parent_ids");
    }

    public void setName(String name) {
        set("name", name);
    }

    public String getName(String name) {
        return get("name");
    }

    public void setSort(String sort) {
        set("sort", sort);
    }

    public java.math.BigDecimal getSort(String sort) {
        return get("sort");
    }

    public void setHref(String href) {
        set("href", href);
    }

    public String getHref(String href) {
        return get("href");
    }

    public void setTarget(String target) {
        set("target", target);
    }

    public String getTarget(String target) {
        return get("target");
    }

    public void setIcon(String icon) {
        set("icon", icon);
    }

    public String getIcon(String icon) {
        return get("icon");
    }

    public void setIsShow(String isShow) {
        set("is_show", isShow);
    }

    public String getIsShow(String isShow) {
        return get("is_show");
    }

    public void setPermission(String permission) {
        set("permission", permission);
    }

    public String getPermission(String permission) {
        return get("permission");
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