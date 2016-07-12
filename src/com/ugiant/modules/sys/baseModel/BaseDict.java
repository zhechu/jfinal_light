package com.ugiant.modules.sys.baseModel;


import com.jfinal.plugin.activerecord.IBean;
import com.ugiant.common.model.BaseModel;

@SuppressWarnings("serial")
public abstract class BaseDict<M extends BaseDict<M>> extends BaseModel<M> implements IBean {

    public final static String TABLE_NAME = "sys_dict";

    public void setId(String id) {
        set("id", id);
    }

    public Long getId(String id) {
        return get("id");
    }

    public void setValue(String value) {
        set("value", value);
    }

    public String getValue(String value) {
        return get("value");
    }

    public void setLabel(String label) {
        set("label", label);
    }

    public String getLabel(String label) {
        return get("label");
    }

    public void setType(String type) {
        set("type", type);
    }

    public String getType(String type) {
        return get("type");
    }

    public void setDescription(String description) {
        set("description", description);
    }

    public String getDescription(String description) {
        return get("description");
    }

    public void setSort(String sort) {
        set("sort", sort);
    }

    public java.math.BigDecimal getSort(String sort) {
        return get("sort");
    }

    public void setParentId(String parentId) {
        set("parent_id", parentId);
    }

    public Long getParentId(String parentId) {
        return get("parent_id");
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