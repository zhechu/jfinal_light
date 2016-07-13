package com.ugiant.modules.sys.baseModel;


import com.jfinal.plugin.activerecord.IBean;
import com.ugiant.common.model.BaseModel;

@SuppressWarnings("serial")
public abstract class BaseMenu<M extends BaseMenu<M>> extends BaseModel<M> implements IBean {

    public final static String TABLE_NAME = "sys_menu";

    public void setId(Long id) {
        set("id", id);
    }

    public Long getId() {
        return get("id");
    }

    public void setParentId(Long parentId) {
        set("parent_id", parentId);
    }

    public Long getParentId() {
        return get("parent_id");
    }

    public void setParentIds(String parentIds) {
        set("parent_ids", parentIds);
    }

    public String getParentIds() {
        return get("parent_ids");
    }

    public void setName(String name) {
        set("name", name);
    }

    public String getName() {
        return get("name");
    }

    public void setSort(java.math.BigDecimal sort) {
        set("sort", sort);
    }

    public java.math.BigDecimal getSort() {
        return get("sort");
    }

    public void setHref(String href) {
        set("href", href);
    }

    public String getHref() {
        return get("href");
    }

    public void setTarget(String target) {
        set("target", target);
    }

    public String getTarget() {
        return get("target");
    }

    public void setIcon(String icon) {
        set("icon", icon);
    }

    public String getIcon() {
        return get("icon");
    }

    public void setIsShow(String isShow) {
        set("is_show", isShow);
    }

    public String getIsShow() {
        return get("is_show");
    }

    public void setPermission(String permission) {
        set("permission", permission);
    }

    public String getPermission() {
        return get("permission");
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