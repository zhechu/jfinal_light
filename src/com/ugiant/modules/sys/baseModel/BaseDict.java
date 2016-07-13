package com.ugiant.modules.sys.baseModel;


import com.jfinal.plugin.activerecord.IBean;
import com.ugiant.common.model.BaseModel;

@SuppressWarnings("serial")
public abstract class BaseDict<M extends BaseDict<M>> extends BaseModel<M> implements IBean {

    public final static String TABLE_NAME = "sys_dict";

    public void setId(Long id) {
        set("id", id);
    }

    public Long getId() {
        return get("id");
    }

    public void setValue(String value) {
        set("value", value);
    }

    public String getValue() {
        return get("value");
    }

    public void setLabel(String label) {
        set("label", label);
    }

    public String getLabel() {
        return get("label");
    }

    public void setType(String type) {
        set("type", type);
    }

    public String getType() {
        return get("type");
    }

    public void setDescription(String description) {
        set("description", description);
    }

    public String getDescription() {
        return get("description");
    }

    public void setSort(java.math.BigDecimal sort) {
        set("sort", sort);
    }

    public java.math.BigDecimal getSort() {
        return get("sort");
    }

    public void setParentId(Long parentId) {
        set("parent_id", parentId);
    }

    public Long getParentId() {
        return get("parent_id");
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