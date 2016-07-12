package com.ugiant.modules.sys.baseModel;


import com.jfinal.plugin.activerecord.IBean;
import com.ugiant.common.model.BaseModel;

@SuppressWarnings("serial")
public abstract class BaseOffice<M extends BaseOffice<M>> extends BaseModel<M> implements IBean {

    public final static String TABLE_NAME = "sys_office";

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

    public void setAreaId(String areaId) {
        set("area_id", areaId);
    }

    public Long getAreaId(String areaId) {
        return get("area_id");
    }

    public void setCode(String code) {
        set("code", code);
    }

    public String getCode(String code) {
        return get("code");
    }

    public void setType(String type) {
        set("type", type);
    }

    public String getType(String type) {
        return get("type");
    }

    public void setGrade(String grade) {
        set("grade", grade);
    }

    public String getGrade(String grade) {
        return get("grade");
    }

    public void setAddress(String address) {
        set("address", address);
    }

    public String getAddress(String address) {
        return get("address");
    }

    public void setZipCode(String zipCode) {
        set("zip_code", zipCode);
    }

    public String getZipCode(String zipCode) {
        return get("zip_code");
    }

    public void setMaster(String master) {
        set("master", master);
    }

    public Long getMaster(String master) {
        return get("master");
    }

    public void setPhone(String phone) {
        set("phone", phone);
    }

    public String getPhone(String phone) {
        return get("phone");
    }

    public void setFax(String fax) {
        set("fax", fax);
    }

    public String getFax(String fax) {
        return get("fax");
    }

    public void setEmail(String email) {
        set("email", email);
    }

    public String getEmail(String email) {
        return get("email");
    }

    public void setUseable(String useable) {
        set("useable", useable);
    }

    public String getUseable(String useable) {
        return get("useable");
    }

    public void setPrimaryPerson(String primaryPerson) {
        set("primary_person", primaryPerson);
    }

    public Long getPrimaryPerson(String primaryPerson) {
        return get("primary_person");
    }

    public void setDeputyPerson(String deputyPerson) {
        set("deputy_person", deputyPerson);
    }

    public Long getDeputyPerson(String deputyPerson) {
        return get("deputy_person");
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